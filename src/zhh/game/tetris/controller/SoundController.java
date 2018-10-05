package zhh.game.tetris.controller;

import java.applet.AudioClip;
import java.net.URL;

import com.sun.media.sound.JavaSoundAudioClip;

import zhh.game.tetris.entity.Ground;
import zhh.game.tetris.entity.Level;
import zhh.game.tetris.entity.Shape;
import zhh.game.tetris.global.Config;
import zhh.game.tetris.listener.GameListener;
import zhh.game.tetris.listener.GameViewListener;
import zhh.game.tetris.listener.ScoringListener;

/**
 * ����������
 * @author fuyunliang
 */

public class SoundController implements GameListener, ScoringListener, GameViewListener  {

	/**
	 * ��������(��ʼ)
	 */
	public static final int START = 0;
	/**
	 * ��������(����)
	 */
	public static final int OVER = 1;
	/**
	 * ��������(��ͣ)
	 */
	public static final int PAUSE = 2;
	/**
	 * ��������(����)
	 */
	public static final int CONTINUE = 3;
	/**
	 * ��������(���䵽λ)
	 */
	public static final int DOWN = 4;
	/**
	 * ��������(ֱ�䵽��)
	 */
	public static final int SWIFT = 5;
	/**
	 * ��������(�÷�)
	 */
	public static final int SCORING = 6;
	/**
	 * ��������(����仯)
	 */
	public static final int LEVEL = 7;
	/**
	 * ��������(Ӯ��)
	 */
	public static final int WINNING = 8;
	
	/**
	 * �����ļ����ļ���
	 */
	private static final String[] sounds = {
		"start.wav",
		"over.wav",
		"pause.wav",
		"start.wav", // continue
		"down.wav",
		"swift.wav",
		"scoring.wav",
		"level.wav",
		"winning.wav"
	};
	
	/**
	 * ����������
	 */
	private static AudioClip[] pool = new AudioClip[sounds.length];
	static {
		for(int i = 0; i < sounds.length; i++) {
			try {
				URL url = SoundController.class.getResource("d:/zhh/game/tetris/resource/sounds" + sounds[i]);
				if(url != null) {
					pool[i] = new JavaSoundAudioClip(url.openStream());
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public SoundController() {
	}
	
	/**
	 * ��ָ�����Ͳ�������
	 * @param type
	 */
	private void play(int type) {
		if(!Config.CURRENT.isSupportSound()) return;
		if(pool[type] != null) {
			pool[type].play();
		}
	}

	public void gameStart() {
		play(START);
	}
	public void gamePause() {
		play(PAUSE);
	}
	public void gameContinue() {
		play(CONTINUE);
	}
	public void gameOver() {
		play(OVER);
	}
	public boolean gameWillStop() {
		return true;
	}

	public void scoringChanged(int scoring, boolean levelChanged) {
		if(!levelChanged) play(SCORING);
	}
	public void levelChanged(Level level) {
		play(LEVEL);
	}
	public void winning(int scoring, int speed, Level level) {
		play(WINNING);
	}
	public void scoringInit(int scoring, int speed, Level level) {}
	public void speedChanged(int speed) {}


	public void shapeDroped(boolean swift) {
		if(swift) {
			play(SWIFT);
		} else {
			play(DOWN);
		}
	}
	public void groundFilledShape(Ground ground, Shape shape) {}
	public void groundCleared() {}
	public void groundDeletedLine(Ground ground) {}
	public void groundFilledRandom(Ground ground) {}
	public void groundWillClear(Ground ground) {}
	public void groundWillDeleteLine(Ground ground, int[] line) {}
	public void shapeCreated(Shape shape) {}
	public void shapeMoved(Shape shape) {}
	public void shapeWillMoved(Shape shape) {}
}



