package zhh.game.tetris.listener;

import zhh.game.tetris.entity.Ground;
import zhh.game.tetris.entity.Shape;

/**
 * 游戏显示监听器<br>
 * 监听游戏内部状态变更事件<br>
 * @author zhaohuihua
 */
public interface GameViewListener {
	
	/**
	 * 创建新形状了
	 * @param shape Shape 新创建的形状
	 */
	public void shapeCreated(final Shape shape);
	
	/**
	 * 形状即将开始移动了
	 * @param shape Shape 发生变化的形状
	 */
	public void shapeWillMoved(final Shape shape);
	
	/**
	 * 形状已经移动到位了
	 * @param shape Shape 发生变化的形状
	 */
	public void shapeMoved(final Shape shape);

	/**
	 * 形状下落已经到位
	 * @param swift boolean 是否直落到底
	 */
	public void shapeDroped(boolean swift);

	/**
	 * 形状已经变成障碍物了
	 * @param ground Ground 地形
	 * @param shape Shape 发生变化的形状
	 * @param swift boolean 是否为直落到底
	 */
	public void groundFilledShape(final Ground ground, final Shape shape);
	
	/**
	 * 障碍物即将删除行了
	 * @param ground Ground 地形
	 * @param line int[] 删除的行号
	 */
	public void groundWillDeleteLine(final Ground ground, final int[] line);
	
	/**
	 * 障碍物行已经被删除了
	 * @param ground Ground 地形
	 */
	public void groundDeletedLine(final Ground ground);
	
	/**
	 * 障碍物即将被清空了
	 * @param ground Ground 地形
	 */
	public void groundWillClear(final Ground ground);
	
	/**
	 * 增加了随机障碍物
	 * @param ground Ground 地形
	 */
	public void groundFilledRandom(final Ground ground);
	
	/**
	 * 障碍物已经被清空了
	 */
	public void groundCleared();
}

