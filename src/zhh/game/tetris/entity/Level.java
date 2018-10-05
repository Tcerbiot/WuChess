package zhh.game.tetris.entity;

/**
 * ����
 * @author zhaohuihua
 */
public class Level {

	/**
	 * ������
	 */
	private final int id;
	
	/**
	 * ��״�����Գ̶�<br>
	 * 		0: ��ͨ<br>
	 * 		1: ����<br>
	 * 		2: ����<br>
	 */
	private final int complexity;

	/**
	 * ��ʼ�����ٶ�
	 */
	private final int initSpeed;

	/**
	 * ��С�����ٶ�
	 */
	private final int minSpeed;
	
	/**
	 * �����������ٶȵķ������
	 */
	private final int interval;
	
	/**
	 * �����������ٶȵ��ٶ�����
	 */
	private final int increment;
	
	/**
	 * ����������
	 */
	private final int fraiseLine;
	
	/**
	 * ������ʱ��������
	 */
	private final double fraiseFillRate;

	/**
	 * ����
	 * @param id int ������
	 * @param complexity int ��״�����Գ̶�<br>
	 * 			0: ��ͨ<br>
	 * 			1: ����<br>
	 * 			2: ����<br>
	 * @param initSpeed int ��ʼ�����ٶ�
	 * @param minSpeed int ��С�����ٶ�
	 * @param interval int �����������ٶȵķ������
	 * @param increment int �����������ٶȵ��ٶ�����
	 * @param fraiseLine int ����������
	 * @param fraiseFillRate double ������ʱ��������
	 */
	Level(int id, int complexity, int initSpeed, int minSpeed, int interval, int increment, 
			int fraiseLine, double fraiseFillRate) {
		this.id = id;
		this.complexity = complexity;
		this.initSpeed = initSpeed;
		this.minSpeed = minSpeed;
		this.interval = interval;
		this.increment = increment;
		this.fraiseLine = fraiseLine;
		this.fraiseFillRate = fraiseFillRate;
	}

	/**
	 * ������
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * ����������
	 * @return
	 */
	public int getFraiseLine() {
		return fraiseLine;
	}
	
	/**
	 * ������ʱ��������
	 * @return
	 */
	public double getFraiseFillRate() {
		return fraiseFillRate;
	}

	/**
	 * ��״�����Գ̶�<br>
	 * 		0: ��ͨ<br>
	 * 		1: ����<br>
	 * 		2: ����<br>
	 * @return
	 */
	public int getComplexity() {
		return complexity;
	}

	/**
	 * ��ʼ�����ٶ�
	 * @return
	 */
	public int getInitSpeed() {
		return initSpeed;
	}

	/**
	 * ��С�����ٶ�
	 * @return
	 */
	public int getMinSpeed() {
		return minSpeed;
	}
	
	/**
	 * �����������ٶȵ��ٶ�����
	 * @return
	 */
	public int getIncrement() {
		return increment;
	}

	/**
	 * �����������ٶȵķ������
	 * @return
	 */
	public int getInterval() {
		return interval;
	}
	
}


