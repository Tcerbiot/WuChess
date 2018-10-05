package zhh.game.tetris.entity;

/**
 * 级别集
 * @author zhaohuihua
 */
public class LevelSet {
	
	/**
	 * 编号(从0开始)
	 */
	private final int id;
	
	/**
	 * 名称
	 */
	private final String name;
	
	/**
	 * 描述
	 */
	private final String description;
	
	/**
	 * 升级的分数间隔
	 */
	private final int interval;
	
	private final Level[] levels;
	
	/**
	 * 级别集
	 * @param id int 编号(从0开始)
	 * @param name String 名称
	 * @param description String 描述
	 * @param interval int 级别之间的分数间隔
	 * @param levels Level[] 级别
	 */
	LevelSet(int id, String name, String description, int interval,  Level[] levels) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.interval = interval;
		this.levels = levels;
	}
	
	/**
	 * 级别集
	 * @param id int 编号(从0开始)
	 * @param name String 名称
	 * @param description String 描述
	 * @param interval int 级别之间的分数间隔
	 * @param levelsParameter int[][] 描述级别的参数<br>
	 *     [i][0]: 形状复杂性程度 0.普通 | 1.较难 | 2.最难<br>
	 *     [i][1]: 初始下落速度<br>
	 *     [i][2]: 最小下落速度<br>
	 *     [i][3]: 级别内增加速度的分数间隔<br>
	 *     [i][4]: 级别内增加速度的速度增量<br>
	 *     [i][5]: 随机填充行数<br>
	 *     [i][6]: 随机填充时的填充比率<br>
	 */
	LevelSet(int id, String name, String description, int interval, int[][] levelsParameter) {
		this(id, name, description, interval, createLevels(levelsParameter));
	}
	
	/**
	 * 根据参数创建级别
	 * @param levelsParameter 级别参数
	 * @return Level[] 创建的所有级别<br>
	 *     [i][0]: 形状复杂性程度 0.普通 | 1.较难 | 2.最难<br>
	 *     [i][1]: 初始下落速度<br>
	 *     [i][2]: 最小下落速度<br>
	 *     [i][3]: 级别内增加速度的分数间隔<br>
	 *     [i][4]: 级别内增加速度的速度增量<br>
	 *     [i][5]: 随机填充行数<br>
	 *     [i][6]: 随机填充时的填充比率<br>
	 */
	private static Level[] createLevels(int[][] levelsParameter) {
		int length = levelsParameter.length;
		Level[] levels = new Level[length];
		for(int i = 0; i < length; i++) {
			levels[i] = new Level(i, levelsParameter[i][0], levelsParameter[i][1],
					levelsParameter[i][2], levelsParameter[i][3], levelsParameter[i][4], 
					levelsParameter[i][5], levelsParameter[i][6] / 100.0D);
		}
		return levels;
	}
	
	/**
	 * 获取编号(从0开始)
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 获取描述
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 该级别集包含的所有级别
	 * @return
	 */
	public Level[] getLevels() {
		return levels;
	}
	
	/**
	 * 根据序号获取级别
	 * @param level
	 * @return
	 */
	public Level getLevel(int level) {
		return levels[level];
	}

	/**
	 * 该级别集包含的级别总数
	 * @return
	 */
	public int getLevelCount() {
		return levels.length;
	}
	
	/**
	 * 获取级别名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取级别之间的分数间隔
	 * @return
	 */
	public int getInterval() {
		return interval;
	}
}

