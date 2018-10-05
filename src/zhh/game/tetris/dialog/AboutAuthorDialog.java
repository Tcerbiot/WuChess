package zhh.game.tetris.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import zhh.game.tetris.global.Config;
import zhh.game.tetris.listener.instance.ReadOnlyListener;

/**
 * "��������"�Ի���
 * @author fuyunliang
 */

public class AboutAuthorDialog extends JDialog  {
	/**
	 * ���л��汾ͳһ��ʶ��
	 */
	private static final long serialVersionUID = -9100800902611224526L;
	
	/**
	 * "��������"�Ի���
	 */
	public AboutAuthorDialog() {
		this(null, false);
	}

	/**
	 * "��������"�Ի���
	 * @param owner Frame �Ի����������
	 */
	public AboutAuthorDialog(Frame owner) {
		this(owner, false);
	}
	
	/**
	 * "��������"�Ի���
	 * @param owner Frame �Ի����������
	 * @param modal boolean �Ƿ�Ϊģʽ�Ի���
	 */
	public AboutAuthorDialog(Frame owner, boolean modal) {
		super(owner, modal);

		
		// ���ô��ڱ���, ���ڳߴ�
		setTitle("��������");
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
		
		// ֻ��������ʵ��, ʵ���ı����ֻ������
		final ReadOnlyListener readOnlyListener = new ReadOnlyListener();
		// �ı���߿�
		final Border txtBorder = new LineBorder(
				Config.CURRENT.getBorderColor(), 1, false);
		
		final JLabel lblAuthor = new JLabel();
		lblAuthor.setText("����:");
		lblAuthor.setBounds(10, 10, 100, 15);
		getContentPane().add(lblAuthor);
		
		final JTextField txtAuthor = new JTextField();
		txtAuthor.setText("fuyunliang");
		txtAuthor.setBounds(40, 31, 170, 21);
		txtAuthor.setBorder(txtBorder);
		txtAuthor.addKeyListener(readOnlyListener);
		getContentPane().add(txtAuthor);

		final JLabel lblEmail = new JLabel();
		lblEmail.setText("E-mail:");
		lblEmail.setBounds(10, 58, 100, 15);
		getContentPane().add(lblEmail);
		
		final JTextField txtEmail = new JTextField();
		txtEmail.setText("1531640334@qq.com");
		txtEmail.setBounds(40, 79, 170, 21);
		txtEmail.setBorder(txtBorder);
		txtEmail.addKeyListener(readOnlyListener);
		getContentPane().add(txtEmail);
		
		// �ָ���
		final JSeparator separator = new JSeparator();
		separator.setBounds(10, 225, 214, 2);
		getContentPane().add(separator);

		// "ȷ��"��ť
		final JButton btnOk = new JButton();
		btnOk.setText("ȷ��(O)");
		btnOk.setMnemonic('O');
		btnOk.setBounds(61, 233, 128, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnOk);
		
		// ȷ����ť��ȡ����
		addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent evt) {
            	btnOk.requestFocus();
            }
        });

	}
	
	
}



