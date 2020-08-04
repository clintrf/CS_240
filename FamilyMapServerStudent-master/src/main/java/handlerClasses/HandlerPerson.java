package handlerClasses;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import serviceClasses.Services;
import serviceClasses.ResponsePeople;
import serviceClasses.ResponsePerson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class HandlerPerson implements HttpHandler {

    public Services services;

    public HandlerPerson(Services services) throws DatabaseException {
        this.services = services;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResponsePerson personResult = new ResponsePerson(services.database);
        ResponsePeople peopleResult = new ResponsePeople(services.database);
        EncoderDecoder coder = new EncoderDecoder();
        String auth_token = null;

        try {
            String[] uri = httpExchange.getRequestURI().toString().split("/");
            Headers reqHeaders = httpExchange.getRequestHeaders();
            if (reqHeaders.containsKey("Authorization")) {
                auth_token = reqHeaders.getFirst("Authorization");
            }
            if(uri.length > 2){
                String personId = uri[2];
                personResult.personResult(auth_token,personId);
                if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    writeString(coder.encode(personResult), respBody);
                    respBody.close();
                }
                if(!personResult.getSuccess()){
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    httpExchange.getResponseBody().close();
                }
            }
            else{
                peopleResult.peopleResult(auth_token);
                if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    writeString(coder.encode(peopleResult), respBody);
                    respBody.close();
                }
                if(!peopleResult.getSuccess()){
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    httpExchange.getResponseBody().close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
