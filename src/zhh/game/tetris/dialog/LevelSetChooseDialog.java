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
	 * ���л��汾ͳһ��ʶ��
	 */
	private static final long serialVersionUID = 6646272036035442L;

	/**
	 * ��ͬ���Ϳؼ�������
	 */
	private final int controlCount = LevelSetFactory.getLevelSetCount();
	
	/**
	 * �ؼ����ڴ洢��ŵ� key<br>
	 * ��Ŵ洢�ڿؼ��� ClientProperty ������<br>
	 */
	private final String controlIndexKey = "INDEX";
	
	/**
	 * �û�ѡ��ļ��𼯱��
	 */
	private int chooseLevelSet;
	
	/**
	 * �û�ѡ��ļ�����
	 */
	private int chooseInitLevel;

	/**
	 * ���ü�����
	 */
	private ConfigListener[] configListeners;
	
	/**
	 * ����ѡ��Ի���
	 */
	public LevelSetChooseDialog() {
		this(null, false, false);
	}

	/**
	 * ����ѡ��Ի���
	 * @param owner Frame �Ի����������
	 */
	public LevelSetChooseDialog(Frame owner) {
		this(owner, false, false);
	}
	
	/**
	 * ����ѡ��Ի���
	 * @param owner Frame �Ի����������
	 * @param modal boolean �Ƿ�Ϊģʽ�Ի���
	 */
	public LevelSetChooseDialog(Frame owner, boolean modal) {
		this(owner, modal, false);
	}
	
	/**
	 * ����ѡ��Ի���
	 * @param owner Frame �Ի����������
	 * @param modal boolean �Ƿ�Ϊģʽ�Ի���
	 * @param isGamePlaying boolean ��Ϸ�Ƿ����ڽ�����
	 */
	public LevelSetChooseDialog(Frame owner, boolean modal, boolean isGamePlaying) {
		super(owner, modal);
		
		// ���ô��ڱ���, ���ڳߴ�
		setTitle("�ؿ�ѡ��");
		getContentPane().setLayout(null);
		setResizable(false);
		setSize(240, 295);
		setLocationRelativeTo(owner);
		
		// ���Ӽ����������ڹر��¼�
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		chooseLevelSet = Config.CURRENT.getCurrentLevelSet();
		chooseInitLevel = Config.CURRENT.getInitLevel();
		
		final JLabel lblLevelSet = new JLabel();
		lblLevelSet.setText("�ؿ�ѡ��:");
		lblLevelSet.setBounds(10, 10, 150, 15);
		getContentPane().add(lblLevelSet);

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(150, controlCount * 23));
		
		final JSlider slider = new JSlider();
		// �û��ı�ѡ��ʱ�Ĵ�����
		final ActionListener handler = new ActionListener() {
			/**
			 * �û�ѡ��ʱ�Ĵ���
			 */
			public void actionPerformed(ActionEvent e) {
				// ����ѡ�������, ���¶�Ӧ�ļ�¼�û�ѡ��ı���ֵ
				JComponent source = (JComponent)e.getSource();
				int index = ((Integer)source.getClientProperty(controlIndexKey)).intValue();
				chooseLevelSet = index;
				slider.setMaximum(LevelSetFactory.getLevelSet(chooseLevelSet).getLevelCount() - 1);
			}
		};

		// ����ѡ���
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
			// ѡ�е�ǰ�ļ���
			if(i == Config.CURRENT.getCurrentLevelSet()) {
				radioButtons[i].setSelected(true);
				choose = i;
			}
			radioButtons[i].addActionListener(handler);
			buttonGroup.add(radioButtons[i]);
			panel.add(radioButtons[i]);
		}

		// �����α߿�
		final Border lineBorder = new LineBorder(
				Config.CURRENT.getBorderColor(), 1, false);
		// �������������(�ڼ��𼯺ܶ�����������)
		final JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(30, 27, 194, 72);
		scrollPane.setBorder(lineBorder);
		getContentPane().add(scrollPane);

		final JLabel label = new JLabel();
		label.setText("����ѡ��:");
		label.setBounds(10, 109, 150, 15);
		getContentPane().add(label);

		// ����ѡ��ʱ���ı���ʾ
		final JTextField textField = new JTextField();
		textField.setText("" + (Config.CURRENT.getInitLevel() + 1));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(30, 132, 30, 23);
		textField.setBorder(lineBorder);
		textField.setFocusable(false);
		getContentPane().add(textField);
		
		// ����ѡ�������
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
		
		// �ָ���
		final JSeparator separator = new JSeparator();
		separator.setBounds(10, isGamePlaying ? 196 : 225, 214, 2);
		getContentPane().add(separator);

		if(isGamePlaying) {
			// "����ϷӦ��"��ť
			final JButton btnApplyLater = new JButton();
			btnApplyLater.setText("�Ժ�Ӧ��(L)");
			btnApplyLater.setMnemonic('L');
			btnApplyLater.setToolTipText("������Ӧ��ѡ��, ֱ����ʼ����Ϸʱ��Ӧ��ѡ��");
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

		// "����Ӧ��"��ť
		final Frame ownerAlias = owner;
		final boolean isGamePlayingAlias = isGamePlaying;
		final JButton btnApply = new JButton();
		btnApply.setText(isGamePlaying ? "����Ӧ��(A)" : "Ӧ��(A)");
		btnApply.setMnemonic('A');
		btnApply.setBounds(10, 233, 128, 23);
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����Ϸ���й����и��ļ���ͼ���, ���û�ȷ��
				if(isGamePlayingAlias) {
					int response = JOptionPane.showConfirmDialog(
						ownerAlias,
						"����Ӧ��ѡ�ֹͣ��ǰ��Ϸ\nȷ��Ҫ����Ӧ����?",
						"ȷ��", 
						JOptionPane.YES_NO_OPTION);
					if(response != 0) return;
				}
				
				// ���¼���ͼ���
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

		// "ȡ��"��ť
		final JButton btnCancel = new JButton();
		btnCancel.setText("ȡ��(C)");
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
     * �������ü�����
     * @param listener ConfigListener ���ü�����
     */
    public void addConfigListener(ConfigListener listener) {
    	if(configListeners == null) configListeners = new ConfigListener[]{};
    	configListeners = (ConfigListener[])Utilities.arrayAddItem(
    			configListeners, listener);
    }
    
    /**
     * �Ƴ����ü�����
     * @param listener ConfigListener ���ü�����
     */
    public void removeConfigListener(ConfigListener listener) {
    	configListeners = (ConfigListener[])Utilities.arrayRemoveItem(
    			configListeners, listener);
    }
}



