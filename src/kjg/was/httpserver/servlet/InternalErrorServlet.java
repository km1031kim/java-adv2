package kjg.was.httpserver.servlet;

import kjg.was.httpserver.HttpRequest;
import kjg.was.httpserver.HttpResponse;
import kjg.was.httpserver.HttpServlet;

import java.io.IOException;

public class InternalErrorServlet implements HttpServlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        response.setStatusCode(500);
        response.writeBody("<h1>Internal Error</h1>");
    }
}
