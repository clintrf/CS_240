package main.java;


import java.io.*;
import java.net.*;
import java.lang.*;
import com.sun.net.httpserver.*;
import main.java.handlerClasses.*;
import main.java.serviceClasses.*;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;

    private static Services service = new Services();
    private static HttpServer server;

    public static void main(String[] args)  {

        System.out.println("Initializing HTTP Server");

        int port = 8080;
        System.out.println("Server running on port: " + port);

        try {
            server = HttpServer.create(
                    new InetSocketAddress(port),
                    MAX_WAITING_CONNECTIONS);
        }catch(IOException e){
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);
        server.createContext("/clear", new HandlerClear(service));
        server.createContext("/event", new HandlerEvent(service));
        server.createContext("/fill", new HandlerFill(service));
        server.createContext("/load", new HandlerLoad(service));
        server.createContext("/user/login", new HandlerLogin(service));
        server.createContext("/person", new HandlerPeople(service));
        server.createContext("/user/register", new HandlerRegister(service));
        server.createContext("/", new HandlerDefault());

        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

}