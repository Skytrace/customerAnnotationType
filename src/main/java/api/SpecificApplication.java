package api;

import static org.apache.commons.lang3.EnumUtils.getEnum;

public enum SpecificApplication {
    Facebook,
    Vk;

    private static SpecificApplication activeSite = getEnum(SpecificApplication.class, System.getProperty("website"));

    public static String getActiveSiteName() {
        return activeSite.name();
    }

    public static SpecificApplication getActiveSite() {
        return activeSite;
    }
}