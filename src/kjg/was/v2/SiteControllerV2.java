package kjg.was.v2;


import kjg.was.httpserver.HttpRequest;
import kjg.was.httpserver.HttpResponse;

public class SiteControllerV2 {

    public void site1(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>site1</h1>");
    }

    public void site2(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>site2</h1>");
    }
}
