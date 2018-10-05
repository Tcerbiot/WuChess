package zhh.game.tetris.controller;

import java.awt.Color;

import javax.swing.JComponent;

import zhh.game.tetris.global.Config;
import zhh.game.tetris.global.Utilities;

/**
 * ��˸������<br>
 * �ں�̨����һ���߳�<br>
 * ���� wink() �����������˸�б�<br>
 * �������̨�߳�ʹ������˸<br>
 * �Ƚ�ԭ����ɫ��Ϊ��˸����ɫ, ��ͣƬ��, <br>
 * �ٻ�ԭԭ����ɫΪԭ������ɫ, ����ͣƬ��, ��Ϊһ����˸<br>
 * @author fuyunliang
 */
public class WinkController {
	/**
	 * ��˸�Ĳ�����<br>
	 * ���һ���߳���һ����Ϊ��˸����ɫ����ԭΪԭ������ɫ�������ڼ�, <br>
	 * ���µ��߳̽���, <br>
	 * ��ôֻ�򵥽���������Ϊ����µ��߳�, �µ��̲߳��������õ�����<br>
	 * (��Ҫ���ڱ����û���ͣ ��ʼ/������Ϸ ��ɵĻ���)<br>
	 */
	private static WinkThread operator;
	
	/**
	 * �Ƿ�����˸���������ڼ�<br>
	 * ��һ����Ϊ��˸����ɫ����ԭΪԭ������ɫ�������ڼ�, �������̲߳���ͬʱ����, �������ɻ���<br>
	 * ���������˸���������ڼ�, ���������ߵȴ�<br>
	 */
	private static boolean running;
	
	/**
	 * Ĭ����˸�Ĵ���
	 */
	private static final int defaultWinkCount = 5;
	
	/**
	 * Ĭ�϶���ԭ����ɫ
	 */
	private static final Color defaultBackgroundColor = 
		Config.CURRENT.getBackgroundColor();
	
	/**
	 * Ĭ����˸����ɫ
	 */
	private static final Color defaultWinkColor = new Color(0xCCCCFF);
	
	/**
	 * ��˸�߳�
	 */
	private final WinkThread winkThread;

	/**
	 * ��˸������
	 */
	public WinkController() {
		winkThread = new WinkThread();
		operator = winkThread;
		winkThread.start();
	}

	/**
	 * ��˸<br>
	 * ʹ����ָ�������ж�����Ĭ����ɫ��˸Ĭ�ϴ���<br>
	 * ����ʱ�����������˸�б�<br>
	 * �������̨�߳�ʹ������˸<br>
	 * @param list JComponent[] ��˸�Ķ���
	 */
	public void wink(JComponent[] list) {
		winkThread.wink(list, defaultBackgroundColor, defaultWinkColor, defaultWinkCount);
	}

	/**
	 * ��˸<br>
	 * ʹ����ָ���Ķ�����Ĭ����ɫ��˸Ĭ�ϴ���<br>
	 * ����ʱ�����������˸�б�<br>
	 * �������̨�߳�ʹ������˸<br>
	 * @param object JComponent ��˸�Ķ���
	 */
	public void wink(JComponent object) {
		winkThread.wink(object, defaultBackgroundColor, defaultWinkColor, defaultWinkCount);
	}

	/**
	 * ��˸<br>
	 * ʹ����ָ�������ж�����ָ����ɫ��˸ָ������<br>
	 * @param list JComponent[] ��˸�Ķ���
	 * @param backgroundColor Color ԭ����ɫ
	 * @param winkColor Color ��˸����ɫ
	 * @param winkCount int ��˸�Ĵ���
	 */
	public void wink(JComponent[] list, Color backgroundColor, Color winkColor, int winkCount) {
		winkThread.wink(list, backgroundColor, winkColor, winkCount);
	}

	/**
	 * ��˸<br>
	 * ʹ����ָ���Ķ�����ָ����ɫ��˸ָ������<br>
	 * @param object JComponent ��˸�Ķ���
	 * @param backgroundColor Color ԭ����ɫ
	 * @param winkColor Color ��˸����ɫ
	 * @param winkCount int ��˸�Ĵ���
	 */
	public void wink(JComponent object, Color backgroundColor, Color winkColor, int winkCount) {
		winkThread.wink(object, backgroundColor, winkColor, winkCount);
	}
	
	/**
	 * ����<br>
	 * ���� dispose() ֮��, �Ͳ�����ʹ�øö���
	 */
	public void dispose() {
		winkThread.kill();
	}
	
	/**
	 * ��˸�߳�<br>
	 * ʹ������˸��ʵ��ִ����
	 * @author fuyunliang
	 */
	private class WinkThread extends Thread {
		
		/**
		 * ���߳��Ƿ����еı�־<br>
		 * ��Ϊfalse, ���������<br>
		 */
		private boolean run;
		
		/**
		 * ��˸���󼰲����ĳ�
		 * [i][0]: JComponent ��˸�Ķ���
		 * [i][1]: Color ����ԭ���ı�����ɫ
		 * [i][2]: Color ��˸����ɫ
		 * [i][3]: Integer ��˸�Ĵ���
		 */
		private Object[][] pool;
		
		/**
		 * ��˸�����б���������˸����
		 */
		private int maxWinkCount;
		
		/**
		 * ��˸�߳�
		 */
		public WinkThread() {
			super();
			run = true;
			setDaemon(true);
		}
		
		/**
		 * ���ٸ��߳�
		 */
		public void kill() {
			run = false;
			synchronized(this) {
				notify();
			}
		}
		
		/**
		 * �߳����з���<br>
		 * ������˸�����б�, ʹ������˸ָ������<b>
		 * ����������ִ�����֮��, ���ڵȴ�״̬<br>
		 */
		public void run() {
			while(run) {
				// ��˸ָ������
				while(!running && operator == this && maxWinkCount > 0) {
					running = true; // ��ʼһ����˸
					
					// ����ԭ����ɫΪ��˸��ɫ
					int length = pool == null ? 0 : pool.length;
					for(int i = 0; i < length; i++) {
						if(((Integer)pool[i][3]).intValue() > 0) {
							((JComponent)pool[i][0]).setBackground((Color)pool[i][2]);
						}
					}
					try {
						Thread.sleep(Config.CURRENT.getWinkPauseTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// ��ԭԭ����ɫΪԭ������ɫ
					for (int i = 0; i < length; i++) {
						int winkCount = ((Integer) pool[i][3]).intValue();
						if (winkCount > 0) {
							((JComponent) pool[i][0]).setBackground((Color) pool[i][1]);
							// ���ĵ�һ�������˸������
							pool[i][3] = new Integer(winkCount - 1);
						}
					}
					// �����ܼ�����
					maxWinkCount--;
					try {
						Thread.sleep(Config.CURRENT.getWinkPauseTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					running = false; // ������˸
				}
				if(!run) break;
				// ��������ִ�����, �ȴ�
				synchronized(this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			pool = null;
			if(operator == this) {
				operator = null;
			}
			// ֪ͨ�ȴ�������
			if(!running && operator != this && operator != null) {
				synchronized(operator) {
					operator.notify();
				}
			}
		}
		
		/**
		 * ������˸����
		 * @param object JComponent ��˸�Ķ���
		 * @param backgroundColor Color ԭ����ɫ
		 * @param winkColor Color ��˸����ɫ
		 * @param winkCount int ��˸�Ĵ���
		 */
		private void addWinkObject(JComponent object, Color backgroundColor, Color winkColor, 
				int winkCount) {
			if(pool == null) pool = new Object[0][];
			pool = (Object[][])Utilities.arrayAddItem(pool, new Object[] {
					object, 
					backgroundColor,
					winkColor,
					new Integer(winkCount)
			});
			if (maxWinkCount < winkCount)
				maxWinkCount = winkCount;
		}

		/**
		 * ������˸����
		 * @param index int ��˸��������
		 * @param winkCount int ��˸����
		 */
		private void addWinkCount(int index, int winkCount) {
			int newWinkCount = ((Integer)pool[index][3]).intValue() + winkCount;
			pool[index][3] = new Integer(newWinkCount);
			if (maxWinkCount < newWinkCount)
				maxWinkCount = newWinkCount;
		}
		
		/**
		 * ������˸�б���ڲ�ִ�з���
		 * @param object JComponent ��˸�Ķ���
		 * @param backgroundColor Color ԭ����ɫ
		 * @param winkColor Color ��˸����ɫ
		 * @param winkCount int ��˸�Ĵ���
		 */
		private void _wink(JComponent object, Color backgroundColor, Color winkColor, 
				int winkCount) {
			if(object == null) return;
			
			boolean contain = false;
			int length = pool == null ? 0 : pool.length;
			for(int i = 0; i < length; i++) {
				if(pool[i][0] == object) {
					pool[i][1] = backgroundColor;
					pool[i][2] = winkColor;
					addWinkCount(i, winkCount);
					contain = true;
					break;
				}
			}
			if(!contain) {
				addWinkObject(object, backgroundColor, winkColor, winkCount);
			}
		}
		
		/**
		 * ��ָ�����������˸�б�<br>
		 * �������̨�߳�ʹ������˸<br>
		 * @param object JComponent ��˸�Ķ���
		 * @param backgroundColor Color ԭ����ɫ
		 * @param winkColor Color ��˸����ɫ
		 * @param winkCount int ��˸�Ĵ���
		 */
		public void wink(JComponent object, Color backgroundColor, Color winkColor, 
				int winkCount) {
			_wink(object, backgroundColor, winkColor, winkCount);
			synchronized(this) {
				notify();
			}
		}
		
		/**
		 * ��ָ�������ж��������˸�б�<br>
		 * �������̨�߳�ʹ������˸<br>
		 * @param list JComponent[] ��˸�Ķ���
		 * @param backgroundColor Color ԭ����ɫ
		 * @param winkColor Color ��˸����ɫ
		 * @param winkCount int ��˸�Ĵ���
		 */
		public void wink(JComponent[] list, Color backgroundColor, Color winkColor, 
				int winkCount) {
			if(list == null) return;
			int length = list.length;
			for(int i = 0; i < length; i++) {
				_wink(list[i], backgroundColor, winkColor, winkCount);
			}
			synchronized(this) {
				notify();
			}
		}
	}
}


