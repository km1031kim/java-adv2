package kjg.was.v3;

import kjg.was.httpserver.HttpServer;
import kjg.was.httpserver.ServletManager;
import kjg.was.v1.servlet.DiscardServlet;
import kjg.was.v1.servlet.HomeServlet;
import kjg.was.v2.ReflectionServlet;
import kjg.was.v2.SearchControllerV2;
import kjg.was.v2.SiteControllerV2;

import java.io.IOException;
import java.util.List;

public class ServerMainV3 {
    public static void main(String[] args) throws IOException {
        List<Object> controllers = List.of(new SearchControllerV3(), new SiteControllerV3());

        KjgAnnotationServlet annotationServlet = new KjgAnnotationServlet(controllers);
        ServletManager servletManager = new ServletManager();
        servletManager.setDefaultServlet(annotationServlet);

        servletManager.add("/favicon.ico", new DiscardServlet());

        HttpServer httpServer = new HttpServer(12345, servletManager);
        httpServer.start();
    }
}
