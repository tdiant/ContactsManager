package nyan.best.contactsmanager.launcher;

public class AppMain {

    public static final AppLauncher launcher;

    static {
        launcher = new AppLauncher();
    }

    public static void main(String[] args) {
        launcher.runApp();
    }

}
