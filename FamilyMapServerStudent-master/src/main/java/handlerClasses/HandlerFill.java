package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import serviceClasses.resultService.ResultsFill;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class HandlerFill implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsFill fillResult = Handler.services.getFillResult();
        EncoderDecoder coder;

        try{
            String[] uri = httpExchange.getRequestURI().toString().split("/");
            String username = uri[2];
            coder = new EncoderDecoder();
            if (uri.length>3){
                fillResult.fillResult(username, Integer.parseInt(uri[3]));
            }
            else{
                fillResult.fillResult(username, 4);
            }

            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                OutputStream respBody = httpExchange.getResponseBody();
                Handler.writeString(coder.encode(fillResult), respBody);
                respBody.close();
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}

