package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import serviceClasses.requestService.RequestRegister;
import serviceClasses.resultService.ResultsRegister;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class HandlerRegister implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsRegister registerResult = Handler.services.getRegisterResult();
        EncoderDecoder coder = Handler.services.getCoder();

        try{
            RequestRegister registerRequest = (RequestRegister) coder.decode(
                    Handler.inStringReader(httpExchange.getRequestBody())
            );
            registerResult.registerResult(registerRequest);

            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = httpExchange.getResponseBody();
                Handler.writeString(coder.encode(registerResult), respBody);
                respBody.close();
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
}
