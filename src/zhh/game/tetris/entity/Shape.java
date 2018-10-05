package zhh.game.tetris.entity;

import java.util.Random;

/**
 * 形状<br>
 * 由一个个点构成<br>
 * 点的x,y坐标标明形状的点相对于该形状左上角的位置<br>
 * @author zhaohuihua
 */
public class Shape {
	
	/**
	 * 随机数生成器
	 */
	private final static Random random = new Random();
	
	/**
	 * 类型<br>
	 * 主要用于获取形状的颜色<br>
	 */
	private final int type;
	
	/**
	 * 由多个形状构成的表示该形状多种状态的点的集合<br>
	 * 表示形状在旋转变形时的点的不同表现<br>
	 * [i]: 表示该形状多种状态的其中一种<br>
	 * [i][0]: 该状态下形状的宽度和高度<br>
	 * [i][1..n]: 该状态下形状的点的x,y坐标<br>
	 */
	private final int[][][] shapes;
	
	/**
	 * 形状与容器顶端的距离
	 */
	private int top;
	
	/**
	 * 形状与容器左侧的距离
	 */
	private int left;
	
	/**
	 * 形状当前的状态
	 */
	private int status;
	
	/**
	 * 形状<br>
	 * 不提供外部实例化方式<br>
	 * 获取实例: ShapeFactory.getShape()<br>
	 * @param type int 类型
	 * @param shapes int[][][] 由多个形状构成的表示该形状多种状态的点的集合
	 */
	Shape(int type, int[][][] shapes) {
		this.type = type;
		this.shapes = shapes;
	}
	
	/**
	 * 形状当前状态下的所占宽度
	 * @return
	 */
	public int getWidth() {
		return shapes[status][0][0];
	}
	
	/**
	 * 形状当前状态下的所占高度
	 * @return
	 */
	public int getHeight() {
		return shapes[status][0][1];
	}
	
	/**
	 * 形状的类型
	 * @return
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * 获取形状当前状态下的所有点的集合<br>
	 * @return int[][] 点的集合<br>
	 * [i][0]: 点相对于容器的x坐标<br>
	 * [i][1]: 点相对于容器的y坐标<br>
	 * [i][2]: 点的类型<br>
	 */	
	public int[][] getPoints() {
		return getPoints(true);
	}
	
	/**
	 * 获取形状当前状态下的所有点的集合<br>
	 * @param absoluteLocation boolean 是否获取绝对位置的坐标<br>
	 *         true: 绝对位置, 点相对于容器的坐标<br>
	 *         false: 相对位置, 点相对于形状左上角的坐标<br>
	 * @return int[][] 点的集合<br>
	 * [i][0]: 点的x坐标<br>
	 * [i][1]: 点的y坐标<br>
	 * [i][2]: 点的类型<br>
	 */
	public int[][] getPoints(boolean absoluteLocation) {
		int[][] shape = shapes[status];
		int length = shape.length;
		int[][] points = new int[length - 1][3];
		// 第一个点表示形状的宽度和高度, 故i从1开始
		for (int i = 1; i < length; i++) {
			if(absoluteLocation) {
				points[i-1][0] = left + shape[i][0];
				points[i-1][1] = top + shape[i][1];
			} else {
				points[i-1][0] = shape[i][0];
				points[i-1][1] = shape[i][1];
			}
			points[i-1][2] = type;
		}
		return points;
	}
	
	/**
     * 移动到某一位置<br>
     * 以形状的左上角来定位<br>
     */
	public void moveTo(int x, int y) {
		this.left = x;
		this.top = y;
	}
	
	/**
     * 向上移动
     */
	public void moveUp() {
		top--;
	}

	/**
     * 向下移动
     */
	public void moveDown() {
		top++;
	}

	/**
     * 向左移动
     */
	public void moveLeft() {
		left--;
	}

	/**
     * 向右移动
     */
	public void moveRight() {
		left++;
	}

	/**
     * 旋转<br>
     * @param boolean clockwise 是否按顺时针方向旋转
     */
	public void rotate(boolean clockwise) {
		if(shapes.length > 1)
			status = (status + 1) % shapes.length;
	}

	/**
     * 按顺时针方向旋转
     */
	public void rotate() {
		rotate(true);
	}
	
	/**
	 * 随机旋转
	 */
	public void rotateRandom() {
		status = shapes.length > 1 ? random.nextInt(shapes.length) : 0;
	}
	
	/**
	 * 转化为点阵坐标表示<br>
	 * 存在点的位置标记为1, 否则为0<br>
	 * 如出现行号为负数的点, 则扩展高度, 直至点阵的高度够容纳所有的点<br>
	 * @return int[][] 该形状的点阵坐标
	 */
	public int[][] toMatrix() {
		int[][] result = new int[getHeight()][getWidth()];
		if(shapes != null && shapes.length > 0) {
			int[][] shape = shapes[status];
			int length = shape.length;
			// 扩展的行数
			int expansion = 0;
			// 第一个点表示形状的宽度和高度, 故i从1开始
			for (int i = 1; i < length; i++) {
				// 如果出现Y轴为负数的点, 扩展高度
				if(shape[i][1] < 0 && -shape[i][1] > expansion) {
					int[][] temp = new int[result.length + -shape[i][1]][getWidth()];
					System.arraycopy(result, 0, temp, -shape[i][1], result.length);
					result = temp;
					expansion += -shape[i][1];
				}
				result[shape[i][1]+expansion][shape[i][0]] = 1;
			}
		}
		return result;
	}
	
	/**
	 * 转化为点阵形式的字符表示<br>
	 * 存在点的位置以实心方块表示, 否则以空心方块表示<br>
	 * @return String 点阵形式字符串
	 * @return
	 */
	public String toMatrixString() {
		StringBuffer result = new StringBuffer();
		int[][] matrix = toMatrix();
		int height = matrix.length;
		for(int i = 0; i < height; i++) {
			if(i > 0)
				result.append("\n");
			int width = matrix[i].length;
			for(int j = 0; j < width; j++) {
				result.append(matrix[i][j] == 0 ? "□" : "■");
			}
		}
		return result.toString();
	}
	
	/**
	 * 复制<br>
	 * 因形状内的点集合不能改变, 不需复制<br>
	 * @return Object 复制后的新形状
	 */
	public Object clone() {
		Shape shape = new Shape(type, shapes);
		shape.top = top;
		shape.left = left;
		shape.status = status;
		return shape;
	}
	
	/**
	 * 转化为字符串
	 * @return String 字符串形式
	 */
	public String toString() {
		String typeStatus = new StringBuffer("00").append(type).append("-").append(status).toString();
		typeStatus = typeStatus.substring(typeStatus.length() - 4);
		StringBuffer result = new StringBuffer().append("[").append(typeStatus).append("] ");
		
		result.append("Width: ").append(getWidth()).append(", ")
				.append("Height: ").append(getHeight()).append(", ");
		
		result.append("Points: ");
		int[][] shape = getPoints();
		int length = shape.length;
		for (int i = 0; i < length; i++) {
			result.append("[").append(shape[i][0]).append(", ").append(shape[i][1]).append("]");
		}
		
		return result.toString();
	}
}


