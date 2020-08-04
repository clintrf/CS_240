package main.java;

import com.sun.net.httpserver.HttpExchange;


/**
 * Created by logan on 10/27/2017.
 */

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.lang.*;
import java.sql.SQLException;

import com.sun.net.httpserver.*;
import main.java.dataAccessClasses.DaoAuthToken;
import main.java.serviceClasses.*;
import org.apache.commons.io.IOUtils;

public class WebServer {

    private static final int MAX_WAITING_CONNECTIONS = 12;
    private static ServerFacade service = new ServerFacade();
    private static HttpServer server;
    private static EncodeDecode coder = new EncodeDecode();

    public static void main(String[] args)  {


        int port = 8088;

        System.out.println("server listening on port: " + port);
        System.out.println();

        try {
            server = HttpServer.create(new InetSocketAddress(port), MAX_WAITING_CONNECTIONS);
        }catch(IOException ex){
            ex.printStackTrace();
            return;
        }

        server.setExecutor(null);

        server.createContext("/clear", new ClearHandler());

        server.createContext("/event", new EventHandler());

        server.createContext("/fill", new FillHandler());

        server.createContext("/load", new LoadHandler());

        server.createContext("/user/login", new LoginHandler());

        server.createContext("/person", new PeopleHandler());

        server.createContext("/user/register", new RegisterHandler());

        server.createContext("/", new RootHandler());

        server.start();

    }






    //handler classes
    public static class ClearHandler implements HttpHandler{

        public void handle(HttpExchange exchange){
            ResponseClear clear = service.clear();
//            System.out.println("Before Clear Json");
            String send_back = coder.encodetoResponseClear(clear);
//            System.out.println("After Clear Json");

            try {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                // Now that the status code and headers have been sent to the client,
                // next we send the JSON data in the HTTP response body.

                // Get the response body output stream.
                OutputStream respBody = exchange.getResponseBody();
                // Write the JSON string to the output stream.
                writeString(send_back, respBody);
                // Close the output stream.  This is how Java knows we are done
                // sending data and the response is complete/
                respBody.close();
//                exchange.sendResponseHeaders(200, 0);
//                exchange.getResponseBody().close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
//            writer.close();
        }

        private void writeString(String str, OutputStream os) throws IOException {
            OutputStreamWriter sw = new OutputStreamWriter(os);
            sw.write(str);
            sw.flush();
        }
    }

    public static class  EventHandler implements HttpHandler{
        ResponseEvent eventResponse;
        ResponseEvents eventsResponse;
        String send_back;
        public void handle(HttpExchange exchange){
            String [] uri = exchange.getRequestURI().toString().split("/");
            Headers reqHeaders = exchange.getRequestHeaders();
            String auth_token = "";
            if (reqHeaders.containsKey("Authorization")) {
                auth_token = reqHeaders.getFirst("Authorization");
//                System.out.println(auth_token);
            }
            if(uri.length > 2){ //this means it is a single event request
                String eventID = uri[2];
                eventResponse = service.event(auth_token, eventID);
                send_back = coder.encodeResponseEvent(eventResponse);
                try {
                    if(eventResponse.getSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(send_back, respBody);
                        respBody.close();
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(send_back, respBody);
                        respBody.close();
                    };

                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }else{
                System.out.println("AUTH_TOKEN: " + auth_token);
                eventsResponse = service.events(auth_token);
                send_back = coder.encodeResponseEvents(eventsResponse);
                System.out.println(send_back);
                try {
                    if(eventsResponse.getSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(send_back, respBody);
                        respBody.close();
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(send_back, respBody);
                        respBody.close();
                    };

                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }

        private void writeString(String str, OutputStream os) throws IOException {
            OutputStreamWriter sw = new OutputStreamWriter(os);
            sw.write(str);
            sw.flush();
        }
    }


    public static class FillHandler implements HttpHandler{
        ResponseFill fillResponse;
        public void handle(HttpExchange exchange){
            String [] uri = exchange.getRequestURI().toString().split("/");
            String username = uri[2];
            if(uri.length > 3){ //specifying the number of generations wanted
                try {
                    fillResponse = service.fill(username, Integer.parseInt(uri[3]));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    fillResponse = service.fill(username);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            String send_back = coder.encodeResponseFill(fillResponse);

            try {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                // Now that the status code and headers have been sent to the client,
                // next we send the JSON data in the HTTP response body.

                // Get the response body output stream.
                OutputStream respBody = exchange.getResponseBody();
                // Write the JSON string to the output stream.
                writeString(send_back, respBody);
                // Close the output stream.  This is how Java knows we are done
                // sending data and the response is complete/
                respBody.close();
//                exchange.sendResponseHeaders(200, 0);
//                exchange.getResponseBody().close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
//            writer.close();
        }

        private void writeString(String str, OutputStream os) throws IOException {
            OutputStreamWriter sw = new OutputStreamWriter(os);
            sw.write(str);
            sw.flush();
        }
    }

    public static class LoadHandler implements HttpHandler{

        private String readString(InputStream is) throws IOException {
            StringBuilder sb = new StringBuilder();
            InputStreamReader sr = new InputStreamReader(is);
            char[] buf = new char[1024];
            int len;
            while ((len = sr.read(buf)) > 0) {
                sb.append(buf, 0, len);
            }
            return sb.toString();
        }

        public void handle(HttpExchange exchange){
            System.out.println("In Load Handler");
            ResponseLoad loadResponse;
            String requestBody = "";
//            System.out.println("in register handler");
            try {
                requestBody = readString(exchange.getRequestBody());
            }catch(IOException ex){
                ex.printStackTrace();
            }
            RequestLoad send_to = coder.decodetoRequestLoad(requestBody);
            loadResponse = service.load(send_to);
            String send_back = coder.encodeResponseLoad(loadResponse);

            try {

                if(loadResponse.getSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(send_back, respBody);
                    respBody.close();
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(send_back, respBody);
                    respBody.close();
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
//            writer.close();
        }

        private void writeString(String str, OutputStream os) throws IOException {
            OutputStreamWriter sw = new OutputStreamWriter(os);
            sw.write(str);
            sw.flush();
        }
    }

    public static class LoginHandler implements HttpHandler{
        ResponseLogin loginResponse;
        public void handle(HttpExchange exchange){
            String requestBody = "";
//            System.out.println("in register handler");
            try {
                requestBody = IOUtils.toString(exchange.getRequestBody(), StandardCharsets.UTF_8);
            }catch(IOException ex){
                ex.printStackTrace();
            }
            RequestLogin loginRequest = coder.decodetoRequestLogin(requestBody);
            loginResponse = service.login(loginRequest);
            String send_back = coder.encodeResponseLogin(loginResponse);

            try {
                if(loginResponse.getSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(send_back, respBody);
                    respBody.close();
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(send_back, respBody);
                    respBody.close();
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
//            writer.close();
        }

        private void writeString(String str, OutputStream os) throws IOException {
            OutputStreamWriter sw = new OutputStreamWriter(os);
            sw.write(str);
            sw.flush();
        }
    }

    public static class PeopleHandler implements HttpHandler{
    //check for people/persons
        ResponsePeople peopleResponse;
        ResponsePerson personResponse;
        String send_back;
        DaoAuthToken tokenAccess = new DaoAuthToken();

        public void handle(HttpExchange exchange) throws IOException {
            Headers reqHeaders = exchange.getRequestHeaders();
            String auth_token = "";
            if (reqHeaders.containsKey("Authorization")) {
                auth_token = reqHeaders.getFirst("Authorization");
            }


            String[] uri = exchange.getRequestURI().toString().split("/");

            if(uri.length > 2){ //going for a single person
                String personID = uri[2];
                personResponse = service.person(auth_token,personID);
                send_back = coder.encodeResponsePerson(personResponse);

                try {
                    if(personResponse.getSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(send_back, respBody);
                        respBody.close();
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(send_back, respBody);
                        respBody.close();
                    };
                }catch(IOException ex){
                    ex.printStackTrace();
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }

            }else{ //multiple people
                peopleResponse = service.people(auth_token);
                send_back = coder.encodeResponsePeople(peopleResponse);
                try {
                    if(peopleResponse.getSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(send_back, respBody);
                        respBody.close();
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(send_back, respBody);
                        respBody.close();
                    };
                }catch(IOException ex){
                    ex.printStackTrace();
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
        }

        private void writeString(String str, OutputStream os) throws IOException {
            OutputStreamWriter sw = new OutputStreamWriter(os);
            sw.write(str);
            sw.flush();
        }
    }

    public static class RegisterHandler implements HttpHandler{
        ResponseRegister registerResponse;
        public void handle(HttpExchange exchange) throws IOException {
            String requestBody = "";

            try {
                requestBody = IOUtils.toString(exchange.getRequestBody(), StandardCharsets.UTF_8);
            }catch(IOException ex){
                ex.printStackTrace();
            }
//            System.out.println(requestBody);
            RequestRegister registerRequest = coder.decodetoRequestRegister(requestBody);
            try {
                registerResponse = service.register(registerRequest);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String send_back = coder.encodeResponseRegister(registerResponse);

            try {
                if(registerResponse.getSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(send_back, respBody);
                    respBody.close();
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(send_back, respBody);
                    respBody.close();
                }
            }catch(IOException ex){
                ex.printStackTrace();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }

        private void writeString(String str, OutputStream os) throws IOException {
            OutputStreamWriter sw = new OutputStreamWriter(os);
            sw.write(str);
            sw.flush();
        }
    }

    public static class RootHandler implements HttpHandler {

        public void handle(HttpExchange exchange){
            String path = "/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/";
            String relative_path = "web" + exchange.getRequestURI().toString();
            if(exchange.getRequestURI().toString().equals("/")){
                relative_path = relative_path + "index.html";
            }

            String filepath = path + relative_path;
            Path file_path = FileSystems.getDefault().getPath(filepath);
            Path path_404 = FileSystems.getDefault().getPath("/home/clint/GITHUB/CS_240/FamilyMapServerStudent-master/web/HTML/404.html");
            try {
                if(!exchange.getRequestURI().toString().equals("get")){
                    exchange.sendResponseHeaders(200, 0);
                    Files.copy(file_path, exchange.getResponseBody());
                    exchange.getResponseBody().close();
                }
                else{
                    exchange.sendResponseHeaders(200, 0);
                    Files.copy(file_path, exchange.getResponseBody());
                    exchange.getResponseBody().close();
                }

            }catch(IOException ex){
                try {
                    Files.copy(path_404, exchange.getResponseBody());
                    exchange.getResponseBody().close();
                }catch(IOException e){
                    e.printStackTrace();
                }

                //how to return 404
            }
        }
    }

}
