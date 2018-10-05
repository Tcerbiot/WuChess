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
 * ��Ϸ������<br>
 * @author fuyunliang
 */
public class TetrisFrame extends JFrame
		implements FocusListener, ActionListener,
		GameListener, ScoringListener {

	/**
	 * ���л��汾ͳһ��ʶ��
	 */
	private static final long serialVersionUID = -8223961532412482771L;

	/** ��Ϸ������ */
	private final GameController controller;
	/** ��Ϸ��ʾ�� */
	private final GamePanel gamePanel;
	/** Ԥ����ʾ�� */
	private final PreviewPanel previewPanel;
	/** ��˸������ */
	private WinkController winkController;
	/** ��ʼ/ֹͣ��ť */
	private final JButton btnStart;
	/** ��ͣ/������ť */
	private final JButton btnPause;
	/** �÷���ʾ */
	private final JLabel lblScoring;
	/** �ٶ���ʾ */
	private final JLabel lblSpeed;
	/** ������ʾ */
	private final JLabel lblLevel;
	/** �ؿ���ʾ */
	private final JLabel lblLevelSet;
	/** �������Ʋ˵� */
	private final JCheckBoxMenuItem mnuSound;
	/** ���Ƽ����ò˵� */
	private final JMenuItem mnuHotkeySet;
	/** ��ʾ���ò˵� */
	private final JMenuItem mnuViewSet;
	/** �ؿ�ѡ��˵� */
	private final JMenuItem mnuLevelSetChoose;
	/** ������Ϸ�˵� */
	private final JMenuItem mnuAboutGame;
	/** �������߲˵� */
	private final JMenuItem mnuAboutAuthor;
	
	public TetrisFrame() {
		super("����˹����");
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
		
		// ��Ϸ��Ϣ
		JLabel lblLevelSetLabel = new JLabel("�ؿ�");
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

		JLabel lblLevelLabel = new JLabel("����");
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

		JLabel lblScoringLabel = new JLabel("�÷�");
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

		JLabel lblSpeedLabel = new JLabel("�ٶ�");
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

		// �˵�
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnuSet = new JMenu();
		mnuSet.setText("����(S)");
		mnuSet.setMnemonic('S');
		menuBar.add(mnuSet);

		JMenu mnuHelp = new JMenu();
		mnuHelp.setText("����(H)");
		mnuHelp.setMnemonic('H');
		menuBar.add(mnuHelp);

		mnuSound = new JCheckBoxMenuItem();
		mnuSound.setText("��Ϸ����(A)");
		mnuSound.setState(Config.CURRENT.isSupportSound());
		mnuSound.setMnemonic('A');
		mnuSound.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 
				InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnuSet.add(mnuSound);
		mnuSound.addActionListener(this);
		
		mnuSet.addSeparator();

		mnuHotkeySet = new JMenuItem();
		mnuHotkeySet.setText("���Ƽ�����(C)...");
		mnuHotkeySet.setMnemonic('C');
		mnuHotkeySet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 
				InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnuSet.add(mnuHotkeySet);
		mnuHotkeySet.addActionListener(this);

		mnuViewSet = new JMenuItem();
		mnuViewSet.setText("��ʾ����(V)...");
		mnuViewSet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, 
				InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnuViewSet.setMnemonic('V');
		mnuSet.add(mnuViewSet);
		mnuViewSet.addActionListener(this);

		mnuLevelSetChoose = new JMenuItem();
		mnuLevelSetChoose.setText("�ؿ�ѡ��(L)...");
		mnuLevelSetChoose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, 
				InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnuLevelSetChoose.setMnemonic('L');
		mnuSet.add(mnuLevelSetChoose);
		mnuLevelSetChoose.addActionListener(this);

		mnuAboutGame = new JMenuItem();
		mnuHelp.add(mnuAboutGame);
		mnuAboutGame.setText("������Ϸ(G)...");
		mnuAboutGame.setMnemonic('G');
		mnuAboutGame.addActionListener(this);

		mnuAboutAuthor = new JMenuItem();
		mnuHelp.add(mnuAboutAuthor);
		mnuAboutAuthor.setText("��������(A)...");
		mnuAboutAuthor.setMnemonic('A');
		mnuAboutAuthor.addActionListener(this);
		
		// ��ʼ/ֹͣ��ť
		btnStart = new JButton();
		btnStart.setText("��ʼ(B)");
		btnStart.setMnemonic('B');
		btnStart.setBounds(10, 348, 80, 23);
		infoPanel.add(btnStart);
		btnStart.addActionListener(this);

		// ��ͣ/������ť
		btnPause = new JButton();
		btnPause.setText("��ͣ(P)");
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
		lblSpeed.setText("" + speed + "����/��");
		winkController.wink(lblSpeed);
	}
	public void scoringChanged(int scoring, boolean levelChanged) {
		lblScoring.setText("" + scoring);
		winkController.wink(lblScoring);
	}

	public void winning(int scoring, int speed, Level level) {
		lblLevel.setText("ͨ��");
		winkController.wink(lblLevel);
	}

	public void scoringInit(int scoring, int speed, Level level) {
		lblLevelSet.setText(LevelSetFactory.getLevelSet(Config.CURRENT.getCurrentLevelSet()).getName());
		lblLevel.setText("" + (level.getId() + 1));
		lblScoring.setText("" + scoring);
		lblSpeed.setText("" + speed + "����/��");
		winkController.wink(new JLabel[] {lblLevelSet, lblLevel, lblScoring, lblSpeed});
	}
	public void shapeDroped(boolean swift) {}

	public void gameContinue() {
		btnPause.setText("��ͣ(P)");
	}

	public void gameOver() {
		btnPause.setEnabled(false);
		btnStart.setText("��ʼ(B)");
		btnStart.setMnemonic('B');
		winkController.dispose();
	}

	public void gamePause() {
		btnPause.setText("����(P)");
	}

	public void gameStart() {
		winkController = new WinkController();
		btnPause.setEnabled(true);
		btnStart.setText("����(E)");
		btnStart.setMnemonic('E');
		btnPause.setText("��ͣ(P)");
	}

	public boolean gameWillStop() {
		int response = JOptionPane.showConfirmDialog(
				this, "ȷ��Ҫֹͣ��ǰ��Ϸ��?", "ȷ��", JOptionPane.YES_NO_OPTION);
		return response == 0;
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnStart) { // ��ʼ/ֹͣ��ť
			if (controller.isPlaying()) {
				controller.gameStop();
			} else {
				// ��ʱ����, �����߳�ִ��
				new Thread() {
					public void run() {
						controller.gameCreate();
					}
				}.start();
			}
		} else if(obj == btnPause) { // ��ͣ/������ť
			if(controller.isPause()) {
				controller.gameContinue();
			} else {
				controller.gamePause();
			}
		} else if(obj == mnuSound) { // �������Ʋ˵�
			JCheckBoxMenuItem mnuSound = (JCheckBoxMenuItem)obj;
			Config.CURRENT.setSupportSound(mnuSound.getState());
			Config.CURRENT.save();
		} else if(obj == mnuHotkeySet) { // ���Ƽ����ò˵�
			HotkeySetDialog dialog = new HotkeySetDialog(this, true);
			dialog.setVisible(true);
		} else if(obj == mnuViewSet) { // ��ʾ���ò˵�
			ViewSetDialog dialog = new ViewSetDialog(this, true);
			dialog.addConfigListener(gamePanel);
			dialog.addConfigListener(previewPanel);
			dialog.setVisible(true);
		} else if(obj == mnuLevelSetChoose) { // �ؿ�ѡ��˵�
			LevelSetChooseDialog dialog = new LevelSetChooseDialog(this, true,
					controller.isPlaying());
			dialog.addConfigListener(controller);
			dialog.setVisible(true);
		} else if(obj == mnuAboutGame) { // ������Ϸ�˵�
			AboutGameDialog dialog = new AboutGameDialog(this, true);
			dialog.setVisible(true);
		} else if(obj == mnuAboutAuthor) { // �������߲˵�
			AboutAuthorDialog dialog = new AboutAuthorDialog(this, true);
			dialog.setVisible(true);
		}
	}

	public void focusLost(FocusEvent e) {
		controller.gamePause();
	}
	public void focusGained(FocusEvent e) {}

	
	
}

