package zhh.game.tetris;

import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import zhh.game.tetris.global.Config;
import zhh.game.tetris.view.TetrisFrame;

/**
 * ����˹������Ϸ<br>
 * <br>
 * ��Ϸ���:<br>
 * ����˹������һ�����Ƿ�����Ϸ<br>
 * �����Ϸ������������ĵ��Կ�ѧ��������ŵ��(Alex Pajitnov)��1985��������<br>
 * ���߸�����һ��Դ��ϣ����4(tetra)������Tetris<br>
 * 1989�����������ڷ���GameBoy��, �Ƴ������ȫ��, 
 * ��Ϊ���Ƿ���������Ϸ��֪������ߵ�һ��<br>
 * �����Ƽ򵥵�ȴ�仯����, ���ּ�������, 
 * ����Ҫ�������������еĲ�����ڷż���, �Ѷ�ȴ����<br>
 * <br>
 * �淨���:<br>
 * ��Ϸ����һ�����ڰڷ�С�����ƽ�����ⳡ��<br>
 * һ���ɼ���С������ɵĹ�����״(Tetromino)<br>
 * ��Ϸÿ��������һ����״�����ض���, �Զ���һ�����ٶ�����<br>
 * �û�����״�Ĺ����п��Կ�����״�������ƶ�����ת�Խ���״��䵽������<br>
 * ֱ����״���������صײ��򱻳��������еķ����赲������������<br>
 * ��Ϸ�ٴ����һ����״, �ܶ���ʼ<br>
 * ��������佫���ص�һ�л������ȫ����, �������Щ�е����з��齫������<br>
 * �����Դ�����ȡһ���Ļ��ֽ���<br>
 * ��δ�������ķ����һֱ�ۻ�, ���Ժ�������״�ڷ���ɸ���Ӱ��<br>
 * �����һ����״�����λ���Ѿ���δ�����ķ�����ռ�ݣ�����Ϸ����<br>
 * @author fuyunliang
 */
public class Tetris {

	@SuppressWarnings("rawtypes")
	public static void main(String args[]) {
		try {
			// ����Ӧ�ó����Ĭ�����
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			
			// ����Ӧ�ó����Ĭ������
			Enumeration keys = UIManager.getDefaults().keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				Object value = UIManager.get(key);
				if (value instanceof FontUIResource)
					UIManager.put(key, new FontUIResource(Config.CURRENT.getDefaultFont()));
			}

			TetrisFrame tetris = new TetrisFrame();
			tetris.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

