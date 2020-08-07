package main.java.handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.serviceClasses.EncoderDecoder;
import main.java.serviceClasses.ResponsePeople;
import main.java.serviceClasses.ResponsePerson;
import main.java.serviceClasses.Services;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class HandlerPeople implements HttpHandler {
    Services service;

    public HandlerPeople(Services service) {
        this.service = service;
    }

    public void handle(HttpExchange exchange) throws IOException {
        EncoderDecoder coder = new EncoderDecoder();
        ResponsePeople peopleResponse;
        ResponsePerson personResponse;
        String[] uri;
        String authToken;
        try {
            if (exchange.getRequestHeaders().containsKey("Authorization")) {
                authToken = exchange.getRequestHeaders().getFirst("Authorization");
            }
            else{
                authToken = null;
            }

            uri = exchange.getRequestURI().toString().split("/");
            if (uri.length > 2) {
                personResponse = service.person(authToken, uri[2]);
                if (personResponse.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(coder.encodeResponsePerson(personResponse), respBody);
                    respBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(coder.encodeResponsePerson(personResponse), respBody);
                    respBody.close();
                }

            } else {
                peopleResponse = service.people(authToken);
                if (peopleResponse.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(coder.encodeResponsePeople(peopleResponse), respBody);
                    respBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(coder.encodeResponsePeople(peopleResponse), respBody);
                    respBody.close();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}