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
 * 游戏预览界面
 * @author fuyunliang
 */
public class PreviewPanel extends JPanel implements PreviewListener, ConfigListener {

	/**
	 * 串行化版本唯一标识符
	 */
	private static final long serialVersionUID = -7185384434960112918L;
	
	/**
	 * 形状
	 */
	protected Shape shape;
	
	/**
	 * 游戏预览界面
	 */
	public PreviewPanel() {
		super();
	}
	
	/**
	 * 绘制预览形状
	 * @param g Graphics
	 * @param shape Shape
	 */
	public void paintShape(Graphics g, Shape shape) {
		if(shape == null) return;
		
		// 转换坐标, 预留出边框的位置
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		g.translate(x, y);
	
		// 设置颜色
		Color oldColor = g.getColor();
		g.setColor(Config.CURRENT.getPreviewColor(shape.getType()));
		
		// 绘制预览形状
		int[][] points = shape.getPoints(false);
		int length = points.length;
		for (int i = 0; i < length; i++) {
			if(1 + points[i][1] < 0) continue;
			g.fill3DRect((1 + points[i][0]) * Config.CURRENT.getPreviewCellWidth(),
					(1 + points[i][1]) * Config.CURRENT.getPreviewCellHeight(), 
					Config.CURRENT.getPreviewCellWidth(), Config.CURRENT.getPreviewCellHeight(), true);
		}
		
		// 还原坐标和颜色
		g.translate(-x, -y);
        g.setColor(oldColor);
	}
	
	/**
	 * 绘制组件
	 * @param g Graphics
	 */
	public void paintComponent(Graphics g) {
		paintBackGround(g);
		paintShape(g, shape);
	}
	
	/**
	 * 绘制背景
	 * @param g Graphics
	 */
	private void paintBackGround(Graphics g) {
		// 转换坐标, 预留出边框的位置
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		g.translate(x, y);

		// 设置颜色
		Color oldColor = g.getColor();
		g.setColor(Config.CURRENT.getBackgroundColor());
		// 设置背景色
		int cellWidth = Config.CURRENT.getPreviewCellWidth();
		int cellHeight = Config.CURRENT.getPreviewCellHeight();
		int width = Config.CURRENT.getPreviewWidth() * cellWidth;
		int height = Config.CURRENT.getPreviewHeight() * cellHeight;
		g.fillRect(0, 0, width, height);
		// 绘制网格
		if(Config.CURRENT.isShowPreviewGridLine()) {
			g.setColor(Config.CURRENT.getGridLineColor());
			// 如果有边框, 不绘制第一条水平线和垂直线
			if(getBorder() == null) {
				g.drawLine(0, 0, width - 1, 0);
				g.drawLine(0, 0, 0, height - 1);
			}
			// 绘制水平线
			for (int i = 1; i <= Config.CURRENT.getPreviewHeight(); i++) {
				g.drawLine(0, i * cellHeight - 1, width - 1, i * cellHeight - 1);
			}
			// 绘制垂直线
			for (int i = 1; i <= Config.CURRENT.getPreviewWidth(); i++) {
				g.drawLine(i * cellWidth - 1, 0, i * cellWidth - 1, height - 1);
			}
		}
		
		// 还原坐标和颜色
		g.translate(-x, -y);
        g.setColor(oldColor);
	}
	
	/**
	 * 创建形状预览的事件响应
	 */
	public void shapePreviewCreated(Shape shape) {
		this.shape = shape;
		paintShape(getGraphics(), shape);
	}

	/**
	 * 形状预览清除的事件响应
	 */
	public void shapePreviewCleared() {
		this.shape = null;
		paintBackGround(getGraphics());
	}

	/**
	 * 有关显示的配置项已经改变的事件响应
	 */
	public void viewConfigChanged() {
		repaint();
	}

	public void hotkeyConfigChanged() {}

	public void levelConfigChanged() {}
}


