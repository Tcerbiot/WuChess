package zhh.game.tetris.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JPanel;

import zhh.game.tetris.entity.Ground;
import zhh.game.tetris.entity.Shape;
import zhh.game.tetris.global.Config;
import zhh.game.tetris.listener.ConfigListener;
import zhh.game.tetris.listener.GameListener;
import zhh.game.tetris.listener.GameViewListener;

/**
 * ��Ϸ����
 * @author fuyunliang
 */
public class GamePanel extends JPanel 
		implements GameViewListener, GameListener,  ConfigListener {
	
	/**
	 * ���л��汾ͳһ��ʶ��
	 */
	private static final long serialVersionUID = -4474743639789611935L;

	
	/**
	 * ����
	 */
	private Ground ground;
	
	/**
	 * ��״
	 */
	private Shape shape;
	
	/**
	 * ��Ϸ�Ƿ����ڽ���
	 */
	private boolean playing;
	
	/**
	 * ��Ϸ�Ƿ���ͣ
	 */
	private boolean pause;

	/**
	 * ��Ϸ����
	 */
	public GamePanel() {
		super();
		playing = true;
		pause = false;
	}
	
	/**
	 * ���Ƶ���
	 * @param g Graphics
	 * @param ground Ground
	 */
	private void paintGround(Graphics g, Ground ground) {
		if(ground == null) return;
		
		// ��������, Ԥ�����߿��λ��
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		g.translate(x, y);

		Color oldColor = g.getColor();
		int[][] points = ground.getPoints();
		if(points == null) return;
		int length = points.length;
		for (int i = 0; i < length; i++) {
			if(points[i][1] < 0) continue;
			g.setColor(Config.CURRENT.getGroundColor(points[i][2]));
			_paintPoint(g, points[i][0], points[i][1]);
		}
		
		// ��ԭ�������ɫ
		g.translate(-x, -y);
        g.setColor(oldColor);
	}
	
	/**
	 * ���Ƶ���<br>
	 * �Ե��ε���ɫ���ѱ�Ϊ�ϰ������״<br>
	 * @param g Graphics
	 * @param shape Shape
	 */
	private void paintGround(Graphics g, Shape shape) {
		if(shape == null) return;
		_paintShape(g, shape, Config.CURRENT.getGroundColor(shape.getType()));
	}
	
	/**
	 * ������״
	 * @param g Graphics
	 * @param shape Shape
	 */
	private void paintShape(Graphics g, Shape shape) {
		if(shape == null) return;
		_paintShape(g, shape, Config.CURRENT.getShapeColor(shape.getType()));
	}
	
	/**
	 * ������״
	 * @param g Graphics
	 * @param shape Shape
	 * @param color Color
	 */
	private void _paintShape(Graphics g, Shape shape, Color color) {
		// ��������, Ԥ�����߿��λ��
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		g.translate(x, y);
		
		// ������ɫ
		Color oldColor = g.getColor();
		g.setColor(color);
		int[][] points = shape.getPoints();
		int length = points.length;
		for (int i = 0; i < length; i++) {
			if(points[i][1] < 0) continue;
			_paintPoint(g, points[i][0], points[i][1]);
		}
		
		// ��ԭ�������ɫ
		g.translate(-x, -y);
        g.setColor(oldColor);
	}
	
	/**
	 * ���Ƶ��ε�һ��
	 * @param g Graphics
	 * @param ground Ground
	 * @param line int
	 * @param color Color
	 */
	private void _paintGroundLine(Graphics g, Ground ground, int line, Color color) {
		// ��������, Ԥ�����߿��λ��
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		g.translate(x, y);

		Color oldColor = g.getColor();
		
		if(ground == null) return;
		int[][] points = ground.getPoints();
		if(points == null) return;
		int length = points.length;
		for (int i = 0; i < length; i++) {
			if(points[i][1] < 0 || points[i][1] != line) continue;
			g.setColor(color == null ? Config.CURRENT.getGroundColor(points[i][2]) : color);
			_paintPoint(g, points[i][0], points[i][1]);
		}
		
		// ��ԭ�������ɫ
		g.translate(-x, -y);
        g.setColor(oldColor);
	}
	
	/**
	 * ���Ƶ�
	 * @param g Graphics
	 * @param x int
	 * @param y int
	 */
	private void _paintPoint(Graphics g, int x, int y) {
		g.fill3DRect(x * Config.CURRENT.getCellWidth(),
				y * Config.CURRENT.getCellHeight(), 
				Config.CURRENT.getCellWidth(),
				Config.CURRENT.getCellHeight(), true);
	}
	
	/**
	 * �������
	 * @param g Graphics
	 */
	protected void paintComponent(Graphics g) {
		paintBackGround(g);
		paintGround(g, ground);
		paintShape(g, shape);
		if(!playing || pause)
			paintCovering(g);
	}
	
	/**
	 * ���Ʊ���
	 * @param g Graphics
	 */
	protected void paintBackGround(Graphics g) {
		// ��������, Ԥ�����߿��λ��
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		g.translate(x, y);

		// ������ɫ
		Color oldColor = g.getColor();
		g.setColor(Config.CURRENT.getBackgroundColor());
		// ���ñ���ɫ
		int cellWidth = Config.CURRENT.getCellWidth();
		int cellHeight = Config.CURRENT.getCellHeight();
		int width = Config.CURRENT.getGroundWidth() * cellWidth;
		int height = Config.CURRENT.getGroundHeight() * cellHeight;
		g.fillRect(0, 0, width, height);
		// ��������
		if(Config.CURRENT.isShowGridLine()) {
			g.setColor(Config.CURRENT.getGridLineColor());
			// ����б߿�, �����Ƶ�һ��ˮƽ�ߺʹ�ֱ��
			if(getBorder() == null) {
				g.drawLine(0, 0, width - 1, 0);
				g.drawLine(0, 0, 0, height - 1);
			}
			// ����ˮƽ��
			for (int i = 1; i <= Config.CURRENT.getGroundHeight(); i++) {
				g.drawLine(0, i * cellHeight - 1, width - 1, i * cellHeight - 1);
			}
			// ���ƴ�ֱ��
			for (int i = 1; i <= Config.CURRENT.getGroundWidth(); i++) {
				g.drawLine(i * cellWidth - 1, 0, i * cellWidth - 1, height - 1);
			}
		}
		
		// ��ԭ�������ɫ
		g.translate(-x, -y);
        g.setColor(oldColor);
	}
	
	/**
	 * ������ɫ<br>
	 * ���ڱ�ʾ��Ϸ�Ѿ�������������ͣ
	 * @param g Graphics
	 */
	protected void paintCovering(Graphics g) {
		// ��������, Ԥ�����߿��λ��
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		g.translate(x, y);
		int width = getSize().width - insets.left - insets.right;
		int height = getSize().height - insets.top - insets.bottom;

		// ������ɫ
		Color oldColor = g.getColor();
		g.setColor(!playing ? Config.CURRENT.getCoveringOverColor() : Config.CURRENT.getCoveringPauseColor());
		g.fillRect(0, 0, width, height);
		
		// ��ԭ�������ɫ
		g.translate(-x, -y);
        g.setColor(oldColor);
	}

	/**
	 * �����Ϸ���ڽ�����, ��Ϊ��ͣ״̬, ��ֹͣ��ʾЧ��, �����ȴ�״̬�ı�
	 */
	private void whilePauseWait() {
		if(playing && pause) {
			synchronized(this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ��������״���¼���Ӧ
	 * @param shape Shape �´�������״
	 */
	public void shapeCreated(final Shape shape) {
		this.shape = shape;
		paintShape(getGraphics(), shape);
	}

	/**
	 * ��״������ʼ�ƶ����¼���Ӧ
	 * @param shape Shape �����仯����״
	 */
	public void shapeWillMoved(final Shape shape) {}

	/**
	 * ��״�Ѿ��ƶ���λ���¼���Ӧ
	 * @param shape Shape �����仯����״
	 */
	public void shapeMoved(final Shape shape) {
		this.shape = shape;
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		int width = getSize().width - insets.left - insets.right;
		int height = getSize().height - insets.top - insets.bottom;
		this.paintImmediately(x, y, width, height);
	}

	/**
	 * ��״�Ѿ�����ϰ�����¼���Ӧ
	 * @param ground Ground ����
	 * @param shape Shape �����仯����״
	 */
	public void groundFilledShape(final Ground ground, final Shape shape) {
		this.ground = ground;
		this.shape = null;
		if(Config.CURRENT.isSupportColorfulShape() != Config.CURRENT.isSupportColorfulGround())
			paintGround(getGraphics(), shape);
	}

	/**
	 * �ϰ��Ｔ��ɾ���е��¼���Ӧ<br>
	 * ����ɾ���е���˸Ч��
	 * @param ground Ground ����
	 * @param line int[] ɾ�����к�
	 */
	public void groundWillDeleteLine(final Ground ground, final int[] fullLine) {
		Graphics g = getGraphics();
		int length = fullLine.length;
		for(int i = 0; i < 2; i++) {
			// �����Ϸ��ֹͣ, ���ټ�����ʾЧ��
			if(!playing) break;
			// ����ʹ��ͬ��, ��������Ϸ��ͣ�Ļ������ɫ������˸�����, �ƻ���ɫ
			// ʹ��ͬ����, ��ɫ������һ����˸��ɺ�ſ�ʼ, ������ͣ�ٸ�
			synchronized (this) {
				// ��ʾ��ɾ���е���˸Ч��
				for(int j = 0; j < length; j++) {
					_paintGroundLine(g, ground, fullLine[j], Config.CURRENT.getWinkColor());
				}
				try {
					Thread.sleep(Config.CURRENT.getWinkPauseTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(int j = 0; j < length; j++) {
					_paintGroundLine(g, ground, fullLine[j], null);
				}
				try {
					Thread.sleep(Config.CURRENT.getWinkPauseTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			whilePauseWait();
		}
	}
	
	/**
	 * �ϰ������Ѿ���ɾ�����¼���Ӧ
	 * @param ground Ground ����
	 */
	public void groundDeletedLine(final Ground ground) {
		this.ground = ground;
		repaint();
	}

	/**
	 * �ϰ��Ｔ������յ��¼���Ӧ<br>
	 * �ϰ������˸Ч��
	 * @param ground Ground ����
	 */
	public void groundWillClear(final Ground ground) {
		if(ground == null || ground.getPoints().length == 0) return;
		
		Ground copy = (Ground)ground.clone();
		Graphics g = getGraphics();
		int deleteCount;
		do {
			// �����Ϸ��ֹͣ, ���ټ�����ʾЧ��
			if(!playing) break;
			// ����ʹ��ͬ��, ��������Ϸ��ͣ�Ļ������ɫ������˸�����, �ƻ���ɫ
			// ʹ��ͬ����, ��ɫ������һ����˸��ɺ�ſ�ʼ, ������ͣ�ٸ�
			synchronized(this) {
				// ��ʾ�ϰ������˸Ч��
				for(int i = 0; i < 2; i++) {
					this._paintGroundLine(g, copy, copy.getHeight() - 1, Config.CURRENT.getWinkColor());
					try {
						Thread.sleep(Config.CURRENT.getWinkPauseTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this._paintGroundLine(g, copy, copy.getHeight() - 1, null);
					try {
						Thread.sleep(Config.CURRENT.getWinkPauseTime());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				deleteCount = copy.deleteLine(new int[] {copy.getHeight() - 1});
				this.ground = copy;
				paintBackGround(g);
				paintGround(g, copy);
			}
			whilePauseWait();
		} while(deleteCount > 0);
	}
	
	/**
	 * ����������ϰ�����¼���Ӧ<br>
	 * �ϰ������˸Ч��
	 * @param ground Ground ����
	 */
	public void groundFilledRandom(final Ground ground) {
		Ground copy = new Ground(ground.getWidth(), ground.getHeight());
		this.ground = copy;
		
		int[][] points = ground.getPoints();
		if(points == null) return;

		Graphics g = getGraphics();

		int length = points.length;
		for (int i = 0; i < length; i++) {
			// �����Ϸ��ֹͣ, ���ټ�����ʾЧ��
			if(!playing) break;
			// ����ʹ��ͬ��, ��������Ϸ��ͣ�Ļ������ɫ������˸�����, �ƻ���ɫ
			// ʹ��ͬ����, ��ɫ������һ����˸��ɺ�ſ�ʼ, ������ͣ�ٸ�
			synchronized(this) {
				if(points[i][1] < 0) continue;
				copy.fill(new int[][]{points[i]});
				
				// ��������, Ԥ�����߿��λ��
				Insets insets = getInsets();
				int x = insets.left;
				int y = insets.top;
				g.translate(x, y);
	
				Color oldColor = g.getColor();
				g.setColor(Config.CURRENT.getGroundColor(points[i][2]));
				_paintPoint(g, points[i][0], points[i][1]);
				// ��ԭ�������ɫ
				g.translate(-x, -y);
		        g.setColor(oldColor);
		        
				try {
					Thread.sleep(Config.CURRENT.getWinkPauseTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			whilePauseWait();
		}
	}
	
	/**
	 * �ϰ����Ѿ�����յ��¼���Ӧ
	 */
	public void groundCleared() {
		this.ground = null;
		Graphics g = getGraphics();
		paintBackGround(g);
	}

	public void shapeDroped(boolean swift) {}

	/**
	 * �й���ʾ���������Ѿ��ı���¼���Ӧ
	 */
	public void viewConfigChanged() {
		repaint();
	}

	public void hotkeyConfigChanged() {}

	public void levelConfigChanged() {}

	/**
	 * ��Ϸ�������¼���Ӧ
	 */
	public void gameContinue() {
		pause = false;
		synchronized(this) {
			notify();
			// ȥ����ʱ��Ϸʱ���ϵ���ɫ
			repaint();
		}
		
	}

	/**
	 * ��Ϸ�������¼���Ӧ
	 */
	public void gameOver() {
		playing = false;
		synchronized(this) {
			notify();
			// ���Ͻ�����Ϸ����ɫ
			if(pause)
				// ��Ҫ�ػ�, ��Ϊ�Ѿ�������һ����ʱ״̬����ɫ
				repaint();
			else
				paintCovering(getGraphics());
		}

	}

	/**
	 * ��Ϸ��ͣ���¼���Ӧ
	 */
	public void gamePause() {
		pause = true;
		synchronized(this) {
			notify();
			
			// ������ʱ��Ϸ����ɫ
			paintCovering(getGraphics());
		}
	}

	/**
	 * ��Ϸ��ʼ���¼���Ӧ
	 */
	public void gameStart() {
		playing = true;
		ground = null;
		shape = null;
		synchronized(this) {
			notify();
			repaint();
		}
	}

	/**
	 * ��Ϸ����ֹͣ���¼���Ӧ
	 */
	public boolean gameWillStop() {
		return true;
	}
}


