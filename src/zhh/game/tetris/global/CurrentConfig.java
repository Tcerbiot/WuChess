package zhh.game.tetris.global;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zhh.game.tetris.entity.LevelSetFactory;

/**
 * ��ǰ������<br>
 * ʵ����ʱ�����ⲿ�����ļ�, �����ж�ȡ����ֵ, <br>
 * ����������ⲿ�����ļ�, ������ڲ�Ĭ�������ļ�
 * (ֻ���ز���ȡ,���Ӧ��֤�ڲ������ļ���DefaultConfig��Ĭ��ֵһ��)
 * @author fuyunliang
 */
public class CurrentConfig extends DefaultConfig {

	protected String configFileName;
	protected Properties properties;

	public CurrentConfig() {
		super();
		configFileName = getConfigFileName();
		properties = new Properties();
		load();
	}
	/**
	 * �Ƿ���ʾ��Ϸ��������
	 * @param showGridLine
	 */
	public void setShowGridLine(boolean showGridLine) {
		this.showGridLine = showGridLine;
		properties.setProperty("show_grid_line",
				String.valueOf(this.showGridLine));
	}
	/**
	 * �Ƿ���ʾԤ����������
	 * @param showPreviewGridLine
	 */
	public void setShowPreviewGridLine(boolean showPreviewGridLine) {
		this.showPreviewGridLine = showPreviewGridLine;
		properties.setProperty("show_preview_grid_line",
				String.valueOf(this.showPreviewGridLine));
	}
	/**
	 * �Ƿ�֧�ֲ�ɫ��״
	 * @param supportColorfulShape
	 */
	public void setSupportColorfulShape(boolean supportColorfulShape) {
		this.supportColorfulShape = supportColorfulShape;
		properties.setProperty("support_colorful_shape",
				String.valueOf(this.supportColorfulShape));
	}
	/**
	 * �Ƿ�֧�ֲ�ɫ�ϰ���
	 * @param supportColorfulGround
	 */
	public void setSupportColorfulGround(boolean supportColorfulGround) {
		this.supportColorfulGround = supportColorfulGround;
		properties.setProperty("support_colorful_ground",
				String.valueOf(this.supportColorfulGround));
	}
	/**
	 * �Ƿ�֧�ֲ�ɫԤ��
	 * @param supportColorfulPreview
	 */
	public void setSupportColorfulPreview(
			boolean supportColorfulPreview) {
		this.supportColorfulPreview = supportColorfulPreview;
		properties.setProperty("support_colorful_preview",
				String.valueOf(this.supportColorfulPreview));
	}
	/**
	 * �Ƿ�֧������
	 * @param supportSound
	 */
	public void setSupportSound(boolean supportSound) {
		this.supportSound = supportSound;
		properties.setProperty("support_sound",
				String.valueOf(this.supportSound));
	}
	/**
	 * ��ǰ�ļ���
	 * @param currentLevelSet
	 */
	public void setCurrentLevelSet(int currentLevelSet) {
		int maxSet = LevelSetFactory.getLevelSetCount();
		if(currentLevelSet >= 0 && currentLevelSet < maxSet) {
			this.currentLevelSet = currentLevelSet;
			int maxLevel = LevelSetFactory.getLevelSet(this.currentLevelSet)
					.getLevelCount();
			if(initLevel < 0 || initLevel >= maxLevel) {
				setInitLevel(0);
			}
		}
		properties.setProperty("current_level_set",
				String.valueOf(this.currentLevelSet));
	}
	/**
	 * ��ʼ����
	 * @param initLevel
	 */
	public void setInitLevel(int initLevel) {
		int max = LevelSetFactory.getLevelSet(currentLevelSet).getLevelCount();
		if(initLevel >= 0 && initLevel < max) {
			this.initLevel = initLevel;
		}
		properties.setProperty("init_level", String.valueOf(this.initLevel));
	}
	/**
	 * ��Ϸ��ʼ/�����ļ���
	 * @param startKey
	 */
	public void setStartKey(int startKey) {
		this.startKey = startKey;
		properties.setProperty("start_key", convertKeyCode(this.startKey));
	}
	/**
	 * ��Ϸ��ͣ/�����ļ���
	 * @param pauseKey
	 */
	public void setPauseKey(int pauseKey) {
		this.pauseKey = pauseKey;
		properties.setProperty("pause_key", convertKeyCode(this.pauseKey));
	}
	/**
	 * ��״���Ƶļ���
	 * @param leftKey
	 */
	public void setLeftKey(int leftKey) {
		this.leftKey = leftKey;
		properties.setProperty("left_key", convertKeyCode(this.leftKey));
	}
	/**
	 * ��״���Ƶļ���
	 * @param rightKey
	 */
	public void setRightKey(int rightKey) {
		this.rightKey = rightKey;
		properties.setProperty("right_key", convertKeyCode(this.rightKey));
	}
	/**
	 * ��״���εļ���
	 * @param rotateKey
	 */
	public void setRotateKey(int rotateKey) {
		this.rotateKey = rotateKey;
		properties.setProperty("rotate_key", convertKeyCode(this.rotateKey));
	}
	/**
	 * ��״���ٵļ���
	 * @param downKey
	 */
	public void setDownKey(int downKey) {
		this.downKey = downKey;
		properties.setProperty("down_key", convertKeyCode(this.downKey));
	}
	/**
	 * ��״ֱ�䵽�׵ļ���
	 * @param swiftKey
	 */
	public void setSwiftKey(int swiftKey) {
		this.swiftKey = swiftKey;
		properties.setProperty("swift_key", convertKeyCode(this.swiftKey));
	}
	/**
	 * ���������ļ�
	 */
	private void load() {
		try {
			// �����ⲿ�����ļ�
			properties.load(configFileName);
		} catch (Exception e) { // �������ⲿ�����ļ�������ⲿ�ļ�����
			if(!(e instanceof FileNotFoundException)) {
				e.printStackTrace();
			}
			// �����ڲ�Ĭ�������ļ�
			URL url = this.getClass().getResource(
					"/zhh/game/tetris/resource/properties/config.res");
			if(url != null) {
				try {
					properties.load(url.openStream());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			return;
		}

		// ��һ��ȡ�����ļ�����
		// ����������ݲ��淶, ���Զ��޸�Ϊ�淶����, ��setter�޸�
		// ��ʾ����
		String val = properties.getProperty("show_grid_line");
		if(val != null) setShowGridLine("true".equalsIgnoreCase(val));
		val = properties.getProperty("show_preview_grid_line");
		if(val != null) setShowPreviewGridLine("true".equalsIgnoreCase(val));
		val = properties.getProperty("support_colorful_shape");
		if(val != null) setSupportColorfulShape("true".equalsIgnoreCase(val));
		val = properties.getProperty("support_colorful_ground");
		if(val != null) setSupportColorfulGround("true".equalsIgnoreCase(val));
		val = properties.getProperty("support_colorful_preview");
		if(val != null) setSupportColorfulPreview("true".equalsIgnoreCase(val));
		// ��������
		val = properties.getProperty("support_sound");
		if(val != null) setSupportSound("true".equalsIgnoreCase(val));
		// �ؿ�ѡ��
		int num = parseInt(properties.getProperty("current_level_set"));
		setCurrentLevelSet(num >= 0 ? num : currentLevelSet);
		num = parseInt(properties.getProperty("init_level"));
		setInitLevel(num >= 0 ? num : initLevel);
		// ���Ƽ�����
		int keyCode = parseKeyCode(properties.getProperty("start_key"));
		setStartKey(keyCode >= 0 ? keyCode : startKey);
		keyCode = parseKeyCode(properties.getProperty("pause_key"));
		setPauseKey(keyCode >= 0 ? keyCode : pauseKey);
		keyCode = parseKeyCode(properties.getProperty("left_key"));
		setLeftKey(keyCode >= 0 ? keyCode : leftKey);
		keyCode = parseKeyCode(properties.getProperty("right_key"));
		setRightKey(keyCode >= 0 ? keyCode : rightKey);
		keyCode = parseKeyCode(properties.getProperty("rotate_key"));
		setRotateKey(keyCode >= 0 ? keyCode : rotateKey);
		keyCode = parseKeyCode(properties.getProperty("down_key"));
		setDownKey(keyCode >= 0 ? keyCode : downKey);
		keyCode = parseKeyCode(properties.getProperty("swift_key"));
		setSwiftKey(keyCode >= 0 ? keyCode : swiftKey);
	}
	/**
	 * �����ַ���Ϊ����<br>
	 * �������ʧ��, ����-1, �Ա�Ϊ�����Ĭ��ֵ
	 * @param value
	 * @return
	 */
	private int parseInt(String value) {
		if(value == null) return -1;
		try {
			return Integer.parseInt(value);
		} catch(NumberFormatException e) {
			return -1;
		}
	}
	private static final Pattern PTN_KEY_CODE = Pattern.compile(
			"0x(\\d+)\\s*(?:\\(.+\\))?");
	/**
	 * �����ַ���Ϊ�����ļ���<br>
	 * �������ʧ��, ����-1, �Ա�Ϊ�����Ĭ��ֵ
	 * @param value
	 * @return
	 */
	private int parseKeyCode(String value) {
		if(value == null) return -1;
		try {
			Matcher matcher = PTN_KEY_CODE.matcher(value);
			if(matcher.matches()) {
				return Integer.parseInt(matcher.group(1), 16);
			}
		} catch(Exception e) {
		}
		return -1;
	}
	/**
	 * �������ļ���ת��Ϊ������ı�<br>
	 * �Ƚ�����ת��Ϊ16����, �������ż��ϼ���������ı�
	 * @param keyCode �����ļ���
	 * @return
	 */
	private String convertKeyCode(int keyCode) {
		return new StringBuffer()
				.append("0x").append(Integer.toString(keyCode, 16))
				.append(" (").append(KeyEvent.getKeyText(keyCode)).append(")")
				.toString();
	}
	public void save() {
		try {
			properties.store(configFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static final String FILE_NAME = "teties";
	private static final String FILE_EXT = ".cfg";
	private static final Pattern PTN_JAR = Pattern.compile(
			"jar:file:.*[/\\\\](.+)\\.jar![/\\\\].*\\.class",
			Pattern.CASE_INSENSITIVE);
	private String getConfigFileName () {
		String classPath = this.getClass().getName().replace('.', '/');
		// ��ȡ������JAR��������ͬ�������ļ���
		URL url = this.getClass().getResource("/" + classPath + ".class");
		if(url != null) {
			Matcher matcher = PTN_JAR.matcher(url.toString());
			if(matcher.matches()) {
				String fileName = matcher.group(1);
				if(fileName != null) {
					return fileName + FILE_EXT;
				}
			}
		}
		return FILE_NAME + FILE_EXT; // ��ȡʧ��, ����Ĭ���ļ���
	}
}

