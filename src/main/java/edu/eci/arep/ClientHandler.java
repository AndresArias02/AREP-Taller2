package edu.eci.arep;

import edu.eci.arep.services.RestService;
import java.io.*;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream())) {

            String requestLine = in.readLine();
            if (requestLine == null) return;

            String[] tokens = requestLine.split(" ");
            String method = tokens[0];
            String URI = tokens[1];
            printRequestHeader(requestLine, in);

            handleRequest(method, URI, out, dataOut);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest(String method, String URI, PrintWriter out, BufferedOutputStream dataOut) throws IOException {
        System.out.println("Handling request: " + method + " " + URI);

        if (method.equals("GET") && !URI.startsWith("/app")) {
            handleGetRequest(URI, out, dataOut);
        } else {
            try {
                String responseBody = callService(URI, method);
                int contentLength = responseBody.getBytes().length;

                out.print("HTTP/1.1 200 OK\r\n");
                out.print("Content-Type: application/json\r\n");
                out.print("Content-Length: " + contentLength + "\r\n");
                out.print("\r\n");
                out.print(responseBody);
                out.flush();
            } catch (Exception e) {
                out.print("HTTP/1.1 500 Internal Server Error\r\n");
                out.print("Content-Type: text/html\r\n");
                out.print("\r\n");
                out.print("<html><body><h1>Internal Server Error</h1></body></html>");
                out.flush();
                e.printStackTrace();
            }
        }
    }

    private void handleGetRequest(String fileRequested, PrintWriter out, BufferedOutputStream dataOut) throws IOException {
        File file = new File(HttpServer.WEB_ROOT, fileRequested);
        int fileLength = (int) file.length();
        String content = getContentType(fileRequested);

        if (file.exists()) {
            byte[] fileData = readFileData(file, fileLength);
            out.print("HTTP/1.1 200 OK\r\n");
            out.print("Content-type: " + content + "\r\n");
            out.print("Content-length: " + fileLength + "\r\n");
            out.print("\r\n");
            out.flush();
            dataOut.write(fileData, 0, fileLength);
            dataOut.flush();
        } else {
            out.print("HTTP/1.1 404 Not Found\r\n");
            out.print("Content-type: text/html\r\n");
            out.print("\r\n");
            out.flush();
            out.print("<html><body><h1>File Not Found</h1></body></html>");
            out.flush();
        }
    }

    private String callService(String URI, String method) {
        RestService restService = SparkServer.findHandler(cleanURI(URI), method);
        return restService.response(URI);
    }

    private String cleanURI(String URI) {
        int queryStartIndex = URI.indexOf('?');
        if (queryStartIndex != -1) {
            return URI.substring(0, queryStartIndex);
        }
        return URI;
    }


    private void printRequestHeader(String requestLine, BufferedReader in) throws IOException {
        System.out.println("Request line: " + requestLine);
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Header: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }
    }

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".html")) return "text/html";
        else if (fileRequested.endsWith(".css")) return "text/css";
        else if (fileRequested.endsWith(".js")) return "application/javascript";
        else if (fileRequested.endsWith(".png")) return "image/png";
        else if (fileRequested.endsWith(".jpg")) return "image/jpeg";
        return "text/plain";
    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        byte[] fileData = new byte[fileLength];
        try (FileInputStream fileIn = new FileInputStream(file)) {
            fileIn.read(fileData);
        }
        return fileData;
    }
}
