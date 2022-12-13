package nyan.best.contactsmanager.uicore;

import io.github.humbleui.types.Rect;
import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.common.Pos;
import nyan.best.contactsmanager.uicore.node.Node;
import nyan.best.contactsmanager.uicore.node.pane.Pane;
import nyan.best.contactsmanager.uicore.render.NodeRenderFactory;
import nyan.best.contactsmanager.uicore.util.GLFWUtils;
import nyan.best.contactsmanager.uicore.util.StepProgress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public class Stage {

    private final UUID uuid;

    protected String title;
    protected AliveSize size;
    protected AlivePosition position;
    protected Pane rootNode;

    protected WindowInstance window;

    protected Node focusNode;

    protected boolean scale = true;

    public Stage(String title, AliveSize size, AlivePosition position) {
        this.title = title;
        this.uuid = UUID.randomUUID();
        this.size = size;
        this.position = position;
    }

    public Node rayToNodeByPos(Pos pos) {
        rootNode.setNodeRender(Objects.requireNonNullElse(
                rootNode.getNodeRender(),
                NodeRenderFactory.gottaNodeRender(window, rootNode)
        ));
        var ls = new ArrayList<>(rootNode.getChildren());
        Collections.reverse(ls);
        for (Node x : ls) {
            x.setNodeRender(Objects.requireNonNullElse(
                    x.getNodeRender(),
                    NodeRenderFactory.gottaNodeRender(window, x)
            ));
            var p = x.getNodeRender().checkRay(pos);
            if (p != null)
                return p;
        }
        return rootNode.getNodeRender().checkRay(pos);
    }

    public void draw() {
        Objects.requireNonNullElse(
                rootNode.getNodeRender(),
                NodeRenderFactory.gottaNodeRender(window, rootNode)
        ).draw();

        onDraw();

        StepProgress.StepProgressRunnable runnable = StepProgress._next();
        if (runnable != null)
            runnable.run();
    }

    public void start() {
        window = new WindowInstance(title, this); //todo
        Rect rect = GLFWUtils.getRectFromSeparatedSizeAndPos(size, position);
        System.out.println(rect);
        window.run((int) rect.getLeft(), (int) rect.getTop(), (int) rect.getRight(), (int) rect.getBottom());
    }

    public void stop() {
        window.close();
        onStop();
    }

    public void onStop() {
    }

    public void onDraw() {
    }

    public UUID getUniqueID() {
        return uuid;
    }

    public void setSize(AliveSize size) {
        this.size = size;
        window.resize(size.getWidth(), size.getHeight());
    }

    public void setPosition(AlivePosition position) {
        this.position = position;
        window.movePos(position.getLeft(), position.getTop());
    }

    public Pane getRootNode() {
        return rootNode;
    }

    public void setRootNode(Pane rootNode) {
        this.rootNode = rootNode;
    }

    public boolean isScale() {
        return scale;
    }

    public void setScale(boolean scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        window.setTitle(title);
    }

    public UUID getUUID() {
        return uuid;
    }

    public AliveSize getSize() {
        return size;
    }

    public AlivePosition getPosition() {
        return position;
    }

    public WindowInstance getWindow() {
        return window;
    }

    public Node getFocusNode() {
        return focusNode;
    }

    public void setFocusNode(Node focusNode) {
        this.focusNode = focusNode;
    }
}
