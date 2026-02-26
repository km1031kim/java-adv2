package kjg.was.v1;

import kjg.was.httpserver.HttpServer;
import kjg.was.httpserver.ServletManager;
import kjg.was.v1.servlet.*;

import java.io.IOException;

public class ServerMainV1 {
    public static void main(String[] args) throws IOException {
        ServletManager servletManager = new ServletManager();
        servletManager.add("/", new HomeServlet());
        servletManager.add("/site1", new Site1Servlet());
        servletManager.add("/site2", new Site2Servlet());
        servletManager.add("/search", new SearchServlet());
        servletManager.add("/favicon.ico", new DiscardServlet());

        HttpServer httpServer = new HttpServer(12345, servletManager);
        httpServer.start();

    }
}
