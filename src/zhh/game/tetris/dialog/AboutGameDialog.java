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
 * "������Ϸ"�Ի���
 * @author fuyunliang
 */
public class AboutGameDialog extends JDialog  {
	/**
	 * ���л��汾ͳһ��ʶ��
	 */
	private static final long serialVersionUID = -6249170602734028357L;
	
	/**
	 * "������Ϸ"�Ի���
	 */
	public AboutGameDialog() {
		this(null, false);
	}

	/**
	 * "������Ϸ"�Ի���
	 * @param owner Frame �Ի����������
	 */
	public AboutGameDialog(Frame owner) {
		this(owner, false);
	}
	
	/**
	 * "������Ϸ"�Ի���
	 * @param owner Frame �Ի����������
	 * @param modal boolean �Ƿ�Ϊģʽ�Ի���
	 */
	public AboutGameDialog(Frame owner, boolean modal) {
		super(owner, modal);

		
		// ���ô��ڱ���, ���ڳߴ�
		setTitle("������Ϸ");
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

		// �߿�
		final Border lineBorder = new LineBorder(
				Config.CURRENT.getBorderColor(), 1, false);

		// ֻ��������ʵ��, ʵ���ı����ֻ������
		final ReadOnlyListener readOnlyListener = new ReadOnlyListener();
		// ������
		final SimpleAttributeSet title = new SimpleAttributeSet();
		StyleConstants.setForeground(title, Color.BLUE);
		// StyleConstants.setBold(title, true); // ����ʵ��̫�ѿ���
		StyleConstants.setFontSize(title, 14);
		// �ӱ�����
		final SimpleAttributeSet subtitle = new SimpleAttributeSet();
		StyleConstants.setForeground(subtitle, Color.BLUE);
		StyleConstants.setFontSize(subtitle, 13);
		// ���ķ��
		final SimpleAttributeSet normal = new SimpleAttributeSet();
		StyleConstants.setFontSize(normal, 13);
		
		final JTextPane txtAboutGame = new JTextPane();
		txtAboutGame.addKeyListener(readOnlyListener);

		try {
			Document doc = txtAboutGame.getDocument();
			String about = "����˹������Ϸ\n\n";
			doc.insertString(doc.getLength(), about, title);
			about = "��Ϸ��飺\n";
			doc.insertString(doc.getLength(), about, subtitle);
			about = new StringBuffer()
				.append("����˹������һ�����Ƿ�������Ϸ��\n")
				.append("�����Ϸ������������ĵ��Կ�ѧ��������ŵ��(Alex Pajitnov)��1985�������ģ�\n")
				.append("���߸�����һ��Դ��ϣ����4(tetra)������Tetris��\n")
				.append("1989�����������ڷ���GameBoy�棬�Ƴ������ȫ��")
				.append("��Ϊ���Ƿ���������Ϸ��֪������ߵ�һ�\n")
				.append("�����Ƽ򵥵�ȴ�仯������ּ������ף�")
				.append("����Ҫ�������������еĲ�����ڷż��ɣ��Ѷ�ȴ���͡�\n")
				.append("\n")
				.toString();
			doc.insertString(doc.getLength(), about, normal);
			about = "�淨��飺\n";
			doc.insertString(doc.getLength(), about, subtitle);
			about = new StringBuffer()
				.append("��Ϸ����һ�����ڰڷ�С�����ƽ�����ⳡ�أ�\n")
				.append("һ���ɼ���С������ɵĹ�����״(Tetromino)��\n")
				.append("��Ϸÿ��������һ����״�����ض������Զ���һ�����ٶ����䣬\n")
				.append("�û�����״�Ĺ����п��Կ�����״�������ƶ�����ת�Խ���״��䵽�����У�\n")
				.append("ֱ����״���������صײ��򱻳��������еķ����赲�����������䣬\n")
				.append("��Ϸ�ٴ����һ����״���ܶ���ʼ��\n")
				.append("��������佫���ص�һ�л������ȫ�������������Щ�е����з��齫��������\n")
				.append("�����Դ�����ȡһ���Ļ��ֽ�����\n")
				.append("��δ�������ķ����һֱ�ۻ������Ժ�������״�ڷ���ɸ���Ӱ�죬\n")
				.append("�����һ����״�����λ���Ѿ���δ�����ķ�����ռ�ݣ�����Ϸ������")
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


