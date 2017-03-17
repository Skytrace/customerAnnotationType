import api.RuntimeInitializer;
import api.RuntimeXpath;
import api.SpecificApplication;
import api.Usage;

public class Main {
    @Usage(app = SpecificApplication.Facebook, xpath = "//div[@id='facebook-form']")
    @Usage(app = SpecificApplication.Vk, xpath = "//div[@id='vk-form']")
    private RuntimeXpath xpath;

    public static void main(String... args) {
        Main main = new Main();
        main.testAnnotation();
    }

    public void testAnnotation() {
        System.setProperty("website", "Vk");
        RuntimeInitializer.initFIeld(getClass());
        System.out.println(xpath.getXpath());
    }

}
