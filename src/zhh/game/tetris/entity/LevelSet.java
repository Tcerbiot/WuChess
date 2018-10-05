package zhh.game.tetris.entity;

/**
 * ����
 * @author zhaohuihua
 */
public class LevelSet {
	
	/**
	 * ���(��0��ʼ)
	 */
	private final int id;
	
	/**
	 * ����
	 */
	private final String name;
	
	/**
	 * ����
	 */
	private final String description;
	
	/**
	 * �����ķ������
	 */
	private final int interval;
	
	private final Level[] levels;
	
	/**
	 * ����
	 * @param id int ���(��0��ʼ)
	 * @param name String ����
	 * @param description String ����
	 * @param interval int ����֮��ķ������
	 * @param levels Level[] ����
	 */
	LevelSet(int id, String name, String description, int interval,  Level[] levels) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.interval = interval;
		this.levels = levels;
	}
	
	/**
	 * ����
	 * @param id int ���(��0��ʼ)
	 * @param name String ����
	 * @param description String ����
	 * @param interval int ����֮��ķ������
	 * @param levelsParameter int[][] ��������Ĳ���<br>
	 *     [i][0]: ��״�����Գ̶� 0.��ͨ | 1.���� | 2.����<br>
	 *     [i][1]: ��ʼ�����ٶ�<br>
	 *     [i][2]: ��С�����ٶ�<br>
	 *     [i][3]: �����������ٶȵķ������<br>
	 *     [i][4]: �����������ٶȵ��ٶ�����<br>
	 *     [i][5]: ����������<br>
	 *     [i][6]: ������ʱ��������<br>
	 */
	LevelSet(int id, String name, String description, int interval, int[][] levelsParameter) {
		this(id, name, description, interval, createLevels(levelsParameter));
	}
	
	/**
	 * ���ݲ�����������
	 * @param levelsParameter �������
	 * @return Level[] ���������м���<br>
	 *     [i][0]: ��״�����Գ̶� 0.��ͨ | 1.���� | 2.����<br>
	 *     [i][1]: ��ʼ�����ٶ�<br>
	 *     [i][2]: ��С�����ٶ�<br>
	 *     [i][3]: �����������ٶȵķ������<br>
	 *     [i][4]: �����������ٶȵ��ٶ�����<br>
	 *     [i][5]: ����������<br>
	 *     [i][6]: ������ʱ��������<br>
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
	 * ��ȡ���(��0��ʼ)
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * ��ȡ����
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * �ü��𼯰��������м���
	 * @return
	 */
	public Level[] getLevels() {
		return levels;
	}
	
	/**
	 * ������Ż�ȡ����
	 * @param level
	 * @return
	 */
	public Level getLevel(int level) {
		return levels[level];
	}

	/**
	 * �ü��𼯰����ļ�������
	 * @return
	 */
	public int getLevelCount() {
		return levels.length;
	}
	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * ��ȡ����֮��ķ������
	 * @return
	 */
	public int getInterval() {
		return interval;
	}
}

