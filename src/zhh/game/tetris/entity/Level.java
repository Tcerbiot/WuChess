package zhh.game.tetris.entity;

/**
 * 级别
 * @author zhaohuihua
 */
public class Level {

	/**
	 * 级别编号
	 */
	private final int id;
	
	/**
	 * 形状复杂性程度<br>
	 * 		0: 普通<br>
	 * 		1: 较难<br>
	 * 		2: 最难<br>
	 */
	private final int complexity;

	/**
	 * 初始下落速度
	 */
	private final int initSpeed;

	/**
	 * 最小下落速度
	 */
	private final int minSpeed;
	
	/**
	 * 级别内增加速度的分数间隔
	 */
	private final int interval;
	
	/**
	 * 级别内增加速度的速度增量
	 */
	private final int increment;
	
	/**
	 * 随机填充行数
	 */
	private final int fraiseLine;
	
	/**
	 * 随机填充时的填充比率
	 */
	private final double fraiseFillRate;

	/**
	 * 级别
	 * @param id int 级别编号
	 * @param complexity int 形状复杂性程度<br>
	 * 			0: 普通<br>
	 * 			1: 较难<br>
	 * 			2: 最难<br>
	 * @param initSpeed int 初始下落速度
	 * @param minSpeed int 最小下落速度
	 * @param interval int 级别内增加速度的分数间隔
	 * @param increment int 级别内增加速度的速度增量
	 * @param fraiseLine int 随机填充行数
	 * @param fraiseFillRate double 随机填充时的填充比率
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
	 * 级别编号
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 随机填充行数
	 * @return
	 */
	public int getFraiseLine() {
		return fraiseLine;
	}
	
	/**
	 * 随机填充时的填充比率
	 * @return
	 */
	public double getFraiseFillRate() {
		return fraiseFillRate;
	}

	/**
	 * 形状复杂性程度<br>
	 * 		0: 普通<br>
	 * 		1: 较难<br>
	 * 		2: 最难<br>
	 * @return
	 */
	public int getComplexity() {
		return complexity;
	}

	/**
	 * 初始下落速度
	 * @return
	 */
	public int getInitSpeed() {
		return initSpeed;
	}

	/**
	 * 最小下落速度
	 * @return
	 */
	public int getMinSpeed() {
		return minSpeed;
	}
	
	/**
	 * 级别内增加速度的速度增量
	 * @return
	 */
	public int getIncrement() {
		return increment;
	}

	/**
	 * 级别内增加速度的分数间隔
	 * @return
	 */
	public int getInterval() {
		return interval;
	}
	
}


