package zhh.game.tetris.listener;

/**
 * 游戏监听器<br>
 * 监听游戏状态变更事件<br>
 * @author zhaohuihua
 */
public interface GameListener {

	/**
	 * 游戏开始了
	 */
	public void gameStart();

	/**
	 * 游戏结束了
	 */
	public void gameOver();

	/**
	 * 游戏暂停了
	 */
	public void gamePause();

	/**
	 * 游戏继续了
	 */
	public void gameContinue();

	/**
	 * 游戏即将结束了
	 * @return boolean 是否允许游戏结束<br>
	 * 		true: 允许<br>
	 * 		false: 不允许<br>
	 */
	public boolean gameWillStop();
}




