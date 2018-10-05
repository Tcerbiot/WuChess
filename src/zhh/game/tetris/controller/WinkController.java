package zhh.game.tetris.controller;

import java.awt.Color;

import javax.swing.JComponent;

import zhh.game.tetris.global.Config;
import zhh.game.tetris.global.Utilities;

/**
 * 闪烁控制器<br>
 * 在后台运行一个线程<br>
 * 调用 wink() 将对象加入闪烁列表<br>
 * 并激活后台线程使对象闪烁<br>
 * 先将原背景色设为闪烁的颜色, 暂停片刻, <br>
 * 再还原原背景色为原来的颜色, 再暂停片刻, 即为一次闪烁<br>
 * @author fuyunliang
 */
public class WinkController {
	/**
	 * 闪烁的操作者<br>
	 * 如果一个线程在一次设为闪烁的颜色至还原为原来的颜色的运行期间, <br>
	 * 有新的线程进入, <br>
	 * 那么只简单将操作者置为这个新的线程, 新的线程不能立即得到运行<br>
	 * (主要用于避免用户不停 开始/结束游戏 造成的混乱)<br>
	 */
	private static WinkThread operator;
	
	/**
	 * 是否处于闪烁正在运行期间<br>
	 * 在一次设为闪烁的颜色至还原为原来的颜色的运行期间, 其他的线程不得同时操作, 否则会造成混乱<br>
	 * 如果处于闪烁正在运行期间, 其他操作者等待<br>
	 */
	private static boolean running;
	
	/**
	 * 默认闪烁的次数
	 */
	private static final int defaultWinkCount = 5;
	
	/**
	 * 默认对象原背景色
	 */
	private static final Color defaultBackgroundColor = 
		Config.CURRENT.getBackgroundColor();
	
	/**
	 * 默认闪烁的颜色
	 */
	private static final Color defaultWinkColor = new Color(0xCCCCFF);
	
	/**
	 * 闪烁线程
	 */
	private final WinkThread winkThread;

	/**
	 * 闪烁控制器
	 */
	public WinkController() {
		winkThread = new WinkThread();
		operator = winkThread;
		winkThread.start();
	}

	/**
	 * 闪烁<br>
	 * 使参数指定的所有对象以默认颜色闪烁默认次数<br>
	 * 调用时将对象加入闪烁列表<br>
	 * 并激活后台线程使对象闪烁<br>
	 * @param list JComponent[] 闪烁的对象
	 */
	public void wink(JComponent[] list) {
		winkThread.wink(list, defaultBackgroundColor, defaultWinkColor, defaultWinkCount);
	}

	/**
	 * 闪烁<br>
	 * 使参数指定的对象以默认颜色闪烁默认次数<br>
	 * 调用时将对象加入闪烁列表<br>
	 * 并激活后台线程使对象闪烁<br>
	 * @param object JComponent 闪烁的对象
	 */
	public void wink(JComponent object) {
		winkThread.wink(object, defaultBackgroundColor, defaultWinkColor, defaultWinkCount);
	}

	/**
	 * 闪烁<br>
	 * 使参数指定的所有对象以指定颜色闪烁指定次数<br>
	 * @param list JComponent[] 闪烁的对象
	 * @param backgroundColor Color 原背景色
	 * @param winkColor Color 闪烁的颜色
	 * @param winkCount int 闪烁的次数
	 */
	public void wink(JComponent[] list, Color backgroundColor, Color winkColor, int winkCount) {
		winkThread.wink(list, backgroundColor, winkColor, winkCount);
	}

	/**
	 * 闪烁<br>
	 * 使参数指定的对象以指定颜色闪烁指定次数<br>
	 * @param object JComponent 闪烁的对象
	 * @param backgroundColor Color 原背景色
	 * @param winkColor Color 闪烁的颜色
	 * @param winkCount int 闪烁的次数
	 */
	public void wink(JComponent object, Color backgroundColor, Color winkColor, int winkCount) {
		winkThread.wink(object, backgroundColor, winkColor, winkCount);
	}
	
	/**
	 * 销毁<br>
	 * 调用 dispose() 之后, 就不能再使用该对象
	 */
	public void dispose() {
		winkThread.kill();
	}
	
	/**
	 * 闪烁线程<br>
	 * 使对象闪烁的实际执行者
	 * @author fuyunliang
	 */
	private class WinkThread extends Thread {
		
		/**
		 * 该线程是否运行的标志<br>
		 * 如为false, 则结束运行<br>
		 */
		private boolean run;
		
		/**
		 * 闪烁对象及参数的池
		 * [i][0]: JComponent 闪烁的对象
		 * [i][1]: Color 对象原来的背景颜色
		 * [i][2]: Color 闪烁的颜色
		 * [i][3]: Integer 闪烁的次数
		 */
		private Object[][] pool;
		
		/**
		 * 闪烁对象列表中最大的闪烁次数
		 */
		private int maxWinkCount;
		
		/**
		 * 闪烁线程
		 */
		public WinkThread() {
			super();
			run = true;
			setDaemon(true);
		}
		
		/**
		 * 销毁该线程
		 */
		public void kill() {
			run = false;
			synchronized(this) {
				notify();
			}
		}
		
		/**
		 * 线程运行方法<br>
		 * 遍历闪烁对象列表, 使对象闪烁指定次数<b>
		 * 在所有任务执行完毕之后, 处于等待状态<br>
		 */
		public void run() {
			while(run) {
				// 闪烁指定次数
				while(!running && operator == this && maxWinkCount > 0) {
					running = true; // 开始一次闪烁
					
					// 设置原背景色为闪烁颜色
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
					// 还原原背景色为原来的颜色
					for (int i = 0; i < length; i++) {
						int winkCount = ((Integer) pool[i][3]).intValue();
						if (winkCount > 0) {
							((JComponent) pool[i][0]).setBackground((Color) pool[i][1]);
							// 更改单一对象的闪烁计数器
							pool[i][3] = new Integer(winkCount - 1);
						}
					}
					// 更改总计数器
					maxWinkCount--;
					try {
						Thread.sleep(Config.CURRENT.getWinkPauseTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					running = false; // 结束闪烁
				}
				if(!run) break;
				// 所有任务执行完毕, 等待
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
			// 通知等待处理者
			if(!running && operator != this && operator != null) {
				synchronized(operator) {
					operator.notify();
				}
			}
		}
		
		/**
		 * 增加闪烁对象
		 * @param object JComponent 闪烁的对象
		 * @param backgroundColor Color 原背景色
		 * @param winkColor Color 闪烁的颜色
		 * @param winkCount int 闪烁的次数
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
		 * 增加闪烁次数
		 * @param index int 闪烁对象的序号
		 * @param winkCount int 闪烁次数
		 */
		private void addWinkCount(int index, int winkCount) {
			int newWinkCount = ((Integer)pool[index][3]).intValue() + winkCount;
			pool[index][3] = new Integer(newWinkCount);
			if (maxWinkCount < newWinkCount)
				maxWinkCount = newWinkCount;
		}
		
		/**
		 * 加入闪烁列表的内部执行方法
		 * @param object JComponent 闪烁的对象
		 * @param backgroundColor Color 原背景色
		 * @param winkColor Color 闪烁的颜色
		 * @param winkCount int 闪烁的次数
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
		 * 将指定对象加入闪烁列表<br>
		 * 并激活后台线程使对象闪烁<br>
		 * @param object JComponent 闪烁的对象
		 * @param backgroundColor Color 原背景色
		 * @param winkColor Color 闪烁的颜色
		 * @param winkCount int 闪烁的次数
		 */
		public void wink(JComponent object, Color backgroundColor, Color winkColor, 
				int winkCount) {
			_wink(object, backgroundColor, winkColor, winkCount);
			synchronized(this) {
				notify();
			}
		}
		
		/**
		 * 将指定的所有对象加入闪烁列表<br>
		 * 并激活后台线程使对象闪烁<br>
		 * @param list JComponent[] 闪烁的对象
		 * @param backgroundColor Color 原背景色
		 * @param winkColor Color 闪烁的颜色
		 * @param winkCount int 闪烁的次数
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


