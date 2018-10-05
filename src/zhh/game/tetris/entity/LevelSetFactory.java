package zhh.game.tetris.entity;

import zhh.game.tetris.global.Utilities;

/**
 * ���𼯹���
 * @author zhaohuihua
 */
public class LevelSetFactory {
	
	/**
	 * ȫ���ļ���<br>
	 */
	private static LevelSet[] allLevelSet;
	
	private LevelSetFactory() {
	}
	
	// ��ʼ�����м���
	static {
		/**
		 * ����<br>
		 *     [i][0]: ��״�����Գ̶� 0.��ͨ | 1.���� | 2.����<br>
		 *     [i][1]: ��ʼ�����ٶ�<br>
		 *     [i][2]: ��С�����ٶ�<br>
		 *     [i][3]: �����������ٶȵķ������<br>
		 *     [i][4]: �����������ٶȵ��ٶ�����<br>
		 *     [i][5]: ����������<br>
		 *     [i][6]: ������ʱ��������<br>
		 */
		
		addLevelSet(new LevelSet(0, 
				"��׼�ؿ�", 
				"�Ѷ��е�, ����2��, 50000��һ��, �ٶȵ���, " +
				"����һ����ٶ�ÿ2000�ּ���10����", 
				50000, 
				new int[][] {
					{0, 500, 250, 2000, 10, 0, 0},
					{0, 250, 120, 2000, 10, 0, 0}
				}
		));

		addLevelSet(new LevelSet(1, 
				"�ٶȹؿ�", 
				"�ѶȽϴ�, ����10��, 10000��һ��, �ٶȿ������ŷ��������Ӷ�����", 
				10000, 
				new int[][] {
					{0, 300, 250, 2000, 10, 0, 0},
					{0, 250, 225, 2000, 5, 0, 0},
					{0, 225, 200, 2000, 5, 0, 0},
					{0, 200, 0, 0, 0, 0, 0},
					{0, 180, 0, 0, 0, 0, 0},
					{0, 170, 0, 0, 0, 0, 0},
					{0, 160, 0, 0, 0, 0, 0},
					{0, 140, 0, 0, 0, 0, 0},
					{0, 130, 0, 0, 0, 0, 0},
					{0, 120, 0, 0, 0, 0, 0}
				}
		));

		addLevelSet(new LevelSet(2, 
				"�����Թؿ�", 
				"�ѶȽϴ�, ����10��, 8000��һ��, ���ż���Խ�߸��Ӷ�Խ��Խ��, " +
				"�������������ϰ���, �Լ�����Ӧ���ĸ�����״", 
				8000, 
				new int[][] {
					{0, 400, 0, 0, 0, 0, 0},
					{0, 350, 0, 0, 0, 5, 50},
					{0, 300, 0, 0, 0, 8, 50},
					{1, 400, 0, 0, 0, 0, 0},
					{1, 350, 0, 0, 0, 3, 50},
					{1, 300, 0, 0, 0, 5, 50},
					{1, 250, 0, 0, 0, 5, 50},
					{2, 400, 0, 0, 0, 0, 0},
					{2, 350, 0, 0, 0, 0, 0},
					{2, 350, 0, 0, 0, 5, 50}
				}
		));

//		addLevelSet(new LevelSet(3, 
//				"���Թؿ�", 
//				"", 
//				500, 
//				new int[][] {
//					{0, 400, 0, 0, 0, 0, 0},
//					{0, 350, 0, 0, 0, 0, 0},
//					{0, 300, 0, 0, 0, 0, 0},
//					{1, 400, 0, 0, 0, 0, 0},
//					{1, 350, 0, 0, 0, 0, 0},
//					{1, 300, 0, 0, 0, 0, 0},
//					{1, 250, 0, 0, 0, 0, 0},
//					{2, 400, 0, 0, 0, 0, 0},
//					{2, 350, 0, 0, 0, 0, 0},
//					{2, 350, 0, 0, 0, 0, 0}
//				}
//		));
	}
	
	/**
	 * ���Ӽ���
	 * @param levelSet
	 */
	public static void addLevelSet(LevelSet levelSet) {
		if(allLevelSet == null) allLevelSet = new LevelSet[0];
		allLevelSet = (LevelSet[])Utilities.arrayAddItem(allLevelSet, levelSet);
	}
	
	/**
	 * ��ȡ���м���
	 * @return LevelSet[]
	 */
	public static LevelSet[] getAllLevelSet() {
		return allLevelSet;
	}
	
	/**
	 * ���ݱ�Ż�ȡ����
	 * @param levelSet int ���𼯱��(��0��ʼ)
	 * @return LevelSet
	 */
	public static LevelSet getLevelSet(int levelSet) {
		return allLevelSet[levelSet];
	}
	
	/**
	 * ��ȡ���𼯵�������
	 * @return int 
	 */
	public static int getLevelSetCount() {
		return allLevelSet.length;
	}
}


