import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import com.sun.net.httpserver.*;
import dataAccessClasses.DaoAuthToken;
import databaseClasses.DatabaseDatabase;
import databaseClasses.DatabaseException;
import handlerClasses.*;
import org.apache.commons.io.IOUtils;
import serviceClasses.Services;
import serviceClasses.requestService.RequestLoad;
import serviceClasses.requestService.RequestLogin;
import serviceClasses.requestService.RequestRegister;
import serviceClasses.resultService.*;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;


    private static  Services services = new Services();



        private void run(String portNumber) throws DatabaseException {

        System.out.println("Initializing HTTP Server");

        String port = "8080";
        if (portNumber == null){
            portNumber = port;
        }

        System.out.println("server listening on port" + portNumber);
        System.out.println("default port: " + port + "\n");

        HttpServer server;
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);

//        System.out.println("Creating contexts");
//        server.createContext("/clear", new HandlerClear());
//        server.createContext("/event/", new HandlerEvent());
//        server.createContext("/fill/", new HandlerFill());
//        server.createContext("/load", new HandlerLoad());
//        server.createContext("/user/login", new HandlerLogin());
//        server.createContext("/person/", new HandlerPerson());
//        server.createContext("/user/register", new HandlerRegister());
//        server.createContext("/", new Handler());
            server.createContext("/clear", new ClearHandler());

            server.createContext("/event/", new EventHandler());

            server.createContext("/fill/", new FillHandler());

            server.createContext("/load", new LoadHandler());

            server.createContext("/user/login", new LoginHandler());

            server.createContext("/person/", new PeopleHandler());

            server.createContext("/user/register", new RegisterHandler());

            server.createContext("/", new RootHandler());

        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    public static void main(String[] args) throws DatabaseException {
        if(args.length!=0){
            System.out.println(args.length);
            String portNumber = args[0];
            new Server().run(portNumber);
        }
        else {
            new Server().run(null);
        }

    }

    //private static ServerFacade service = new ServerFacade();





    private static HttpServer server;
    private static EncoderDecoder coder = new EncoderDecoder();

    //handler classes
    public static class ClearHandler implements HttpHandler{

        public void handle(HttpExchange exchange){
            //cf ResultsClear clear = service.clear();
            ResultsClear clear = services.getClearResult();
            clear.clearResult();
//            System.out.println("Before Clear Json");
            String send_back = coder.encodeResultsClear(clear);
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
        ResultsEvent eventResponse;
        ResultsEvents eventsResponse;
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
                //cf eventResponse = service.event(auth_token, eventID);
                eventResponse = services.getEventResult();
                eventResponse.eventResult(auth_token,eventID);
                send_back = coder.encodeResultsEvent(eventResponse);
            }else{
                System.out.println("AUTH_TOKEN: " + auth_token);
                //cf eventsResponse = service.events(auth_token);
                eventsResponse = services.getEventsResult();
                eventsResponse.eventsResult(auth_token);
                send_back = coder.encodeResultsEvents(eventsResponse);
                System.out.println(send_back);
            }

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


    public static class FillHandler implements HttpHandler{
        ResultsFill fillResponse;
        public void handle(HttpExchange exchange){
            String [] uri = exchange.getRequestURI().toString().split("/");
            String username = uri[2];
            if(uri.length > 3){ //specifying the number of generations wanted
                //cf fillResponse = service.fill(username, Integer.parseInt(uri[3]));
                fillResponse = services.getFillResult();
                fillResponse.fillResult(username,Integer.parseInt(uri[3]));
            }else{
                //cf fillResponse = service.fill(username);
                fillResponse = services.getFillResult();
                fillResponse.fillResult(username,0); //fix later
            }
            String send_back = coder.encode(fillResponse);

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
            ResultsLoad loadResponse;
            String requestBody = "";
//            System.out.println("in register handler");
            try {
                requestBody = readString(exchange.getRequestBody());
            }catch(IOException ex){
                ex.printStackTrace();
            }
            RequestLoad send_to = coder.decodeToRequestLoad(requestBody);
            //cf loadResponse = service.load(send_to);
            loadResponse = services.getLoadResult();
            loadResponse.loadResult(send_to);
            String send_back = coder.encode(loadResponse);

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

    public static class LoginHandler implements HttpHandler{
        ResultsLogin loginResponse;
        public void handle(HttpExchange exchange){
            String requestBody = "";
//            System.out.println("in register handler");
            try {
                requestBody = IOUtils.toString(exchange.getRequestBody(), StandardCharsets.UTF_8);
            }catch(IOException ex){
                ex.printStackTrace();
            }
            RequestLogin loginRequest = coder.decodeToRequestLogin(requestBody);
            //cf loginResponse = service.login(loginRequest);
            loginResponse = services.getLoginResult();
            loginResponse.loginResult(loginRequest);
            String send_back = coder.encode(loginResponse);

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

    public static class PeopleHandler implements HttpHandler{
        //check for people/persons
        ResultsPeople peopleResponse;
        ResultsPerson personResponse;
        String send_back;
        //DaoAuthToken tokenAccess = new DaoAuthToken();

        public void handle(HttpExchange exchange){
            Headers reqHeaders = exchange.getRequestHeaders();
            String auth_token = "";
            if (reqHeaders.containsKey("Authorization")) {
                auth_token = reqHeaders.getFirst("Authorization");
//                System.out.println(auth_token);
            }
            try {
//                String[] uri = IOUtils.toString(exchange.getRequestURI(), StandardCharsets.UTF_8).split("/");
                String[] uri = exchange.getRequestURI().toString().split("/");
//                System.out.println(exchange.getRequestURI().toString());
//                System.out.println(uri.length);
                if(uri.length > 2){ //going for a single person
                    String personID = uri[2];
//                    System.out.println(personID);
                    //personResponse = service.person(auth_token,personID);
                    personResponse = services.getPersonResult();
                    personResponse.personResult(auth_token,personID);
                    send_back = coder.encode(personResponse);
//                    System.out.println(send_back);
                }else{ //multiple people
                    //cf peopleResponse = service.people(auth_token);
                    peopleResponse = services.getPeopleResult();
                    peopleResponse.peopleResult(auth_token);
                    send_back = coder.encode(peopleResponse);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

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

    public static class RegisterHandler implements HttpHandler{
        ResultsRegister registerResponse;
        public void handle(HttpExchange exchange){
            String requestBody = "";
//            System.out.println("in register handler");
            try {
                requestBody = IOUtils.toString(exchange.getRequestBody(), StandardCharsets.UTF_8);
            }catch(IOException ex){
                ex.printStackTrace();
            }
//            System.out.println(requestBody);
            RequestRegister registerRequest = coder.decodeToRequestRegister(requestBody);
            //cf registerResponse = service.register(registerRequest);
            registerResponse = services.registerResult(registerRequest);
            //registerResponse = services.getRegisterResult();
            //registerResponse.registerResult(registerRequest);
            String send_back = coder.encodeResponseRegister(registerResponse);
//            System.out.println(send_back);
//            PrintWriter writer = new PrintWriter(exchange.getResponseBody());
//            writer.write(send_back);
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

    public static class RootHandler implements HttpHandler{

        public void handle(HttpExchange exchange){
            String path = "C:/Users/logan/Documents/Android/Fmserver/fm_server_lib/src/";
            String relative_path = "web" + exchange.getRequestURI().toString();
            if(exchange.getRequestURI().toString().equals("/")){
                relative_path = relative_path + "index.html";
            }
            String filepath = path + relative_path;
            Path file_path = FileSystems.getDefault().getPath(filepath);
            Path path_404 = FileSystems.getDefault().getPath("C:/Users/logan/Documents/Android/Fmserver/fm_server_lib/src/web/HTML/404.html");
            try {
                exchange.sendResponseHeaders(200, 0);
                Files.copy(file_path, exchange.getResponseBody());
                exchange.getResponseBody().close();
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

