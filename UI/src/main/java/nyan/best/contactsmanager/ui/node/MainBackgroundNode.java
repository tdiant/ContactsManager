package nyan.best.contactsmanager.ui.node;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Matrix33;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.types.RRect;
import nyan.best.contactsmanager.uicore.WindowInstance;
import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static nyan.best.contactsmanager.uicore.render.NodeRenderFactory.NODE_RENDER_CLZ;

public class MainBackgroundNode extends Node {

    static {
        NODE_RENDER_CLZ.put(MainBackgroundNode.class, MainBackgroundNodeRender.class);
    }

    public static class MainBackgroundNodeRender extends NodeRender<MainBackgroundNode> {
        private final List<PathColorPair> ls = new ArrayList<>();

        public MainBackgroundNodeRender(WindowInstance window, MainBackgroundNode nodeObj) {
            super(window, nodeObj);

            var random = new Random();
            for (int i = 0; i < 100; ++i) {
                var path = new Path();

                path.addRRect(RRect.makeXYWH(-0.6f, -0.4f, 1.2f, 0.8f, 0.4f));

                path.transform(Matrix33.makeRotate(random.nextInt(360)));
                path.transform(Matrix33.makeScale(10 + random.nextInt(250)));
                path.transform(Matrix33.makeTranslate(random.nextInt(1920), random.nextInt(1080)));

                int color = 0xFF000000 | random.nextInt(0xFFFFFF);

                ls.add(new PathColorPair(path, color));
            }
        }

        @Override
        public void onDraw() {
            Canvas canvas = window.getCanvas();

            try (Paint fill = new Paint()) {
                for (var p : ls) {
                    fill.setAntiAlias(true);
                    fill.setColor(p.color);
                    canvas.drawPath(p.path, fill);
                }
            }

        }
    }

    public record PathColorPair(Path path, int color) {
    }

}
