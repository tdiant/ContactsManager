package nyan.best.contactsmanager.ui.stage;

import nyan.best.contactsmanager.common.i18n.I18N;
import nyan.best.contactsmanager.eventsys.EventManager;
import nyan.best.contactsmanager.ui.event.LoadingFinishEvent;
import nyan.best.contactsmanager.uicore.Stage;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.node.pane.Pane;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleCircle;
import nyan.best.contactsmanager.uicore.node.spirit.SimpleRectangle;
import nyan.best.contactsmanager.uicore.util.GLFWUtils;
import nyan.best.contactsmanager.uicore.util.StepProgress;

import java.util.Arrays;
import java.util.List;

public class LoadingStage extends Stage {

    private Pane rootPane = new Pane();

    public LoadingStage(AliveSize size, AlivePosition position) {
        super(I18N.lang("stage.loading.window.title"),
                size,
                position
        );
        this.setRootNode(rootPane);
        init();

        new StepProgress() {
            long createdTime = System.currentTimeMillis();

            @Override
            public void meow() {
            }

            @Override
            public boolean checkCanEnd() {
                return System.currentTimeMillis() - createdTime > 3500;
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onEnd() {
                if (animationStepProgress != null)
                    animationStepProgress.stop();
                EventManager.callEvent(new LoadingFinishEvent());
            }
        }.start();
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDraw() {
        rootPane.getSize().setWidth(size.getWidth());
        rootPane.getSize().setHeight(size.getHeight());
        var logo = rootPane.getChildren().get(0);
        logo.getPosition().setType(AliveType.RELATED_SEPARATED);
        logo.getPosition().setLeft(size.getWidth() / 2 - logo.getSize().getWidth() / 2);
        logo.getPosition().setTop(size.getHeight() / 2 - logo.getSize().getHeight());
    }

    AnimationStepProgress animationStepProgress = new AnimationStepProgress();

    private void init() {
        rootPane.getStyle().put("Background", "255,255,255,255");

        Pane logoPane = new Pane();
        logoPane.setSize(new AliveSize(AliveType.SEPARATED, 290, 120));
//        logoPane.getStyle().put("Background", "255,0,0,0");

        logoPane.addChild(genLogoRec(60, logoPane, 176, 213, 240));
        logoPane.addChild(genLogoRec(112, logoPane, 51, 153, 255));
        logoPane.addChild(genLogoCir(logoPane));

        rootPane.addChild(logoPane);

        animationStepProgress.start();
    }

    private SimpleRectangle genLogoRec(int x, Pane p, int r, int g, int b) {
        SimpleRectangle rec = new SimpleRectangle();
        rec.getStyle().put("Color", "255," + r + "," + g + "," + b);
        rec.getStyle().put("Rotate", "45");
        rec.getStyle().put("CirRadius", "30");
        rec.getStyle().put("RectangleWidth", "56");
        rec.getStyle().put("RectangleHeight", "125");
        rec.getStyle().put("OverflowShow", "true");
        rec.setSize(new AliveSize(AliveType.SEPARATED, 110, 110));
        rec.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, p.getPosition(), x, 20));
        return rec;
    }

    private SimpleCircle genLogoCir(Pane p) {
        SimpleCircle cir = new SimpleCircle();
        cir.getStyle().put("Color", "255,51,102,255");
        cir.getStyle().put("OverflowShow", "true");
        cir.setSize(new AliveSize(AliveType.SEPARATED, 62, 62));
        cir.setPosition(new AlivePosition(AliveType.RELATED_SEPARATED, p.getPosition(), 180, 80));
        return cir;
    }

    private class AnimationStepProgress extends StepProgress {
        private static final int eachTime = 15;
        private static final int eachOffsetX = 20;

        private static final List<Integer> xs = Arrays.asList(60, 112, 180);

        private int cnt = 0;

        public AnimationStepProgress() {
            super(15);
        }

        @Override
        public void meow() {
            int x = cnt % eachTime;
            int no = cnt / eachTime;
            Node node = null;
            if (no < 3)
                node = rootPane.getChildren().get(0).getChildren().get(no);

            if (node != null) {
                int[] colors = GLFWUtils.getARGB(node.getStyle().get("Color"));
                node.getStyle().put(
                        "Color",
                        colorY(x) + "," + colors[1] + "," + colors[2] + "," + colors[3]
                );
                var pos = node.getPosition();
                pos.setLeft(xs.get(no) + y(x));
                node.setPosition(pos);
            }

            cnt++;
        }

        public float y(float x) {
            return -1.0f * eachOffsetX / (eachTime * eachTime) * x * x + (2.0f * eachOffsetX / eachTime) * x;
        }

        public int colorY(int x) {
            return (int) Math.max(0.05 * x, 200);
        }

        @Override
        public boolean checkCanEnd() {
            return cnt >= eachTime * 6;
        }

        @Override
        public void onStart() {
            for (int i = 0; i < 3; i++) {
                Node node = rootPane.getChildren().get(0).getChildren().get(i);
                var pos = node.getPosition();
                pos.setLeft(pos.getLeft() - eachOffsetX);
                node.setPosition(pos);
                node.getStyle().put("Color", node.getStyle().get("Color").replaceFirst("255", "0"));
            }
        }

        @Override
        public void onEnd() {
            reset();
            if (rootPane != null) {
                cnt = 0;
                start();
            }
        }

        void reset() {
            for (int i = 0; i < 3; i++) {
                Node node = rootPane.getChildren().get(0).getChildren().get(i);
                var pos = node.getPosition();
                pos.setLeft(xs.get(0));
                node.setPosition(pos);
                node.getStyle().put("Color",
                        new StringBuilder(node.getStyle().get("Color"))
                                .replace(0, 3, "255")
                                .toString()
                );
            }
        }
    }

}
