package zhh.game.tetris.listener;

import zhh.game.tetris.entity.Level;

/**
 * �Ʒּ�����<br>
 * �����÷�,����仯�¼�
 * @author fuyunliang
 */
public interface ScoringListener {

	/**
	 * ��ʼ��
	 * @param scoring int ��ǰ�ĵ÷�
	 * @param speed int ��ǰ���ٶ�
	 * @param level Level ��ǰ�ļ���
	 */
	public void scoringInit(int scoring, int speed, Level level);

	/**
	 * �÷ֱ仯��
	 * @param scoring int ��ǰ�ĵ÷�
	 * @param levelChanged boolean �Ƿ���˵����˼���仯
	 */
	public void scoringChanged(int scoring, boolean levelChanged);
	
	/**
	 * �ٶȱ仯��
	 * @param speed int ��ǰ�ٶ�
	 */
	public void speedChanged(int speed);

	/**
	 * ����仯��
	 * @param level Level ��ǰ�ļ���
	 */
	public void levelChanged(Level level);
	
	/**
	 * ʤ����, �������һ��
	 * @param scoring int ���ĵ÷�
	 * @param speed int �����ٶ�
	 * @param level Level ���ļ���
	 */
	public void winning(int scoring, int speed, Level level);
	
}
