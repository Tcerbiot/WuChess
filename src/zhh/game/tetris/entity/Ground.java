package zhh.game.tetris.entity;

import java.util.Random;

/**
 * 地形<br>
 * 内含障碍物<br>
 * 障碍物由一个个点构成<br>
 * 点的x,y坐标标明该障碍物位于地形的位置<br>
 * @author zhaohuihua
 */
public class Ground {

	/**
	 * 随机数生成器
	 */
	private final static Random random = new Random();
	
	/**
	 * 宽度
	 */
	private int width;
	
	/**
	 * 高度
	 */
	private int height;

	/**
	 * 保存障碍物的每一点的坐标和类型<br>
	 * 每个障碍物点由坐标和类型组成<br>
	 * [i][0]: 障碍物点的x坐标<br>
	 * [i][1]: 障碍物点的y坐标<br>
	 * [i][2]: 障碍物点的类型<br>
	 * (类型主要用于获取障碍物点的颜色, 默认类型为-1, 将形状变为障碍物时将由形状的类型决定)<br>
	 */
	private int[][] points;

	/**
	 * 地形
	 * @param width 宽度
	 * @param height 高度
	 */
	public Ground(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 获取宽度
	 * @return int 宽度
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * 获取高度
	 * @return int 高度
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * 获取障碍物
	 * @return int[][] 障碍物的每一点的坐标和类型<br>
	 * 由多个障碍物的点组成<br>
	 * 每个障碍物点由坐标和类型组成<br>
	 * [i][0]: 障碍物点的x坐标<br>
	 * [i][1]: 障碍物点的y坐标<br>
	 * [i][2]: 障碍物点的类型<br>
	 * (类型主要用于获取障碍物点的颜色, 默认类型为-1, 将形状变为障碍物时将由形状的类型决定)<br>
	 */
	public int[][] getPoints() {
		return points;
	}

	/**
	 * 清空障碍物
	 */
	public void clear() {
		this.points = null;
	}
	
	/**
	 * 检查满行<br>
	 * 一行的障碍物个数等于最大个数即判定为满行<br>
	 * 未考虑重叠问题(正常情况下不会发生重叠)<br>
	 * @return int[] 满行的行号(自上至下计行号,第1行行号为0)
	 */
	public int[] checkFullLine() {
		/**
		 * 定义一个可记录所有行的数组 fullLine <br>
		 * 遍历障碍物, 计算每行的障碍物个数<br>
		 * 如果某一行的个数等于最大个数, 记下该行号, 并统计已有多少满行<br>
		 * 遍历完后所有满行的行号位于数组 fullLine 的头几个<br>
		 * 取得这几个行号返回<br>
		 */
		int fullLineCount = 0;
		int length = points.length;
		int[] lineCount = new int[height];
		int[] fullLine = new int[height];
		for(int i = 0; i < length; i++) {
			if(points[i][1] < 0) continue;
			int temp = ++lineCount[points[i][1]];
			if(temp == width) {
				fullLine[fullLineCount] = points[i][1];
				fullLineCount++;
			}
		}
		int[] temp = new int[fullLineCount];
		System.arraycopy(fullLine, 0, temp, 0, fullLineCount);
		return temp;
	}
	
	/**
	 * 删行<br>
	 * 根据行号删行<br>
	 * 可一次删除多行<br>
	 * 删行后, 剩余的障碍物自动沉底<br>
	 * @param line int[] 待删除的行号
	 * @return int 共删除了多少个障碍物点
	 */
	public int deleteLine(int[] line) {
		/**
		 * 遍历障碍物<br>
		 * 将障碍物点的y坐标与待删除的行号一一比较, 记下这个点应该下沉多少行<br>
		 * 如果匹配, 表明该点将被删除<br>
		 * 否则, 将该点转移至临时数组, 同时该点按要求下沉<br>
		 * 遍历完后所有不应删除的障碍物点位于临时数组的头几个<br>
		 * 取得这些点作为删除后的障碍物<br>
		 */
		int length = this.points.length;
		int[][] temp = new int[length][3];
		int count = 0;
		for(int i = 0; i < length; i++) {
			int lineCount = line.length;
			boolean delete = false;
			int offset = 0;
			for(int j = 0; j < lineCount; j++) {
				if(this.points[i][1] == line[j]) {
					delete = true;
				} else if(line[j] < height && this.points[i][1] < line[j]) {
					// 记录障碍物点应该下沉多少行
					offset++;
				}
			}
			if(!delete) {
				temp[count][0] = this.points[i][0];
				temp[count][1] = this.points[i][1] + offset;
				temp[count][2] = this.points[i][2];
				count++;
			}
		}
    	if(count == length)
    		return 0;
		int[][] points = new int[count][];
		System.arraycopy(temp, 0, points, 0, count);
		this.points = points;
		return length - count;
	}
	
	/**
	 * 填充一个形状<br>
	 * 未考虑重叠问题, 应在外部保证不会发生重叠<br>
	 * @param shape Shape 待填充的形状
	 */
	public void fill(Shape shape) {
		fill(shape.getPoints());
	}
	
	/**
	 * 填充一些障碍物点<br>
	 * 未考虑重叠问题, 应在外部保证不会发生重叠<br>
	 * @param points int[][] 待填充的障碍物点<br>
	 * 障碍物点由坐标和类型组成<br>
	 * [i][0]: 障碍物点的x坐标<br>
	 * [i][1]: 障碍物点的y坐标<br>
	 * [i][2]: 障碍物点的类型<br>
	 */
	public void fill(int[][] points) {
		if(this.points == null || this.points.length == 0) {
			this.points = points;
			return;
		}
		int[][] temp = new int[this.points.length + points.length][];
		System.arraycopy(this.points, 0, temp, 0, this.points.length);
		System.arraycopy(points, 0, temp, this.points.length, points.length);
		this.points = temp;
	}
	
	/**
	 * 随机填充一些障碍物点<br>
	 * 从底部开始填充<br>
	 * 填充的障碍物点的类型为默认类型-1<br>
	 * @param lineCount int 填充多少行
	 * @param rate double 填充比率<br>
	 * 比率不能超过 0.90, 否则会出现满行, 不执行操作<br>
	 */
	public void randomFill(int lineCount, double rate) {
		randomFill(lineCount, (int)(width * lineCount * rate));
	}
	
	/**
	 * 随机填充一些障碍物点<br>
	 * 从底部开始填充<br>
	 * 填充的障碍物点的类型为默认类型-1<br>
	 * @param lineCount int 填充多少行
	 * @param fillCount int 填充多少个障碍物点<br>
	 * 填充的个数必须小于 lineCount * width - lineCount, 否则会出现满行, 不执行操作<br>
	 */
	public void randomFill(int lineCount, int fillCount) {
		
		this.points = null;
		
		if(lineCount <= 0 || lineCount >= height)
			return;
		
		// 填充的个数必须小于 lineCount * width - lineCount, 否则会出现满行
		if(fillCount > lineCount * width - lineCount)
			return;

		// 随机产生的点
		int[][] points = new int[fillCount][3];
		// 为简化计算保存的随机产生的点相对位置
		int[] temp = new int[fillCount];
		// 每一行的点的个数, 用于判断是否即将满行
		int[] linePointCount = new int[lineCount];
		int max = lineCount * width;
		for (int i = 0; i < fillCount; i++) {
			boolean cancel;
			int row;
			do {
				// 随机产生一个点的相对位置
				temp[i] = random.nextInt(max);
				// 判断是否满行
				row = temp[i] / width;
				if(linePointCount[row] >= width - 1) {
					// 即将满行, 该点作废
					cancel = true;
					continue;
				}
				// 遍历已经产生的点, 判断是否发生碰撞
				cancel = false;
				for (int j = 0; j < i; j++) {
					if (temp[i] == temp[j]) {
						// 发生碰撞, 该点作废
						cancel = true;
						break;
					}
				}
			} while(cancel);
			// 根据点的相对位置计算实际坐标
			points[i][0] = temp[i] % width;
			points[i][1] = height - lineCount + row;
			points[i][2] = -1;
			// 计算每一行的点的个数, 用于判断是否即将满行
			linePointCount[row]++;
		}
		fill(points);
	}
	
	/**
	 * 判断一个形状与地形是否发生碰撞<br>
	 * 形状所在的点已有障碍物, 或者形状的点超过地形的宽度或高度, 即判定为碰撞<br>
	 * 但形状的点的行号为负数, 不是碰撞<br>
	 * @param shape Shape 待比较的形状
	 * @return boolean 是否发生碰撞
	 */
	public boolean collisional(Shape shape) {
		int[][] shapePoints = shape.getPoints();
		int shapeLength = shapePoints.length;
		int thisLength = points == null ? 0 : points.length;
		for(int i = 0; i < shapeLength; i++) {
			if(shapePoints[i][0] < 0 || shapePoints[i][0] >= width
					|| shapePoints[i][1] >= height)
				return true;
			for (int j = 0; j < thisLength; j++) {
				if (points[j][0] == shapePoints[i][0] && points[j][1] == shapePoints[i][1])
					return true;
			}
		}
		return false;
	}

	/**
	 * 障碍物转化为点阵坐标表示<br>
	 * 存在障碍物的点标记为1, 否则为0<br>
	 * 行号为负数的障碍物将被抛弃<br>
	 * @return int[][] 该地形的障碍物的点阵坐标
	 */
	public int[][] toMatrix() {
		/**
		 * 首先创建一个与地形等高宽的数组<br>
		 * 然后遍历每一个障碍物点, 按点的x,y坐标将数组相对应的位置标记为1<br>
		 */
		int[][] result = new int[height][width];
		if(points != null && points.length > 0)
			for(int i = 0; i < points.length; i++) {
				if(points[i][1] < 0) continue;
				result[points[i][1]][points[i][0]] = 1;
			}
		return result;
	}
	
	/**
	 * 障碍物转化为点阵形式的字符表示<br>
	 * 存在障碍物的点以实心方块表示, 否则以空心方块表示<br>
	 * @return String 障碍物的点阵形式字符串
	 */
	public String toMatrixString() {
		StringBuffer result = new StringBuffer();
		int[][] matrix = toMatrix();
		for(int i = 0; i < height; i++) {
			if(i > 0)
				result.append("\n");
			for(int j = 0; j < width; j++) {
				result.append(matrix[i][j] == 0 ? "□" : "■");
			}
		}
		return result.toString();
	}
	
	/**
	 * 复制
	 * @return Object 复制后的新地形
	 */
	public Object clone() {
		Ground ground = new Ground(width, height);
		if(this.points != null) {
			int[][] points = new int[this.points.length][];
			for(int i = 0; i < this.points.length; i++) {
				points[i] = new int[this.points[i].length];
				for(int j = 0; j < this.points[i].length; j++) {
					points[i][j] = this.points[i][j];
				}
			}
			ground.points = points;
		}
		return ground;
	}
	
	/**
	 * 转化为字符串
	 * @return String 字符串形式
	 */
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Width: ").append(width).append(", ")
			.append("Height: ").append(height).append(", ");
		
		result.append("Points: ");
		if(points == null || points.length == 0)
			result.append("[]");
		else {
			int length = points.length;
			for(int i = 0; i < length; i++) {
				result.append("[").append(points[i][0]).append(", ")
					.append(points[i][1]).append(", ")
					.append("Type: ").append(points[i][2]).append("]");
			}
		}
		
		return result.toString();
	}
}



