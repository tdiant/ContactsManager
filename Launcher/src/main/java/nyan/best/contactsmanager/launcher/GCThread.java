package nyan.best.contactsmanager.launcher;

public class GCThread extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.gc();
            run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
