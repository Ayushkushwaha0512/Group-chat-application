import java.net.*;
import java.io.*;

class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server() {
        try {
            server = new ServerSocket(777);
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting");
            socket = server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("Reader started");
            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Client : " + msg);
                }

            } catch (Exception e) {
                System.out.println("Connection closed");
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("Writer Started");
            try {
                while (true && !socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")) {
                        socket.close();
                        System.out.println("Connection closed");
                        break;
                    }

                }

            } catch (Exception e) {
                System.out.println("Connection closed");
            }

        };
        new Thread(r2).start();
    }

    public static void main(String args[]) {
        System.out.println("Server is starting");
        new Server();
    }

}
