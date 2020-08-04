package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import serviceClasses.Services;
import serviceClasses.requestService.RequestRegister;
import serviceClasses.resultService.ResultsRegister;

import java.io.*;
import java.net.HttpURLConnection;

public class HandlerRegister implements HttpHandler {

    public Services services;

    public HandlerRegister() throws DatabaseException {
        services = new Services();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResultsRegister registerResult = services.getRegisterResult();
        EncoderDecoder coder = services.getCoder();

        try{
            RequestRegister registerRequest = coder.decodeToRequestRegister(
                    inStringReader(httpExchange.getRequestBody())
            );
            registerResult.registerResult(registerRequest);

            String send_back = coder.encode(registerResult);

            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = httpExchange.getResponseBody();
                writeString(send_back, respBody);
                respBody.close();
            }
        } catch (NumberFormatException e){
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
