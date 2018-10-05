package zhh.game.tetris.entity;

import java.util.Random;

/**
 * ����<br>
 * �ں��ϰ���<br>
 * �ϰ�����һ�����㹹��<br>
 * ���x,y����������ϰ���λ�ڵ��ε�λ��<br>
 * @author zhaohuihua
 */
public class Ground {

	/**
	 * �����������
	 */
	private final static Random random = new Random();
	
	/**
	 * ���
	 */
	private int width;
	
	/**
	 * �߶�
	 */
	private int height;

	/**
	 * �����ϰ����ÿһ������������<br>
	 * ÿ���ϰ������������������<br>
	 * [i][0]: �ϰ�����x����<br>
	 * [i][1]: �ϰ�����y����<br>
	 * [i][2]: �ϰ���������<br>
	 * (������Ҫ���ڻ�ȡ�ϰ�������ɫ, Ĭ������Ϊ-1, ����״��Ϊ�ϰ���ʱ������״�����;���)<br>
	 */
	private int[][] points;

	/**
	 * ����
	 * @param width ���
	 * @param height �߶�
	 */
	public Ground(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * ��ȡ���
	 * @return int ���
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * ��ȡ�߶�
	 * @return int �߶�
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * ��ȡ�ϰ���
	 * @return int[][] �ϰ����ÿһ������������<br>
	 * �ɶ���ϰ���ĵ����<br>
	 * ÿ���ϰ������������������<br>
	 * [i][0]: �ϰ�����x����<br>
	 * [i][1]: �ϰ�����y����<br>
	 * [i][2]: �ϰ���������<br>
	 * (������Ҫ���ڻ�ȡ�ϰ�������ɫ, Ĭ������Ϊ-1, ����״��Ϊ�ϰ���ʱ������״�����;���)<br>
	 */
	public int[][] getPoints() {
		return points;
	}

	/**
	 * ����ϰ���
	 */
	public void clear() {
		this.points = null;
	}
	
	/**
	 * �������<br>
	 * һ�е��ϰ�������������������ж�Ϊ����<br>
	 * δ�����ص�����(��������²��ᷢ���ص�)<br>
	 * @return int[] ���е��к�(�������¼��к�,��1���к�Ϊ0)
	 */
	public int[] checkFullLine() {
		/**
		 * ����һ���ɼ�¼�����е����� fullLine <br>
		 * �����ϰ���, ����ÿ�е��ϰ������<br>
		 * ���ĳһ�еĸ�������������, ���¸��к�, ��ͳ�����ж�������<br>
		 * ��������������е��к�λ������ fullLine ��ͷ����<br>
		 * ȡ���⼸���кŷ���<br>
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
	 * ɾ��<br>
	 * �����к�ɾ��<br>
	 * ��һ��ɾ������<br>
	 * ɾ�к�, ʣ����ϰ����Զ�����<br>
	 * @param line int[] ��ɾ�����к�
	 * @return int ��ɾ���˶��ٸ��ϰ����
	 */
	public int deleteLine(int[] line) {
		/**
		 * �����ϰ���<br>
		 * ���ϰ�����y�������ɾ�����к�һһ�Ƚ�, ���������Ӧ���³�������<br>
		 * ���ƥ��, �����õ㽫��ɾ��<br>
		 * ����, ���õ�ת������ʱ����, ͬʱ�õ㰴Ҫ���³�<br>
		 * ����������в�Ӧɾ�����ϰ����λ����ʱ�����ͷ����<br>
		 * ȡ����Щ����Ϊɾ������ϰ���<br>
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
					// ��¼�ϰ����Ӧ���³�������
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
	 * ���һ����״<br>
	 * δ�����ص�����, Ӧ���ⲿ��֤���ᷢ���ص�<br>
	 * @param shape Shape ��������״
	 */
	public void fill(Shape shape) {
		fill(shape.getPoints());
	}
	
	/**
	 * ���һЩ�ϰ����<br>
	 * δ�����ص�����, Ӧ���ⲿ��֤���ᷢ���ص�<br>
	 * @param points int[][] �������ϰ����<br>
	 * �ϰ������������������<br>
	 * [i][0]: �ϰ�����x����<br>
	 * [i][1]: �ϰ�����y����<br>
	 * [i][2]: �ϰ���������<br>
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
	 * ������һЩ�ϰ����<br>
	 * �ӵײ���ʼ���<br>
	 * �����ϰ���������ΪĬ������-1<br>
	 * @param lineCount int ��������
	 * @param rate double ������<br>
	 * ���ʲ��ܳ��� 0.90, ������������, ��ִ�в���<br>
	 */
	public void randomFill(int lineCount, double rate) {
		randomFill(lineCount, (int)(width * lineCount * rate));
	}
	
	/**
	 * ������һЩ�ϰ����<br>
	 * �ӵײ���ʼ���<br>
	 * �����ϰ���������ΪĬ������-1<br>
	 * @param lineCount int ��������
	 * @param fillCount int �����ٸ��ϰ����<br>
	 * ���ĸ�������С�� lineCount * width - lineCount, ������������, ��ִ�в���<br>
	 */
	public void randomFill(int lineCount, int fillCount) {
		
		this.points = null;
		
		if(lineCount <= 0 || lineCount >= height)
			return;
		
		// ���ĸ�������С�� lineCount * width - lineCount, ������������
		if(fillCount > lineCount * width - lineCount)
			return;

		// ��������ĵ�
		int[][] points = new int[fillCount][3];
		// Ϊ�򻯼��㱣�����������ĵ����λ��
		int[] temp = new int[fillCount];
		// ÿһ�еĵ�ĸ���, �����ж��Ƿ񼴽�����
		int[] linePointCount = new int[lineCount];
		int max = lineCount * width;
		for (int i = 0; i < fillCount; i++) {
			boolean cancel;
			int row;
			do {
				// �������һ��������λ��
				temp[i] = random.nextInt(max);
				// �ж��Ƿ�����
				row = temp[i] / width;
				if(linePointCount[row] >= width - 1) {
					// ��������, �õ�����
					cancel = true;
					continue;
				}
				// �����Ѿ������ĵ�, �ж��Ƿ�����ײ
				cancel = false;
				for (int j = 0; j < i; j++) {
					if (temp[i] == temp[j]) {
						// ������ײ, �õ�����
						cancel = true;
						break;
					}
				}
			} while(cancel);
			// ���ݵ�����λ�ü���ʵ������
			points[i][0] = temp[i] % width;
			points[i][1] = height - lineCount + row;
			points[i][2] = -1;
			// ����ÿһ�еĵ�ĸ���, �����ж��Ƿ񼴽�����
			linePointCount[row]++;
		}
		fill(points);
	}
	
	/**
	 * �ж�һ����״������Ƿ�����ײ<br>
	 * ��״���ڵĵ������ϰ���, ������״�ĵ㳬�����εĿ�Ȼ�߶�, ���ж�Ϊ��ײ<br>
	 * ����״�ĵ���к�Ϊ����, ������ײ<br>
	 * @param shape Shape ���Ƚϵ���״
	 * @return boolean �Ƿ�����ײ
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
	 * �ϰ���ת��Ϊ���������ʾ<br>
	 * �����ϰ���ĵ���Ϊ1, ����Ϊ0<br>
	 * �к�Ϊ�������ϰ��ｫ������<br>
	 * @return int[][] �õ��ε��ϰ���ĵ�������
	 */
	public int[][] toMatrix() {
		/**
		 * ���ȴ���һ������εȸ߿������<br>
		 * Ȼ�����ÿһ���ϰ����, �����x,y���꽫�������Ӧ��λ�ñ��Ϊ1<br>
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
	 * �ϰ���ת��Ϊ������ʽ���ַ���ʾ<br>
	 * �����ϰ���ĵ���ʵ�ķ����ʾ, �����Կ��ķ����ʾ<br>
	 * @return String �ϰ���ĵ�����ʽ�ַ���
	 */
	public String toMatrixString() {
		StringBuffer result = new StringBuffer();
		int[][] matrix = toMatrix();
		for(int i = 0; i < height; i++) {
			if(i > 0)
				result.append("\n");
			for(int j = 0; j < width; j++) {
				result.append(matrix[i][j] == 0 ? "��" : "��");
			}
		}
		return result.toString();
	}
	
	/**
	 * ����
	 * @return Object ���ƺ���µ���
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
	 * ת��Ϊ�ַ���
	 * @return String �ַ�����ʽ
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



