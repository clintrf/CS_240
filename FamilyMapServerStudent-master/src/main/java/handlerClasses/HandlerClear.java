package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import serviceClasses.Services;
import serviceClasses.resultService.ResultsClear;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class HandlerClear implements HttpHandler {

    public Services services;

    public HandlerClear() throws DatabaseException {
        services = new Services();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsClear clearResult;
        EncoderDecoder coder;
        clearResult = services.getClearResult();
        clearResult.clearResult();
        coder = services.getCoder();
        System.out.println(clearResult.getSuccess());
        try {
            System.out.println(clearResult.getSuccess());
            if(!clearResult.getSuccess()){
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                httpExchange.getResponseBody().close();
                return;
            }
            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                OutputStream respBody = httpExchange.getResponseBody();
                writeString(coder.encode(clearResult), respBody);
                respBody.close();
            }

//            if(!clearResult.getSuccess()){
//                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                httpExchange.getResponseBody().close();
//            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            httpExchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
