package kjg.was.v2;

import kjg.was.httpserver.HttpServer;
import kjg.was.httpserver.ServletManager;
import kjg.was.v1.servlet.*;

import java.io.IOException;
import java.util.List;

public class ServerMainV2 {
    public static void main(String[] args) throws IOException {
        List<Object> controllers = List.of(new SearchControllerV2(), new SiteControllerV2());

        ReflectionServlet reflectionServlet = new ReflectionServlet(controllers);
        ServletManager servletManager = new ServletManager();
        servletManager.setDefaultServlet(reflectionServlet);
        servletManager.add("/favicon.ico", new DiscardServlet());

        // 메서드 이름으로 / 는 안되니까..
        servletManager.add("/", new HomeServlet());

        HttpServer httpServer = new HttpServer(12345, servletManager);
        httpServer.start();
    }
}
