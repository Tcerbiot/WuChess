package zhh.game.tetris.entity;

import java.util.Random;

/**
 * ��״����
 * @author fuyunliang
 */
public class ShapeFactory {
	
	/**
	 * �����������
	 */
	private static Random random = new Random();

	/**
	 * ȫ����״:<br>
	 * 0:<br>
	 *	��������	��������<br>
	 *	��������	��������<br>
	 *	��������	��������<br>
	 *	��������	��������<br>
	 * 1:<br>
	 *	������	������<br>
	 *	������	������<br>
	 *	������	������<br>
	 * 2:<br>
	 *	������	������<br>
	 *	������	������<br>
	 *	������	������<br>
	 * 3:<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 * 4:<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 * 5:<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 * 6:<br>
	 *	������<br>
	 *	������<br>
	 *	������<br>
	 * 7:<br>
	 *	������	������<br>
	 *	������	������<br>
	 *	������	������<br>
	 * 8:<br>
	 *	������	������<br>
	 *	������	������<br>
	 *	������	������<br>
	 * 9:<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 * 10:<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 * 11:<br>
	 *	������<br>
	 *	������<br>
	 *	������<br>
	 * 12:<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 *	������	������	������	������<br>
	 * 13:<br>
	 *	������	������<br>
	 *	������	������<br>
	 *	������	������<br>
	 * 14:<br>
	 *	������	������<br>
	 *	������	������<br>
	 *	������	������<br>
	 * 15:<br>
	 *	������<br>
	 *	������<br>
	 *	������<br>
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
	 * ���ݲ����ĵ��ھŹ����λ��ת��������״
	 * @param type int ����
	 * @param nineCellPoints int[][] ���ھŹ����λ��
	 * @return Shape �´�������״
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
			// ���ݲ����ĵ��ھŹ����λ��ת��Ϊ��״������
			for(int j = 0; j < pointCount; j++) {
				points[i][j+1][0] = nineCellPoints[i][j] % 3;
				points[i][j+1][1] = nineCellPoints[i][j] / 3;
				if(width < points[i][j+1][0]) width = points[i][j+1][0];
				if(height < points[i][j+1][1]) height = points[i][j+1][1];
			}
			// ÿ����״�ĵ�һ�����ʾ����״�Ŀ�Ⱥ͸߶�
			points[i][0][0] = width + 1;
			points[i][0][1] = height + 1;
		}
		return new Shape(type, points);
	}
	
	/**
	 * ����ֱ����״
	 * @param type int ����
	 * @param pointCount int ��ĸ���
	 * @return Shape �´�������״
	 */
	private static Shape createLineShape(int type, int pointCount) {
		if(pointCount <= 0) 
			return null;
		else if(pointCount == 1)
			return createPointShape(type);
		
		int[][][] points = new int[2][pointCount + 1][2];
		int offset = 0;
		if(pointCount > 2) offset = (pointCount - 1) / 2;
		// ÿ����״�ĵ�һ�����ʾ����״�Ŀ�Ⱥ͸߶�
		points[0][0][0] = pointCount;
		points[0][0][1] = 1;
		points[1][0][0] = 1 + offset;
		points[1][0][1] = pointCount - offset;
		// �������ʾ����
		for(int i = 0; i < pointCount; i++) {
			points[0][i+1][0] = i;
			points[0][i+1][1] = 0;
			points[1][i+1][0] = offset;
			points[1][i+1][1] = i - offset;
		}
		return new Shape(type, points);
	}
	
	/**
	 * ��������״
	 * @param type int ����
	 * @return Shape �´�������״
	 */
	private static Shape createPointShape(int type) {
		// ��һ�����ʾ��Ⱥ͸߶�
		// �ڶ������ʾ����
		return new Shape(type, new int[][][] {{{1, 1}, {0, 0}}});
	}

	/**
	 * ��ȡ�����״
	 * @return Shape ����״ʵ��
	 */
	public static Shape getRandomShape() {
		int type = random.nextInt(shapes.length);
		Shape shape = (Shape)shapes[type].clone();
		shape.rotateRandom();
		return shape;
	}
	
	/**
	 * ���ݼ��������ȡ�����״
	 * @param complexity int �����Գ̶�<br>
	 * 			0: ��ͨ<br>
	 * 			1: ����<br>
	 * 			2: ����<br>
	 * @return Shape ����״ʵ��
	 */
	public static Shape getRandomShape(int complexity) {
		// ���ݸ����Գ̶Ȼ�ȡ�����״�ܸ���
		int count = 7;
		switch(complexity) {
		case 0: count = 7; break;
		case 1: count = 16; break;
		case 2: count = 12; break;
		default: count = 7;
		}
		// ��ȡ�����״
		int type = random.nextInt(count);
		Shape shape = (Shape)shapes[type].clone();
		shape.rotateRandom();
		return shape;
	}
	
	/**
	 * ������Ż�ȡ��״
	 * @param index int ���
	 * @return Shape ����״ʵ��
	 */
	public static Shape getShapeByIndex(int index) {
		return (Shape)shapes[index].clone();
	}
}
