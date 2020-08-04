package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import serviceClasses.Services;
import serviceClasses.ResponseFill;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class HandlerFill implements HttpHandler {

    public Services services;

    public HandlerFill(Services services) throws DatabaseException {
        this.services = services;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResponseFill fillResult = new ResponseFill(services.database);
        EncoderDecoder coder = new EncoderDecoder();

        try{
            String[] uri = httpExchange.getRequestURI().toString().split("/");
            String username = uri[2];
            if (uri.length>3){
                services.fillResponse(username, Integer.parseInt(uri[3]));
            }
            else{
                services.fillResponse(username, 4);
            }

            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                OutputStream respBody = httpExchange.getResponseBody();
                writeString(coder.encode(fillResult), respBody);
                respBody.close();
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}

