package network.tcp.autoclosable;

public class ResourceCloseMainV4 {

    public static void main(String[] args)  {
        try {
            logic();
        } catch (CallException e) {
            System.out.println("call catch");
            Throwable[] suppressed = e.getSuppressed();
            for (Throwable throwable : suppressed) {
                System.out.println("throwable = " + throwable);
            }

            throw new RuntimeException(e);
        } catch (CloseException e) {
            System.out.println("close catch");
            throw new RuntimeException(e);
        }
    }

    private static void logic() throws CallException, CloseException {
         try (ResourceV2 resource1 = new ResourceV2("resource1");
              ResourceV2 resource2 = new ResourceV2("resource2")) {

            resource1.call();
            resource2.callEx(); // CallException

             // 자동으로 close() 호출
             // close 순서도 보장
             // close 시 발생한 예외는 Suppressed로 표시.
             // close에서 발생한 예외는 핵심 예외가 아니다! 라는 처리방식.
             // try with resource는 예외 발생 시 바로 자원 정리 -> catch 호출.
             // 기존에는 예외 발생 -> catch -> 자원 정리
        } catch (CallException e) {
            System.out.println("ex : " + e);
            throw e;
        }
    }
}
