package zhh.game.tetris.listener;

import zhh.game.tetris.entity.Shape;

/**
 * ��ϷԤ��������<br>
 * ������ϷԤ������¼�<br>
 * @author zhaohuihua
 */
public interface PreviewListener {
	
	/**
	 * ������״Ԥ����
	 * @param shape Shape ��״Ԥ��
	 */
	public void shapePreviewCreated(Shape shape);
	
	/**
	 * ��״Ԥ�������
	 */
	public void shapePreviewCleared();
}


