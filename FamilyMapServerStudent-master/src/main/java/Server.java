import java.io.*;
import java.net.*;
import java.util.UUID;

import com.sun.net.httpserver.*;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import handlerClasses.*;
import serviceClasses.Services;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;






        private void run(String portNumber) throws DatabaseException {

        System.out.println("Initializing HTTP Server");

        String port = "8080";
        if (portNumber == null){
            portNumber = port;
        }

        System.out.println("server listening on port" + portNumber);
        System.out.println("default port: " + port + "\n");

        HttpServer server;
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
        server.createContext("/event", new HandlerEvent());
        server.createContext("/fill", new HandlerFill());
        server.createContext("/load", new HandlerLoad());
        server.createContext("/user/login", new HandlerLogin());
        server.createContext("/person", new HandlerPerson());
        server.createContext("/user/register", new HandlerRegister());
        server.createContext("/", new Handler());


        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    public static void main(String[] args) throws DatabaseException {
        if(args.length!=0){
            System.out.println(args.length);
            String portNumber = args[0];
            new Server().run(portNumber);
        }
        else {
            new Server().run(null);
        }

    }
}
