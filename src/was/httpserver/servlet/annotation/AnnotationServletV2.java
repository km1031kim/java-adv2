package was.httpserver.servlet.annotation;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;
import was.httpserver.HttpServlet;
import was.httpserver.servlet.PageNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class AnnotationServletV2 implements HttpServlet {

    private final List<Object> controllers;

    public AnnotationServletV2(List<Object> controllers) {
        this.controllers = controllers;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String path = request.getPath();

        for (Object controller : controllers) {
            Method[] methods = controller.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Mapping.class)) {
                    Mapping mapping = method.getAnnotation(Mapping.class);
                    String value = mapping.value();
                    if (value.equals(path)) {
                        invoke(controller, method, request, response);
                        return;
                    }
                }
            }
        }
        throw new PageNotFoundException("request=" + path);
    }

    private static void invoke(Object controller, Method method, HttpRequest request, HttpResponse response) {
        // 대상이 어떤 아규먼트를 가지는지 꺼낸다.
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i] == HttpRequest.class) {
                args[i] = request;
            } else if (parameterTypes[i] == HttpResponse.class) {
                args[i] = response;
            } else {
                throw new IllegalArgumentException("Unsupported parameter type : " + parameterTypes[i]);
            }
        }


        try {
            method.invoke(controller, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
