package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import org.apache.commons.io.IOUtils;
import serviceClasses.Services;
import serviceClasses.RequestRegister;
import serviceClasses.ResponseRegister;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class HandlerRegister implements HttpHandler {

    public Services services;

    public HandlerRegister(Services services) throws DatabaseException {
        this.services = services;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResponseRegister registerResult = new ResponseRegister(services.database);
        EncoderDecoder coder = new EncoderDecoder();

        try{
            RequestRegister registerRequest = coder.decodeToRequestRegister(
                    //inStringReader(httpExchange.getRequestBody())
                    IOUtils.toString(httpExchange.getRequestBody(), StandardCharsets.UTF_8)
            );
            registerResult.registerResult(registerRequest);
            String send_back = coder.encode(registerResult);
            System.out.println("here");
            if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = httpExchange.getResponseBody();
                writeString(send_back, respBody);
                respBody.close();
            }
        } catch (IOException ex){
            ex.printStackTrace();
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

        int BUFFER_SIZE=1024;
        char[] buffer = new char[BUFFER_SIZE]; // or some other size,
        int charsRead = 0;
        while ( (charsRead  = sr.read(buffer, 0, BUFFER_SIZE)) != -1) {
            sb.append(buffer, 0, charsRead);
        }

//        int temp = sr.read(buffer);
//        while (temp > 0) {
//            sb.append(buffer, 0, temp);
//            temp = sr.read(buffer);
//        }
        return sb.toString();
    }
}
