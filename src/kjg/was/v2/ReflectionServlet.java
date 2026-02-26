package kjg.was.v2;

import kjg.was.httpserver.HttpRequest;
import kjg.was.httpserver.HttpResponse;
import kjg.was.httpserver.HttpServlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectionServlet implements HttpServlet {
    /**
     * reflection을 이용해서 메서드명을 키로 설정
     * @param controllers
     */
    private final List<Object> controllers;

    public ReflectionServlet(List<Object> controllers) {
        this.controllers = controllers;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String path = request.getPath();

        // SiteController, SearchController
        for (Object controller : controllers) {
            Class<?> aClass = controller.getClass();
            Method[] methods = aClass.getDeclaredMethods();

            // site1, site2
            for (Method method : methods) {
                String methodName = method.getName();
                if (path.equals("/" + methodName)) { //site1.equals(/site1)
                    invoke(controller, method, request, response);
                    return;
                }
            }
        }
    }


    private static void invoke(Object controller, Method method, HttpRequest request, HttpResponse response)  {
        try {
            method.invoke(controller, request, response);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
