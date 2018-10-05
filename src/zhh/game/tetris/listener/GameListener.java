package zhh.game.tetris.listener;

/**
 * ��Ϸ������<br>
 * ������Ϸ״̬����¼�<br>
 * @author zhaohuihua
 */
public interface GameListener {

	/**
	 * ��Ϸ��ʼ��
	 */
	public void gameStart();

	/**
	 * ��Ϸ������
	 */
	public void gameOver();

	/**
	 * ��Ϸ��ͣ��
	 */
	public void gamePause();

	/**
	 * ��Ϸ������
	 */
	public void gameContinue();

	/**
	 * ��Ϸ����������
	 * @return boolean �Ƿ�������Ϸ����<br>
	 * 		true: ����<br>
	 * 		false: ������<br>
	 */
	public boolean gameWillStop();
}




