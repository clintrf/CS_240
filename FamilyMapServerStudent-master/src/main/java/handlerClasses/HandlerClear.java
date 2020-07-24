package handlerClasses;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import serviceClasses.resultService.ResultsClear;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class HandlerClear implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            ResultsClear clear = new ResultsClear();
            if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                OutputStream respBody = httpExchange.getResponseBody();
                HandlerDefault.writeString(clear.getMessage(), respBody);
                respBody.close();
            }
            if(!clear.getSuccess()){
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                httpExchange.getResponseBody().close();
            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
