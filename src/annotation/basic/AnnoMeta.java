package annotation.basic;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented // api 문서에 해당 어노테이션 포함. 보통 적는다.
public @interface AnnoMeta {

}
