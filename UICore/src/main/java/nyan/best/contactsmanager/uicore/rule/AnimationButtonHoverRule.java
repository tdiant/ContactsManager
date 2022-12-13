package nyan.best.contactsmanager.uicore.rule;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventListener;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseHoverNodeEvent;
import nyan.best.contactsmanager.uicore.event.mouse.UIMouseLeaveNodeEvent;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.spirit.Button;
import nyan.best.contactsmanager.uicore.util.GLFWUtils;
import nyan.best.contactsmanager.uicore.util.StepProgress;

import java.util.HashMap;

public class AnimationButtonHoverRule implements EventListener {

    public static HashMap<Button, ButtonAnimationStepProgress> animationProgresses = new HashMap<>();

    @EventHandle
    public void onMouseHoverBtn(UIMouseHoverNodeEvent e) {
        if (e.getHoverNode() instanceof Button btn) {
            if (animationProgresses.containsKey(btn)) {
                var progress = animationProgresses.get(btn);
                progress.stop();
                animationProgresses.remove(btn);
            }
            var p = new ButtonAnimationStepProgress(btn, true);
            animationProgresses.put(btn, p);
            p.start();

            e.getWindow().setCursor(WindowInstance.CursorType.POINTING_HAND);
        }
    }

    @EventHandle
    public void onMouseLeaveBtn(UIMouseLeaveNodeEvent e) {
        if (e.getLeaveNode() instanceof Button btn && animationProgresses.containsKey(btn)) {
            var progress = animationProgresses.get(btn);
            progress.stop();
            var p = new ButtonAnimationStepProgress(btn, false);
            animationProgresses.put(btn, p);
            p.start();

            e.getWindow().setCursor(WindowInstance.CursorType.ARROW);
        }
    }

    public static class ButtonAnimationStepProgress extends StepProgress {
        private final Button btn;
        private int cnt = 0;
        private final boolean target;

        public ButtonAnimationStepProgress(Button btn, boolean target) {
            super(10);
            this.btn = btn;
            this.target = target;
        }

        @Override
        public void meow() {
            int[] color = GLFWUtils.getARGB(btn.getStyle().get("#BtnBackgroundColor"));
            color[1] += (target ? 1 : -1);
            color[2] += (target ? 1 : -1);
            color[3] += (target ? 1 : -1);
            btn.getStyle().put(
                    "#BtnBackgroundColor",
                    color[0] + "," + color[1] + "," + color[2] + "," + color[3]
            );
            cnt++;
        }

        @Override
        public boolean checkCanEnd() {
            if (target) {
                return cnt >= 15;
            } else {
                if (btn.getStyle().get("#BtnBackgroundColor").equalsIgnoreCase(
                        btn.getStyle().get("BtnBackgroundColor")))
                    return true;
                if (cnt >= 25) {
                    resetBgColor();
                    return true;
                }
                return false;
            }
        }

        @Override
        public void onStart() {
            if (!btn.getStyle().containsKey("#BtnBackgroundColor"))
                resetBgColor();
        }

        @Override
        public void onEnd() {
        }

        public void resetBgColor() {
            btn.getStyle().put(
                    "#BtnBackgroundColor",
                    btn.getStyle().get("BtnBackgroundColor")
            );
        }
    }

}
