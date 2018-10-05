package zhh.game.tetris.entity;

import java.util.Random;

/**
 * 形状工厂
 * @author fuyunliang
 */
public class ShapeFactory {
	
	/**
	 * 随机数生成器
	 */
	private static Random random = new Random();

	/**
	 * 全部形状:<br>
	 * 0:<br>
	 *	□□□□	□■□□<br>
	 *	■■■■	□■□□<br>
	 *	□□□□	□■□□<br>
	 *	□□□□	□■□□<br>
	 * 1:<br>
	 *	■□□	□■■<br>
	 *	■■□	■■□<br>
	 *	□■□	□□□<br>
	 * 2:<br>
	 *	□■□	■■□<br>
	 *	■■□	□■■<br>
	 *	■□□	□□□<br>
	 * 3:<br>
	 *	■□□	■■□	■■■	□■□<br>
	 *	■■■	■□□	□□■	□■□<br>
	 *	□□□	■□□	□□□	■■□<br>
	 * 4:<br>
	 *	□□■	■□□	■■■	■■□<br>
	 *	■■■	■□□	■□□	□■□<br>
	 *	□□□	■■□	□□□	□■□<br>
	 * 5:<br>
	 *	□■□	■□□	■■■	□■□<br>
	 *	■■■	■■□	□■□	■■□<br>
	 *	□□□	■□□	□□□	□■□<br>
	 * 6:<br>
	 *	■■□<br>
	 *	■■□<br>
	 *	□□□<br>
	 * 7:<br>
	 *	■□□	□■■<br>
	 *	■■■	□■□<br>
	 *	□□■	■■□<br>
	 * 8:<br>
	 *	□□■	■■□<br>
	 *	■■■	□■□<br>
	 *	■□□	□■■<br>
	 * 9:<br>
	 *	■■■	■■□	■□■	■■□<br>
	 *	■□■	□■□	■■■	■□□<br>
	 *	□□□	■■□	□□□	■■□<br>
	 * 10:<br>
	 *	■■■	□□■	□■□	■□□<br>
	 *	□■□	■■■	□■□	■■■<br>
	 *	□■□	□□■	■■■	■□□<br>
	 * 11:<br>
	 *	□■□<br>
	 *	■■■<br>
	 *	□■□<br>
	 * 12:<br>
	 *	■■□	■■□	□■□	■□□<br>
	 *	■□□	□■□	■■□	■■□<br>
	 *	□□□	□□□	□□□	□□□<br>
	 * 13:<br>
	 *	□□□	□■□<br>
	 *	■■■	□■□<br>
	 *	□□□	□■□<br>
	 * 14:<br>
	 *	■■□	■□□<br>
	 *	□□□	■□□<br>
	 *	□□□	□□□<br>
	 * 15:<br>
	 *	■□□<br>
	 *	□□□<br>
	 *	□□□<br>
	 */
	private static final Shape[] shapes = new Shape[] {
		createLineShape(0, 4),
		createNineCellShape(1, new int[][]{{0,3,4,7}, {1,2,3,4}}),
		createNineCellShape(2, new int[][]{{1,3,4,6}, {0,1,4,5}}),
		createNineCellShape(3, new int[][]{{0,3,4,5}, {0,1,3,6}, {0,1,2,5}, {1,4,6,7}}),
		createNineCellShape(4, new int[][]{{2,3,4,5}, {0,3,6,7}, {0,1,2,3}, {0,1,4,7}}),
		createNineCellShape(5, new int[][]{{1,3,4,5}, {0,3,4,6}, {0,1,2,4}, {1,3,4,7}}),
		createNineCellShape(6, new int[][]{{0,1,3,4}}),
		createNineCellShape(7, new int[][]{{0,3,4,5,8}, {1,2,4,6,7}}),
		createNineCellShape(8, new int[][]{{2,3,4,5,6}, {0,1,4,7,8}}),
		createNineCellShape(9, new int[][]{{0,1,2,3,5}, {0,1,4,7,6}, {0,2,3,4,5}, {0,1,3,6,7}}),
		createNineCellShape(10, new int[][]{{0,1,2,4,7}, {2,3,4,5,8}, {1,4,6,7,8}, {0,3,4,5,6}}),
		createNineCellShape(11, new int[][]{{1,3,4,5,7}}),
		createNineCellShape(12, new int[][]{{0,1,3}, {0,1,4}, {1,3,4}, {0,3,4}}),
		createLineShape(13, 3),
		createLineShape(14, 2),
		createPointShape(15)
		
	};
	
	/**
	 * 根据参数的点在九宫格的位置转化创建形状
	 * @param type int 类型
	 * @param nineCellPoints int[][] 点在九宫格的位置
	 * @return Shape 新创建的形状
	 */
	private static Shape createNineCellShape(int type, int[][] nineCellPoints) {
		if(nineCellPoints == null || nineCellPoints.length == 0) 
			return null;
		int shapeCount = nineCellPoints.length;
		int[][][] points = new int[shapeCount][][];
		for(int i = 0; i < shapeCount; i++) {
			int pointCount = nineCellPoints[i].length;
			points[i] = new int[pointCount + 1][2];
			int width = 0;
			int height = 0;
			// 根据参数的点在九宫格的位置转化为形状的坐标
			for(int j = 0; j < pointCount; j++) {
				points[i][j+1][0] = nineCellPoints[i][j] % 3;
				points[i][j+1][1] = nineCellPoints[i][j] / 3;
				if(width < points[i][j+1][0]) width = points[i][j+1][0];
				if(height < points[i][j+1][1]) height = points[i][j+1][1];
			}
			// 每个形状的第一个点表示该形状的宽度和高度
			points[i][0][0] = width + 1;
			points[i][0][1] = height + 1;
		}
		return new Shape(type, points);
	}
	
	/**
	 * 创建直线形状
	 * @param type int 类型
	 * @param pointCount int 点的个数
	 * @return Shape 新创建的形状
	 */
	private static Shape createLineShape(int type, int pointCount) {
		if(pointCount <= 0) 
			return null;
		else if(pointCount == 1)
			return createPointShape(type);
		
		int[][][] points = new int[2][pointCount + 1][2];
		int offset = 0;
		if(pointCount > 2) offset = (pointCount - 1) / 2;
		// 每个形状的第一个点表示该形状的宽度和高度
		points[0][0][0] = pointCount;
		points[0][0][1] = 1;
		points[1][0][0] = 1 + offset;
		points[1][0][1] = pointCount - offset;
		// 其他点表示坐标
		for(int i = 0; i < pointCount; i++) {
			points[0][i+1][0] = i;
			points[0][i+1][1] = 0;
			points[1][i+1][0] = offset;
			points[1][i+1][1] = i - offset;
		}
		return new Shape(type, points);
	}
	
	/**
	 * 创建点形状
	 * @param type int 类型
	 * @return Shape 新创建的形状
	 */
	private static Shape createPointShape(int type) {
		// 第一个点表示宽度和高度
		// 第二个点表示坐标
		return new Shape(type, new int[][][] {{{1, 1}, {0, 0}}});
	}

	/**
	 * 获取随机形状
	 * @return Shape 新形状实例
	 */
	public static Shape getRandomShape() {
		int type = random.nextInt(shapes.length);
		Shape shape = (Shape)shapes[type].clone();
		shape.rotateRandom();
		return shape;
	}
	
	/**
	 * 根据级别参数获取随机形状
	 * @param complexity int 复杂性程度<br>
	 * 			0: 普通<br>
	 * 			1: 较难<br>
	 * 			2: 最难<br>
	 * @return Shape 新形状实例
	 */
	public static Shape getRandomShape(int complexity) {
		// 根据复杂性程度获取随机形状总个数
		int count = 7;
		switch(complexity) {
		case 0: count = 7; break;
		case 1: count = 16; break;
		case 2: count = 12; break;
		default: count = 7;
		}
		// 获取随机形状
		int type = random.nextInt(count);
		Shape shape = (Shape)shapes[type].clone();
		shape.rotateRandom();
		return shape;
	}
	
	/**
	 * 根据序号获取形状
	 * @param index int 序号
	 * @return Shape 新形状实例
	 */
	public static Shape getShapeByIndex(int index) {
		return (Shape)shapes[index].clone();
	}
}
