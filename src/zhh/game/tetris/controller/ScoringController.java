package zhh.game.tetris.controller;

import zhh.game.tetris.entity.Level;
import zhh.game.tetris.entity.LevelSet;
import zhh.game.tetris.entity.LevelSetFactory;
import zhh.game.tetris.global.Config;
import zhh.game.tetris.global.Utilities;
import zhh.game.tetris.listener.ScoringListener;

/**
 * �Ʒֿ�����<br>
 * ����Ʒ�,����Ĺ���<br>
 * ά����ǰ�������ж��ټ���, �Լ����ݵ÷ֽ��м������������<br>
 * �ڵ÷�,����仯ʱ��ע��ļ����������Ʒ��¼�<br>
 * @author fuyunliang
 */

public class ScoringController {
	/**
	 * �Ʒֹ���
	 */
	public static final int[] SCORING_RULE = {0, 100, 220, 360, 520};
	
	/**
	 * �Ʒּ�����
	 */
	private ScoringListener[] scoringListeners;
	
	/**
	 * ��ǰ����
	 */
	private LevelSet levelSet;
	
	/**
	 * ��ǰ����
	 */
	private int level;
	
	/**
	 * ��ʼ����
	 */
	private int initLevel;
	
	/**
	 * ��ǰ�÷�
	 */
	private int scoring;
	
	/**
	 * ��ǰ�ٶ�
	 */
	private int speed;
	
	/**
	 * �Ʒֿ�����<br>
	 * Ĭ�ϵ�<br>
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
	 * ��ʼ���ڲ�����
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
	 * �Ʒ�
	 * @param line int �÷ֵ�����
	 */
    public void score(int line) {
    	// ���ݵ÷ֵ�������ȡӦ�÷���
    	if(line > SCORING_RULE.length)
    		line = SCORING_RULE.length;
    	// �Ʒ�
    	this.scoring += SCORING_RULE[line];
		// ���㼶��
		int newLevel = scoring / levelSet.getInterval() + initLevel;
    	int length = scoringListeners == null ? 0 : scoringListeners.length;
		for(int i = 0; i < length; i++)
			scoringListeners[i].scoringChanged(scoring, level != newLevel);
		if(level < newLevel) {
			if(newLevel < levelSet.getLevelCount()) {
				// ����仯��
				level = newLevel;
				int newSpeed = getCurrentLevel().getInitSpeed();
				// �ٶȱ仯��
				if(speed != newSpeed) {
					speed = newSpeed;
					for(int i = 0; i < length; i++)
						scoringListeners[i].speedChanged(speed);
				}
				for(int i = 0; i < length; i++) 
					scoringListeners[i].levelChanged(getCurrentLevel());
			} else { // �������һ��
				level = -1;
				for(int i = 0; i < length; i++)
					scoringListeners[i].winning(scoring, speed,
							levelSet.getLevels()[levelSet.getLevelCount() - 1]);
			}
		} else if(getCurrentLevel().getInterval() > 0) { // ͬ�����ڿ��ܱ���
			// �����ٶ�
			Level currentLevel = getCurrentLevel();
			// ��ǰ����ĵ÷�
			int currentLevelScoring = scoring - (level - initLevel)
					* levelSet.getInterval();
			// �µ��ٶ�
			int newSpeed = currentLevel.getInitSpeed() - 
					(currentLevelScoring / currentLevel.getInterval()) * 
					currentLevel.getIncrement();
			if(newSpeed < currentLevel.getMinSpeed())
				newSpeed = currentLevel.getMinSpeed();
			// �ٶȱ仯��
			if(speed != newSpeed) {
				speed = newSpeed;
				for(int i = 0; i < length; i++)
					scoringListeners[i].speedChanged(speed);
			}
		}
    }
    
    /**
     * ��ȡ��ǰ�÷�
     * @return int ��ǰ�÷�
     */
	public int getCurrentScoring() {
		return scoring;
	}
	
	/**
	 * ��ȡ��ǰ�ٶ�
	 * @return
	 */
	public int getCurrentSpeed() {
		return speed;
	}
	
	/**
	 * ��ȡ��ǰ����
	 * @return Level ��ǰ����<br>
	 * ������صļ���Ϊnull, ��ʾ�����ѳ�����ǰ�����е���߼���<br>
	 */
	public Level getCurrentLevel() {
		return level == -1 ? null : levelSet.getLevel(level);
	}

	/**
	 * �ж���Ϸ�Ƿ��Ѿ��������һ��
	 * @return
	 */
	public boolean isWinning() {
		return level == -1 || level >= levelSet.getLevelCount();
	}
	
	/**
	 * ���ӼƷּ�����
	 * @param listener ScoringListener �Ʒּ�����
	 */
	public void addScoringListener(ScoringListener listener) {
		addScoringListener(listener, false);
	}
    
	/**
	 * ���ӼƷּ�����
	 * @param listener ScoringListener �Ʒּ�����
     * @param first boolean �Ƿ���������λ
	 */
    public void addScoringListener(ScoringListener listener, boolean first) {
    	if(scoringListeners == null) scoringListeners = new ScoringListener[]{};
    	scoringListeners = (ScoringListener[])Utilities.arrayAddItem(
    			scoringListeners, listener, first);
    }
    
    /**
     * �Ƴ��Ʒּ�����
     * @param listener ScoringListener �Ʒּ�����
     */
    public void removeScoringListener(ScoringListener listener) {
    	scoringListeners = (ScoringListener[])Utilities.arrayRemoveItem(
    			scoringListeners, listener);
    }
}



