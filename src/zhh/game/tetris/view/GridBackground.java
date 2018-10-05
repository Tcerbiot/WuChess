package zhh.game.tetris.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

/**
 * 网格背景
 * @author fuyunliang
 */
public class GridBackground {
	private final int cellWidth;
	private final int cellHeight;
	private final Color backgroundColor;
	private final Color gridLineColor;
	private final boolean isRound;
	
	public GridBackground() {
		this(2, 2, null, null, false);
	}
	
	public GridBackground(int cellWidth, int cellHeight) {
		this(cellWidth, cellHeight, null, null, false);
	}
	
	public GridBackground(int cellWidth, int cellHeight, boolean isRound) {
		this(cellWidth, cellHeight, null, null, isRound);
	}
	public GridBackground(Color backgroundColor, Color gridLineColor) {
		this(2, 2, backgroundColor, gridLineColor, false);
	}
	public GridBackground(Color backgroundColor, Color gridLineColor, boolean isRound) {
		this(2, 2, backgroundColor, gridLineColor, isRound);
	}
	public GridBackground(int cellWidth, int cellHeight, 
			Color backgroundColor, Color gridLineColor, boolean isRound) {
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		this.backgroundColor = backgroundColor;
		this.gridLineColor = gridLineColor;
		this.isRound = isRound;
	}

	/**
	 * 按指定的位置和尺寸绘制指定组件的背景
     * @param c Component 要为其绘制边框的组件
     * @param g Graphics 图形对象
     * @param x int 所绘制边框的 x 坐标位置
     * @param y int 所绘制边框的 y 坐标位置
     * @param width int 所绘制边框的宽度
     * @param height int 所绘制边框的高度
	 */
	public void paintBackground(Component c, Graphics g, int x, int y, int width, int height) {
		g.translate(x, y);
		Color oldColor = g.getColor();

		int cellWidth = this.cellWidth;
		int cellHeight = this.cellHeight;
		Color backgroundColor = this.backgroundColor == null 
				? c.getBackground() : this.backgroundColor;
		Color gridLineColor = this.gridLineColor == null 
				? c.getForeground() : this.gridLineColor;
		
		// 填充背景色
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		
		// 绘制网格
		g.setColor(gridLineColor);
		// 如果围绕, 绘制一个包围的方框
		if(isRound) {
			g.drawRect(x, y, width, height);
		}
		// 绘制水平线
		int count = height / cellHeight;
		for (int i = 1; i <= count; i++) {
			g.drawLine(0, i * cellHeight - 1, width - 1, i * cellHeight - 1);
		}
		// 绘制垂直线
		count = width / cellWidth;
		for (int i = 1; i <= count; i++) {
			g.drawLine(i * cellWidth - 1, 0, i * cellWidth - 1, height - 1);
		}

		// 还原坐标和颜色
		g.translate(-x, -y);
        g.setColor(oldColor);
	}

}


