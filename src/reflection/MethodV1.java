package reflection;

import reflection.data.BasicData;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodV1 {

    public static void main(String[] args) {
        Class<BasicData> helloClass = BasicData.class;

        System.out.println("==== methods() ====");
        Method[] methods = helloClass.getMethods();
        for (Method method : methods) {
            System.out.println("method = " + method);
        }


        // 내갸 선언한 모든 메서드. 상속 제외
        System.out.println("==== declaredMethods() ====");
        Method[] declaredMethods = helloClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println("declaredMethod = " + declaredMethod);
        }
    }
}
