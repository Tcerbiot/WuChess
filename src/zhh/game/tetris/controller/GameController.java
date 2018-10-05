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
 * ��Ϸ������<br>
 * ������Ϸ�Ŀ�ʼ,����,��ͣ,����<br>
 * ������Ϸ����״���ƶ�,����,�Զ�����,�ϰ��������,�Ʒ�,��������<br>
 * ����Ϸ״̬�仯ʱ��ע��ļ�����������Ϸ�¼�<br>
 * ����״,�ϰ��﷢���仯ʱ��ע��ļ�����������Ϸ��ʾ�¼�<br>
 * @author fuyunliang
 */
public class GameController extends KeyAdapter
		implements ConfigListener, ScoringListener {
	
	/**
	 * ��Ϸ������
	 */
	private GameListener[] gameListeners;
	
	/**
	 * ��Ϸ��ʾ������
	 */
	private GameViewListener[] gameViewListeners;
	
	/**
	 * Ԥ��������
	 */
	private PreviewListener[] previewListeners;
	
	/**
	 * �Ʒֹ�����
	 */
	
	private ScoringController scorer;
	
	/**
	 * ����
	 */
	private Ground ground;
	
	/**
	 * ��״
	 */
	private Shape shape;

	/**
	 * ��״������ʱ��<br>
	 * ����������״�մ���ʱ�Ĳ����¼�<br>
	 * ��ʱ�û���һ����״����ִ��ĳһ����, ֱ������״���ױ���ϰ���, <br>
	 * ���û�������δֹͣ����, ���¸ò���ֱ����������һ��״, <br>
	 * ����һ����������һ��״���ܲ�������<br>
	 * ����״������ʱ�����һ�����õ�ƫ��ʱ����������״�մ���ʱ�Ĳ��ֲ����¼�<br>
	 * �Լ����������<br>
	 */
	private long shapeCreateTime;
	
	/**
	 * ��һ�ε���״, �����ṩԤ��
	 */
	private Shape nextShape;
	
	/**
	 * ��״�Զ�����Ŀ�����
	 */
	private Thread shapeDropDriver;
	
	/**
	 * ��Ϸ�Ƿ���������
	 */
	private boolean playing;
	
	/**
	 * ��Ϸ�Ƿ�����ͣ
	 */
	private boolean pause;
	
	
	public GameController() {
		// ����������ʼ��Ϊ0��������, ���ٺ����¼��㲥ʱ�Ŀ�ֵ�ж�
		gameListeners = new GameListener[0];
		gameViewListeners = new GameViewListener[0];
		previewListeners = new PreviewListener[0];

		// �Ʒ���
		scorer = new ScoringController();
		// �Ʒ������Ӽ�����
		scorer.addScoringListener(this);

		// ��������������
		SoundController sound = new SoundController();
		addGameListener(sound); // ��Ϸ״̬�仯����������
		addGameViewListener(sound); // ��Ϸ��ʾ�仯����������(��״���䵽λ)
		scorer.addScoringListener(sound); // �Ʒֱ仯����������
	}
	
	/**
	 * ��Ϸ�Ƿ�����ͣ״̬
	 * @return
	 */
    public boolean isPause() {
		return pause;
	}

    /**
     * ��Ϸ�Ƿ���������
     * @return
     */
	public boolean isPlaying() {
		return playing;
	}

	/**
     * ��������:<br>
     * 1. ��ʼ/����<br>
     * 2. ��ͣ/����<br>
     * 3. ��״�����ƶ�<br>
     * 4. ��״�����ƶ�<br>
     * 5. ��״����<br>
     * 6. ��״�����ƶ�<br>
     * 7. ��״һ�䵽��<br>
     * ��״�İ������������ж���״�ƶ�����κ��Ƿ�����η�����ײ<br>
     * �����������ײ��ִ���ƶ�����β���<br>
     * ����״�����������ƶ�ʱ, ������״����ϰ���, Ȼ���������,�ƷֵȺ�������<br>
     * ��״���ƶ������ǰ�ͺ󶼽�������Ϸ��ʾ�¼�, �Ա�֪ͨ��ʾ���������ʾ<br>
     * ��״�����ƶ��Ĵ�����̿�����Ҫ�ϳ�ʱ��, �������߳����е�<br>
     */
    public void keyPressed(KeyEvent e) {
    	int keyCode = e.getKeyCode();
    	if(keyCode == Config.CURRENT.getStartKey()) {
    		// ��ʼ/�����İ�������
    		if(playing) {
    			gameStop();
    		} else {
		    	// ��ʱ����, �����߳�ִ��
		    	new Thread() {
					public void run() {
		    			gameCreate();
					}
				}.start();
    		}
    	} else if(keyCode == Config.CURRENT.getPauseKey()) {
    		// ��ͣ/�����İ�������
    		if(!playing) return;
    		
    		if(pause)
    			gameContinue();
    		else
    			gamePause();
    	} else { // �����״�İ����¼�
        	// �����Ϸδ��ʼ������ͣ, ����֮
	    	if(!playing || pause)
	    		return;
	    	// ��״��δ�������򰴼���ʱ�����ڴ���ʱ��, ����֮
	    	if(shape == null|| e.getWhen() <= shapeCreateTime)
	    		return;
	    	if(keyCode == Config.CURRENT.getLeftKey()) {
	    		// ��״�����ƶ��İ�������
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
	    		// ��״�����ƶ��İ�������
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
	    		// ��״���εİ�������
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
	    		// ��״�����ƶ��İ�������
	    		// �ô�����̿�����Ҫ�ϳ�ʱ��, �������߳�����
	    		new Thread() {
	    			public void run() {
	    	    		shapeDrop(false);
	    			}
	    		}.start();
	    	} else if(keyCode == Config.CURRENT.getSwiftKey()) {
	    		// ��״һ�䵽�׵İ�������
	    		// �ص�Ŀǰ����״�Զ�����������
	    		// ���⿪��һ���޼�Ъ����״�Զ�����������
	    		((ShapeDropDriver)this.shapeDropDriver).kill();
				shapeDropDriver = new ShapeDropDriver(true);
				shapeDropDriver.start();
	    	}
    	}
    }
    
    /**
     * ��״�����ƶ��Ĵ���<br>
     * �����ж���״�����ƶ����Ƿ�����η�����ײ, �����������ײ��ִ���ƶ�����<br>
     * ����״�����������ƶ�ʱ, ������״����ϰ���<br>
     * �����к�������:<br>
     * 1. �ж��Ƿ��������<br>
     * 2. ɾ������<br>
     *    ɾ������ǰ������Ϸ��ʾ�¼�, �Ա�֪ͨ��ʾ�����ʾһЩЧ��<br>
     * 3. �Ʒ�<br>
     *    �Ʒ��п��ܵ��¼���仯<br>
     * 4. ��������״<br>
     * @param swift �Ƿ�ֱ�䵽��
     */
    private synchronized void shapeDrop(boolean swift) {
    	// �ж���״�����ƶ����Ƿ�����η�����ײ
    	// ������״, �����״����, �ж����ƺ��Ƿ�����η�����ײ
		Shape cloneShape = (Shape)shape.clone();
		cloneShape.moveDown();
		if(!ground.collisional(cloneShape)) {
			// δ����η�����ײ, �����ƶ�
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].shapeWillMoved(shape);
			shape.moveDown();
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].shapeMoved(shape);
		} else {
			// ��״����ϰ���
			ground.fill(shape);

			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].shapeDroped(swift);
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].groundFilledShape(ground, shape);
			
			// ���ٵ�ǰ����״
			shape = null;
			((ShapeDropDriver)shapeDropDriver).kill();
			shapeDropDriver = null;
			
			// �������
			int[] fullLine = ground.checkFullLine();
			if(fullLine.length > 0) {
				for(int i = 0; i < gameViewListeners.length; i++)
					gameViewListeners[i].groundWillDeleteLine(ground, fullLine);
				// ɾ������
				ground.deleteLine(fullLine);
				for(int i = 0; i < gameViewListeners.length; i++)
					gameViewListeners[i].groundDeletedLine(ground);
				// �Ʒ�
				scorer.score(fullLine.length);
			}
			// ��������״
			shapeCreate();
		}
    }
    
    /**
     * ��������״<br>
     * ��Ԥ����״��Ϊ��ǰ��״, �ٴ���һ���µ�Ԥ����״<br>
     * û��λ�ô�������״��ʱ��, �ж�Ϊ��Ϸ����<br>
     */
    private void shapeCreate() {
    	if(!playing)
    		return;
    	
    	// ��ʼλ��
    	int x = (ground.getWidth() - nextShape.getWidth()) / 2;
    	int y = 1 - nextShape.getHeight();
    	nextShape.moveTo(x, y);
    	
    	// û��λ�ô�������״��, �ж�Ϊ��Ϸ����
    	if(ground.collisional(nextShape)) {
			playing = false;
			shape = null;
			for(int i = 0; i < gameListeners.length; i++)
				gameListeners[i].gameOver();
    	} else {
    		// ��Ԥ����״��Ϊ��ǰ��״
			shape = nextShape;
			shapeCreateTime = System.currentTimeMillis();
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].shapeCreated(shape);
			shapeDropDriver = new ShapeDropDriver();
			shapeDropDriver.start();
			nextShape = null;
			for(int i = 0; i < previewListeners.length; i++)
				previewListeners[i].shapePreviewCleared();
			
			// ����һ���µ�Ԥ����״
			int complexity = scorer.getCurrentLevel().getComplexity();
			nextShape = ShapeFactory.getRandomShape(complexity);
			for(int i = 0; i < previewListeners.length; i++)
				previewListeners[i].shapePreviewCreated(nextShape);
    	}
    }
    
    /**
     * ��������Ϸ
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
		
    	// ��ʼ����Ϸ����
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
		
		// ��ʼ���Ʒ���
		scorer.init();
		if(playing) {
			// ����Ԥ����״
			int complexity = scorer.getCurrentLevel().getComplexity();
			nextShape = ShapeFactory.getRandomShape(complexity);
			for(int i = 0; i < previewListeners.length; i++)
				previewListeners[i].shapePreviewCreated(nextShape);
			// ��������״
			shapeCreate();
		}
    }

	/**
	 * ֹͣ��ǰ��Ϸ
	 */
	public void gameStop() {
    	if(!playing) return;

		// ֹͣ��Ϸȷ��
		boolean confirm = true;
		for(int i = 0; i < gameListeners.length; i++) {
			if(!gameListeners[i].gameWillStop()) {
				confirm = false;
			}
		}
		// ����ֹͣ��Ϸ
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
	 * ��ͣ��Ϸ
	 */
	public void gamePause() {
		if(!playing || pause) return;
		
		pause = true;
		for(int i = 0; i < gameListeners.length; i++)
			gameListeners[i].gamePause();
	}

	/**
	 * ������Ϸ
	 */
	public void gameContinue() {
		if(!playing || !pause) return;
		
		pause = false;
		for(int i = 0; i < gameListeners.length; i++)
			gameListeners[i].gameContinue();
	}

	/**
	 * �йؼ����������ı�ʱ�Ĵ���
	 */
	public void levelConfigChanged() {
		if(!playing) return;
		
		// ֹͣ��ǰ��Ϸ
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
	 * ����ı�ʱ�Ĵ���<br>
	 * 1. ��Ԥ����״���<br>
	 * 2. ���ϰ������<br>
	 *    ���ǰ������Ϸ��ʾ�¼�, �Ա�֪ͨ��ʾ�����ʾһЩЧ��<br>
	 * 3. �����µļ���Ҫ�󴴽�һ���µ�Ԥ����״<br>
	 * 4. ����µļ���Ҫ�����һЩ����ϰ���, ���֮<br>
	 *    ���󷢳���Ϸ��ʾ�¼�, �Ա�֪ͨ��ʾ�����ʾһЩЧ��<br>
	 */
	public void levelChanged(Level level) {
		
		// 1. ��Ԥ����״���<br>
		nextShape = null;
		for(int i = 0; i < previewListeners.length; i++)
			previewListeners[i].shapePreviewCleared();
		
		// 2. ���ϰ������
		//    ���ǰ������Ϸ��ʾ�¼�, �Ա�֪ͨ��ʾ�����ʾһЩЧ��
		for(int i = 0; i < gameViewListeners.length; i++)
			gameViewListeners[i].groundWillClear(ground);
		if(!playing) return;
		ground.clear();
		for(int i = 0; i < gameViewListeners.length; i++)
			gameViewListeners[i].groundCleared();
		
		// 3. �����µļ���Ҫ�󴴽�һ���µ�Ԥ����״
		nextShape = ShapeFactory.getRandomShape(
				scorer.getCurrentLevel().getComplexity());
		for(int i = 0; i < previewListeners.length; i++)
			previewListeners[i].shapePreviewCreated(nextShape);

		// 4. ����µļ���Ҫ�����һЩ����ϰ���, ���֮
		if(level.getFraiseLine() > 0) {
			ground.randomFill(scorer.getCurrentLevel().getFraiseLine(), 
					scorer.getCurrentLevel().getFraiseFillRate());
			// ���󷢳���Ϸ��ʾ�¼�, �Ա�֪ͨ��ʾ�����ʾһЩЧ��
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].groundFilledRandom(ground);
		}
		
	}
	
	/**
	 * �Ʒֳ�ʼ��ʱ�Ĵ���<br>
	 * �����ʼ����Ҫ�����һЩ����ϰ���, ���֮<br>
	 */
	public void scoringInit(int scoring, int speed, Level level) {
		// ����µļ���Ҫ�����һЩ����ϰ���, ���֮
		if(level.getFraiseLine() > 0) {
			ground.randomFill(scorer.getCurrentLevel().getFraiseLine(), 
					scorer.getCurrentLevel().getFraiseFillRate());
			// ���󷢳���Ϸ��ʾ�¼�, �Ա�֪ͨ��ʾ�����ʾһЩЧ��
			for(int i = 0; i < gameViewListeners.length; i++)
				gameViewListeners[i].groundFilledRandom(ground);
		}
	}
	public void shapeDroped(boolean swift) {}
	public void scoringChanged(int scoring, boolean levelChanged) {}
	public void speedChanged(int speed) {}

	/**
	 * ������߼���, ��Ϸ����
	 */
	public void winning(int scoring, int speed, Level level) {
		playing = false;
		shape = null;
		for(int i = 0; i < gameListeners.length; i++)
			gameListeners[i].gameOver();
	}
	
	/**
	 * ������Ϸ������
	 * @param listener GameListener ��Ϸ������
	 */
    public void addGameListener(GameListener listener) {
    	gameListeners = (GameListener[])Utilities.arrayAddItem(
    			gameListeners, listener);
    }
    
    /**
     * �Ƴ���Ϸ������
     * @param listener GameListener ��Ϸ������
     */
    public void removeGameListener(GameListener listener) {
    	gameListeners = (GameListener[])Utilities.arrayRemoveItem(
    			gameListeners, listener);
    }

	/**
	 * ������Ϸ��ʾ������
	 * @param listener GameViewListener ��Ϸ��ʾ������
	 */
    public void addGameViewListener(GameViewListener listener) {
    	gameViewListeners = (GameViewListener[])Utilities.arrayAddItem(
    			gameViewListeners, listener);
    }
    
    /**
     * �Ƴ���Ϸ��ʾ������
     * @param listener GameViewListener ��Ϸ��ʾ������
     */
    public void removeGameViewListener(GameViewListener listener) {
    	gameViewListeners = (GameViewListener[])Utilities.arrayRemoveItem(
    			gameViewListeners, listener);
    }
    
    /**
     * ����Ԥ��������
     * @param listener PreviewListener Ԥ��������
     */
    public void addPreviewListener(PreviewListener listener) {
    	previewListeners = (PreviewListener[])Utilities.arrayAddItem(
    			previewListeners, listener);
    }
    
    /**
     * �Ƴ�Ԥ��������
     * @param listener PreviewListener Ԥ��������
     */
    public void removePreviewListener(PreviewListener listener) {
    	previewListeners = (PreviewListener[])Utilities.arrayRemoveItem(
    			previewListeners, listener);
    }
    
    /**
     * ���ӼƷּ�����
     * @param listener ScoringListener �Ʒּ�����
     */
    public void addScoringListener(ScoringListener listener) {
    	scorer.addScoringListener(listener);
    }
    
    /**
     * ���ӼƷּ�����
     * @param listener ScoringListener �Ʒּ�����
     * @param first boolean �Ƿ���������λ
     */
    public void addScoringListener(ScoringListener listener, boolean first) {
    	scorer.addScoringListener(listener, first);
    }

    /**
     * �Ƴ��Ʒּ�����
     * @param listener ScoringListener �Ʒּ�����
     */
    public void removeScoringListener(ScoringListener listener) {
    	scorer.removeScoringListener(listener);
    }
    
    /**
     * ��״�Զ�����������
     * @author fuyunliang
     */
	private class ShapeDropDriver extends Thread {
		
		/**
		 * ���������Ƿ����еı�־<br>
		 * ��Ϊfalse, ���������<br>
		 */
		private boolean run;
		
		/**
		 * �Ƿ�ֱ�䵽�׵ı�־<br>
		 * ��Ϊfalse, ÿ������ǰ��Ъһ������<br>
		 * ��Ϊtrue, �޼�Ъֱ�䵽��<br>
		 */
		private boolean swift;
		
		/**
		 * ��״�Զ�����������
		 */
		public ShapeDropDriver() {
			this.run = true;
		}
		
		/**
		 * ��״�Զ�����������
		 * @param swift boolean �Ƿ�ֱ�䵽��
		 */
		public ShapeDropDriver(boolean swift) {
			this.run = true;
			this.swift = swift;
			this.setDaemon(true);
		}
		
		/**
		 * ����������
		 */
		public void kill() {
			run = false;
		}
		
		/**
		 * ����һ�����ں���״�Զ������ƶ�һ��
		 */
		public void run() {
			while(playing && run) {
				try {
					// �������ֱ�䵽��, ����һ������
					if(!swift)
						Thread.sleep(scorer.getCurrentSpeed());
				} catch (InterruptedException e) {
				}
				if(playing && !pause && shape != null) {
					// �����ƶ�
					shapeDrop(swift);
				}
			}
		}
	}

}	
	
