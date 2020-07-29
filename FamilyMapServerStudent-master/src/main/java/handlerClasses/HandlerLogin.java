package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import modelClasses.ModelUser;
import serviceClasses.requestService.RequestLogin;
import serviceClasses.resultService.ResultsLogin;
import serviceClasses.resultService.ResultsRegister;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class HandlerLogin implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsLogin loginResult = Handler.services.getLoginResult();
        RequestLogin loginRequest;
        EncoderDecoder coder;

        try {
            coder = Handler.services.getCoder();
            loginRequest = (RequestLogin) coder.decode(
                            Handler.inStringReader(httpExchange.getRequestBody()));
            loginResult.loginResult(
                    coder.decodeToRequestLoad(
                            coder.encode(loginRequest)
                    )
            );

            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = httpExchange.getResponseBody();
                Handler.writeString(coder.encode(loginResult), respBody);
                respBody.close();
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
