package zhh.game.tetris.listener;

/**
 * �����������
 * @author zhaohuihua
 */
public interface ConfigListener {
	
	/**
	 * �й���ʾ���������Ѿ��ı���
	 */
	public void viewConfigChanged();
	
	/**
	 * �йؿ��Ƽ����������Ѿ��ı���
	 */
	public void hotkeyConfigChanged();
	
	/**
	 * �йؼ�����������Ѿ��ı���
	 */
	public void levelConfigChanged();
}

