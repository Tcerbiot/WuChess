package zhh.game.tetris.dialog;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import zhh.game.tetris.global.Config;
import zhh.game.tetris.listener.instance.ReadOnlyListener;

/**
 * "关于游戏"对话框
 * @author fuyunliang
 */
public class AboutGameDialog extends JDialog  {
	/**
	 * 串行化版本统一标识符
	 */
	private static final long serialVersionUID = -6249170602734028357L;
	
	/**
	 * "关于游戏"对话框
	 */
	public AboutGameDialog() {
		this(null, false);
	}

	/**
	 * "关于游戏"对话框
	 * @param owner Frame 对话框的所有者
	 */
	public AboutGameDialog(Frame owner) {
		this(owner, false);
	}
	
	/**
	 * "关于游戏"对话框
	 * @param owner Frame 对话框的所有者
	 * @param modal boolean 是否为模式对话框
	 */
	public AboutGameDialog(Frame owner, boolean modal) {
		super(owner, modal);

		
		// 设置窗口标题, 窗口尺寸
		setTitle("关于游戏");
		getContentPane().setLayout(null);
		setResizable(false);
		setSize(240, 295);
		setLocationRelativeTo(owner);
		
		// 增加监听器处理窗口关闭事件
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		// 边框
		final Border lineBorder = new LineBorder(
				Config.CURRENT.getBorderColor(), 1, false);

		// 只读监听器实例, 实现文本框的只读功能
		final ReadOnlyListener readOnlyListener = new ReadOnlyListener();
		// 标题风格
		final SimpleAttributeSet title = new SimpleAttributeSet();
		StyleConstants.setForeground(title, Color.BLUE);
		// StyleConstants.setBold(title, true); // 粗体实在太难看了
		StyleConstants.setFontSize(title, 14);
		// 子标题风格
		final SimpleAttributeSet subtitle = new SimpleAttributeSet();
		StyleConstants.setForeground(subtitle, Color.BLUE);
		StyleConstants.setFontSize(subtitle, 13);
		// 正文风格
		final SimpleAttributeSet normal = new SimpleAttributeSet();
		StyleConstants.setFontSize(normal, 13);
		
		final JTextPane txtAboutGame = new JTextPane();
		txtAboutGame.addKeyListener(readOnlyListener);

		try {
			Document doc = txtAboutGame.getDocument();
			String about = "俄罗斯方块游戏\n\n";
			doc.insertString(doc.getLength(), about, title);
			about = "游戏简介：\n";
			doc.insertString(doc.getLength(), about, subtitle);
			about = new StringBuffer()
				.append("俄罗斯方块是一款益智方块类游戏，\n")
				.append("这款游戏最初是由苏联的电脑科学家帕吉特诺夫(Alex Pajitnov)于1985年制作的，\n")
				.append("作者给了他一个源自希腊字4(tetra)的名字Tetris。\n")
				.append("1989年由任天堂于发行GameBoy版，推出后风靡全球，")
				.append("成为益智方块类型游戏中知名度最高的一款。\n")
				.append("它看似简单但却变化无穷，上手极其容易，")
				.append("但是要熟练地掌握其中的操作与摆放技巧，难度却不低。\n")
				.append("\n")
				.toString();
			doc.insertString(doc.getLength(), about, normal);
			about = "玩法简介：\n";
			doc.insertString(doc.getLength(), about, subtitle);
			about = new StringBuffer()
				.append("游戏具有一个用于摆放小方块的平面虚拟场地，\n")
				.append("一组由几个小方块组成的规则形状(Tetromino)，\n")
				.append("游戏每次随机输出一种形状到场地顶部，自动以一定的速度下落，\n")
				.append("用户在形状的过程中可以控制形状的左右移动及旋转以将形状填充到场地中，\n")
				.append("直至形状下落至场地底部或被场地中已有的方块阻挡而不能再下落，\n")
				.append("游戏再次输出一个形状，周而复始。\n")
				.append("如果这次填充将场地的一行或多行完全填满，则组成这些行的所有方块将被消除，\n")
				.append("并且以此来换取一定的积分奖励，\n")
				.append("而未被消除的方块会一直累积，并对后来的形状摆放造成各种影响，\n")
				.append("如果下一个形状的输出位置已经被未消除的方块所占据，则游戏结束。")
				.toString();
			doc.insertString(doc.getLength(), about, normal);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		txtAboutGame.setCaretPosition(0);
		
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 214, 209);
		scrollPane.setViewportView(txtAboutGame);
		scrollPane.setBorder(lineBorder);
		getContentPane().add(scrollPane);
		
		// 分隔符
		final JSeparator separator = new JSeparator();
		separator.setBounds(10, 225, 214, 2);
		getContentPane().add(separator);

		// "确定"按钮
		final JButton btnOk = new JButton();
		btnOk.setText("确定(O)");
		btnOk.setMnemonic('O');
		btnOk.setBounds(61, 233, 128, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnOk);
		
		// 确定按钮获取焦点
		addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent evt) {
            	btnOk.requestFocus();
            }
        });

	}
	
	
}


