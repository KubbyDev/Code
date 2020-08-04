package mccmd;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class Bot {

    private Robot robot;

    public Bot() {
        try {
            robot = new Robot();
        } catch (AWTException e) { e.printStackTrace(); }
    }

    public void typeCommand(String command) {
        openChat();
        robot.delay(200);
        type(command);
        robot.delay(200);
        enter();
    }

    private void copyToClipboard(String s) {
        boolean success = false;
        while(!success) {
            try {
                StringSelection ss = new StringSelection(s);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
                success = true;
            } catch (Exception ignored) {
                robot.delay(50);
            }
        }
    }

    private void type(String s)
    {
        copyToClipboard(s);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    private void enter() {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    private void openChat() {
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_T);
    }
}
