package zhh.game.tetris.controller;

import zhh.game.tetris.entity.Level;
import zhh.game.tetris.entity.LevelSet;
import zhh.game.tetris.entity.LevelSetFactory;
import zhh.game.tetris.global.Config;
import zhh.game.tetris.global.Utilities;
import zhh.game.tetris.listener.ScoringListener;

/**
 * 计分控制器<br>
 * 负责计分,级别的管理<br>
 * 维护当前环境共有多少级别, 以及根据得分进行级别的升级调整<br>
 * 在得分,级别变化时向注册的监听器发出计分事件<br>
 * @author fuyunliang
 */

public class ScoringController {
	/**
	 * 计分规则
	 */
	public static final int[] SCORING_RULE = {0, 100, 220, 360, 520};
	
	/**
	 * 计分监听器
	 */
	private ScoringListener[] scoringListeners;
	
	/**
	 * 当前级别集
	 */
	private LevelSet levelSet;
	
	/**
	 * 当前级别
	 */
	private int level;
	
	/**
	 * 初始级别
	 */
	private int initLevel;
	
	/**
	 * 当前得分
	 */
	private int scoring;
	
	/**
	 * 当前速度
	 */
	private int speed;
	
	/**
	 * 计分控制器<br>
	 * 默认的<br>
	 */
	public ScoringController() {
		this.scoring = 0;
		this.level = Config.CURRENT.getInitLevel();
		this.initLevel = Config.CURRENT.getInitLevel();
		this.levelSet = LevelSetFactory.getLevelSet(
				Config.CURRENT.getCurrentLevelSet());
		this.speed = getCurrentLevel().getInitSpeed();
	}
    
	/**
	 * 初始化内部参数
	 */
	public void init() {
		this.scoring = 0;
		this.level = Config.CURRENT.getInitLevel();
		this.initLevel = Config.CURRENT.getInitLevel();
		this.levelSet = LevelSetFactory.getLevelSet(
				Config.CURRENT.getCurrentLevelSet());
		this.speed = getCurrentLevel().getInitSpeed();
    	int length = scoringListeners == null ? 0 : scoringListeners.length;
		for(int i = 0; i < length; i++)
			scoringListeners[i].scoringInit(scoring, speed, getCurrentLevel());
	}
	
	/**
	 * 计分
	 * @param line int 得分的行数
	 */
    public void score(int line) {
    	// 根据得分的行数获取应得分数
    	if(line > SCORING_RULE.length)
    		line = SCORING_RULE.length;
    	// 计分
    	this.scoring += SCORING_RULE[line];
		// 计算级别
		int newLevel = scoring / levelSet.getInterval() + initLevel;
    	int length = scoringListeners == null ? 0 : scoringListeners.length;
		for(int i = 0; i < length; i++)
			scoringListeners[i].scoringChanged(scoring, level != newLevel);
		if(level < newLevel) {
			if(newLevel < levelSet.getLevelCount()) {
				// 级别变化了
				level = newLevel;
				int newSpeed = getCurrentLevel().getInitSpeed();
				// 速度变化了
				if(speed != newSpeed) {
					speed = newSpeed;
					for(int i = 0; i < length; i++)
						scoringListeners[i].speedChanged(speed);
				}
				for(int i = 0; i < length; i++) 
					scoringListeners[i].levelChanged(getCurrentLevel());
			} else { // 过了最后一关
				level = -1;
				for(int i = 0; i < length; i++)
					scoringListeners[i].winning(scoring, speed,
							levelSet.getLevels()[levelSet.getLevelCount() - 1]);
			}
		} else if(getCurrentLevel().getInterval() > 0) { // 同级别内可能变速
			// 计算速度
			Level currentLevel = getCurrentLevel();
			// 当前级别的得分
			int currentLevelScoring = scoring - (level - initLevel)
					* levelSet.getInterval();
			// 新的速度
			int newSpeed = currentLevel.getInitSpeed() - 
					(currentLevelScoring / currentLevel.getInterval()) * 
					currentLevel.getIncrement();
			if(newSpeed < currentLevel.getMinSpeed())
				newSpeed = currentLevel.getMinSpeed();
			// 速度变化了
			if(speed != newSpeed) {
				speed = newSpeed;
				for(int i = 0; i < length; i++)
					scoringListeners[i].speedChanged(speed);
			}
		}
    }
    
    /**
     * 获取当前得分
     * @return int 当前得分
     */
	public int getCurrentScoring() {
		return scoring;
	}
	
	/**
	 * 获取当前速度
	 * @return
	 */
	public int getCurrentSpeed() {
		return speed;
	}
	
	/**
	 * 获取当前级别
	 * @return Level 当前级别<br>
	 * 如果返回的级别为null, 表示级别已超过当前环境中的最高级别<br>
	 */
	public Level getCurrentLevel() {
		return level == -1 ? null : levelSet.getLevel(level);
	}

	/**
	 * 判断游戏是否已经过了最后一关
	 * @return
	 */
	public boolean isWinning() {
		return level == -1 || level >= levelSet.getLevelCount();
	}
	
	/**
	 * 增加计分监听器
	 * @param listener ScoringListener 计分监听器
	 */
	public void addScoringListener(ScoringListener listener) {
		addScoringListener(listener, false);
	}
    
	/**
	 * 增加计分监听器
	 * @param listener ScoringListener 计分监听器
     * @param first boolean 是否增加至首位
	 */
    public void addScoringListener(ScoringListener listener, boolean first) {
    	if(scoringListeners == null) scoringListeners = new ScoringListener[]{};
    	scoringListeners = (ScoringListener[])Utilities.arrayAddItem(
    			scoringListeners, listener, first);
    }
    
    /**
     * 移除计分监听器
     * @param listener ScoringListener 计分监听器
     */
    public void removeScoringListener(ScoringListener listener) {
    	scoringListeners = (ScoringListener[])Utilities.arrayRemoveItem(
    			scoringListeners, listener);
    }
}



