package zhh.game.tetris.entity;

import java.util.Random;

/**
 * ��״<br>
 * ��һ�����㹹��<br>
 * ���x,y���������״�ĵ�����ڸ���״���Ͻǵ�λ��<br>
 * @author zhaohuihua
 */
public class Shape {
	
	/**
	 * �����������
	 */
	private final static Random random = new Random();
	
	/**
	 * ����<br>
	 * ��Ҫ���ڻ�ȡ��״����ɫ<br>
	 */
	private final int type;
	
	/**
	 * �ɶ����״���ɵı�ʾ����״����״̬�ĵ�ļ���<br>
	 * ��ʾ��״����ת����ʱ�ĵ�Ĳ�ͬ����<br>
	 * [i]: ��ʾ����״����״̬������һ��<br>
	 * [i][0]: ��״̬����״�Ŀ�Ⱥ͸߶�<br>
	 * [i][1..n]: ��״̬����״�ĵ��x,y����<br>
	 */
	private final int[][][] shapes;
	
	/**
	 * ��״���������˵ľ���
	 */
	private int top;
	
	/**
	 * ��״���������ľ���
	 */
	private int left;
	
	/**
	 * ��״��ǰ��״̬
	 */
	private int status;
	
	/**
	 * ��״<br>
	 * ���ṩ�ⲿʵ������ʽ<br>
	 * ��ȡʵ��: ShapeFactory.getShape()<br>
	 * @param type int ����
	 * @param shapes int[][][] �ɶ����״���ɵı�ʾ����״����״̬�ĵ�ļ���
	 */
	Shape(int type, int[][][] shapes) {
		this.type = type;
		this.shapes = shapes;
	}
	
	/**
	 * ��״��ǰ״̬�µ���ռ���
	 * @return
	 */
	public int getWidth() {
		return shapes[status][0][0];
	}
	
	/**
	 * ��״��ǰ״̬�µ���ռ�߶�
	 * @return
	 */
	public int getHeight() {
		return shapes[status][0][1];
	}
	
	/**
	 * ��״������
	 * @return
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * ��ȡ��״��ǰ״̬�µ����е�ļ���<br>
	 * @return int[][] ��ļ���<br>
	 * [i][0]: �������������x����<br>
	 * [i][1]: �������������y����<br>
	 * [i][2]: �������<br>
	 */	
	public int[][] getPoints() {
		return getPoints(true);
	}
	
	/**
	 * ��ȡ��״��ǰ״̬�µ����е�ļ���<br>
	 * @param absoluteLocation boolean �Ƿ��ȡ����λ�õ�����<br>
	 *         true: ����λ��, �����������������<br>
	 *         false: ���λ��, ���������״���Ͻǵ�����<br>
	 * @return int[][] ��ļ���<br>
	 * [i][0]: ���x����<br>
	 * [i][1]: ���y����<br>
	 * [i][2]: �������<br>
	 */
	public int[][] getPoints(boolean absoluteLocation) {
		int[][] shape = shapes[status];
		int length = shape.length;
		int[][] points = new int[length - 1][3];
		// ��һ�����ʾ��״�Ŀ�Ⱥ͸߶�, ��i��1��ʼ
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
     * �ƶ���ĳһλ��<br>
     * ����״�����Ͻ�����λ<br>
     */
	public void moveTo(int x, int y) {
		this.left = x;
		this.top = y;
	}
	
	/**
     * �����ƶ�
     */
	public void moveUp() {
		top--;
	}

	/**
     * �����ƶ�
     */
	public void moveDown() {
		top++;
	}

	/**
     * �����ƶ�
     */
	public void moveLeft() {
		left--;
	}

	/**
     * �����ƶ�
     */
	public void moveRight() {
		left++;
	}

	/**
     * ��ת<br>
     * @param boolean clockwise �Ƿ�˳ʱ�뷽����ת
     */
	public void rotate(boolean clockwise) {
		if(shapes.length > 1)
			status = (status + 1) % shapes.length;
	}

	/**
     * ��˳ʱ�뷽����ת
     */
	public void rotate() {
		rotate(true);
	}
	
	/**
	 * �����ת
	 */
	public void rotateRandom() {
		status = shapes.length > 1 ? random.nextInt(shapes.length) : 0;
	}
	
	/**
	 * ת��Ϊ���������ʾ<br>
	 * ���ڵ��λ�ñ��Ϊ1, ����Ϊ0<br>
	 * ������к�Ϊ�����ĵ�, ����չ�߶�, ֱ������ĸ߶ȹ��������еĵ�<br>
	 * @return int[][] ����״�ĵ�������
	 */
	public int[][] toMatrix() {
		int[][] result = new int[getHeight()][getWidth()];
		if(shapes != null && shapes.length > 0) {
			int[][] shape = shapes[status];
			int length = shape.length;
			// ��չ������
			int expansion = 0;
			// ��һ�����ʾ��״�Ŀ�Ⱥ͸߶�, ��i��1��ʼ
			for (int i = 1; i < length; i++) {
				// �������Y��Ϊ�����ĵ�, ��չ�߶�
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
	 * ת��Ϊ������ʽ���ַ���ʾ<br>
	 * ���ڵ��λ����ʵ�ķ����ʾ, �����Կ��ķ����ʾ<br>
	 * @return String ������ʽ�ַ���
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
				result.append(matrix[i][j] == 0 ? "��" : "��");
			}
		}
		return result.toString();
	}
	
	/**
	 * ����<br>
	 * ����״�ڵĵ㼯�ϲ��ܸı�, ���踴��<br>
	 * @return Object ���ƺ������״
	 */
	public Object clone() {
		Shape shape = new Shape(type, shapes);
		shape.top = top;
		shape.left = left;
		shape.status = status;
		return shape;
	}
	
	/**
	 * ת��Ϊ�ַ���
	 * @return String �ַ�����ʽ
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


