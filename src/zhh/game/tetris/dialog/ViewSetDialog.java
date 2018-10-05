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
	 * ���л��汾ͳһ��ʶ��
	 */
	private static final long serialVersionUID = 5027145225657888387L;

	/**
	 * ��ͬ���Ϳؼ�������
	 */
	private final int controlCount = 5;

	/**
	 * �ؼ����ڴ洢��ŵ� key<br>
	 * ��Ŵ洢�ڿؼ��� ClientProperty ������<br>
	 */
	private final String controlIndexKey = "INDEX";
	
	/**
	 * �񶨷��Ĵ�����
	 */
	private final ChooseHandler handlerFalse = new ChooseHandler(false);
	
	/**
	 * �û���ѡ��
	 */
	private boolean[] choose;
	
	/**
	 * ���ü�����
	 */
	private ConfigListener[] configListeners;
	
	/**
	 * ��Ϸ��ʾ���öԻ���
	 */
	public ViewSetDialog() {
		this(null, false);
	}

	/**
	 * ��Ϸ��ʾ���öԻ���
	 * @param owner Frame �Ի����������
	 */
	public ViewSetDialog(Frame owner) {
		this(owner, false);
	}
	
	/**
	 * ��Ϸ��ʾ���öԻ���
	 * @param owner Frame �Ի����������
	 * @param modal boolean �Ƿ�Ϊģʽ�Ի���
	 */
	public ViewSetDialog(Frame owner, boolean modal) {
		super(owner, modal);
		
		// ���ô��ڱ���, ���ڳߴ�
		setTitle("��ʾ����");
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
		
		// ��ʼ��ѡ��������һ��
		choose = new boolean[controlCount];
		choose[0] = Config.CURRENT.isShowGridLine();
		choose[1] = Config.CURRENT.isShowPreviewGridLine();
		choose[2] = Config.CURRENT.isSupportColorfulShape();
		choose[3] = Config.CURRENT.isSupportColorfulGround();
		choose[4] = Config.CURRENT.isSupportColorfulPreview();
		
		// ��ʾ��ǩ���ı�
		final String[] labelText = {"��Ϸ���Ƿ���ʾ����:", "Ԥ�����Ƿ���ʾ����:", 
				"��״�Ƿ�֧�ֲ�ɫ:", "�ϰ����Ƿ�֧�ֲ�ɫ:", "Ԥ���Ƿ�֧�ֲ�ɫ:"};
		// ��ʾ��ǩ
		final JLabel[] labels = new JLabel[controlCount];
		// ѡ���
		ButtonGroup[] buttonGroup = new ButtonGroup[controlCount];
		// �϶�����ѡ���
		final JRadioButton[] rdoTrue = new JRadioButton[controlCount];
		// �񶨷���ѡ���
		final JRadioButton[] rdoFalse = new JRadioButton[controlCount];
		// �϶����Ĵ�����
		final ChooseHandler handlerTrue = new ChooseHandler(true);
		for(int i = 0; i < controlCount; i++) {
			// ������ʾ��ǩ
			labels[i] = new JLabel();
			labels[i].setText(labelText[i]);
			labels[i].setBounds(10, 10 + i * 36, 210, 15);
			getContentPane().add(labels[i]);
			
			// ����ѡ���
			rdoTrue[i] = new JRadioButton();
			rdoTrue[i].setText(i < 2 ? "��ʾ" : "��ɫ");
			rdoTrue[i].setBounds(30, 25 + i * 36, 85, 19);
			rdoTrue[i].putClientProperty(controlIndexKey, new Integer(i));
			rdoTrue[i].addActionListener(handlerTrue);
			getContentPane().add(rdoTrue[i]);

			rdoFalse[i] = new JRadioButton();
			rdoFalse[i].setText(i < 2 ? "����ʾ" : "��ɫ");
			rdoFalse[i].setBounds(126, 25 + i * 36, 85, 19);
			rdoFalse[i].putClientProperty(controlIndexKey, new Integer(i));
			rdoFalse[i].addActionListener(handlerFalse);
			getContentPane().add(rdoFalse[i]);

			// ͬһ��ŵĿ϶���ѡ�����񶨷�ѡ���Ϊһ��
			buttonGroup[i] = new ButtonGroup();
			buttonGroup[i].add(rdoTrue[i]);
			buttonGroup[i].add(rdoFalse[i]);
			
			// ��ʼѡ��״̬
			if(choose[i])
				rdoTrue[i].setSelected(true);
			else
				rdoFalse[i].setSelected(true);
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
				choose[0] = Config.DEFAULT.isShowGridLine();
				choose[1] = Config.DEFAULT.isShowPreviewGridLine();
				choose[2] = Config.DEFAULT.isSupportColorfulShape();
				choose[3] = Config.DEFAULT.isSupportColorfulGround();
				choose[4] = Config.DEFAULT.isSupportColorfulPreview();
				// ����ѡ��״̬
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

		// "ȷ��"��ť
		final JButton btnOk = new JButton();
		btnOk.setText("ȷ��(O)");
		btnOk.setMnemonic('O');
		btnOk.setBounds(10, 233, 128, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��������Ƿ��Ѿ����ı�
				if(Config.CURRENT.isShowGridLine() != choose[0] ||
		        		Config.CURRENT.isShowPreviewGridLine() != choose[1] ||
		        		Config.CURRENT.isSupportColorfulShape() != choose[2] || 
		        		Config.CURRENT.isSupportColorfulGround() != choose[3] ||
		        		Config.CURRENT.isSupportColorfulPreview() != choose[4]) {
					// ��������
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
    
	
	/**
	 * �û��ı�ѡ��ʱ�Ĵ�����
	 * @author zhaohuihua
	 */
	private class ChooseHandler implements ActionListener {
		/**
		 * �ô�������֧�ֿ϶������Ƿ񶨷��ı�־
		 */
		private boolean choice;
		
		public ChooseHandler(boolean choice) {
			this.choice = choice;
		}
		
		/**
		 * �û�ѡ��ʱ�Ĵ���
		 */
		public void actionPerformed(ActionEvent e) {
			// ����ѡ�������, ���¶�Ӧ�ļ�¼�û�ѡ��ı���ֵ
			JComponent source = (JComponent)e.getSource();
			int index = ((Integer)source.getClientProperty(controlIndexKey)).intValue();
			choose[index] = choice;
		}
	}
}



