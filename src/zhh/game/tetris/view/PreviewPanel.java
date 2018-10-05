package zhh.game.tetris.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JPanel;

import zhh.game.tetris.entity.Shape;
import zhh.game.tetris.global.Config;
import zhh.game.tetris.listener.ConfigListener;
import zhh.game.tetris.listener.PreviewListener;

/**
 * ��ϷԤ������
 * @author fuyunliang
 */
public class PreviewPanel extends JPanel implements PreviewListener, ConfigListener {

	/**
	 * ���л��汾Ψһ��ʶ��
	 */
	private static final long serialVersionUID = -7185384434960112918L;
	
	/**
	 * ��״
	 */
	protected Shape shape;
	
	/**
	 * ��ϷԤ������
	 */
	public PreviewPanel() {
		super();
	}
	
	/**
	 * ����Ԥ����״
	 * @param g Graphics
	 * @param shape Shape
	 */
	public void paintShape(Graphics g, Shape shape) {
		if(shape == null) return;
		
		// ת������, Ԥ�����߿��λ��
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		g.translate(x, y);
	
		// ������ɫ
		Color oldColor = g.getColor();
		g.setColor(Config.CURRENT.getPreviewColor(shape.getType()));
		
		// ����Ԥ����״
		int[][] points = shape.getPoints(false);
		int length = points.length;
		for (int i = 0; i < length; i++) {
			if(1 + points[i][1] < 0) continue;
			g.fill3DRect((1 + points[i][0]) * Config.CURRENT.getPreviewCellWidth(),
					(1 + points[i][1]) * Config.CURRENT.getPreviewCellHeight(), 
					Config.CURRENT.getPreviewCellWidth(), Config.CURRENT.getPreviewCellHeight(), true);
		}
		
		// ��ԭ�������ɫ
		g.translate(-x, -y);
        g.setColor(oldColor);
	}
	
	/**
	 * �������
	 * @param g Graphics
	 */
	public void paintComponent(Graphics g) {
		paintBackGround(g);
		paintShape(g, shape);
	}
	
	/**
	 * ���Ʊ���
	 * @param g Graphics
	 */
	private void paintBackGround(Graphics g) {
		// ת������, Ԥ�����߿��λ��
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		g.translate(x, y);

		// ������ɫ
		Color oldColor = g.getColor();
		g.setColor(Config.CURRENT.getBackgroundColor());
		// ���ñ���ɫ
		int cellWidth = Config.CURRENT.getPreviewCellWidth();
		int cellHeight = Config.CURRENT.getPreviewCellHeight();
		int width = Config.CURRENT.getPreviewWidth() * cellWidth;
		int height = Config.CURRENT.getPreviewHeight() * cellHeight;
		g.fillRect(0, 0, width, height);
		// ��������
		if(Config.CURRENT.isShowPreviewGridLine()) {
			g.setColor(Config.CURRENT.getGridLineColor());
			// ����б߿�, �����Ƶ�һ��ˮƽ�ߺʹ�ֱ��
			if(getBorder() == null) {
				g.drawLine(0, 0, width - 1, 0);
				g.drawLine(0, 0, 0, height - 1);
			}
			// ����ˮƽ��
			for (int i = 1; i <= Config.CURRENT.getPreviewHeight(); i++) {
				g.drawLine(0, i * cellHeight - 1, width - 1, i * cellHeight - 1);
			}
			// ���ƴ�ֱ��
			for (int i = 1; i <= Config.CURRENT.getPreviewWidth(); i++) {
				g.drawLine(i * cellWidth - 1, 0, i * cellWidth - 1, height - 1);
			}
		}
		
		// ��ԭ�������ɫ
		g.translate(-x, -y);
        g.setColor(oldColor);
	}
	
	/**
	 * ������״Ԥ�����¼���Ӧ
	 */
	public void shapePreviewCreated(Shape shape) {
		this.shape = shape;
		paintShape(getGraphics(), shape);
	}

	/**
	 * ��״Ԥ��������¼���Ӧ
	 */
	public void shapePreviewCleared() {
		this.shape = null;
		paintBackGround(getGraphics());
	}

	/**
	 * �й���ʾ���������Ѿ��ı���¼���Ӧ
	 */
	public void viewConfigChanged() {
		repaint();
	}

	public void hotkeyConfigChanged() {}

	public void levelConfigChanged() {}
}


