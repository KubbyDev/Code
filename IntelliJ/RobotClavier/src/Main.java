import java.awt.*;
import java.awt.event.KeyEvent;

public class Main {

    public static Robot robot = null;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {e.printStackTrace();}
    }

    public static void main(String... args) {

        robot.delay(2000);

        for(int i = 0; i < 30; i++) {
            press("Toi\n");
        }

    }

    public static void press(String keys) {
        for (char c : keys.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                throw new RuntimeException(
                        "Key code not found for character '" + c + "'");
            }
            robot.keyPress(keyCode);
            robot.delay(20);
            robot.delay((int) (Math.random()*50));
            robot.keyRelease(keyCode);
            robot.delay(20);
            robot.delay((int) (Math.random()*50));
        }
    }
}