package zhh.game.tetris.global;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;


/**
 * Ĭ��������<br>
 * ���ݲ����޸�
 * @author zhaohuihua
 */
public class DefaultConfig {
	
	/**
	 * Ĭ������
	 */
	protected Font defaultFont = new Font("����", Font.PLAIN, 12);
	
	/**
	 * Ĭ����ɫ
	 */
	protected Color defaultColor = Color.GRAY; // ��ɫ,gray,0x808080;
	
	/**
	 * �߿���ɫ
	 */
	protected Color borderColor = new Color(0x000088);
	
	/**
	 * ������ɫ
	 */
	protected Color backgroundColor = Color.WHITE;
	
	/**
	 * ������ɫ
	 */
	protected Color gridLineColor = new Color(0xCCCCCC);
	
	/**
	 * ��Ϸ��������ɫ
	 */
	protected Color coveringOverColor = new Color(255, 153, 153, 128);
	
	/**
	 * ��Ϸ��ͣ����ɫ
	 */
	protected Color coveringPauseColor = new Color(153, 153, 255, 128);
	
	/**
	 * ��˸��ɫ
	 */
	protected Color winkColor = Color.WHITE;
	
	/**
	 * ��˸����ͣʱ��
	 */
	protected int winkPauseTime = 100;
	
	/**
	 * ���εĿ��(��λ:��)
	 */
	protected int groundWidth = 12;
	
	/**
	 * ���εĸ߶�(��λ:��)
	 */
	protected int groundHeight = 20;

	/**
	 * ��Ԫ��Ŀ��
	 */
	protected int cellWidth = 20;
	
	/**
	 * ��Ԫ��ĸ߶�
	 */
	protected int cellHeight = 20;

	/**
	 * Ԥ���Ŀ��(��λ:��)
	 */
	protected int previewWidth = 5;
	
	/**
	 * Ԥ���ĸ߶�(��λ:��)
	 */
	protected int previewHeight = 5;
	
	/**
	 * Ԥ���ĵ�Ԫ��Ŀ��
	 */
	protected int previewCellWidth = 14;
	
	/**
	 * Ԥ���ĵ�Ԫ��ĸ߶�
	 */
	protected int previewCellHeight = 14;
	
	/**
	 * �Ƿ���ʾ��Ϸ��������
	 */
	protected boolean showGridLine = true;
	
	/**
	 * �Ƿ���ʾԤ����������
	 */
	protected boolean showPreviewGridLine = true;
	
	/**
	 * ��״�Ƿ�֧�ֲ�ɫ
	 */
	protected boolean supportColorfulShape = true;
	
	/**
	 * �ϰ����Ƿ�֧�ֲ�ɫ
	 */
	protected boolean supportColorfulGround = true;
	
	/**
	 * Ԥ���Ƿ�֧�ֲ�ɫ
	 */
	protected boolean supportColorfulPreview = true;
	
	/**
	 * �Ƿ�֧������
	 */
	protected boolean supportSound = false;
	
	/**
	 * ��ǰ�ļ���
	 */
	protected int currentLevelSet = 2;
	
	/**
	 * ��ʼ����
	 */
	protected int initLevel = 0;

	/**
	 * ��Ϸ��ʼ/�����ļ���
	 */
	protected int startKey = KeyEvent.VK_ENTER;
	
	/**
	 * ��Ϸ��ͣ/�����ļ���
	 */
	protected int pauseKey = KeyEvent.VK_P;
	
	/**
	 * ��״���Ƶļ���
	 */
	protected int leftKey = KeyEvent.VK_LEFT;
	
	/**
	 * ��״���Ƶļ���
	 */
	protected int rightKey = KeyEvent.VK_RIGHT;
	
	/**
	 * ��״���εļ���
	 */
	protected int rotateKey = KeyEvent.VK_UP;
	
	/**
	 * ��״���ٵļ���
	 */
	protected int downKey = KeyEvent.VK_DOWN;
	
	/**
	 * ��״ֱ�䵽�׵ļ���
	 */
	protected int swiftKey = KeyEvent.VK_SPACE;

	DefaultConfig() {
		
	}

	/**
	 * Ĭ������
	 * @return
	 */
	public Font getDefaultFont() {
		return defaultFont;
	}
	/**
	 * Ĭ����ɫ
	 * @return
	 */
	public Color getDefaultColor() {
		return defaultColor;
	}
	/**
	 * �߿���ɫ
	 * @return
	 */
	public Color getBorderColor() {
		return borderColor;
	}
	/**
	 * ������ɫ
	 * @return
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	/**
	 * ������ɫ
	 * @return
	 */
	public Color getGridLineColor() {
		return gridLineColor;
	}
	/**
	 * ��Ϸ��������ɫ
	 * @return
	 */
	public Color getCoveringOverColor() {
		return coveringOverColor;
	}
	/**
	 * ��Ϸ��ͣ����ɫ
	 * @return
	 */
	public Color getCoveringPauseColor() {
		return coveringPauseColor;
	}
	/**
	 * ��˸��ɫ
	 * @return
	 */
	public Color getWinkColor() {
		return winkColor;
	}
	/**
	 * ��˸����ͣʱ��
	 * @return
	 */
	public int getWinkPauseTime() {
		return winkPauseTime;
	}
	/**
	 * ���εĿ��(��λ:��)
	 * @return
	 */
	public int getGroundWidth() {
		return groundWidth;
	}
	/**
	 * ���εĸ߶�(��λ:��)
	 * @return
	 */
	public int getGroundHeight() {
		return groundHeight;
	}
	/**
	 * ��Ԫ��Ŀ��
	 * @return
	 */
	public int getCellWidth() {
		return cellWidth;
	}
	/**
	 * ��Ԫ��ĸ߶�
	 * @return
	 */
	public int getCellHeight() {
		return cellHeight;
	}
	/**
	 * Ԥ���Ŀ��(��λ:��)
	 * @return
	 */
	public int getPreviewWidth() {
		return previewWidth;
	}
	/**
	 * Ԥ���ĸ߶�(��λ:��)
	 * @return
	 */
	public int getPreviewHeight() {
		return previewHeight;
	}
	/**
	 * Ԥ���ĵ�Ԫ��Ŀ��
	 * @return
	 */
	public int getPreviewCellWidth() {
		return previewCellWidth;
	}
	/**
	 * Ԥ���ĵ�Ԫ��ĸ߶�
	 * @return
	 */
	public int getPreviewCellHeight() {
		return previewCellHeight;
	}
	/**
	 * �Ƿ���ʾ��Ϸ��������
	 * @return
	 */
	public boolean isShowGridLine() {
		return showGridLine;
	}
	/**
	 * �Ƿ���ʾԤ����������
	 * @return
	 */
	public boolean isShowPreviewGridLine() {
		return showPreviewGridLine;
	}
	/**
	 * �Ƿ�֧�ֲ�ɫ��״
	 * @return
	 */
	public boolean isSupportColorfulShape() {
		return supportColorfulShape;
	}
	/**
	 * �Ƿ�֧�ֲ�ɫ�ϰ���
	 * @return
	 */
	public boolean isSupportColorfulGround() {
		return supportColorfulGround;
	}
	/**
	 * �Ƿ�֧�ֲ�ɫԤ��
	 * @return
	 */
	public boolean isSupportColorfulPreview() {
		return supportColorfulPreview;
	}
	/**
	 * �Ƿ�֧������
	 * @return
	 */
	public boolean isSupportSound() {
		return supportSound;
	}
	/**
	 * ��ǰ�ļ���
	 * @return
	 */
	public int getCurrentLevelSet() {
		return currentLevelSet;
	}
	/**
	 * ��ʼ����
	 * @return
	 */
	public int getInitLevel() {
		return initLevel;
	}
	/**
	 * ��Ϸ��ʼ/�����ļ���
	 * @return
	 */
	public int getStartKey() {
		return startKey;
	}
	/**
	 * ��Ϸ��ͣ/�����ļ���
	 * @return
	 */
	public int getPauseKey() {
		return pauseKey;
	}
	/**
	 * ��״���Ƶļ���
	 * @return
	 */
	public int getLeftKey() {
		return leftKey;
	}
	/**
	 * ��״���Ƶļ���
	 * @return
	 */
	public int getRightKey() {
		return rightKey;
	}
	/**
	 * ��״���εļ���
	 * @return
	 */
	public int getRotateKey() {
		return rotateKey;
	}
	/**
	 * ��״���ٵļ���
	 * @return
	 */
	public int getDownKey() {
		return downKey;
	}
	/**
	 * ��״ֱ�䵽�׵ļ���
	 * @return
	 */
	public int getSwiftKey() {
		return swiftKey;
	}

	/**
	 * ��״��������ɫ
	 */
	public static final Color[] COLORS = new Color[] {
		new Color(0xD2691E), // �ɿ���,chocolate,0xD2691E
		new Color(0x800000), // ��ɫ,maroon,0x800000
		new Color(0x808000), // �����,olive,0x808000
		new Color(0x008000), // ����,darkgreen,0x008000
		new Color(0x008080), // ����,teal,0x008080
		new Color(0x000080), // ����,navy,0x000080
		new Color(0x800080), // ��ɫ,purple,0x800080
		new Color(0x4682B4), // ����,steelblue,0x4682B4
		new Color(0x9ACD32), // ����,yellowgreen,0x9ACD32
		new Color(0x8FBC8B), // ���,darkseagreen0x8FBC8B
		new Color(0xDC143C), // ���,crimson,0xDC143C
		new Color(0xBA55D3), // ����,mediumorchid,0xBA55D3
		new Color(0x6A5ACD), // ������,slateblue,0x6A5ACD
		new Color(0xDAA520), // ������ɫ,goldenrod,0xDAA520
		new Color(0xFF4500), // �ۺ�,orangered,0xFF4500
		new Color(0xFA8072)  // ��ɫ,salmon,0xFA8072
//		new Color(0x0000FF), // ��ɫ,blue,0x0000FF(�������)
//		new Color(0xFF00FF), // �Ϻ�,fuchsia,0xFF00FF(�������)
//		new Color(0xFFA500), // ��ɫ,orange,0xFFA500(�������ɫ̫�ӽ�)
//		new Color(0xFFD700), // ��ɫ,gold,0xFFD700(̫ǳ)
//		new Color(0x00BFFF), // ������,deepskyblue,0x00BFFF(̫ǳ)
	};
	
	/**
	 * �������ͻ�ȡ��״����ɫ
	 * @param type int ����
	 * @return Color
	 */
	public Color getShapeColor(int type) {
		if(this.supportColorfulShape && type >= 0 && type < COLORS.length)
			return COLORS[type];
		else
			return defaultColor;
	}
	
	/**
	 * �������ͻ�ȡ�ϰ������ɫ
	 * @param type int ����
	 * @return Color
	 */
	public Color getGroundColor(int type) {
		if(this.supportColorfulGround && type >= 0 && type < COLORS.length)
			return COLORS[type];
		else
			return defaultColor;
	}

	/**
	 * �������ͻ�ȡԤ������ɫ
	 * @param type int ����
	 * @return Color
	 */
	public Color getPreviewColor(int type) {
		if(this.supportColorfulPreview && type >= 0 && type < COLORS.length)
			return COLORS[type];
		else
			return defaultColor;
	}
}

