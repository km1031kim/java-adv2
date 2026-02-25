package annotation.mapping;

import UTIL.MyLogger;
import annotation.basic.AnnoElement;

public class TestController {

    @SimpleMapping(value="/")
    public void home() {
        System.out.println("TestController.home");
    }

    @SimpleMapping(value="/site1")
    public void page1() {
        System.out.println("TestController.page1");
    }
}
