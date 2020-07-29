package handlerClasses;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import serviceClasses.resultService.ResultsEvent;
import serviceClasses.resultService.ResultsEvents;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class HandlerEvent implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsEvent eventResult;
        ResultsEvents eventsResult;
        EncoderDecoder coder;
        String auth_token = null;

        try{
            String[] uri = httpExchange.getRequestURI().toString().split("/");
            Headers reqHeaders = httpExchange.getRequestHeaders();

            if (reqHeaders.containsKey("Authorization")) {
                auth_token = reqHeaders.getFirst("Authorization");
            }
            coder = new EncoderDecoder();
            if (uri.length>2){
                String eventId = uri[2];
                eventResult = Handler.services.getEventResult();
                eventResult.eventResult(auth_token,eventId);
                if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    Handler.writeString(coder.encode(eventResult), respBody);
                    respBody.close();
                }
                if(!eventResult.getSuccess()){
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    httpExchange.getResponseBody().close();
                }
            }
            else{
                eventsResult = Handler.services.getEventsResult();
                eventsResult.eventsResult(auth_token);
                if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    Handler.writeString(coder.encode(eventsResult), respBody);
                    respBody.close();
                }
                if(!eventsResult.getSuccess()){
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    httpExchange.getResponseBody().close();
                }
            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
