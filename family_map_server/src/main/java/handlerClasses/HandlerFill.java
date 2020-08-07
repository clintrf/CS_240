package main.java.handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.serviceClasses.EncoderDecoder;
import main.java.serviceClasses.ResponseFill;
import main.java.serviceClasses.Services;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class HandlerFill implements HttpHandler {

    Services service;

    public HandlerFill(Services service) {
        this.service = service;
    }


    public void handle(HttpExchange exchange){
        EncoderDecoder coder = new EncoderDecoder();
        ResponseFill fillResponse;

        String [] uri;

        try {
            uri = exchange.getRequestURI().toString().split("/");

            if(uri.length > 3){
                fillResponse = service.fill(uri[2], Integer.parseInt(uri[3]));

            }else{
                fillResponse = service.fill(uri[2],4);
            }
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            writeString(coder.encodeResponseFill(fillResponse), respBody);
            respBody.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}