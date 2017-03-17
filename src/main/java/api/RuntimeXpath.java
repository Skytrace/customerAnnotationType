package api;

import static api.SpecificApplication.getActiveSiteName;

public class RuntimeXpath {
    private String xpath = getActiveSiteName();

    public RuntimeXpath(String xpath) {
        this.xpath = xpath;
    }

    public String getXpath() {
        return this.xpath;
    }
}
