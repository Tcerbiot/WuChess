package zhh.game.tetris.listener;

import zhh.game.tetris.entity.Ground;
import zhh.game.tetris.entity.Shape;

/**
 * ��Ϸ��ʾ������<br>
 * ������Ϸ�ڲ�״̬����¼�<br>
 * @author zhaohuihua
 */
public interface GameViewListener {
	
	/**
	 * ��������״��
	 * @param shape Shape �´�������״
	 */
	public void shapeCreated(final Shape shape);
	
	/**
	 * ��״������ʼ�ƶ���
	 * @param shape Shape �����仯����״
	 */
	public void shapeWillMoved(final Shape shape);
	
	/**
	 * ��״�Ѿ��ƶ���λ��
	 * @param shape Shape �����仯����״
	 */
	public void shapeMoved(final Shape shape);

	/**
	 * ��״�����Ѿ���λ
	 * @param swift boolean �Ƿ�ֱ�䵽��
	 */
	public void shapeDroped(boolean swift);

	/**
	 * ��״�Ѿ�����ϰ�����
	 * @param ground Ground ����
	 * @param shape Shape �����仯����״
	 * @param swift boolean �Ƿ�Ϊֱ�䵽��
	 */
	public void groundFilledShape(final Ground ground, final Shape shape);
	
	/**
	 * �ϰ��Ｔ��ɾ������
	 * @param ground Ground ����
	 * @param line int[] ɾ�����к�
	 */
	public void groundWillDeleteLine(final Ground ground, final int[] line);
	
	/**
	 * �ϰ������Ѿ���ɾ����
	 * @param ground Ground ����
	 */
	public void groundDeletedLine(final Ground ground);
	
	/**
	 * �ϰ��Ｔ���������
	 * @param ground Ground ����
	 */
	public void groundWillClear(final Ground ground);
	
	/**
	 * ����������ϰ���
	 * @param ground Ground ����
	 */
	public void groundFilledRandom(final Ground ground);
	
	/**
	 * �ϰ����Ѿ��������
	 */
	public void groundCleared();
}

