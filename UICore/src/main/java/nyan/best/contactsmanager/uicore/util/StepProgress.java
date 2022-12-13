package nyan.best.contactsmanager.uicore.util;

import java.util.PriorityQueue;

public abstract class StepProgress {

    private boolean statusFlag = false;
    private long delta = 150;

    public StepProgress() {
    }

    public StepProgress(long delta) {
        this.delta = delta;
    }

    public void start() {
        if (statusFlag)
            return;
        statusFlag = true;
        runnableQueue.add(new StepProgressRunnable(System.currentTimeMillis() + delta));
        onStart();
    }

    public void stop() {
        if (!statusFlag)
            return;
        statusFlag = false;
        onEnd();
    }

    public abstract void meow();

    public abstract boolean checkCanEnd();

    public abstract void onStart();

    public abstract void onEnd();

    private static final PriorityQueue<StepProgressRunnable> runnableQueue
            = new PriorityQueue<>((x, y) -> (int) (x.expectRunnableTimestamp - y.expectRunnableTimestamp));

    public static StepProgressRunnable _next() {
        if (runnableQueue.size() == 0)
            return null;
        StepProgressRunnable runnable = runnableQueue.peek();
        if (runnable.expectRunnableTimestamp > System.currentTimeMillis())
            return null;
        return runnableQueue.poll();
    }

    public class StepProgressRunnable implements Runnable {
        public final long expectRunnableTimestamp;

        public StepProgressRunnable(long expectRunnableTimestamp) {
            this.expectRunnableTimestamp = expectRunnableTimestamp;
        }

        @Override
        public void run() {
            if (checkCanEnd() || !statusFlag) {
                stop();
            } else {
                meow();
                runnableQueue.add(new StepProgressRunnable(System.currentTimeMillis() + delta));
            }
        }
    }

}
