package zhh.game.tetris.listener;

import zhh.game.tetris.entity.Shape;

/**
 * 游戏预览监听器<br>
 * 监听游戏预览变更事件<br>
 * @author zhaohuihua
 */
public interface PreviewListener {
	
	/**
	 * 创建形状预览了
	 * @param shape Shape 形状预览
	 */
	public void shapePreviewCreated(Shape shape);
	
	/**
	 * 形状预览清除了
	 */
	public void shapePreviewCleared();
}


