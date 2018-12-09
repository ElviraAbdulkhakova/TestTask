package utils;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.log4j.Logger;

public class KeyboardHelper {
	static final Logger logger = Logger.getLogger(KeyboardHelper.class);
	
	public void holdKeyboardKeys(int[] keys) throws Exception {
		Robot robot = new Robot();
		if (keys.length > 0) {
			for (int i = 0; i < keys.length; i++) {
				robot.keyPress(keys[i]);
			}

			for (int i = 0; i < keys.length; i++) {
				robot.keyRelease(keys[i]);
			}
		}
	}
	
	public void pressPageDown() throws Exception{
		holdKeyboardKeys(new int[] {KeyEvent.VK_PAGE_DOWN});
		logger.info("Press pageDown");
	}
	
}
