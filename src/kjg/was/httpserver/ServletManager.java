package kjg.was.httpserver;

import kjg.was.httpserver.servlet.InternalErrorServlet;
import kjg.was.httpserver.servlet.NotFoundServlet;
import was.httpserver.servlet.PageNotFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServletManager {

    private final Map<String, HttpServlet> servletMap = new HashMap<>();
    private HttpServlet defaultServlet;
    private HttpServlet notFoundServlet = new NotFoundServlet();
    private HttpServlet internalErrorServlet = new InternalErrorServlet();

    public void setDefaultServlet(HttpServlet defaultServlet) {
        this.defaultServlet = defaultServlet;
    }

    public void add(String path, HttpServlet httpServlet) {
        servletMap.put(path, httpServlet);
    }

    public void execute(HttpRequest request, HttpResponse response) throws IOException {
        String path = request.getPath();

        try {
            HttpServlet servlet = servletMap.getOrDefault(path, defaultServlet);

            if (servlet == null) {
                throw new PageNotFoundException("request url = " + request.getPath());
            }

            servlet.service(request, response);
        } catch (PageNotFoundException e) {
            e.printStackTrace();
            notFoundServlet.service(request,response);
        } catch (IOException e) {
            internalErrorServlet.service(request, response);
        }
    }
}
