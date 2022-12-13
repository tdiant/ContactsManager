package nyan.best.contactsmanager.uicore.rule;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventListener;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseClickEvent;
import nyan.best.contactsmanager.uicore.node.spirit.Button;
import nyan.best.contactsmanager.uicore.util.StepProgress;

import java.util.HashMap;
import java.util.Map;

public class AnimationButtonClickRule implements EventListener {

    public static Map<Button, AnimationButtonClickStepProgress> animationProgresses = new HashMap<>();

    @EventHandle
    public void onBtnClick(UIMouseClickEvent e) {
        if (e.getClickedNode() instanceof Button btn) {
            if (animationProgresses.containsKey(btn)) {
                return;
            }
            var p = new AnimationButtonClickStepProgress(btn);
            animationProgresses.put(btn, p);
            p.start();
        }
    }

    public static class AnimationButtonClickStepProgress extends StepProgress {
        private static final int maxIdx = 5;
        private final Button btn;
        private int cnt = 0;

        public AnimationButtonClickStepProgress(Button btn) {
            super(20);
            this.btn = btn;
            meow();
        }

        @Override
        public void meow() {
            if (cnt <= maxIdx / 2) {
                btn.getStyle().put(
                        "#BtnClickProgressIdx",
                        "" + y(cnt)
                );
            } else {
                btn.getStyle().put(
                        "#BtnClickProgressIdx",
                        "" + y(maxIdx - cnt)
                );
            }
            cnt++;
        }

        private double y(int x) {
            return 2.0 / maxIdx * (x * x);
        }

        @Override
        public boolean checkCanEnd() {
            return cnt > maxIdx;
        }

        @Override
        public void onStart() {
            if (!btn.getStyle().containsKey("#BtnClickProgressIdx"))
                reset();
        }

        @Override
        public void onEnd() {
            reset();
            animationProgresses.remove(this.btn);
        }

        public void reset() {
            btn.getStyle().put(
                    "#BtnClickProgressIdx",
                    "0"
            );
        }
    }

}
