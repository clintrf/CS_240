package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import org.apache.commons.io.IOUtils;
import serviceClasses.Services;
import serviceClasses.RequestLogin;
import serviceClasses.ResponseLogin;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class HandlerLogin implements HttpHandler {

    public Services services;

    public HandlerLogin(Services services) throws DatabaseException {
        this.services = services;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResponseLogin loginResult = new ResponseLogin(services.database);
        RequestLogin loginRequest;
        EncoderDecoder coder = new EncoderDecoder();

        try {
            loginRequest = coder.decodeToRequestLogin(
                            //inStringReader(httpExchange.getRequestBody())
                    IOUtils.toString(httpExchange.getRequestBody(), StandardCharsets.UTF_8)
            );
            loginResult.loginResult(
                    coder.decodeToRequestLogin(
                            coder.encode(loginRequest)
                    )
            );

            if (httpExchange.getRequestMethod().toLowerCase().equals("post") && loginResult.getSuccess()) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = httpExchange.getResponseBody();
                writeString(coder.encode(loginResult), respBody);
                respBody.close();
            }
            else{
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                httpExchange.getResponseBody().close();
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

    public static String inStringReader(InputStream requestBody) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(requestBody);
        char[] buffer = new char[1024];
        int temp = sr.read(buffer);
        while (temp > 0) {
            sb.append(buffer, 0, temp);
            temp = sr.read(buffer);
        }
        return sb.toString();
    }

}
