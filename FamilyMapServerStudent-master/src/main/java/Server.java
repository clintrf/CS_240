import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import handlerClasses.*;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;
    private String port = "8888";


    private void run(String portNumber) {

        System.out.println("Initializing HTTP Server");

        if (portNumber == null){
            portNumber = port;
        }

        System.out.println("server listening on port" + portNumber);
        System.out.println("default port: " + port + "\n");

        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating contexts");
        server.createContext("/clear", new HandlerClear());
        server.createContext("/event", new HandlerClear());
        server.createContext("/fill", new HandlerClear());
        server.createContext("/load", new HandlerClear());
        server.createContext("/user/login", new HandlerClear());
        server.createContext("/person", new HandlerClear());
        server.createContext("/user/register", new HandlerRegister());
        server.createContext("/", new HandlerDefault());


        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
