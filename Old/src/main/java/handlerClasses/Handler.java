package handlerClasses;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import databaseClasses.DatabaseException;
import modelClasses.ModelUser;
import serviceClasses.Services;
import serviceClasses.requestService.RequestLoad;
import serviceClasses.requestService.RequestLogin;
import serviceClasses.requestService.RequestRegister;
import serviceClasses.resultService.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class Handler implements HttpHandler {


//    public static Services services;
//
//    static {
//        try {
//            services = new Services();
//        } catch (DatabaseException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String path = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/";
        String relative_path = "web" + httpExchange.getRequestURI().toString();
        if(httpExchange.getRequestURI().toString().equals("/")){
            relative_path = relative_path + "index.html";
        }
        String filepath = path + relative_path;
        Path file_path = FileSystems.getDefault().getPath(filepath);
        Path path_404 = FileSystems.getDefault().getPath(
                "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/web/HTML/404.html");
        try {
            httpExchange.sendResponseHeaders(200, 0);
            Files.copy(file_path, httpExchange.getResponseBody());
            httpExchange.getResponseBody().close();
        }catch(IOException ex){
            try {
                Files.copy(path_404, httpExchange.getResponseBody());
                httpExchange.getResponseBody().close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

//    public static String inStringReader(InputStream requestBody) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        InputStreamReader sr = new InputStreamReader(requestBody);
//        char[] buffer = new char[1024];
//        int temp = sr.read(buffer);
//        while (temp > 0) {
//            sb.append(buffer, 0, temp);
//            temp = sr.read(buffer);
//        }
//        return sb.toString();
//    }
//
//    public static void writeString(String str, OutputStream os) throws IOException {
//        OutputStreamWriter sw = new OutputStreamWriter(os);
//        sw.write(str);
//        sw.flush();
//    }
}
