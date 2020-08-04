package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import modelClasses.ModelUser;
import serviceClasses.Services;
import serviceClasses.requestService.RequestLoad;
import serviceClasses.resultService.ResultsLoad;

import java.io.*;
import java.net.HttpURLConnection;

public class HandlerLoad implements HttpHandler {

    public Services services;

    public HandlerLoad() throws DatabaseException {
        services = new Services();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        ResultsLoad loadResult = services.getLoadResult();
        EncoderDecoder coder = services.getCoder();

        RequestLoad loadRequest = coder.decodeToRequestLoad(
                inStringReader(httpExchange.getRequestBody())
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
