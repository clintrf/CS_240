package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import serviceClasses.Services;
import serviceClasses.resultService.ResultsFill;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class HandlerFill implements HttpHandler {

    public Services services;

    public HandlerFill() throws DatabaseException {
        services = new Services();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsFill fillResult = services.getFillResult();
        EncoderDecoder coder = services.getCoder();

        try{
            String[] uri = httpExchange.getRequestURI().toString().split("/");
            String username = uri[2];
            if (uri.length>3){
                fillResult.fillResult(username, Integer.parseInt(uri[3]));
            }
            else{
                fillResult.fillResult(username, 4);
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

