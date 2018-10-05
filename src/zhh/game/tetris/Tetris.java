package zhh.game.tetris;

import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import zhh.game.tetris.global.Config;
import zhh.game.tetris.view.TetrisFrame;

/**
 * 俄罗斯方块游戏<br>
 * <br>
 * 游戏简介:<br>
 * 俄罗斯方块是一款益智方块游戏<br>
 * 这款游戏最初是由苏联的电脑科学家帕吉特诺夫(Alex Pajitnov)于1985年制作的<br>
 * 作者给了他一个源自希腊字4(tetra)的名字Tetris<br>
 * 1989年由任天堂于发行GameBoy版, 推出后风靡全球, 
 * 成为益智方块类型游戏中知名度最高的一款<br>
 * 它看似简单但却变化无穷, 上手极其容易, 
 * 但是要熟练地掌握其中的操作与摆放技巧, 难度却不低<br>
 * <br>
 * 玩法简介:<br>
 * 游戏具有一个用于摆放小方块的平面虚拟场地<br>
 * 一组由几个小方块组成的规则形状(Tetromino)<br>
 * 游戏每次随机输出一种形状到场地顶部, 自动以一定的速度下落<br>
 * 用户在形状的过程中可以控制形状的左右移动及旋转以将形状填充到场地中<br>
 * 直至形状下落至场地底部或被场地中已有的方块阻挡而不能再下落<br>
 * 游戏再次输出一个形状, 周而复始<br>
 * 如果这次填充将场地的一行或多行完全填满, 则组成这些行的所有方块将被消除<br>
 * 并且以此来换取一定的积分奖励<br>
 * 而未被消除的方块会一直累积, 并对后来的形状摆放造成各种影响<br>
 * 如果下一个形状的输出位置已经被未消除的方块所占据，则游戏结束<br>
 * @author Tcerbiot
 */
public class Tetris {

	@SuppressWarnings("rawtypes")
	public static void main(String args[]) {
		try {
			// 更改应用程序的默认外观
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			
			// 更改应用程序的默认字体
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

