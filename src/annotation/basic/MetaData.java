package annotation.basic;

@AnnoMeta
public class MetaData {

    // @AnnoMeta // 필드 적용 - 컴파일 오류
    private String id;


    @AnnoMeta
    public void call() {

    }

    public static void main(String[] args) throws NoSuchMethodException {
        AnnoMeta annotation = MetaData.class.getAnnotation(AnnoMeta.class);
        System.out.println("annotation = " + annotation);

        AnnoMeta methodAnno = MetaData.class.getMethod("call").getAnnotation(AnnoMeta.class);
        System.out.println("methodAnno = " + methodAnno);
    }
}
