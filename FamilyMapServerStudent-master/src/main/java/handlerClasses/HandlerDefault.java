package handlerClasses;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.w3c.dom.html.HTMLAnchorElement;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class HandlerDefault implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }

    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
