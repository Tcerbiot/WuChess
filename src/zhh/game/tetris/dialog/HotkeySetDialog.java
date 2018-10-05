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
 * ��Ϸ���Ƽ����öԻ���<br>
 * ���û�����Ŀ��Ƽ����Ĭ�ϵĿ��Ƽ�<br>
 * 1. "��ʼ/����"<br>
 * 2. "��ͣ/����"<br>
 * 3. "����"<br>
 * 4. "����"<br>
 * 5. "����"<br>
 * 6. "��������"<br>
 * 7. "һ�䵽��"<br>
 * @author zhaohuihua
 */
public class HotkeySetDialog extends JDialog {
	/**
	 * ���л��汾ͳһ��ʶ��
	 */
	private static final long serialVersionUID = -8372498997747949041L;
	
	/**
	 * ��ͬ���Ϳؼ�������
	 */
	private final int controlCount = 7;

	/**
	 * �ؼ����ڴ洢��ŵ� key<br>
	 * ��Ŵ洢�ڿؼ��� ClientProperty ������<br>
	 */
	private final String controlIndexKey = "INDEX";
	
	/**
	 * "ȷ��"��ť
	 */
	private final JButton btnOk;

	/**
	 * Ĭ�ϵı�����ɫ
	 */
	private final Color background;
	
	/**
	 * ���ڻ�ȡ����������ı���
	 */
	private JTextField[] textFields;
	
	/**
	 * ���п��Ƽ�������
	 */
	private int[] keyCodes;

	/**
	 * ���ü�����
	 */
	private ConfigListener[] configListeners;
	
	/**
	 * ��Ϸ���Ƽ����öԻ���
	 */
	public HotkeySetDialog() {
		this(null, false);
	}

	/**
	 * ��Ϸ���Ƽ����öԻ���
	 * @param owner Frame �Ի����������
	 */
	public HotkeySetDialog(Frame owner) {
		this(owner, false);
	}
	
	/**
	 * ��Ϸ���Ƽ����öԻ���
	 * @param owner Frame �Ի����������
	 * @param modal boolean �Ƿ�Ϊģʽ�Ի���
	 */
	public HotkeySetDialog(Frame owner, boolean modal) {
		super(owner, modal);
		
		// ���ô��ڱ���, ���ڳߴ�
		setTitle("���Ƽ�����");
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

		// ��ʼ�����Ƽ�������, �����п��Ƽ���ͬ
		keyCodes = new int[controlCount];
		keyCodes[0] = Config.CURRENT.getStartKey();
		keyCodes[1] = Config.CURRENT.getPauseKey();
		keyCodes[2] = Config.CURRENT.getLeftKey();
		keyCodes[3] = Config.CURRENT.getRightKey();
		keyCodes[4] = Config.CURRENT.getRotateKey();
		keyCodes[5] = Config.CURRENT.getDownKey();
		keyCodes[6] = Config.CURRENT.getSwiftKey();
		
		// �ı���ؼ��İ���������
		final KeyAdapter handler = new KeyAdapter() {
			/**
			 * �û�����ʱ�Ĵ���
			 */
			public void keyPressed(KeyEvent e) {
				if(e.isAltDown() || e.isControlDown() || e.isShiftDown()) 
					return;
				// ��ȡ�û�����ļ����ַ�, ��Ϊ�µĿ��Ƽ�
				int keyCode = e.getKeyCode();
				JTextField source = (JTextField)e.getSource();
				int index = ((Integer)source.getClientProperty(controlIndexKey)).intValue();
				if(keyCode > 0) {
					((JTextField)e.getSource()).setText(KeyEvent.getKeyText(keyCode));
					keyCodes[index] = keyCode;
				}
				
				// �����Ƽ��໥֮���Ƿ��ͻ
				boolean collisional = checkCollisional();
				
				// ֻ���ڿ��Ƽ��໥֮�䲻��ͻ��ʱ������޸�
				btnOk.setEnabled(!collisional);
				
				// ���ٸð����¼�, �����������
				e.consume();
			}
			public void keyTyped(KeyEvent e) {
				// ���������ı�����
				e.consume();
			}
		};
		
		// ��ʾ��ǩ���ı�
		final String[] labelText = {"��ʼ/����:", "��ͣ/����:", "����:", "����:", "����:", 
				"��������:", "һ�䵽��:"};
		// �ı���߿�
		final Border txtBorder = new LineBorder(
				Config.CURRENT.getBorderColor(), 1, false);
		// ��ʾ��ǩ
		final JLabel[] labels = new JLabel[controlCount];
		// ��ȡ������ı���
		textFields = new JTextField[controlCount];
		for(int i = 0; i < controlCount; i++) {
			// ������ʾ��ǩ
			labels[i] = new JLabel();
			labels[i].setText(labelText[i]);
			labels[i].setBounds(10, 10 + i * 27, 100, 15);
			getContentPane().add(labels[i]);
			
			// �����ı���
			textFields[i] = new JTextField();
			textFields[i].setText(KeyEvent.getKeyText(keyCodes[i]));
			textFields[i].setBounds(114, 7 + i * 27, 110, 21);
			textFields[i].setBorder(txtBorder);
			textFields[i].setHorizontalAlignment(SwingConstants.CENTER);
			textFields[i].putClientProperty(controlIndexKey, new Integer(i));
			textFields[i].addKeyListener(handler);
			getContentPane().add(textFields[i]);
		}
		
		// �ָ���
		final JSeparator separator = new JSeparator();
		separator.setBounds(10, 196, 214, 2);
		getContentPane().add(separator);

		// "�ָ�"��ť
		final JButton btnRestoration = new JButton();
		btnRestoration.setText("�ָ�(R)");
		btnRestoration.setMnemonic('R');
		btnRestoration.setToolTipText("�ָ�ΪӦ�ó����Ĭ��״̬");
		btnRestoration.setBounds(10, 204, 128, 23);
		btnRestoration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �ָ�ΪӦ�ó����Ĭ�Ͽ��Ƽ�
				keyCodes[0] = Config.DEFAULT.getStartKey();
				keyCodes[1] = Config.DEFAULT.getPauseKey();
				keyCodes[2] = Config.DEFAULT.getLeftKey();
				keyCodes[3] = Config.DEFAULT.getRightKey();
				keyCodes[4] = Config.DEFAULT.getRotateKey();
				keyCodes[5] = Config.DEFAULT.getDownKey();
				keyCodes[6] = Config.DEFAULT.getSwiftKey();
				// �����ı���
				for(int i = 0; i < controlCount; i++) {
					textFields[i].setText(KeyEvent.getKeyText(keyCodes[i]));
					textFields[i].setBackground(background);
				}
				btnOk.setEnabled(true);
			}
		});
		getContentPane().add(btnRestoration);

		// "ȷ��"��ť
		btnOk = new JButton();
		btnOk.setText("ȷ��(O)");
		btnOk.setMnemonic('O');
		btnOk.setBounds(10, 233, 128, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �����Ƽ��Ƿ��Ѿ����ı�
				if(Config.CURRENT.getStartKey() != keyCodes[0]
		                || Config.CURRENT.getPauseKey() != keyCodes[1]
            			|| Config.CURRENT.getLeftKey() != keyCodes[2]
            			|| Config.CURRENT.getRightKey() != keyCodes[3]
            			|| Config.CURRENT.getRotateKey() != keyCodes[4]
            			|| Config.CURRENT.getDownKey() != keyCodes[5]
            			|| Config.CURRENT.getSwiftKey() != keyCodes[6]) {
					// ���¿��Ƽ�
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
		
		background = textFields[0].getBackground();
	}
	
	/**
	 * �����Ƽ��໥֮���Ƿ��ͻ<br>
	 * ����ͻ�Ŀ��Ƽ��ò�ͬ��ɫ���Ա�ʶ<br>
	 */
	private boolean checkCollisional() {
		/*
		 * mapping: ��¼���Ƽ������λ�õ�ӳ�����
		 * [i][0] KeyCode
		 * [i][1] IndexCount
		 * [i][2..n] Index
		 */
		int[][] mapping = new int[controlCount][controlCount + 2];
		
		// count: ���ж����鲻ͬ�Ŀ��Ƽ�
		int count = 0;
		for(int i = 0; i < controlCount; i++) {
			// ���Ƽ���ӳ������Ƿ��Ѵ���
			boolean existent = false;
			for(int j = 0; j < count; j++) {
				if(keyCodes[i] == mapping[j][0]) {
					// ���иÿ��Ƽ���ӳ�����
					// �޸�ӳ�����, ���ӳ���λ��
					mapping[j][mapping[j][1] + 2] = i;
					mapping[j][1] ++;
					existent = true;
					break;
				}
			}
			if(!existent) {
				// ��һ�α������ÿ��Ƽ�, ��¼ӳ�����
				mapping[count][0] = keyCodes[i];
				mapping[count][1] = 1;
				mapping[count][2] = i;
				count ++;
			}
		}
		
		// ���Ƽ���ͻʱ�ı�ʶ��ɫ
		final Color[] colors = new Color[] {
				new Color(0xFFCCCC),
				new Color(0xCCCCFF),
				new Color(0xCCFFCC)
			};
		// index: ��ͻ���Ƽ������, ��¼���м����ͻ, ���Ի�ȡ��ͬ����ɫ
		// ��ÿ���ͻ�Ŀ��Ƽ��ò�ͬ��ɫ���Ա�ʶ
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
		
		// ���Ƽ��Ƿ��ͻ
		return index > 0;
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



