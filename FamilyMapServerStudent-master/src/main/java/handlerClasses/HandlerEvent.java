package handlerClasses;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import serviceClasses.resultService.ResultsClear;
import serviceClasses.resultService.ResultsEvent;
import serviceClasses.resultService.ResultsEvents;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class HandlerEvent implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsEvent event;
        ResultsEvents events;
        try{
            String[] uri = httpExchange.getRequestURI().toString().split("/");
            Headers reqHeaders = httpExchange.getRequestHeaders();
            String auth_token = null;
            if (reqHeaders.containsKey("Authorization")) {
                auth_token = reqHeaders.getFirst("Authorization");
            }
            if (uri.length>2){
                String eventId = uri[2];
                event = new ResultsEvent(auth_token,eventId);
                if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    HandlerDefault.writeString(event.getMessage(), respBody);
                    respBody.close();
                }
                if(!event.getSuccess()){
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    httpExchange.getResponseBody().close();
                }
            }
            else{
                events = new ResultsEvents(auth_token);
                if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    HandlerDefault.writeString(events.getMessage(), respBody);
                    respBody.close();
                }
                if(!events.getSuccess()){
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    httpExchange.getResponseBody().close();
                }
            }
        } catch (DatabaseException e) {
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
