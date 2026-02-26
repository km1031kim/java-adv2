package kjg.was.v3;

import kjg.was.httpserver.HttpRequest;
import kjg.was.httpserver.HttpResponse;
import kjg.was.httpserver.HttpServlet;
import was.httpserver.servlet.PageNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KjgAnnotationServlet implements HttpServlet {

    private final List<Object> controllers;
    private final Map<String, ControllerMethod> methodMap = new HashMap<>();

    public KjgAnnotationServlet(List<Object> controllers) {
        this.controllers = controllers;
        initMethodMap();
    }

    private void initMethodMap() {
        for (Object controller : controllers) {
            Method[] methods = controller.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    String path = method.getAnnotation(GetMapping.class).value();
                    if (methodMap.containsKey(path)) {
                        ControllerMethod existController = methodMap.get(path);
                        throw new IllegalArgumentException("경로 중복 등록, path = " + path + ", method = " + method + ", 이미 등록된 메서드 = " + existController.method);
                    }
                    methodMap.put(path, new ControllerMethod(controller, method));
                }
            }
        }
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String path = request.getPath();
        ControllerMethod controller = methodMap.get(path);

        if (controller == null) {
            throw new PageNotFoundException("해당되는 페이지가 존재하지 않습니다. request = " + path);
        }
        controller.invoke(request, response);
    }

    static class ControllerMethod {
        private final Object controller;
        private final Method method;

        ControllerMethod(Object controller, Method method) {
            this.controller = controller;
            this.method = method;
        }

        public void invoke(HttpRequest request, HttpResponse response) {
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
            }  catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
