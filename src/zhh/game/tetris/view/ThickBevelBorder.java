package zhh.game.tetris.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * �ֵ�б��߿�
 * @author fuyunliang
 */
public class ThickBevelBorder extends BevelBorder implements Border {
	
	/**
	 * ���л��汾Ψһ��ʶ��
	 */
	private static final long serialVersionUID = 4926637553111203848L;
	
	/**
	 * �߿�ĺ��
	 */
	protected final int thickness;
	
	/**
	 * �����ֵ�б��߿�<br>
	 * Ĭ�Ϻ��Ϊ2<br>
	 * @param bevelType int �߿��б������<br>
	 *         BevelBorder.RAISED: ͹��б������<br>
	 *         BevelBorder.LOWERED: ����б������<br>
	 */
    public ThickBevelBorder(int bevelType) {
        this(bevelType, 2);
    }
    
    /**
	 * �����ֵ�б��߿�<br>
	 * Ĭ�Ϻ��Ϊ2<br>
	 * @param bevelType int �߿��б������<br>
	 *         BevelBorder.RAISED: ͹��б������<br>
	 *         BevelBorder.LOWERED: ����б������<br>
     * @param highlightColor Color б�������ʾ������ɫ
     * @param shadowColor Color б����Ӱ������ɫ
     */
    public ThickBevelBorder(int bevelType, Color highlightColor, Color shadowColor) {
    	this(bevelType, 2, highlightColor, shadowColor);
    }
    
    /**
	 * �����ֵ�б��߿�<br>
	 * Ĭ�Ϻ��Ϊ2<br>
	 * @param bevelType int �߿��б������<br>
	 *         BevelBorder.RAISED: ͹��б������<br>
	 *         BevelBorder.LOWERED: ����б������<br>
     * @param highlightOuterColor Color б���ⲿ������ʾ������ɫ
     * @param highlightInnerColor Color б���ڲ�������ʾ������ɫ
     * @param shadowOuterColor Color б���ⲿ��Ӱ������ɫ
     * @param shadowInnerColor Color б���ڲ���Ӱ������ɫ
     */
    public ThickBevelBorder(int bevelType, Color highlightOuterColor, 
            Color highlightInnerColor, Color shadowOuterColor, 
            Color shadowInnerColor) {
    	this(bevelType, 2, highlightOuterColor, 
                highlightInnerColor, shadowOuterColor, 
                shadowInnerColor);
    }
    
    /**
	 * �����ֵ�б��߿�<br>
	 * @param bevelType int �߿��б������<br>
	 *         BevelBorder.RAISED: ͹��б������<br>
	 *         BevelBorder.LOWERED: ����б������<br>
     * @param thickness int �߿���<br>
     *         ��С���Ϊ2<br>
     *         ֻ����ż�����, �������Ϊ����, ��ȡ�Ȳ���С�����ż��<br>
     */
    public ThickBevelBorder(int bevelType, int thickness) {
        super(bevelType);
        this.thickness = calculateThickness(thickness);
    }
    
	/**
	 * �����ֵ�б��߿�<br>
	 * @param bevelType int �߿��б������<br>
	 *         BevelBorder.RAISED: ͹��б������<br>
	 *         BevelBorder.LOWERED: ����б������<br>
     * @param thickness int �߿���<br>
     *         ��С���Ϊ2<br>
     *         ֻ����ż�����, �������Ϊ����, ��ȡ�Ȳ���С�����ż��<br>
     * @param highlightColor Color б�������ʾ������ɫ
     * @param shadowColor Color б����Ӱ������ɫ
	 */
    public ThickBevelBorder(int bevelType, int thickness, 
    		Color highlightColor, Color shadowColor) {
    	super(bevelType, highlightColor, shadowColor);
        this.thickness = calculateThickness(thickness);
    }
    
    /**
	 * �����ֵ�б��߿�<br>
	 * @param bevelType int �߿��б������<br>
	 *         BevelBorder.RAISED: ͹��б������<br>
	 *         BevelBorder.LOWERED: ����б������<br>
     * @param thickness int �߿���<br>
     *         ��С���Ϊ2<br>
     *         ֻ����ż�����, �������Ϊ����, ��ȡ�Ȳ���С�����ż��<br>
     * @param highlightOuterColor Color б���ⲿ������ʾ������ɫ
     * @param highlightInnerColor Color б���ڲ�������ʾ������ɫ
     * @param shadowOuterColor Color б���ⲿ��Ӱ������ɫ
     * @param shadowInnerColor Color б���ڲ���Ӱ������ɫ
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
     * ������
     * @param thickness int ��Ȳ���
     * @return int ����Ҫ��ĺ��
     */
    private int calculateThickness(int thickness) {
    	return thickness < 2 ? 2 : thickness / 2 * 2;
    }
    
    /**
     * ��ָ����λ�úͳߴ����ָ������ı߿�<br>
     * �ڻ��ư���б��ʱб����Ӱ��������ɫ�Ե�<br>
     * ���븸�� BevelBorder ����Ϊһ��<br>
     * @param c Component ҪΪ����Ʊ߿�����
     * @param g Graphics ͼ�ζ���
     * @param x int �����Ʊ߿�� x ����λ��
     * @param y int �����Ʊ߿�� y ����λ��
     * @param width int �����Ʊ߿�Ŀ��
     * @param height int �����Ʊ߿�ĸ߶�
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    	if (bevelType != RAISED && bevelType != LOWERED) return;
    	
        Color oldColor = g.getColor();
        
        // �����ķֽ���
        int half = thickness / 2;

        int w = width;
        int h = height;
        // ���Ʊ߿�
        for (int i = 1; i <= thickness; i++) {
        	/*
        	 * б����Ӱ��ɫ<br>
        	 * �ڻ��ư���б��ʱб����Ӱ��������ɫ�Ե�<br>
        	 * ���븸�� BevelBorder ����Ϊһ��
        	 */
			Color shadow = (bevelType == RAISED ? i <= half : i > half) 
							? getShadowOuterColor(c)
							: getShadowInnerColor(c);
			// б�������ɫ
			Color highlight = i <= half 
							? getHighlightOuterColor(c) 
							: getHighlightInnerColor(c);

			// ����б�����ͶԵ���Ӱ�������ɫ, ����������
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
     * ���ر߿�� insets
     * @return Insets
     */
    public Insets getBorderInsets(Component c)       {
    	return new Insets(thickness, thickness, thickness, thickness);
    }
    
    /**
     * �ô˱߿�ĵ�ǰ Insets ���³�ʼ�� insets ����
     * @param c Component Ӧ�ô˱߿� insets ֵ�����
     * @param insets Insets Ҫ���³�ʼ���Ķ���
     * @return Insets
     */
    public Insets getBorderInsets(Component c, Insets insets) {
    	insets.left = insets.top = insets.right = insets.bottom = thickness;
    	return insets;
    }
    
    /**
     * ����͹��б�����͵ı߿�<br>
     * ����Ļ���ʵ��ֻ�Ǹ���б�����Ͳ�ͬ������������Ӱ��˳�򼴿�, ������ò�ͬ�ķ���<br>
     * ��˸÷���ֱ�ӵ��� paintBorder() ����<br>
     * @param c Component ҪΪ����Ʊ߿�����
     * @param g Graphics ͼ�ζ���
     * @param x int �����Ʊ߿�� x ����λ��
     * @param y int �����Ʊ߿�� y ����λ��
     * @param width int �����Ʊ߿�Ŀ��
     * @param height int �����Ʊ߿�ĸ߶�
     */
    protected void paintRaisedBevel(Component c, Graphics g, 
    		int x, int y, int width, int height)  {
    	paintBorder(c, g, x, y, width, height);
    }

    /**
     * ���ư���б�����͵ı߿�<br>
     * ����Ļ���ʵ��ֻ�Ǹ���б�����Ͳ�ͬ������������Ӱ��˳�򼴿�, ������ò�ͬ�ķ���<br>
     * ��˸÷���ֱ�ӵ��� paintBorder() ����<br>
     * @param c Component ҪΪ����Ʊ߿�����
     * @param g Graphics ͼ�ζ���
     * @param x int �����Ʊ߿�� x ����λ��
     * @param y int �����Ʊ߿�� y ����λ��
     * @param width int �����Ʊ߿�Ŀ��
     * @param height int �����Ʊ߿�ĸ߶�
     */
    protected void paintLoweredBevel(Component c, Graphics g, 
    		int x, int y, int width, int height)  {
    	paintBorder(c, g, x, y, width, height);
    }
}



