package nyan.best.contactsmanager.uicore.node;

import nyan.best.contactsmanager.uicore.attribute.AlivePosition;
import nyan.best.contactsmanager.uicore.attribute.AliveSize;
import nyan.best.contactsmanager.uicore.attribute.AliveType;
import nyan.best.contactsmanager.uicore.render.StyleSheet;
import nyan.best.contactsmanager.uicore.render.node.NodeRender;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Node {

    protected String name;
    private final UUID uuid = UUID.randomUUID();
    protected int zOrder = 1;

    protected final AliveSize size = new AliveSize(AliveType.SEPARATED, 0, 0);
    protected final AlivePosition position = new AlivePosition(AliveType.SEPARATED, 0, 0);

    private final StyleSheet style = new StyleSheet();
    private NodeRender<? extends Node> nodeRender;

    private Node parent = null;
    private final List<Node> children = new ArrayList<>();

    private boolean innerTag = false;

    private boolean focus = false;

    public Node() {
        this.name = getClass().getName() + "_" + uuid;
    }

    public Node(String name) {
        this.name = name;
    }

    public UUID getUniqueID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AliveSize getSize() {
        return size;
    }

    public void setSize(AliveSize size) {
        this.size.setType(size.getType());
        this.size.setHeight(size.getHeight());
        this.size.setWidth(size.getWidth());
        this.size.setParent(size.getParent());
    }

    public AlivePosition getPosition() {
        return position;
    }

    public void setPosition(AlivePosition position) {
        this.position.setType(position.getType());
        this.position.setLeft(position.getLeft());
        this.position.setTop(position.getTop());
        this.position.setParent(position.getParent());
    }

    public int getZOrder() {
        return zOrder;
    }

    public void setZOrder(int zOrder) {
        this.zOrder = zOrder;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
        if (parent != null) {
            position.setParent(parent.position);
            size.setParent(parent.size);
        }
    }

    public List<Node> getChildren() {
        return getChildren(false);
    }

    public List<Node> getChildren(boolean containsInner) {
        return children.stream()
                .distinct()
                .filter(x -> containsInner || !x.innerTag)
                .sorted(Comparator.comparingInt(Node::getZOrder))
                .collect(Collectors.toList());
    }

    public void addChild(Node node) {
        if (children.contains(node))
            throw new RuntimeException("Cannot add this child.");
        if (node == this)
            throw new RuntimeException("Cannot add myself to child!");
        children.add(node);
        node.setParent(this);
    }

    public void removeChild(Node node) {
        children.remove(node);
        if (node.getParent() == this)
            node.setParent(null);
    }

    public NodeRender<? extends Node> getNodeRender() {
        return nodeRender;
    }

    public void setNodeRender(NodeRender<? extends Node> nodeRender) {
        this.nodeRender = nodeRender;
    }

    public StyleSheet getStyle() {
        return style;
    }

    public boolean isInnerTag() {
        return innerTag;
    }

    public void setInnerTag(boolean innerTag) {
        this.innerTag = innerTag;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }
}
