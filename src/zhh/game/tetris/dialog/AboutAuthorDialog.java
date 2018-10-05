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
 * "关于作者"对话框
 * @author fuyunliang
 */

public class AboutAuthorDialog extends JDialog  {
	/**
	 * 串行化版本统一标识符
	 */
	private static final long serialVersionUID = -9100800902611224526L;
	
	/**
	 * "关于作者"对话框
	 */
	public AboutAuthorDialog() {
		this(null, false);
	}

	/**
	 * "关于作者"对话框
	 * @param owner Frame 对话框的所有者
	 */
	public AboutAuthorDialog(Frame owner) {
		this(owner, false);
	}
	
	/**
	 * "关于作者"对话框
	 * @param owner Frame 对话框的所有者
	 * @param modal boolean 是否为模式对话框
	 */
	public AboutAuthorDialog(Frame owner, boolean modal) {
		super(owner, modal);

		
		// 设置窗口标题, 窗口尺寸
		setTitle("关于作者");
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
		
		// 只读监听器实例, 实现文本框的只读功能
		final ReadOnlyListener readOnlyListener = new ReadOnlyListener();
		// 文本框边框
		final Border txtBorder = new LineBorder(
				Config.CURRENT.getBorderColor(), 1, false);
		
		final JLabel lblAuthor = new JLabel();
		lblAuthor.setText("作者:");
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
		
		// 分隔符
		final JSeparator separator = new JSeparator();
		separator.setBounds(10, 225, 214, 2);
		getContentPane().add(separator);

		// "确定"按钮
		final JButton btnOk = new JButton();
		btnOk.setText("确定(O)");
		btnOk.setMnemonic('O');
		btnOk.setBounds(61, 233, 128, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnOk);
		
		// 确定按钮获取焦点
		addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent evt) {
            	btnOk.requestFocus();
            }
        });

	}
	
	
}



