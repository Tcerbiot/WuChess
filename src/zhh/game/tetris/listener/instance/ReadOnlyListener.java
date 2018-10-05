package zhh.game.tetris.listener.instance;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 只读监听器实例
 * @author Tcerbiot
 */
public class ReadOnlyListener implements  KeyListener {
	public void keyPressed(KeyEvent e) {
		if(e.isAltDown()) 
			return;
		int keyCode = e.getKeyCode();
		// 只允许复制和光标移动
		if (keyCode != KeyEvent.VK_C && keyCode != KeyEvent.VK_A 
				&& keyCode != KeyEvent.VK_PAGE_UP
				&& keyCode != KeyEvent.VK_PAGE_DOWN
				&& keyCode != KeyEvent.VK_LEFT && keyCode != KeyEvent.VK_RIGHT 
				&& keyCode != KeyEvent.VK_UP && keyCode != KeyEvent.VK_DOWN 
				&& keyCode != KeyEvent.VK_HOME && keyCode != KeyEvent.VK_END)
			e.consume();
	}

	public void keyReleased(KeyEvent e) {
		// 禁止所有操作
		e.consume();
	}

	public void keyTyped(KeyEvent e) {
		// 禁止输入
		e.consume();
	}
}


