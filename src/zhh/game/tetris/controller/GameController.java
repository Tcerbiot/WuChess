package zhh.game.tetris.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import zhh.game.tetris.entity.Ground;
import zhh.game.tetris.entity.Level;
import zhh.game.tetris.entity.Shape;
import zhh.game.tetris.entity.ShapeFactory;
import zhh.game.tetris.global.Config;
import zhh.game.tetris.global.Utilities;
import zhh.game.tetris.listener.ConfigListener;
import zhh.game.tetris.listener.GameListener;
import zhh.game.tetris.listener.GameViewListener;
import zhh.game.tetris.listener.PreviewListener;
import zhh.game.tetris.listener.ScoringListener;


/**
 * 游戏控制器<br>
 * 控制游戏的开始,结束,暂停,继续<br>
 * 控制游戏中形状的移动,变形,自动下落,障碍物的满行,计分,级别变更等<br>
 * 在游戏状态变化时向注册的监听器发出游戏事件<br>
 * 在形状,障碍物发生变化时向注册的监听器发出游戏显示事件<br>
 * @author fuyunliang
 */
public class GameController extends KeyAdapter
		implements ConfigListener, ScoringListener {
	
	/**
	 * 游戏监听器
	 */
	private GameListener[] gameListeners;
	
	/**
	 * 游戏显示监听器
	 */
	private GameViewListener[] gameViewListeners;
	
	/**
	 * 预览监听器
	 */
	private PreviewListener[] previewListeners;
	
	/**
	 * 计分管理器
	 */
	
	private ScoringController scorer;
	
	/**
	 * 地形
	 */
	private Ground ground;
	
	/**
	 * 形状
	 */
	private Shape shape;

	/**
	 * 形状创建的时间<br>
	 * 用于屏蔽形状刚创建时的操作事件<br>
	 * 有时用户对一个形状持续执行某一操作, 直至该形状触底变成障碍物, <br>
	 * 而用户可能仍未停止操作, 导致该操作直接作用于下一形状, <br>
	 * 但这一操作对于下一形状可能并不合适<br>
	 * 以形状创建的时间加上一个配置的偏移时间来屏蔽形状刚创建时的部分操作事件<br>
	 * 以减少这种情况<br>
	 */
	private long shapeCreateTime;
	
	/**
	 * 下一次的形状, 用于提供预览
	 */
	private Shape nextShape;
	
	/**
	 * 形状自动下落的控制器
	 */
	private Thread shapeDropDriver;
	
	/**
	 * 游戏是否正在运行
	 */
	private boolean playing;
	
	/**
	 * 游戏是否已暂停
	 */
	private boolean pause;
	
	
	public GameController() {
		// 将监听器初始化为0长度数组, 减少后续事件广播时的空值判断
		gameListeners = new GameListener[0];
		gameViewListeners = new GameViewListener[0];
		previewListeners = new PreviewListener[0];

		// 计分器
		scorer = new ScoringController();
		// 计分器增加监听器
		scorer.addScoringListener(this);

		// 增加声音监听器
		SoundController sound = new SoundController();
		addGameListener(sound); // 游戏状态变化触发的声音
		addGameViewListener(sound); // 游戏显示变化触发的声音(形状下落到位)
		scorer.addScoringListener(sound); // 计分变化触发的声音
	}
	
	/**
	 * 游戏是否是暂停状态
	 * @return
	 */
    public boolean isPause() {
		return pause;
	}

    /**
     * 游戏是否正在运行
     * @return
     */
	public boolean isPlaying() {
		return playing;
	}

	/**
     * 按键处理:<br>
     * 1. 开始/结束<br>
     * 2. 暂停/继续<br>
     * 3. 形状向左移动<br>
     * 4. 形状向右移动<br>
     * 5. 形状变形<br>
     * 6. 形状向下移动<br>
     * 7. 形状一落到底<br>
     * 形状的按键处理首先判断形状移动或变形后是否与地形发生碰撞<br>
     * 如果不发生碰撞则执行移动或变形操作<br>
     * 当形状不能再向下移动时, 将该形状变成障碍物, 然后进行满行,计分等后续处理<br>
     * 形状在移动或变形前和后都将发出游戏显示事件, 以便通知显示组件更新显示<br>
     * 形状向下移动的处理过程可能需要较长时间, 是另启线程运行的<br>
     */
    public void keyPressed(KeyEvent e) {
    	int keyCode = e.getKeyCode();
    	if(keyCode == Config.CURRENT.getStartKey()) {
    		// 开始/结束的按键处理
    		if(playing) {
    			gameStop();
    		} else {
		    	// 耗时操作, 另启线程执行
		    	new Thread() {
					public void run() {
		    			gameCreate();
					}
				}.start();
    		}
    	} else if(keyCode == Config.CURRENT.getPauseKey()) {
    		// 暂停/继续的按键处理
    		if(!playing) return;
    		
    		if(pause)
    			gameContinue();
    		else
    			gamePause();
    	} else { // 针对形状的按键事件
        	// 如果游戏未开始或已暂停, 抛弃之
	    	if(!playing || pause)
	    		return;
	    	// 形状仍未被创建或按键的时间早于创建时间, 抛弃之
	    	if(shape == null|| e.getWhen() <= shapeCreateTime)
	    		return;
	    	if(keyCode == Config.CURRENT.getLeftKey()) {
	    		// 形状向左移动的按键处理
				Shape shape = (Shape)this.shape.clone();
	    		shape.moveLeft();
	    		if(!ground.collisional(shape)) {
	    			for(int i = 0; i < gameViewListeners.length; i++)
	    				gameViewListeners[i].shapeWillMoved(this.shape);
	    			this.shape.moveLeft();
	    			for(int i = 0; i < gameViewListeners.length; i++)
	    				gameViewListeners[i].shapeMoved(this.shape);
	    		}
	    	} else if(keyCode == Config.CURRENT.getRightKey()) {
	    		// 形状向右移动的按键处理
				Shape shape = (Shape)this.shape.clone();
	    		shape.moveRight();
	    		if(!ground.collisional(shape)) {
	    			for(int i = 0; i < gameViewListeners.length; i++)
	    				gameViewListeners[i].shapeWillMoved(this.shape);
	    			this.shape.moveRight();
	    			for(int i = 0; i < gameViewListeners.length; i++)
	    				gameViewListeners[i].shapeMoved(this.shape);
	    		}
	    	} else if(keyCode == Config.CURRENT.getRotateKey()) {
	    		// 形状变形的按键处理
				Shape shape = (Shape)this.shape.clone();
	    		shape.rotate();
	    		if(!ground.collisional(shape)) {
	    			for(int i = 0; i < gameViewListeners.length; i++)
	    				gameViewListeners[i].shapeWillMoved(this.shape);
	    			this.shape.rotate();
	    			for(int i = 0; i < gameViewListeners.length; i++)
	    				gameViewListeners[i].shapeMoved(this.shape);
	    		}
	    	} else if(keyCode == Config.CURRENT.getDownKey()) {
	    		// 形状向下移动的按键处理
	    		// 该处理过程可能需要较长时间, 故另启线程运行
	    		new Thread() {
	    			public void run() {
	    	    		shapeDrop(false);
	    			}
	    		}.start();
	    	} else if(keyCode == Config.CURRENT.getSwiftKey()) {
	    		// 形状一落到底的按键处理
	    		// 关掉目前的形状自动下落驱动器
	    		// 另外开启一个无间歇的形状自动下落驱动器
	    		((ShapeDropDriver)this.shapeDropDriver).kill();
				shapeDropDriver = new ShapeDropDriver(true);
				shapeDropDriver.start();
	    	}
    	}
    }
    
    /**
     * 形状向下移动的处理<br>
     * 首先判断形状向下移动后是否与地形发生碰撞, 如果不发生碰撞则执行移动操作<br>
     * 当形状不能再向下移动时, 将该形状变成障碍物<br>
     * 并进行后续处理:<br>
     * 1. 判断是否存在满行<br>
     * 2. 删除满行<br>
     *    删除满行前发出游戏显示事件, 以便通知显示组件显示一些效果<br>
     * 3. 计分<br>
     *    计分有可能导致级别变化<br>
     * 4. 创建新形状<br>
     * @param swift 是否直落到底
     */
    private synchronized void shapeDrop(boolean swift) {
    	// 判断形状向下移动后是否与地形发生碰撞
    	// 复制形状, 令该形状下移, 判断下移后是否与地形发生碰撞
		Shape cloneShape = (Shape)shape.clone();
		cloneShape.moveDown();
		if(!ground.collisional(cloneShape)) {
			// 未与地形发生碰撞, 向下移动
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].shapeWillMoved(shape);
			shape.moveDown();
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].shapeMoved(shape);
		} else {
			// 形状变成障碍物
			ground.fill(shape);

			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].shapeDroped(swift);
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].groundFilledShape(ground, shape);
			
			// 销毁当前的形状
			shape = null;
			((ShapeDropDriver)shapeDropDriver).kill();
			shapeDropDriver = null;
			
			// 检查满行
			int[] fullLine = ground.checkFullLine();
			if(fullLine.length > 0) {
				for(int i = 0; i < gameViewListeners.length; i++)
					gameViewListeners[i].groundWillDeleteLine(ground, fullLine);
				// 删除满行
				ground.deleteLine(fullLine);
				for(int i = 0; i < gameViewListeners.length; i++)
					gameViewListeners[i].groundDeletedLine(ground);
				// 计分
				scorer.score(fullLine.length);
			}
			// 创建新形状
			shapeCreate();
		}
    }
    
    /**
     * 创建新形状<br>
     * 将预览形状置为当前形状, 再创建一个新的预览形状<br>
     * 没有位置创建新形状的时候, 判定为游戏结束<br>
     */
    private void shapeCreate() {
    	if(!playing)
    		return;
    	
    	// 初始位置
    	int x = (ground.getWidth() - nextShape.getWidth()) / 2;
    	int y = 1 - nextShape.getHeight();
    	nextShape.moveTo(x, y);
    	
    	// 没有位置创建新形状了, 判定为游戏结束
    	if(ground.collisional(nextShape)) {
			playing = false;
			shape = null;
			for(int i = 0; i < gameListeners.length; i++)
				gameListeners[i].gameOver();
    	} else {
    		// 将预览形状置为当前形状
			shape = nextShape;
			shapeCreateTime = System.currentTimeMillis();
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].shapeCreated(shape);
			shapeDropDriver = new ShapeDropDriver();
			shapeDropDriver.start();
			nextShape = null;
			for(int i = 0; i < previewListeners.length; i++)
				previewListeners[i].shapePreviewCleared();
			
			// 创建一个新的预览形状
			int complexity = scorer.getCurrentLevel().getComplexity();
			nextShape = ShapeFactory.getRandomShape(complexity);
			for(int i = 0; i < previewListeners.length; i++)
				previewListeners[i].shapePreviewCreated(nextShape);
    	}
    }
    
    /**
     * 创建新游戏
     */
    public void gameCreate() {
		synchronized(this) {
	    	if(playing) return;
			playing = true;
		}
		if(pause) {
			pause = false;
			for(int i = 0; i < gameListeners.length; i++)
				gameListeners[i].gameContinue();
		}
		
		for(int i = 0; i < gameListeners.length; i++)
			gameListeners[i].gameStart(); 
		
    	// 初始化游戏环境
		if(ground == null) {
			int width = Config.CURRENT.getGroundWidth();
			int height = Config.CURRENT.getGroundHeight();
			ground = new Ground(width, height);
		} else {
			ground.clear();
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].groundCleared();
			for(int i = 0; i < previewListeners.length; i++)
				previewListeners[i].shapePreviewCleared();
		}
		
		// 初始化计分器
		scorer.init();
		if(playing) {
			// 创建预览形状
			int complexity = scorer.getCurrentLevel().getComplexity();
			nextShape = ShapeFactory.getRandomShape(complexity);
			for(int i = 0; i < previewListeners.length; i++)
				previewListeners[i].shapePreviewCreated(nextShape);
			// 创建新形状
			shapeCreate();
		}
    }

	/**
	 * 停止当前游戏
	 */
	public void gameStop() {
    	if(!playing) return;

		// 停止游戏确认
		boolean confirm = true;
		for(int i = 0; i < gameListeners.length; i++) {
			if(!gameListeners[i].gameWillStop()) {
				confirm = false;
			}
		}
		// 可以停止游戏
		if(confirm) {
			playing = false;
			shape = null;
			if(this.shapeDropDriver != null) {
				((ShapeDropDriver)this.shapeDropDriver).kill();
				this.shapeDropDriver = null;
			}
			for(int i = 0; i < gameListeners.length; i++)
				gameListeners[i].gameOver();
		}
	}

	/**
	 * 暂停游戏
	 */
	public void gamePause() {
		if(!playing || pause) return;
		
		pause = true;
		for(int i = 0; i < gameListeners.length; i++)
			gameListeners[i].gamePause();
	}

	/**
	 * 继续游戏
	 */
	public void gameContinue() {
		if(!playing || !pause) return;
		
		pause = false;
		for(int i = 0; i < gameListeners.length; i++)
			gameListeners[i].gameContinue();
	}

	/**
	 * 有关级别的配置项改变时的处理
	 */
	public void levelConfigChanged() {
		if(!playing) return;
		
		// 停止当前游戏
		playing = false;
		shape = null;
		if(this.shapeDropDriver != null) {
			((ShapeDropDriver)this.shapeDropDriver).kill();
			this.shapeDropDriver = null;
		}
		
		for(int i = 0; i < gameListeners.length; i++)
			gameListeners[i].gameOver();
		
		new Thread() {
			public void run() {
				gameCreate();
			}
		}.start();
	}

	public void hotkeyConfigChanged() {}
	public void viewConfigChanged() {}

	/**
	 * 级别改变时的处理<br>
	 * 1. 将预览形状清空<br>
	 * 2. 将障碍物清空<br>
	 *    清空前发出游戏显示事件, 以便通知显示组件显示一些效果<br>
	 * 3. 根据新的级别要求创建一个新的预览形状<br>
	 * 4. 如果新的级别要求填充一些随机障碍物, 填充之<br>
	 *    填充后发出游戏显示事件, 以便通知显示组件显示一些效果<br>
	 */
	public void levelChanged(Level level) {
		
		// 1. 将预览形状清空<br>
		nextShape = null;
		for(int i = 0; i < previewListeners.length; i++)
			previewListeners[i].shapePreviewCleared();
		
		// 2. 将障碍物清空
		//    清空前发出游戏显示事件, 以便通知显示组件显示一些效果
		for(int i = 0; i < gameViewListeners.length; i++)
			gameViewListeners[i].groundWillClear(ground);
		if(!playing) return;
		ground.clear();
		for(int i = 0; i < gameViewListeners.length; i++)
			gameViewListeners[i].groundCleared();
		
		// 3. 根据新的级别要求创建一个新的预览形状
		nextShape = ShapeFactory.getRandomShape(
				scorer.getCurrentLevel().getComplexity());
		for(int i = 0; i < previewListeners.length; i++)
			previewListeners[i].shapePreviewCreated(nextShape);

		// 4. 如果新的级别要求填充一些随机障碍物, 填充之
		if(level.getFraiseLine() > 0) {
			ground.randomFill(scorer.getCurrentLevel().getFraiseLine(), 
					scorer.getCurrentLevel().getFraiseFillRate());
			// 填充后发出游戏显示事件, 以便通知显示组件显示一些效果
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].groundFilledRandom(ground);
		}
		
	}
	
	/**
	 * 计分初始化时的处理<br>
	 * 如果初始级别要求填充一些随机障碍物, 填充之<br>
	 */
	public void scoringInit(int scoring, int speed, Level level) {
		// 如果新的级别要求填充一些随机障碍物, 填充之
		if(level.getFraiseLine() > 0) {
			ground.randomFill(scorer.getCurrentLevel().getFraiseLine(), 
					scorer.getCurrentLevel().getFraiseFillRate());
			// 填充后发出游戏显示事件, 以便通知显示组件显示一些效果
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].groundFilledRandom(ground);
		}
	}
	public void shapeDroped(boolean swift) {}
	public void scoringChanged(int scoring, boolean levelChanged) {}
	public void speedChanged(int speed) {}

	/**
	 * 超过最高级别, 游戏结束
	 */
	public void winning(int scoring, int speed, Level level) {
		playing = false;
		shape = null;
		for(int i = 0; i < gameListeners.length; i++)
			gameListeners[i].gameOver();
	}
	
	/**
	 * 增加游戏监听器
	 * @param listener GameListener 游戏监听器
	 */
    public void addGameListener(GameListener listener) {
    	gameListeners = (GameListener[])Utilities.arrayAddItem(
    			gameListeners, listener);
    }
    
    /**
     * 移除游戏监听器
     * @param listener GameListener 游戏监听器
     */
    public void removeGameListener(GameListener listener) {
    	gameListeners = (GameListener[])Utilities.arrayRemoveItem(
    			gameListeners, listener);
    }

	/**
	 * 增加游戏显示监听器
	 * @param listener GameViewListener 游戏显示监听器
	 */
    public void addGameViewListener(GameViewListener listener) {
    	gameViewListeners = (GameViewListener[])Utilities.arrayAddItem(
    			gameViewListeners, listener);
    }
    
    /**
     * 移除游戏显示监听器
     * @param listener GameViewListener 游戏显示监听器
     */
    public void removeGameViewListener(GameViewListener listener) {
    	gameViewListeners = (GameViewListener[])Utilities.arrayRemoveItem(
    			gameViewListeners, listener);
    }
    
    /**
     * 增加预览监听器
     * @param listener PreviewListener 预览监听器
     */
    public void addPreviewListener(PreviewListener listener) {
    	previewListeners = (PreviewListener[])Utilities.arrayAddItem(
    			previewListeners, listener);
    }
    
    /**
     * 移除预览监听器
     * @param listener PreviewListener 预览监听器
     */
    public void removePreviewListener(PreviewListener listener) {
    	previewListeners = (PreviewListener[])Utilities.arrayRemoveItem(
    			previewListeners, listener);
    }
    
    /**
     * 增加计分监听器
     * @param listener ScoringListener 计分监听器
     */
    public void addScoringListener(ScoringListener listener) {
    	scorer.addScoringListener(listener);
    }
    
    /**
     * 增加计分监听器
     * @param listener ScoringListener 计分监听器
     * @param first boolean 是否增加至首位
     */
    public void addScoringListener(ScoringListener listener, boolean first) {
    	scorer.addScoringListener(listener, first);
    }

    /**
     * 移除计分监听器
     * @param listener ScoringListener 计分监听器
     */
    public void removeScoringListener(ScoringListener listener) {
    	scorer.removeScoringListener(listener);
    }
    
    /**
     * 形状自动下落驱动器
     * @author fuyunliang
     */
	private class ShapeDropDriver extends Thread {
		
		/**
		 * 该驱动器是否运行的标志<br>
		 * 如为false, 则结束运行<br>
		 */
		private boolean run;
		
		/**
		 * 是否直落到底的标志<br>
		 * 如为false, 每次下落前间歇一定周期<br>
		 * 如为true, 无间歇直落到底<br>
		 */
		private boolean swift;
		
		/**
		 * 形状自动下落驱动器
		 */
		public ShapeDropDriver() {
			this.run = true;
		}
		
		/**
		 * 形状自动下落驱动器
		 * @param swift boolean 是否直落到底
		 */
		public ShapeDropDriver(boolean swift) {
			this.run = true;
			this.swift = swift;
			this.setDaemon(true);
		}
		
		/**
		 * 销毁驱动器
		 */
		public void kill() {
			run = false;
		}
		
		/**
		 * 休眠一定周期后形状自动向下移动一格
		 */
		public void run() {
			while(playing && run) {
				try {
					// 如果不是直落到底, 休眠一个周期
					if(!swift)
						Thread.sleep(scorer.getCurrentSpeed());
				} catch (InterruptedException e) {
				}
				if(playing && !pause && shape != null) {
					// 向下移动
					shapeDrop(swift);
				}
			}
		}
	}

}	
	
