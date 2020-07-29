package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseDatabase;
import serviceClasses.resultService.ResultsClear;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class HandlerClear implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsClear clearResult;
        EncoderDecoder coder;
        try {
            clearResult = Handler.services.getClearResult();
            coder = Handler.services.getCoder();

            if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                OutputStream respBody = httpExchange.getResponseBody();
                Handler.writeString(coder.encode(clearResult), respBody);
                respBody.close();
            }
            if(!clearResult.getSuccess()){
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
