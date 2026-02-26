package kjg.was.v1.servlet;

import kjg.was.httpserver.HttpRequest;
import kjg.was.httpserver.HttpResponse;
import kjg.was.httpserver.HttpServlet;

import java.io.IOException;

public class SearchServlet implements HttpServlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String query = request.getParameter("q");
        response.writeBody("<h1>Search</h1>");
        response.writeBody("<ul>");
        response.writeBody("<li>query : " + query + "</li>");
        response.writeBody("</ul>");
    }
}
