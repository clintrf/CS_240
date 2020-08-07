package main.java.handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.serviceClasses.EncoderDecoder;
import main.java.serviceClasses.ResponseEvent;
import main.java.serviceClasses.ResponseEvents;
import main.java.serviceClasses.Services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class HandlerEvent implements HttpHandler {

    Services service;

    public HandlerEvent(Services service) {
        this.service = service;
    }

    public void handle(HttpExchange exchange){
        EncoderDecoder coder = new EncoderDecoder();
        ResponseEvent eventResponse;
        ResponseEvents eventsResponse;
        String authToken;
        String [] uri;
        try {
            if (exchange.getRequestHeaders().containsKey("Authorization")) {
                authToken = exchange.getRequestHeaders().getFirst("Authorization");
            }
            else{
                authToken = null;
            }
            uri = exchange.getRequestURI().toString().split("/");
            if (uri.length > 2) {
                eventResponse = service.event(authToken, uri[2]);
                if (eventResponse.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(coder.encodeResponseEvent(eventResponse), respBody);
                    respBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(coder.encodeResponseEvent(eventResponse), respBody);
                    respBody.close();
                }

            } else {
                eventsResponse = service.events(authToken);
                if (eventsResponse.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(coder.encodeResponseEvents(eventsResponse), respBody);
                    respBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(coder.encodeResponseEvents(eventsResponse), respBody);
                    respBody.close();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}