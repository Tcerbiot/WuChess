package zhh.game.tetris.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

import zhh.game.tetris.global.Config;
import zhh.game.tetris.global.Utilities;
import zhh.game.tetris.listener.ConfigListener;

public class ViewSetDialog extends JDialog{
	/**
	 * 串行化版本统一标识符
	 */
	private static final long serialVersionUID = 5027145225657888387L;

	/**
	 * 相同类型控件的总数
	 */
	private final int controlCount = 5;

	/**
	 * 控件用于存储序号的 key<br>
	 * 序号存储于控件的 ClientProperty 属性中<br>
	 */
	private final String controlIndexKey = "INDEX";
	
	/**
	 * 否定方的处理器
	 */
	private final ChooseHandler handlerFalse = new ChooseHandler(false);
	
	/**
	 * 用户的选择
	 */
	private boolean[] choose;
	
	/**
	 * 配置监听器
	 */
	private ConfigListener[] configListeners;
	
	/**
	 * 游戏显示设置对话框
	 */
	public ViewSetDialog() {
		this(null, false);
	}

	/**
	 * 游戏显示设置对话框
	 * @param owner Frame 对话框的所有者
	 */
	public ViewSetDialog(Frame owner) {
		this(owner, false);
	}
	
	/**
	 * 游戏显示设置对话框
	 * @param owner Frame 对话框的所有者
	 * @param modal boolean 是否为模式对话框
	 */
	public ViewSetDialog(Frame owner, boolean modal) {
		super(owner, modal);
		
		// 设置窗口标题, 窗口尺寸
		setTitle("显示设置");
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
		
		// 初始化选择与配置一致
		choose = new boolean[controlCount];
		choose[0] = Config.CURRENT.isShowGridLine();
		choose[1] = Config.CURRENT.isShowPreviewGridLine();
		choose[2] = Config.CURRENT.isSupportColorfulShape();
		choose[3] = Config.CURRENT.isSupportColorfulGround();
		choose[4] = Config.CURRENT.isSupportColorfulPreview();
		
		// 提示标签的文本
		final String[] labelText = {"游戏区是否显示网格:", "预览区是否显示网格:", 
				"形状是否支持彩色:", "障碍物是否支持彩色:", "预览是否支持彩色:"};
		// 提示标签
		final JLabel[] labels = new JLabel[controlCount];
		// 选择框
		ButtonGroup[] buttonGroup = new ButtonGroup[controlCount];
		// 肯定方的选择框
		final JRadioButton[] rdoTrue = new JRadioButton[controlCount];
		// 否定方的选择框
		final JRadioButton[] rdoFalse = new JRadioButton[controlCount];
		// 肯定方的处理器
		final ChooseHandler handlerTrue = new ChooseHandler(true);
		for(int i = 0; i < controlCount; i++) {
			// 设置提示标签
			labels[i] = new JLabel();
			labels[i].setText(labelText[i]);
			labels[i].setBounds(10, 10 + i * 36, 210, 15);
			getContentPane().add(labels[i]);
			
			// 设置选择框
			rdoTrue[i] = new JRadioButton();
			rdoTrue[i].setText(i < 2 ? "显示" : "彩色");
			rdoTrue[i].setBounds(30, 25 + i * 36, 85, 19);
			rdoTrue[i].putClientProperty(controlIndexKey, new Integer(i));
			rdoTrue[i].addActionListener(handlerTrue);
			getContentPane().add(rdoTrue[i]);

			rdoFalse[i] = new JRadioButton();
			rdoFalse[i].setText(i < 2 ? "不显示" : "单色");
			rdoFalse[i].setBounds(126, 25 + i * 36, 85, 19);
			rdoFalse[i].putClientProperty(controlIndexKey, new Integer(i));
			rdoFalse[i].addActionListener(handlerFalse);
			getContentPane().add(rdoFalse[i]);

			// 同一序号的肯定方选择框与否定方选择框为一组
			buttonGroup[i] = new ButtonGroup();
			buttonGroup[i].add(rdoTrue[i]);
			buttonGroup[i].add(rdoFalse[i]);
			
			// 初始选择状态
			if(choose[i])
				rdoTrue[i].setSelected(true);
			else
				rdoFalse[i].setSelected(true);
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
				choose[0] = Config.DEFAULT.isShowGridLine();
				choose[1] = Config.DEFAULT.isShowPreviewGridLine();
				choose[2] = Config.DEFAULT.isSupportColorfulShape();
				choose[3] = Config.DEFAULT.isSupportColorfulGround();
				choose[4] = Config.DEFAULT.isSupportColorfulPreview();
				// 更新选择状态
				for(int i = 0; i < controlCount; i++) {
					if(choose[i]) {
						rdoTrue[i].setSelected(true);
					} else {
						rdoFalse[i].setSelected(true);
					}
				}
			}
		});
		getContentPane().add(btnRestoration);

		// "确定"按钮
		final JButton btnOk = new JButton();
		btnOk.setText("确定(O)");
		btnOk.setMnemonic('O');
		btnOk.setBounds(10, 233, 128, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 检查配置是否已经被改变
				if(Config.CURRENT.isShowGridLine() != choose[0] ||
		        		Config.CURRENT.isShowPreviewGridLine() != choose[1] ||
		        		Config.CURRENT.isSupportColorfulShape() != choose[2] || 
		        		Config.CURRENT.isSupportColorfulGround() != choose[3] ||
		        		Config.CURRENT.isSupportColorfulPreview() != choose[4]) {
					// 更新配置
					Config.CURRENT.setShowGridLine(choose[0]);
					Config.CURRENT.setShowPreviewGridLine(choose[1]);
					Config.CURRENT.setSupportColorfulShape(choose[2]);
					Config.CURRENT.setSupportColorfulGround(choose[3]);
					Config.CURRENT.setSupportColorfulPreview(choose[4]);
					int length = configListeners == null ? 0 : configListeners.length;
					for (int i = 0; i < length; i++) {
						configListeners[i].viewConfigChanged();
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
    
	
	/**
	 * 用户改变选择时的处理器
	 * @author zhaohuihua
	 */
	private class ChooseHandler implements ActionListener {
		/**
		 * 该处理器是支持肯定方还是否定方的标志
		 */
		private boolean choice;
		
		public ChooseHandler(boolean choice) {
			this.choice = choice;
		}
		
		/**
		 * 用户选择时的处理
		 */
		public void actionPerformed(ActionEvent e) {
			// 根据选择框的序号, 更新对应的记录用户选择的变量值
			JComponent source = (JComponent)e.getSource();
			int index = ((Integer)source.getClientProperty(controlIndexKey)).intValue();
			choose[index] = choice;
		}
	}
}



