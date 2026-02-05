package network.tcp.autoclosable;

public class ResourceCloseMainV3 {

    public static void main(String[] args)  {
        try {
            logic();
        } catch (CallException e) {
            System.out.println("call catch");
            throw new RuntimeException(e);
        } catch (CloseException e) {
            System.out.println("close catch");
            throw new RuntimeException(e);
        }
    }

    private static void logic() throws CallException, CloseException {
        ResourceV1 resource1 = null;
        ResourceV1 resource2 = null;
        try {
            resource1 = new ResourceV1("resource1");
            resource2 = new ResourceV1("resource2");

            resource1.call();
            resource2.callEx();
        } catch (CallException e) {
            System.out.println("ex : " + e);
            throw e;
        } finally {
            System.out.println("finally");
            if (resource2 != null) {

//                resource2.closeEx(); // 여기서도 예외.. CloseException 발생. CallException이 사라짐.
            }
            if (resource1 != null) {
//                resource1.closeEx(); // 이 코드는 호출 안됨.
            }
        }
    }
}
