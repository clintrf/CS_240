package handlerClasses;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import serviceClasses.Services;
import serviceClasses.resultService.ResultsEvent;
import serviceClasses.resultService.ResultsEvents;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class HandlerEvent implements HttpHandler {

    public Services services;

    public HandlerEvent() throws DatabaseException {
        services = new Services();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsEvent eventResult;
        ResultsEvents eventsResult;
        EncoderDecoder coder = services.getCoder();
        String auth_token = null;

        try{
            String[] uri = httpExchange.getRequestURI().toString().split("/");
            Headers reqHeaders = httpExchange.getRequestHeaders();

            if (reqHeaders.containsKey("Authorization")) {
                auth_token = reqHeaders.getFirst("Authorization");
            }

            if (uri.length>2){
                String eventId = uri[2];
                eventResult = services.getEventResult();
                eventResult.eventResult(auth_token,eventId);
                if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    writeString(coder.encode(eventResult), respBody);
                    respBody.close();
                }
                if(!eventResult.getSuccess()){
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    httpExchange.getResponseBody().close();
                }
            }
            else{
                eventsResult = services.getEventsResult();
                eventsResult.eventsResult(auth_token);
                if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    writeString(coder.encode(eventsResult), respBody);
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

    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}