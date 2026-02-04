package network.tcp.autoclosable;

public class ResourceCloseMainV1 {

    public static void main(String[] args)  {
        try {
            logic();
        } catch (CallException e) {
            throw new RuntimeException(e);
        } catch (CloseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void logic() throws CallException, CloseException {
        ResourceV1 resource1 = new ResourceV1("resource1");
        ResourceV1 resource2 = new ResourceV1("resource2");

        resource1.call();
        resource2.callEx();

        System.out.println("자원 정리"); // 호출 안됨
        resource2.closeEx();
        resource1.closeEx();
    }
}
