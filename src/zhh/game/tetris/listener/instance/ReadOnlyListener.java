package zhh.game.tetris.listener.instance;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * ֻ��������ʵ��
 * @author fuyunliang
 */
public class ReadOnlyListener implements  KeyListener {
	public void keyPressed(KeyEvent e) {
		if(e.isAltDown()) 
			return;
		int keyCode = e.getKeyCode();
		// ֻ�����ƺ͹���ƶ�
		if (keyCode != KeyEvent.VK_C && keyCode != KeyEvent.VK_A 
				&& keyCode != KeyEvent.VK_PAGE_UP
				&& keyCode != KeyEvent.VK_PAGE_DOWN
				&& keyCode != KeyEvent.VK_LEFT && keyCode != KeyEvent.VK_RIGHT 
				&& keyCode != KeyEvent.VK_UP && keyCode != KeyEvent.VK_DOWN 
				&& keyCode != KeyEvent.VK_HOME && keyCode != KeyEvent.VK_END)
			e.consume();
	}

	public void keyReleased(KeyEvent e) {
		// ��ֹ���в���
		e.consume();
	}

	public void keyTyped(KeyEvent e) {
		// ��ֹ����
		e.consume();
	}
}


