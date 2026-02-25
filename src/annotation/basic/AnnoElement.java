package annotation.basic;

import UTIL.MyLogger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
public @interface AnnoElement {
    String value();
    int count() default 0;
    String[] tags() default {};

    // MyLogger data();
    Class<? extends MyLogger> annoData() default MyLogger.class;


}
