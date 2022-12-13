package nyan.best.contactsmanager.uicore.rule;

import nyan.best.contactsmanager.eventsys.EventHandle;
import nyan.best.contactsmanager.eventsys.EventListener;
import nyan.best.contactsmanager.eventsys.EventManager;
import nyan.best.contactsmanager.uicore.event.UIEvent;
import nyan.best.contactsmanager.uicore.event.keyboard.UIKeyboardClickEvent;
import nyan.best.contactsmanager.uicore.event.text.UITextInputEvent;
import nyan.best.contactsmanager.uicore.event.text.UITextInputValueChangeEvent;
import nyan.best.contactsmanager.uicore.node.spirit.TextInput;
import org.lwjgl.glfw.GLFW;

public class TextInputIbeamPosRule implements EventListener {

    @EventHandle
    public void onKeyPress(UIKeyboardClickEvent e) {
        var node = nowTextInput(e);
        if (node == null)
            return;
        node.ibeamPosRec.getStyle().put("Color", "255,0,0,0");
        if (e.getKeyCode() == GLFW.GLFW_KEY_LEFT) {
            node.moveIbeamPos(node.getIbeamPosCharIdx() - 1);
        }
        if (e.getKeyCode() == GLFW.GLFW_KEY_RIGHT) {
            node.moveIbeamPos(node.getIbeamPosCharIdx() + 1);
        }
        if (e.getKeyCode() == GLFW.GLFW_KEY_HOME) {
            node.moveIbeamPos(0);
        }
        if (e.getKeyCode() == GLFW.GLFW_KEY_END) {
            node.moveIbeamPos(node.getValue().length());
        }
        if (e.getKeyCode() == GLFW.GLFW_KEY_BACKSPACE) {
            if (node.getIbeamPosCharIdx() > 0) {
                node.setValue(
                        new StringBuilder(node.getValue())
                                .deleteCharAt(node.getIbeamPosCharIdx() - 1)
                                .toString()
                );
                node.moveIbeamPos(node.getIbeamPosCharIdx() - 1);
            }
        }
        if (e.getKeyCode() == GLFW.GLFW_KEY_DELETE) {
            if (node.getIbeamPosCharIdx() < node.getValue().length()) {
                node.setValue(
                        new StringBuilder(node.getValue())
                                .deleteCharAt(node.getIbeamPosCharIdx())
                                .toString()
                );
            }
        }
    }

    @EventHandle
    public void onTextPress(UITextInputEvent e) {
        var node = nowTextInput(e);
        if (node == null)
            return;
        var newValue = String.valueOf(Character.toChars((int) e.getCharUnicode())) +
                node.getValue().substring(node.getIbeamPosCharIdx());
        if (node.getIbeamPosCharIdx() > 0)
            newValue = node.getValue().substring(0, node.getIbeamPosCharIdx()) + newValue;
        node.setValue(newValue);
        node.moveIbeamPos(node.getIbeamPosCharIdx() + 1);
        EventManager.callEvent(new UITextInputValueChangeEvent(e.getWindow(), node));
    }

    public TextInput nowTextInput(UIEvent e) {
        var node = e.getWindow().getStage().getFocusNode();
        if (node instanceof TextInput)
            return (TextInput) node;
        return null;
    }

}
