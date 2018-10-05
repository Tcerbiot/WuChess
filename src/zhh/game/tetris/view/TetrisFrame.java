package zhh.game.tetris.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import zhh.game.tetris.controller.GameController;
import zhh.game.tetris.controller.WinkController;
import zhh.game.tetris.dialog.AboutAuthorDialog;
import zhh.game.tetris.dialog.AboutGameDialog;
import zhh.game.tetris.dialog.HotkeySetDialog;
import zhh.game.tetris.dialog.LevelSetChooseDialog;
import zhh.game.tetris.dialog.ViewSetDialog;
import zhh.game.tetris.entity.Level;
import zhh.game.tetris.entity.LevelSetFactory;
import zhh.game.tetris.global.Config;
import zhh.game.tetris.listener.GameListener;
import zhh.game.tetris.listener.ScoringListener;

/**
 * 游戏主界面<br>
 * @author fuyunliang
 */
public class TetrisFrame extends JFrame
		implements FocusListener, ActionListener,
		GameListener, ScoringListener {

	/**
	 * 串行化版本统一标识符
	 */
	private static final long serialVersionUID = -8223961532412482771L;

	/** 游戏控制器 */
	private final GameController controller;
	/** 游戏显示区 */
	private final GamePanel gamePanel;
	/** 预览显示区 */
	private final PreviewPanel previewPanel;
	/** 闪烁控制器 */
	private WinkController winkController;
	/** 开始/停止按钮 */
	private final JButton btnStart;
	/** 暂停/继续按钮 */
	private final JButton btnPause;
	/** 得分提示 */
	private final JLabel lblScoring;
	/** 速度提示 */
	private final JLabel lblSpeed;
	/** 级别提示 */
	private final JLabel lblLevel;
	/** 关卡提示 */
	private final JLabel lblLevelSet;
	/** 声音控制菜单 */
	private final JCheckBoxMenuItem mnuSound;
	/** 控制键设置菜单 */
	private final JMenuItem mnuHotkeySet;
	/** 显示设置菜单 */
	private final JMenuItem mnuViewSet;
	/** 关卡选择菜单 */
	private final JMenuItem mnuLevelSetChoose;
	/** 关于游戏菜单 */
	private final JMenuItem mnuAboutGame;
	/** 关于作者菜单 */
	private final JMenuItem mnuAboutAuthor;
	
	public TetrisFrame() {
		super("俄罗斯方块");
		this.getContentPane().setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		try {
			URL url = this.getClass().getResource("d:/zhh/game/tetris/resource/images/tetris.png");
			if(url != null) {
				ImageIcon icon = new ImageIcon(url);
				if(icon != null) {
					this.setIconImage(icon.getImage());
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		this.setSize(356, 463);
		this.setLocationRelativeTo(null);
		
		controller = new GameController();

		final JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBounds(0, 0, 100, 410);
		getContentPane().add(infoPanel);

		final LineBorder lineBorder = new LineBorder(
				Config.CURRENT.getBorderColor(), 1, false);
		final CompoundBorder compoundBorder = new CompoundBorder(
				lineBorder,
				new ThickBevelBorder(1, 4, 
						new Color(0xF1EFE2), new Color(0xECE9D8), 
						new Color(0x716F64), new Color(0xACA899)));
		
		previewPanel = new PreviewPanel();
		previewPanel.setBounds(10, 10, 80, 80);
		previewPanel.setBorder(compoundBorder);
		infoPanel.add(previewPanel);
		
		gamePanel = new GamePanel();
		gamePanel.setBounds(100, 0, 250, 410);
		gamePanel.setBorder(compoundBorder);
		this.getContentPane().add(gamePanel);

		controller.addScoringListener(this, true);
		controller.addGameListener(gamePanel);
		controller.addGameListener(this);
		controller.addGameViewListener(gamePanel);
		controller.addPreviewListener(previewPanel);
		gamePanel.addKeyListener(controller);
		gamePanel.addFocusListener(this);
		
		// 游戏信息
		JLabel lblLevelSetLabel = new JLabel("关卡");
		lblLevelSetLabel.setBorder(lineBorder);
		lblLevelSetLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevelSetLabel.setBounds(10, 100, 80, 19);
		infoPanel.add(lblLevelSetLabel);

		lblLevelSet = new JLabel();
		lblLevelSet.setBorder(lineBorder);
		lblLevelSet.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevelSet.setOpaque(true);
		lblLevelSet.setBackground(Config.CURRENT.getBackgroundColor());
		lblLevelSet.setBounds(10, 125, 80, 19);
		infoPanel.add(lblLevelSet);

		JLabel lblLevelLabel = new JLabel("级别");
		lblLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevelLabel.setBorder(lineBorder);
		lblLevelLabel.setBounds(10, 154, 80, 19);
		infoPanel.add(lblLevelLabel);

		lblLevel = new JLabel();
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setBorder(lineBorder);
		lblLevel.setOpaque(true);
		lblLevel.setBackground(Config.CURRENT.getBackgroundColor());
		lblLevel.setBounds(10, 175, 80, 19);
		infoPanel.add(lblLevel);

		JLabel lblScoringLabel = new JLabel("得分");
		lblScoringLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoringLabel.setBorder(lineBorder);
		lblScoringLabel.setBounds(10, 204, 80, 19);
		infoPanel.add(lblScoringLabel);

		lblScoring = new JLabel();
		lblScoring.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoring.setBorder(lineBorder);
		lblScoring.setOpaque(true);
		lblScoring.setBackground(Config.CURRENT.getBackgroundColor());
		lblScoring.setBounds(10, 225, 80, 19);
		infoPanel.add(lblScoring);

		JLabel lblSpeedLabel = new JLabel("速度");
		lblSpeedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpeedLabel.setBorder(lineBorder);
		lblSpeedLabel.setBounds(10, 254, 80, 19);
		infoPanel.add(lblSpeedLabel);
		
		lblSpeed = new JLabel();
		lblSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpeed.setBorder(lineBorder);
		lblSpeed.setOpaque(true);
		lblSpeed.setBackground(Config.CURRENT.getBackgroundColor());
		lblSpeed.setBounds(10, 275, 80, 19);
		infoPanel.add(lblSpeed);

		// 菜单
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnuSet = new JMenu();
		mnuSet.setText("设置(S)");
		mnuSet.setMnemonic('S');
		menuBar.add(mnuSet);

		JMenu mnuHelp = new JMenu();
		mnuHelp.setText("帮助(H)");
		mnuHelp.setMnemonic('H');
		menuBar.add(mnuHelp);

		mnuSound = new JCheckBoxMenuItem();
		mnuSound.setText("游戏声音(A)");
		mnuSound.setState(Config.CURRENT.isSupportSound());
		mnuSound.setMnemonic('A');
		mnuSound.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 
				InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnuSet.add(mnuSound);
		mnuSound.addActionListener(this);
		
		mnuSet.addSeparator();

		mnuHotkeySet = new JMenuItem();
		mnuHotkeySet.setText("控制键设置(C)...");
		mnuHotkeySet.setMnemonic('C');
		mnuHotkeySet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 
				InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnuSet.add(mnuHotkeySet);
		mnuHotkeySet.addActionListener(this);

		mnuViewSet = new JMenuItem();
		mnuViewSet.setText("显示设置(V)...");
		mnuViewSet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, 
				InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnuViewSet.setMnemonic('V');
		mnuSet.add(mnuViewSet);
		mnuViewSet.addActionListener(this);

		mnuLevelSetChoose = new JMenuItem();
		mnuLevelSetChoose.setText("关卡选择(L)...");
		mnuLevelSetChoose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, 
				InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnuLevelSetChoose.setMnemonic('L');
		mnuSet.add(mnuLevelSetChoose);
		mnuLevelSetChoose.addActionListener(this);

		mnuAboutGame = new JMenuItem();
		mnuHelp.add(mnuAboutGame);
		mnuAboutGame.setText("关于游戏(G)...");
		mnuAboutGame.setMnemonic('G');
		mnuAboutGame.addActionListener(this);

		mnuAboutAuthor = new JMenuItem();
		mnuHelp.add(mnuAboutAuthor);
		mnuAboutAuthor.setText("关于作者(A)...");
		mnuAboutAuthor.setMnemonic('A');
		mnuAboutAuthor.addActionListener(this);
		
		// 开始/停止按钮
		btnStart = new JButton();
		btnStart.setText("开始(B)");
		btnStart.setMnemonic('B');
		btnStart.setBounds(10, 348, 80, 23);
		infoPanel.add(btnStart);
		btnStart.addActionListener(this);

		// 暂停/继续按钮
		btnPause = new JButton();
		btnPause.setText("暂停(P)");
		btnPause.setMnemonic('P');
		btnPause.setEnabled(false);
		btnPause.setBounds(10, 377, 80, 23);
		infoPanel.add(btnPause);
		btnPause.addActionListener(this);
		
		btnStart.setFocusable(false);
		btnPause.setFocusable(false);
		gamePanel.setFocusable(true);
		menuBar.setFocusable(false);
	}
	
	public void levelChanged(Level level) {
		lblLevel.setText("" + (level.getId() + 1));
		winkController.wink(lblLevel);
	}

	public void speedChanged(int speed) {
		lblSpeed.setText("" + speed + "毫秒/格");
		winkController.wink(lblSpeed);
	}
	public void scoringChanged(int scoring, boolean levelChanged) {
		lblScoring.setText("" + scoring);
		winkController.wink(lblScoring);
	}

	public void winning(int scoring, int speed, Level level) {
		lblLevel.setText("通关");
		winkController.wink(lblLevel);
	}

	public void scoringInit(int scoring, int speed, Level level) {
		lblLevelSet.setText(LevelSetFactory.getLevelSet(Config.CURRENT.getCurrentLevelSet()).getName());
		lblLevel.setText("" + (level.getId() + 1));
		lblScoring.setText("" + scoring);
		lblSpeed.setText("" + speed + "毫秒/格");
		winkController.wink(new JLabel[] {lblLevelSet, lblLevel, lblScoring, lblSpeed});
	}
	public void shapeDroped(boolean swift) {}

	public void gameContinue() {
		btnPause.setText("暂停(P)");
	}

	public void gameOver() {
		btnPause.setEnabled(false);
		btnStart.setText("开始(B)");
		btnStart.setMnemonic('B');
		winkController.dispose();
	}

	public void gamePause() {
		btnPause.setText("继续(P)");
	}

	public void gameStart() {
		winkController = new WinkController();
		btnPause.setEnabled(true);
		btnStart.setText("结束(E)");
		btnStart.setMnemonic('E');
		btnPause.setText("暂停(P)");
	}

	public boolean gameWillStop() {
		int response = JOptionPane.showConfirmDialog(
				this, "确定要停止当前游戏吗?", "确认", JOptionPane.YES_NO_OPTION);
		return response == 0;
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnStart) { // 开始/停止按钮
			if (controller.isPlaying()) {
				controller.gameStop();
			} else {
				// 耗时操作, 另启线程执行
				new Thread() {
					public void run() {
						controller.gameCreate();
					}
				}.start();
			}
		} else if(obj == btnPause) { // 暂停/继续按钮
			if(controller.isPause()) {
				controller.gameContinue();
			} else {
				controller.gamePause();
			}
		} else if(obj == mnuSound) { // 声音控制菜单
			JCheckBoxMenuItem mnuSound = (JCheckBoxMenuItem)obj;
			Config.CURRENT.setSupportSound(mnuSound.getState());
			Config.CURRENT.save();
		} else if(obj == mnuHotkeySet) { // 控制键设置菜单
			HotkeySetDialog dialog = new HotkeySetDialog(this, true);
			dialog.setVisible(true);
		} else if(obj == mnuViewSet) { // 显示设置菜单
			ViewSetDialog dialog = new ViewSetDialog(this, true);
			dialog.addConfigListener(gamePanel);
			dialog.addConfigListener(previewPanel);
			dialog.setVisible(true);
		} else if(obj == mnuLevelSetChoose) { // 关卡选择菜单
			LevelSetChooseDialog dialog = new LevelSetChooseDialog(this, true,
					controller.isPlaying());
			dialog.addConfigListener(controller);
			dialog.setVisible(true);
		} else if(obj == mnuAboutGame) { // 关于游戏菜单
			AboutGameDialog dialog = new AboutGameDialog(this, true);
			dialog.setVisible(true);
		} else if(obj == mnuAboutAuthor) { // 关于作者菜单
			AboutAuthorDialog dialog = new AboutAuthorDialog(this, true);
			dialog.setVisible(true);
		}
	}

	public void focusLost(FocusEvent e) {
		controller.gamePause();
	}
	public void focusGained(FocusEvent e) {}

	
	
}

