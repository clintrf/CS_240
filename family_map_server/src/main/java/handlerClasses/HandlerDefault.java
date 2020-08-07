package main.java.handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class HandlerDefault implements HttpHandler {

    public void handle(HttpExchange exchange) {
        String string_home = "/home/clint/GITHUB/CS_240/family_map_server/web/";
        String string_404 = "HTML/404.html";
        String string_non_404;
        if(exchange.getRequestURI().toString().equals("/")) {
            string_non_404 = exchange.getRequestURI().toString() + "index.html";
        }
        else{
            string_non_404 = exchange.getRequestURI().toString();
        }

        Path path_non_404 = FileSystems.getDefault().getPath(string_home + string_non_404);
        Path path_404 = FileSystems.getDefault().getPath(string_home + string_404);
        try {
            if(exchange.getRequestURI().toString().contains("css")){
                exchange.sendResponseHeaders(200, 0);
                Files.copy(path_non_404, exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
            if(!exchange.getRequestURI().toString().equals("/")){
                exchange.sendResponseHeaders(404, 0);
                Files.copy(path_404, exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
            if(!exchange.getRequestURI().toString().equals("get")){
                exchange.sendResponseHeaders(200, 0);
                Files.copy(path_non_404, exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
            else{
                exchange.sendResponseHeaders(400, 0);
                Files.copy(path_non_404, exchange.getResponseBody());
                exchange.getResponseBody().close();
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}