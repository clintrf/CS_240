package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import modelClasses.ModelUser;
import serviceClasses.requestService.RequestLoad;
import serviceClasses.resultService.ResultsLoad;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class HandlerLoad implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsLoad loadResult = Handler.services.getLoadResult();
        RequestLoad loadRequest;
        EncoderDecoder coder;

        try {
            coder = Handler.services.getCoder();
            loadRequest = (RequestLoad) coder.decode(
                    Handler.inStringReader(httpExchange.getRequestBody())
            );
            loadResult.loadResult(loadRequest);

            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = httpExchange.getResponseBody();
                Handler.writeString(coder.encode(loadResult), respBody);
                respBody.close();
            }
        }catch (NumberFormatException | DatabaseException e) {
                e.printStackTrace();
        }
    }
}
