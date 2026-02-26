package kjg.was.v1.servlet;

import kjg.was.httpserver.HttpRequest;
import kjg.was.httpserver.HttpResponse;
import kjg.was.httpserver.HttpServlet;

import java.io.IOException;

public class Site1Servlet implements HttpServlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        response.writeBody("<h1>site1</h1>");

    }
}
