package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import org.apache.commons.io.IOUtils;
import serviceClasses.Services;
import serviceClasses.RequestLoad;
import serviceClasses.ResponseLoad;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
/*
public class HandlerLoad implements HttpHandler {

    public Services services;

    public HandlerLoad(Services services) throws DatabaseException {
        this.services = services;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        ResponseLoad loadResult = new ResponseLoad(services.database);
        EncoderDecoder coder = new EncoderDecoder();

        RequestLoad loadRequest = coder.decodeToRequestLoad(
                //inStringReader(httpExchange.getRequestBody())
                IOUtils.toString(httpExchange.getRequestBody(), StandardCharsets.UTF_8)
        );
        loadResult.loadResult(loadRequest);

        try {

            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = httpExchange.getResponseBody();
                writeString(coder.encode(loadResult), respBody);
                respBody.close();
            }
        }catch (NumberFormatException e) {
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
*/