package zhh.game.tetris.listener;

import zhh.game.tetris.entity.Level;

/**
 * 计分监听器<br>
 * 监听得分,级别变化事件
 * @author fuyunliang
 */
public interface ScoringListener {

	/**
	 * 初始化
	 * @param scoring int 当前的得分
	 * @param speed int 当前的速度
	 * @param level Level 当前的级别
	 */
	public void scoringInit(int scoring, int speed, Level level);

	/**
	 * 得分变化了
	 * @param scoring int 当前的得分
	 * @param levelChanged boolean 是否因此导致了级别变化
	 */
	public void scoringChanged(int scoring, boolean levelChanged);
	
	/**
	 * 速度变化了
	 * @param speed int 当前速度
	 */
	public void speedChanged(int speed);

	/**
	 * 级别变化了
	 * @param level Level 当前的级别
	 */
	public void levelChanged(Level level);
	
	/**
	 * 胜利了, 过了最后一关
	 * @param scoring int 最后的得分
	 * @param speed int 最后的速度
	 * @param level Level 最后的级别
	 */
	public void winning(int scoring, int speed, Level level);
	
}
