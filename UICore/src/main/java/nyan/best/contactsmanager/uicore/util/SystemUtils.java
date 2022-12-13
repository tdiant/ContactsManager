package nyan.best.contactsmanager.uicore.util;

public class SystemUtils {

    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static boolean isMac() {
        return getOSName().contains("darwin") || getOSName().contains("mac");
    }


}
