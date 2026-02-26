package kjg.was.v3;


import kjg.was.httpserver.HttpRequest;
import kjg.was.httpserver.HttpResponse;

public class SiteControllerV3 {

    @GetMapping("/")
    public void home(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>home</h1>");
        response.writeBody("<ul>");
        response.writeBody("<li><a href='/site1'>site1</a></li>");
        response.writeBody("<li><a href='/site2'>site2</a></li>");
        response.writeBody("<li><a href='/search?q=hello'>검색</a></li>");
        response.writeBody("</ul>");
    }

    @GetMapping("/site1")
    public void sameName(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>site1</h1>");
    }

    @GetMapping("/site1")
    public void site1Call(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>site1</h1>");
    }

    @GetMapping("/site2")
    public void site2Call(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>site2</h1>");
    }
}
