package zhh.game.tetris.dialog;

import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import zhh.game.tetris.entity.LevelSet;
import zhh.game.tetris.entity.LevelSetFactory;
import zhh.game.tetris.global.Config;
import zhh.game.tetris.global.Utilities;
import zhh.game.tetris.listener.ConfigListener;

public class LevelSetChooseDialog extends JDialog{

	/**
	 * 串行化版本统一标识符
	 */
	private static final long serialVersionUID = 6646272036035442L;

	/**
	 * 相同类型控件的总数
	 */
	private final int controlCount = LevelSetFactory.getLevelSetCount();
	
	/**
	 * 控件用于存储序号的 key<br>
	 * 序号存储于控件的 ClientProperty 属性中<br>
	 */
	private final String controlIndexKey = "INDEX";
	
	/**
	 * 用户选择的级别集编号
	 */
	private int chooseLevelSet;
	
	/**
	 * 用户选择的级别编号
	 */
	private int chooseInitLevel;

	/**
	 * 配置监听器
	 */
	private ConfigListener[] configListeners;
	
	/**
	 * 级别选择对话框
	 */
	public LevelSetChooseDialog() {
		this(null, false, false);
	}

	/**
	 * 级别选择对话框
	 * @param owner Frame 对话框的所有者
	 */
	public LevelSetChooseDialog(Frame owner) {
		this(owner, false, false);
	}
	
	/**
	 * 级别选择对话框
	 * @param owner Frame 对话框的所有者
	 * @param modal boolean 是否为模式对话框
	 */
	public LevelSetChooseDialog(Frame owner, boolean modal) {
		this(owner, modal, false);
	}
	
	/**
	 * 级别选择对话框
	 * @param owner Frame 对话框的所有者
	 * @param modal boolean 是否为模式对话框
	 * @param isGamePlaying boolean 游戏是否正在进行中
	 */
	public LevelSetChooseDialog(Frame owner, boolean modal, boolean isGamePlaying) {
		super(owner, modal);
		
		// 设置窗口标题, 窗口尺寸
		setTitle("关卡选择");
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

		chooseLevelSet = Config.CURRENT.getCurrentLevelSet();
		chooseInitLevel = Config.CURRENT.getInitLevel();
		
		final JLabel lblLevelSet = new JLabel();
		lblLevelSet.setText("关卡选择:");
		lblLevelSet.setBounds(10, 10, 150, 15);
		getContentPane().add(lblLevelSet);

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(150, controlCount * 23));
		
		final JSlider slider = new JSlider();
		// 用户改变选择时的处理器
		final ActionListener handler = new ActionListener() {
			/**
			 * 用户选择时的处理
			 */
			public void actionPerformed(ActionEvent e) {
				// 根据选择框的序号, 更新对应的记录用户选择的变量值
				JComponent source = (JComponent)e.getSource();
				int index = ((Integer)source.getClientProperty(controlIndexKey)).intValue();
				chooseLevelSet = index;
				slider.setMaximum(LevelSetFactory.getLevelSet(chooseLevelSet).getLevelCount() - 1);
			}
		};

		// 级别集选择框
		final ButtonGroup buttonGroup = new ButtonGroup();	
		final JRadioButton[] radioButtons = new JRadioButton[controlCount];
		LevelSet[] allLevelSet = LevelSetFactory.getAllLevelSet();
		int choose = 0;
		for(int i = 0; i < controlCount; i++) {
			radioButtons[i] = new JRadioButton();
			radioButtons[i].setBounds(10, i * 23, 120, 23);
			radioButtons[i].putClientProperty(controlIndexKey, new Integer(i));
			radioButtons[i].setText(allLevelSet[i].getName());
			String description = allLevelSet[i].getDescription();
			if(description != null && description.trim().length() > 0)
				radioButtons[i].setToolTipText(description);
			// 选中当前的级别集
			if(i == Config.CURRENT.getCurrentLevelSet()) {
				radioButtons[i].setSelected(true);
				choose = i;
			}
			radioButtons[i].addActionListener(handler);
			buttonGroup.add(radioButtons[i]);
			panel.add(radioButtons[i]);
		}

		// 线条形边框
		final Border lineBorder = new LineBorder(
				Config.CURRENT.getBorderColor(), 1, false);
		// 带滚动条的面板(在级别集很多的情况下有用)
		final JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(30, 27, 194, 72);
		scrollPane.setBorder(lineBorder);
		getContentPane().add(scrollPane);

		final JLabel label = new JLabel();
		label.setText("级别选择:");
		label.setBounds(10, 109, 150, 15);
		getContentPane().add(label);

		// 级别选择时的文本提示
		final JTextField textField = new JTextField();
		textField.setText("" + (Config.CURRENT.getInitLevel() + 1));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(30, 132, 30, 23);
		textField.setBorder(lineBorder);
		textField.setFocusable(false);
		getContentPane().add(textField);
		
		// 级别选择滚动条
		slider.setBounds(66, 126, 158, 35);
		slider.setMaximum(allLevelSet[choose].getLevelCount() - 1);
		slider.setBorder(lineBorder);
		slider.setValue(Config.CURRENT.getInitLevel());
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(1);
		slider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				textField.setText("" + (value + 1));
				chooseInitLevel = value;
			}
		});
		getContentPane().add(slider);
		
		// 分隔符
		final JSeparator separator = new JSeparator();
		separator.setBounds(10, isGamePlaying ? 196 : 225, 214, 2);
		getContentPane().add(separator);

		if(isGamePlaying) {
			// "新游戏应用"按钮
			final JButton btnApplyLater = new JButton();
			btnApplyLater.setText("稍后应用(L)");
			btnApplyLater.setMnemonic('L');
			btnApplyLater.setToolTipText("不立即应用选项, 直至开始新游戏时才应用选项");
			btnApplyLater.setBounds(10, 204, 128, 23);
			btnApplyLater.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Config.CURRENT.setCurrentLevelSet(chooseLevelSet);
					Config.CURRENT.setInitLevel(chooseInitLevel);
					dispose();
					Config.CURRENT.save();
				}
			});
			getContentPane().add(btnApplyLater);
		}

		// "立即应用"按钮
		final Frame ownerAlias = owner;
		final boolean isGamePlayingAlias = isGamePlaying;
		final JButton btnApply = new JButton();
		btnApply.setText(isGamePlaying ? "立即应用(A)" : "应用(A)");
		btnApply.setMnemonic('A');
		btnApply.setBounds(10, 233, 128, 23);
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 在游戏运行过程中更改级别和级别集, 与用户确认
				if(isGamePlayingAlias) {
					int response = JOptionPane.showConfirmDialog(
						ownerAlias,
						"立即应用选项将停止当前游戏\n确定要立即应用吗?",
						"确认", 
						JOptionPane.YES_NO_OPTION);
					if(response != 0) return;
				}
				
				// 更新级别和级别集
				Config.CURRENT.setCurrentLevelSet(chooseLevelSet);
				Config.CURRENT.setInitLevel(chooseInitLevel);
				int length = configListeners == null ? 0 : configListeners.length;
				for (int i = 0; i < length; i++) {
					configListeners[i].levelConfigChanged();
				}
				dispose();
				Config.CURRENT.save();
			}
		});
		getContentPane().add(btnApply);

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
}



