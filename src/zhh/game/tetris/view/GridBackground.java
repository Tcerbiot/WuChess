package zhh.game.tetris.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

/**
 * ���񱳾�
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
	 * ��ָ����λ�úͳߴ����ָ������ı���
     * @param c Component ҪΪ����Ʊ߿�����
     * @param g Graphics ͼ�ζ���
     * @param x int �����Ʊ߿�� x ����λ��
     * @param y int �����Ʊ߿�� y ����λ��
     * @param width int �����Ʊ߿�Ŀ��
     * @param height int �����Ʊ߿�ĸ߶�
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
		
		// ��䱳��ɫ
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		
		// ��������
		g.setColor(gridLineColor);
		// ���Χ��, ����һ����Χ�ķ���
		if(isRound) {
			g.drawRect(x, y, width, height);
		}
		// ����ˮƽ��
		int count = height / cellHeight;
		for (int i = 1; i <= count; i++) {
			g.drawLine(0, i * cellHeight - 1, width - 1, i * cellHeight - 1);
		}
		// ���ƴ�ֱ��
		count = width / cellWidth;
		for (int i = 1; i <= count; i++) {
			g.drawLine(i * cellWidth - 1, 0, i * cellWidth - 1, height - 1);
		}

		// ��ԭ�������ɫ
		g.translate(-x, -y);
        g.setColor(oldColor);
	}

}


