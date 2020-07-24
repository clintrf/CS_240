package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import serviceClasses.resultService.ResultsRegister;

import java.nio.charset.StandardCharsets;

public class HandlerRegister implements HttpHandler {

    private ResultsRegister results;
    private EncoderDecoder coder;

    public void handle(HttpExchange exchange){

    }
}
