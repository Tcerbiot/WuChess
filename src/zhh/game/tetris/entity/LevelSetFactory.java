package zhh.game.tetris.entity;

import zhh.game.tetris.global.Utilities;

/**
 * 级别集工厂
 * @author zhaohuihua
 */
public class LevelSetFactory {
	
	/**
	 * 全部的级别集<br>
	 */
	private static LevelSet[] allLevelSet;
	
	private LevelSetFactory() {
	}
	
	// 初始化所有级别卡
	static {
		/**
		 * 参数<br>
		 *     [i][0]: 形状复杂性程度 0.普通 | 1.较难 | 2.最难<br>
		 *     [i][1]: 初始下落速度<br>
		 *     [i][2]: 最小下落速度<br>
		 *     [i][3]: 级别内增加速度的分数间隔<br>
		 *     [i][4]: 级别内增加速度的速度增量<br>
		 *     [i][5]: 随机填充行数<br>
		 *     [i][6]: 随机填充时的填充比率<br>
		 */
		
		addLevelSet(new LevelSet(0, 
				"标准关卡", 
				"难度中等, 共分2级, 50000分一级, 速度递增, " +
				"下落一格的速度每2000分减少10毫秒", 
				50000, 
				new int[][] {
					{0, 500, 250, 2000, 10, 0, 0},
					{0, 250, 120, 2000, 10, 0, 0}
				}
		));

		addLevelSet(new LevelSet(1, 
				"速度关卡", 
				"难度较大, 共分10级, 10000分一级, 速度快且随着分数的增加而递增", 
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
				"复杂性关卡", 
				"难度较大, 共分10级, 8000分一级, 随着级别越高复杂度越来越大, " +
				"会出现随机填充的障碍物, 以及难以应付的复杂形状", 
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
//				"测试关卡", 
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
	 * 增加级别集
	 * @param levelSet
	 */
	public static void addLevelSet(LevelSet levelSet) {
		if(allLevelSet == null) allLevelSet = new LevelSet[0];
		allLevelSet = (LevelSet[])Utilities.arrayAddItem(allLevelSet, levelSet);
	}
	
	/**
	 * 获取所有级别集
	 * @return LevelSet[]
	 */
	public static LevelSet[] getAllLevelSet() {
		return allLevelSet;
	}
	
	/**
	 * 根据编号获取级别集
	 * @param levelSet int 级别集编号(从0开始)
	 * @return LevelSet
	 */
	public static LevelSet getLevelSet(int levelSet) {
		return allLevelSet[levelSet];
	}
	
	/**
	 * 获取级别集的总数量
	 * @return int 
	 */
	public static int getLevelSetCount() {
		return allLevelSet.length;
	}
}


