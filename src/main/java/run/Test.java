package run;

public class Test {

    @org.testng.annotations.Test
    public static void test1() {
        System.out.println("test");
    }

    @org.testng.annotations.Test
    public static void test2() {
        Main main = new Main();
        main.testAnnotation();
    }


}
