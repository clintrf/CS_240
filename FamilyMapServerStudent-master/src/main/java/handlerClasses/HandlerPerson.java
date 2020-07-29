package handlerClasses;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import serviceClasses.resultService.ResultsEvents;
import serviceClasses.resultService.ResultsPeople;
import serviceClasses.resultService.ResultsPerson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class HandlerPerson implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsPerson personResult = Handler.services.getPersonResult();
        ResultsPeople peopleResult = Handler.services.getPeopleResult();
        EncoderDecoder coder;
        String auth_token = null;

        try {
            String[] uri = httpExchange.getRequestURI().toString().split("/");
            Headers reqHeaders = httpExchange.getRequestHeaders();
            if (reqHeaders.containsKey("Authorization")) {
                auth_token = reqHeaders.getFirst("Authorization");
            }
            coder = new EncoderDecoder();
            if(uri.length > 2){
                String personId = uri[2];
                personResult.personResult(auth_token,personId);
                if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    Handler.writeString(coder.encode(personResult), respBody);
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
                    Handler.writeString(coder.encode(peopleResult), respBody);
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
}
