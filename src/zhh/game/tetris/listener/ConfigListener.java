package zhh.game.tetris.listener;

/**
 * 配置项监听器
 * @author zhaohuihua
 */
public interface ConfigListener {
	
	/**
	 * 有关显示的配置项已经改变了
	 */
	public void viewConfigChanged();
	
	/**
	 * 有关控制键的配置项已经改变了
	 */
	public void hotkeyConfigChanged();
	
	/**
	 * 有关级别的配置项已经改变了
	 */
	public void levelConfigChanged();
}

