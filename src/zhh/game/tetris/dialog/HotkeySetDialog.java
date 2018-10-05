package zhh.game.tetris.dialog;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import zhh.game.tetris.global.Config;
import zhh.game.tetris.global.Utilities;
import zhh.game.tetris.listener.ConfigListener;

/**
 * 游戏控制键设置对话框<br>
 * 以用户输入的控制键替代默认的控制键<br>
 * 1. "开始/结束"<br>
 * 2. "暂停/继续"<br>
 * 3. "左移"<br>
 * 4. "右移"<br>
 * 5. "变形"<br>
 * 6. "加速下落"<br>
 * 7. "一落到底"<br>
 * @author zhaohuihua
 */
public class HotkeySetDialog extends JDialog {
	/**
	 * 串行化版本统一标识符
	 */
	private static final long serialVersionUID = -8372498997747949041L;
	
	/**
	 * 相同类型控件的总数
	 */
	private final int controlCount = 7;

	/**
	 * 控件用于存储序号的 key<br>
	 * 序号存储于控件的 ClientProperty 属性中<br>
	 */
	private final String controlIndexKey = "INDEX";
	
	/**
	 * "确定"按钮
	 */
	private final JButton btnOk;

	/**
	 * 默认的背景颜色
	 */
	private final Color background;
	
	/**
	 * 用于获取输入的所有文本框
	 */
	private JTextField[] textFields;
	
	/**
	 * 所有控制键的内容
	 */
	private int[] keyCodes;

	/**
	 * 配置监听器
	 */
	private ConfigListener[] configListeners;
	
	/**
	 * 游戏控制键设置对话框
	 */
	public HotkeySetDialog() {
		this(null, false);
	}

	/**
	 * 游戏控制键设置对话框
	 * @param owner Frame 对话框的所有者
	 */
	public HotkeySetDialog(Frame owner) {
		this(owner, false);
	}
	
	/**
	 * 游戏控制键设置对话框
	 * @param owner Frame 对话框的所有者
	 * @param modal boolean 是否为模式对话框
	 */
	public HotkeySetDialog(Frame owner, boolean modal) {
		super(owner, modal);
		
		// 设置窗口标题, 窗口尺寸
		setTitle("控制键设置");
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

		// 初始化控制键的内容, 与现有控制键相同
		keyCodes = new int[controlCount];
		keyCodes[0] = Config.CURRENT.getStartKey();
		keyCodes[1] = Config.CURRENT.getPauseKey();
		keyCodes[2] = Config.CURRENT.getLeftKey();
		keyCodes[3] = Config.CURRENT.getRightKey();
		keyCodes[4] = Config.CURRENT.getRotateKey();
		keyCodes[5] = Config.CURRENT.getDownKey();
		keyCodes[6] = Config.CURRENT.getSwiftKey();
		
		// 文本框控件的按键处理器
		final KeyAdapter handler = new KeyAdapter() {
			/**
			 * 用户按键时的处理
			 */
			public void keyPressed(KeyEvent e) {
				if(e.isAltDown() || e.isControlDown() || e.isShiftDown()) 
					return;
				// 获取用户输入的键盘字符, 作为新的控制键
				int keyCode = e.getKeyCode();
				JTextField source = (JTextField)e.getSource();
				int index = ((Integer)source.getClientProperty(controlIndexKey)).intValue();
				if(keyCode > 0) {
					((JTextField)e.getSource()).setText(KeyEvent.getKeyText(keyCode));
					keyCodes[index] = keyCode;
				}
				
				// 检查控制键相互之间是否冲突
				boolean collisional = checkCollisional();
				
				// 只有在控制键相互之间不冲突的时候才能修改
				btnOk.setEnabled(!collisional);
				
				// 销毁该按键事件, 避免继续传播
				e.consume();
			}
			public void keyTyped(KeyEvent e) {
				// 不处理按键文本输入
				e.consume();
			}
		};
		
		// 提示标签的文本
		final String[] labelText = {"开始/结束:", "暂停/继续:", "左移:", "右移:", "变形:", 
				"加速下落:", "一落到底:"};
		// 文本框边框
		final Border txtBorder = new LineBorder(
				Config.CURRENT.getBorderColor(), 1, false);
		// 提示标签
		final JLabel[] labels = new JLabel[controlCount];
		// 获取输入的文本框
		textFields = new JTextField[controlCount];
		for(int i = 0; i < controlCount; i++) {
			// 设置提示标签
			labels[i] = new JLabel();
			labels[i].setText(labelText[i]);
			labels[i].setBounds(10, 10 + i * 27, 100, 15);
			getContentPane().add(labels[i]);
			
			// 设置文本框
			textFields[i] = new JTextField();
			textFields[i].setText(KeyEvent.getKeyText(keyCodes[i]));
			textFields[i].setBounds(114, 7 + i * 27, 110, 21);
			textFields[i].setBorder(txtBorder);
			textFields[i].setHorizontalAlignment(SwingConstants.CENTER);
			textFields[i].putClientProperty(controlIndexKey, new Integer(i));
			textFields[i].addKeyListener(handler);
			getContentPane().add(textFields[i]);
		}
		
		// 分隔符
		final JSeparator separator = new JSeparator();
		separator.setBounds(10, 196, 214, 2);
		getContentPane().add(separator);

		// "恢复"按钮
		final JButton btnRestoration = new JButton();
		btnRestoration.setText("恢复(R)");
		btnRestoration.setMnemonic('R');
		btnRestoration.setToolTipText("恢复为应用程序的默认状态");
		btnRestoration.setBounds(10, 204, 128, 23);
		btnRestoration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 恢复为应用程序的默认控制键
				keyCodes[0] = Config.DEFAULT.getStartKey();
				keyCodes[1] = Config.DEFAULT.getPauseKey();
				keyCodes[2] = Config.DEFAULT.getLeftKey();
				keyCodes[3] = Config.DEFAULT.getRightKey();
				keyCodes[4] = Config.DEFAULT.getRotateKey();
				keyCodes[5] = Config.DEFAULT.getDownKey();
				keyCodes[6] = Config.DEFAULT.getSwiftKey();
				// 更新文本框
				for(int i = 0; i < controlCount; i++) {
					textFields[i].setText(KeyEvent.getKeyText(keyCodes[i]));
					textFields[i].setBackground(background);
				}
				btnOk.setEnabled(true);
			}
		});
		getContentPane().add(btnRestoration);

		// "确定"按钮
		btnOk = new JButton();
		btnOk.setText("确定(O)");
		btnOk.setMnemonic('O');
		btnOk.setBounds(10, 233, 128, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 检查控制键是否已经被改变
				if(Config.CURRENT.getStartKey() != keyCodes[0]
		                || Config.CURRENT.getPauseKey() != keyCodes[1]
            			|| Config.CURRENT.getLeftKey() != keyCodes[2]
            			|| Config.CURRENT.getRightKey() != keyCodes[3]
            			|| Config.CURRENT.getRotateKey() != keyCodes[4]
            			|| Config.CURRENT.getDownKey() != keyCodes[5]
            			|| Config.CURRENT.getSwiftKey() != keyCodes[6]) {
					// 更新控制键
					Config.CURRENT.setStartKey(keyCodes[0]);
					Config.CURRENT.setPauseKey(keyCodes[1]);
					Config.CURRENT.setLeftKey(keyCodes[2]);
					Config.CURRENT.setRightKey(keyCodes[3]);
					Config.CURRENT.setRotateKey(keyCodes[4]);
					Config.CURRENT.setDownKey(keyCodes[5]);
					Config.CURRENT.setSwiftKey(keyCodes[6]);
					int length = configListeners == null ? 0 : configListeners.length;
					for (int i = 0; i < length; i++) {
						configListeners[i].hotkeyConfigChanged();
					}
					Config.CURRENT.save();
				}
				dispose();
			}
		});
		getContentPane().add(btnOk);

		// "取消"按钮
		final JButton btnCancel = new JButton();
		btnCancel.setText("取消(C)");
		btnCancel.setMnemonic('C');
		btnCancel.setBounds(144, 233, 80, 23);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnCancel);
		
		background = textFields[0].getBackground();
	}
	
	/**
	 * 检查控制键相互之间是否冲突<br>
	 * 将冲突的控制键用不同颜色予以标识<br>
	 */
	private boolean checkCollisional() {
		/*
		 * mapping: 记录控制键与出现位置的映射情况
		 * [i][0] KeyCode
		 * [i][1] IndexCount
		 * [i][2..n] Index
		 */
		int[][] mapping = new int[controlCount][controlCount + 2];
		
		// count: 共有多少组不同的控制键
		int count = 0;
		for(int i = 0; i < controlCount; i++) {
			// 控制键的映射情况是否已存在
			boolean existent = false;
			for(int j = 0; j < count; j++) {
				if(keyCodes[i] == mapping[j][0]) {
					// 已有该控制键的映射情况
					// 修改映射情况, 增加出现位置
					mapping[j][mapping[j][1] + 2] = i;
					mapping[j][1] ++;
					existent = true;
					break;
				}
			}
			if(!existent) {
				// 第一次遍历到该控制键, 记录映射情况
				mapping[count][0] = keyCodes[i];
				mapping[count][1] = 1;
				mapping[count][2] = i;
				count ++;
			}
		}
		
		// 控制键冲突时的标识颜色
		final Color[] colors = new Color[] {
				new Color(0xFFCCCC),
				new Color(0xCCCCFF),
				new Color(0xCCFFCC)
			};
		// index: 冲突控制键的序号, 记录已有几组冲突, 用以获取不同的颜色
		// 将每组冲突的控制键用不同颜色予以标识
		int index = 0;
		for(int i = 0; i < count; i++) {
			int indexCount = mapping[i][1];
			if(indexCount == 1)
				textFields[mapping[i][2]].setBackground(background);
			else {
				for(int j = 2; j < indexCount + 2; j++)
					textFields[mapping[i][j]].setBackground(colors[index]);
				index ++;
			}
		}
		
		// 控制键是否冲突
		return index > 0;
	}

    /**
     * 增加配置监听器
     * @param listener ConfigListener 配置监听器
     */
    public void addConfigListener(ConfigListener listener) {
    	if(configListeners == null) configListeners = new ConfigListener[]{};
    	configListeners = (ConfigListener[])Utilities.arrayAddItem(
    			configListeners, listener);
    }
    
    /**
     * 移除配置监听器
     * @param listener ConfigListener 配置监听器
     */
    public void removeConfigListener(ConfigListener listener) {
    	configListeners = (ConfigListener[])Utilities.arrayRemoveItem(
    			configListeners, listener);
    }
}



