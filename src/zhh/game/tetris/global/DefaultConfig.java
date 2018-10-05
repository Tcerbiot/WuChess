package zhh.game.tetris.global;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;


/**
 * 默认配置类<br>
 * 内容不可修改
 * @author zhaohuihua
 */
public class DefaultConfig {
	
	/**
	 * 默认字体
	 */
	protected Font defaultFont = new Font("宋体", Font.PLAIN, 12);
	
	/**
	 * 默认颜色
	 */
	protected Color defaultColor = Color.GRAY; // 灰色,gray,0x808080;
	
	/**
	 * 边框颜色
	 */
	protected Color borderColor = new Color(0x000088);
	
	/**
	 * 背景颜色
	 */
	protected Color backgroundColor = Color.WHITE;
	
	/**
	 * 网格颜色
	 */
	protected Color gridLineColor = new Color(0xCCCCCC);
	
	/**
	 * 游戏结束的蒙色
	 */
	protected Color coveringOverColor = new Color(255, 153, 153, 128);
	
	/**
	 * 游戏暂停的蒙色
	 */
	protected Color coveringPauseColor = new Color(153, 153, 255, 128);
	
	/**
	 * 闪烁颜色
	 */
	protected Color winkColor = Color.WHITE;
	
	/**
	 * 闪烁的暂停时间
	 */
	protected int winkPauseTime = 100;
	
	/**
	 * 地形的宽度(单位:格)
	 */
	protected int groundWidth = 12;
	
	/**
	 * 地形的高度(单位:格)
	 */
	protected int groundHeight = 20;

	/**
	 * 单元格的宽度
	 */
	protected int cellWidth = 20;
	
	/**
	 * 单元格的高度
	 */
	protected int cellHeight = 20;

	/**
	 * 预览的宽度(单位:格)
	 */
	protected int previewWidth = 5;
	
	/**
	 * 预览的高度(单位:格)
	 */
	protected int previewHeight = 5;
	
	/**
	 * 预览的单元格的宽度
	 */
	protected int previewCellWidth = 14;
	
	/**
	 * 预览的单元格的高度
	 */
	protected int previewCellHeight = 14;
	
	/**
	 * 是否显示游戏区的网格
	 */
	protected boolean showGridLine = true;
	
	/**
	 * 是否显示预览区的网格
	 */
	protected boolean showPreviewGridLine = true;
	
	/**
	 * 形状是否支持彩色
	 */
	protected boolean supportColorfulShape = true;
	
	/**
	 * 障碍物是否支持彩色
	 */
	protected boolean supportColorfulGround = true;
	
	/**
	 * 预览是否支持彩色
	 */
	protected boolean supportColorfulPreview = true;
	
	/**
	 * 是否支持声音
	 */
	protected boolean supportSound = false;
	
	/**
	 * 当前的级别集
	 */
	protected int currentLevelSet = 2;
	
	/**
	 * 初始级别
	 */
	protected int initLevel = 0;

	/**
	 * 游戏开始/结束的键码
	 */
	protected int startKey = KeyEvent.VK_ENTER;
	
	/**
	 * 游戏暂停/继续的键码
	 */
	protected int pauseKey = KeyEvent.VK_P;
	
	/**
	 * 形状左移的键码
	 */
	protected int leftKey = KeyEvent.VK_LEFT;
	
	/**
	 * 形状右移的键码
	 */
	protected int rightKey = KeyEvent.VK_RIGHT;
	
	/**
	 * 形状变形的键码
	 */
	protected int rotateKey = KeyEvent.VK_UP;
	
	/**
	 * 形状加速的键码
	 */
	protected int downKey = KeyEvent.VK_DOWN;
	
	/**
	 * 形状直落到底的键码
	 */
	protected int swiftKey = KeyEvent.VK_SPACE;

	DefaultConfig() {
		
	}

	/**
	 * 默认字体
	 * @return
	 */
	public Font getDefaultFont() {
		return defaultFont;
	}
	/**
	 * 默认颜色
	 * @return
	 */
	public Color getDefaultColor() {
		return defaultColor;
	}
	/**
	 * 边框颜色
	 * @return
	 */
	public Color getBorderColor() {
		return borderColor;
	}
	/**
	 * 背景颜色
	 * @return
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	/**
	 * 网格颜色
	 * @return
	 */
	public Color getGridLineColor() {
		return gridLineColor;
	}
	/**
	 * 游戏结束的蒙色
	 * @return
	 */
	public Color getCoveringOverColor() {
		return coveringOverColor;
	}
	/**
	 * 游戏暂停的蒙色
	 * @return
	 */
	public Color getCoveringPauseColor() {
		return coveringPauseColor;
	}
	/**
	 * 闪烁颜色
	 * @return
	 */
	public Color getWinkColor() {
		return winkColor;
	}
	/**
	 * 闪烁的暂停时间
	 * @return
	 */
	public int getWinkPauseTime() {
		return winkPauseTime;
	}
	/**
	 * 地形的宽度(单位:格)
	 * @return
	 */
	public int getGroundWidth() {
		return groundWidth;
	}
	/**
	 * 地形的高度(单位:格)
	 * @return
	 */
	public int getGroundHeight() {
		return groundHeight;
	}
	/**
	 * 单元格的宽度
	 * @return
	 */
	public int getCellWidth() {
		return cellWidth;
	}
	/**
	 * 单元格的高度
	 * @return
	 */
	public int getCellHeight() {
		return cellHeight;
	}
	/**
	 * 预览的宽度(单位:格)
	 * @return
	 */
	public int getPreviewWidth() {
		return previewWidth;
	}
	/**
	 * 预览的高度(单位:格)
	 * @return
	 */
	public int getPreviewHeight() {
		return previewHeight;
	}
	/**
	 * 预览的单元格的宽度
	 * @return
	 */
	public int getPreviewCellWidth() {
		return previewCellWidth;
	}
	/**
	 * 预览的单元格的高度
	 * @return
	 */
	public int getPreviewCellHeight() {
		return previewCellHeight;
	}
	/**
	 * 是否显示游戏区的网格
	 * @return
	 */
	public boolean isShowGridLine() {
		return showGridLine;
	}
	/**
	 * 是否显示预览区的网格
	 * @return
	 */
	public boolean isShowPreviewGridLine() {
		return showPreviewGridLine;
	}
	/**
	 * 是否支持彩色形状
	 * @return
	 */
	public boolean isSupportColorfulShape() {
		return supportColorfulShape;
	}
	/**
	 * 是否支持彩色障碍物
	 * @return
	 */
	public boolean isSupportColorfulGround() {
		return supportColorfulGround;
	}
	/**
	 * 是否支持彩色预览
	 * @return
	 */
	public boolean isSupportColorfulPreview() {
		return supportColorfulPreview;
	}
	/**
	 * 是否支持声音
	 * @return
	 */
	public boolean isSupportSound() {
		return supportSound;
	}
	/**
	 * 当前的级别集
	 * @return
	 */
	public int getCurrentLevelSet() {
		return currentLevelSet;
	}
	/**
	 * 初始级别
	 * @return
	 */
	public int getInitLevel() {
		return initLevel;
	}
	/**
	 * 游戏开始/结束的键码
	 * @return
	 */
	public int getStartKey() {
		return startKey;
	}
	/**
	 * 游戏暂停/继续的键码
	 * @return
	 */
	public int getPauseKey() {
		return pauseKey;
	}
	/**
	 * 形状左移的键码
	 * @return
	 */
	public int getLeftKey() {
		return leftKey;
	}
	/**
	 * 形状右移的键码
	 * @return
	 */
	public int getRightKey() {
		return rightKey;
	}
	/**
	 * 形状变形的键码
	 * @return
	 */
	public int getRotateKey() {
		return rotateKey;
	}
	/**
	 * 形状加速的键码
	 * @return
	 */
	public int getDownKey() {
		return downKey;
	}
	/**
	 * 形状直落到底的键码
	 * @return
	 */
	public int getSwiftKey() {
		return swiftKey;
	}

	/**
	 * 形状的所有颜色
	 */
	public static final Color[] COLORS = new Color[] {
		new Color(0xD2691E), // 巧克力,chocolate,0xD2691E
		new Color(0x800000), // 栗色,maroon,0x800000
		new Color(0x808000), // 橄榄绿,olive,0x808000
		new Color(0x008000), // 深绿,darkgreen,0x008000
		new Color(0x008080), // 蓝绿,teal,0x008080
		new Color(0x000080), // 海蓝,navy,0x000080
		new Color(0x800080), // 紫色,purple,0x800080
		new Color(0x4682B4), // 钢青,steelblue,0x4682B4
		new Color(0x9ACD32), // 黄绿,yellowgreen,0x9ACD32
		new Color(0x8FBC8B), // 深海绿,darkseagreen0x8FBC8B
		new Color(0xDC143C), // 深红,crimson,0xDC143C
		new Color(0xBA55D3), // 中紫,mediumorchid,0xBA55D3
		new Color(0x6A5ACD), // 暗灰蓝,slateblue,0x6A5ACD
		new Color(0xDAA520), // 金麒麟色,goldenrod,0xDAA520
		new Color(0xFF4500), // 桔红,orangered,0xFF4500
		new Color(0xFA8072)  // 肉色,salmon,0xFA8072
//		new Color(0x0000FF), // 蓝色,blue,0x0000FF(无立体感)
//		new Color(0xFF00FF), // 紫红,fuchsia,0xFF00FF(无立体感)
//		new Color(0xFFA500), // 橙色,orange,0xFFA500(与金麒麟色太接近)
//		new Color(0xFFD700), // 金色,gold,0xFFD700(太浅)
//		new Color(0x00BFFF), // 深天蓝,deepskyblue,0x00BFFF(太浅)
	};
	
	/**
	 * 根据类型获取形状的颜色
	 * @param type int 类型
	 * @return Color
	 */
	public Color getShapeColor(int type) {
		if(this.supportColorfulShape && type >= 0 && type < COLORS.length)
			return COLORS[type];
		else
			return defaultColor;
	}
	
	/**
	 * 根据类型获取障碍物的颜色
	 * @param type int 类型
	 * @return Color
	 */
	public Color getGroundColor(int type) {
		if(this.supportColorfulGround && type >= 0 && type < COLORS.length)
			return COLORS[type];
		else
			return defaultColor;
	}

	/**
	 * 根据类型获取预览的颜色
	 * @param type int 类型
	 * @return Color
	 */
	public Color getPreviewColor(int type) {
		if(this.supportColorfulPreview && type >= 0 && type < COLORS.length)
			return COLORS[type];
		else
			return defaultColor;
	}
}

