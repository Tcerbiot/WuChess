package zhh.game.tetris.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * 粗的斜面边框
 * @author fuyunliang
 */
public class ThickBevelBorder extends BevelBorder implements Border {
	
	/**
	 * 串行化版本唯一标识符
	 */
	private static final long serialVersionUID = 4926637553111203848L;
	
	/**
	 * 边框的厚度
	 */
	protected final int thickness;
	
	/**
	 * 创建粗的斜面边框<br>
	 * 默认厚度为2<br>
	 * @param bevelType int 边框的斜面类型<br>
	 *         BevelBorder.RAISED: 凸出斜面类型<br>
	 *         BevelBorder.LOWERED: 凹入斜面类型<br>
	 */
    public ThickBevelBorder(int bevelType) {
        this(bevelType, 2);
    }
    
    /**
	 * 创建粗的斜面边框<br>
	 * 默认厚度为2<br>
	 * @param bevelType int 边框的斜面类型<br>
	 *         BevelBorder.RAISED: 凸出斜面类型<br>
	 *         BevelBorder.LOWERED: 凹入斜面类型<br>
     * @param highlightColor Color 斜面高亮显示所用颜色
     * @param shadowColor Color 斜面阴影所用颜色
     */
    public ThickBevelBorder(int bevelType, Color highlightColor, Color shadowColor) {
    	this(bevelType, 2, highlightColor, shadowColor);
    }
    
    /**
	 * 创建粗的斜面边框<br>
	 * 默认厚度为2<br>
	 * @param bevelType int 边框的斜面类型<br>
	 *         BevelBorder.RAISED: 凸出斜面类型<br>
	 *         BevelBorder.LOWERED: 凹入斜面类型<br>
     * @param highlightOuterColor Color 斜面外部高亮显示所用颜色
     * @param highlightInnerColor Color 斜面内部高亮显示所用颜色
     * @param shadowOuterColor Color 斜面外部阴影所用颜色
     * @param shadowInnerColor Color 斜面内部阴影所用颜色
     */
    public ThickBevelBorder(int bevelType, Color highlightOuterColor, 
            Color highlightInnerColor, Color shadowOuterColor, 
            Color shadowInnerColor) {
    	this(bevelType, 2, highlightOuterColor, 
                highlightInnerColor, shadowOuterColor, 
                shadowInnerColor);
    }
    
    /**
	 * 创建粗的斜面边框<br>
	 * @param bevelType int 边框的斜面类型<br>
	 *         BevelBorder.RAISED: 凸出斜面类型<br>
	 *         BevelBorder.LOWERED: 凹入斜面类型<br>
     * @param thickness int 边框厚度<br>
     *         最小厚度为2<br>
     *         只接受偶数厚度, 如果参数为奇数, 则取比参数小的最大偶数<br>
     */
    public ThickBevelBorder(int bevelType, int thickness) {
        super(bevelType);
        this.thickness = calculateThickness(thickness);
    }
    
	/**
	 * 创建粗的斜面边框<br>
	 * @param bevelType int 边框的斜面类型<br>
	 *         BevelBorder.RAISED: 凸出斜面类型<br>
	 *         BevelBorder.LOWERED: 凹入斜面类型<br>
     * @param thickness int 边框厚度<br>
     *         最小厚度为2<br>
     *         只接受偶数厚度, 如果参数为奇数, 则取比参数小的最大偶数<br>
     * @param highlightColor Color 斜面高亮显示所用颜色
     * @param shadowColor Color 斜面阴影所用颜色
	 */
    public ThickBevelBorder(int bevelType, int thickness, 
    		Color highlightColor, Color shadowColor) {
    	super(bevelType, highlightColor, shadowColor);
        this.thickness = calculateThickness(thickness);
    }
    
    /**
	 * 创建粗的斜面边框<br>
	 * @param bevelType int 边框的斜面类型<br>
	 *         BevelBorder.RAISED: 凸出斜面类型<br>
	 *         BevelBorder.LOWERED: 凹入斜面类型<br>
     * @param thickness int 边框厚度<br>
     *         最小厚度为2<br>
     *         只接受偶数厚度, 如果参数为奇数, 则取比参数小的最大偶数<br>
     * @param highlightOuterColor Color 斜面外部高亮显示所用颜色
     * @param highlightInnerColor Color 斜面内部高亮显示所用颜色
     * @param shadowOuterColor Color 斜面外部阴影所用颜色
     * @param shadowInnerColor Color 斜面内部阴影所用颜色
     */
    public ThickBevelBorder(int bevelType, int thickness, 
    		Color highlightOuterColor, Color highlightInnerColor, 
    		Color shadowOuterColor, Color shadowInnerColor) {
    	super(bevelType, highlightOuterColor, 
                highlightInnerColor, shadowOuterColor, 
                shadowInnerColor);
        this.thickness = calculateThickness(thickness);
    }
    
    /**
     * 计算厚度
     * @param thickness int 厚度参数
     * @return int 符合要求的厚度
     */
    private int calculateThickness(int thickness) {
    	return thickness < 2 ? 2 : thickness / 2 * 2;
    }
    
    /**
     * 按指定的位置和尺寸绘制指定组件的边框<br>
     * 在绘制凹入斜面时斜面阴影的内外颜色对调<br>
     * 以与父类 BevelBorder 的行为一致<br>
     * @param c Component 要为其绘制边框的组件
     * @param g Graphics 图形对象
     * @param x int 所绘制边框的 x 坐标位置
     * @param y int 所绘制边框的 y 坐标位置
     * @param width int 所绘制边框的宽度
     * @param height int 所绘制边框的高度
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    	if (bevelType != RAISED && bevelType != LOWERED) return;
    	
        Color oldColor = g.getColor();
        
        // 内外框的分界线
        int half = thickness / 2;

        int w = width;
        int h = height;
        // 绘制边框
        for (int i = 1; i <= thickness; i++) {
        	/*
        	 * 斜面阴影颜色<br>
        	 * 在绘制凹入斜面时斜面阴影的内外颜色对调<br>
        	 * 以与父类 BevelBorder 的行为一致
        	 */
			Color shadow = (bevelType == RAISED ? i <= half : i > half) 
							? getShadowOuterColor(c)
							: getShadowInnerColor(c);
			// 斜面高亮颜色
			Color highlight = i <= half 
							? getHighlightOuterColor(c) 
							: getHighlightInnerColor(c);

			// 依据斜面类型对调阴影与高亮颜色, 并绘制线条
			g.setColor(bevelType == RAISED ? highlight : shadow);
			g.drawLine(x, y, w, y);
			g.drawLine(x, y, x, h);
			g.setColor(bevelType == RAISED ? shadow : highlight);
			g.drawLine(w, y, w, h);
			g.drawLine(x, h, w, h);
			x++; y++;
			w--; h--;
		}

        g.setColor(oldColor);
    }
    
    /**
     * 返回边框的 insets
     * @return Insets
     */
    public Insets getBorderInsets(Component c)       {
    	return new Insets(thickness, thickness, thickness, thickness);
    }
    
    /**
     * 用此边框的当前 Insets 重新初始化 insets 参数
     * @param c Component 应用此边框 insets 值的组件
     * @param insets Insets 要重新初始化的对象
     * @return Insets
     */
    public Insets getBorderInsets(Component c, Insets insets) {
    	insets.left = insets.top = insets.right = insets.bottom = thickness;
    	return insets;
    }
    
    /**
     * 绘制凸出斜面类型的边框<br>
     * 该类的绘制实现只是根据斜面类型不同调整高亮与阴影的顺序即可, 无需调用不同的方法<br>
     * 因此该方法直接调用 paintBorder() 即可<br>
     * @param c Component 要为其绘制边框的组件
     * @param g Graphics 图形对象
     * @param x int 所绘制边框的 x 坐标位置
     * @param y int 所绘制边框的 y 坐标位置
     * @param width int 所绘制边框的宽度
     * @param height int 所绘制边框的高度
     */
    protected void paintRaisedBevel(Component c, Graphics g, 
    		int x, int y, int width, int height)  {
    	paintBorder(c, g, x, y, width, height);
    }

    /**
     * 绘制凹入斜面类型的边框<br>
     * 该类的绘制实现只是根据斜面类型不同调整高亮与阴影的顺序即可, 无需调用不同的方法<br>
     * 因此该方法直接调用 paintBorder() 即可<br>
     * @param c Component 要为其绘制边框的组件
     * @param g Graphics 图形对象
     * @param x int 所绘制边框的 x 坐标位置
     * @param y int 所绘制边框的 y 坐标位置
     * @param width int 所绘制边框的宽度
     * @param height int 所绘制边框的高度
     */
    protected void paintLoweredBevel(Component c, Graphics g, 
    		int x, int y, int width, int height)  {
    	paintBorder(c, g, x, y, width, height);
    }
}



