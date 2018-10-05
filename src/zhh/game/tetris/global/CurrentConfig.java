package zhh.game.tetris.global;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zhh.game.tetris.entity.LevelSetFactory;

/**
 * 当前配置类<br>
 * 实例化时加载外部配置文件, 并从中读取配置值, <br>
 * 如果不存在外部配置文件, 则加载内部默认配置文件
 * (只加载不读取,因此应保证内部配置文件与DefaultConfig的默认值一致)
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
	 * 是否显示游戏区的网格
	 * @param showGridLine
	 */
	public void setShowGridLine(boolean showGridLine) {
		this.showGridLine = showGridLine;
		properties.setProperty("show_grid_line",
				String.valueOf(this.showGridLine));
	}
	/**
	 * 是否显示预览区的网格
	 * @param showPreviewGridLine
	 */
	public void setShowPreviewGridLine(boolean showPreviewGridLine) {
		this.showPreviewGridLine = showPreviewGridLine;
		properties.setProperty("show_preview_grid_line",
				String.valueOf(this.showPreviewGridLine));
	}
	/**
	 * 是否支持彩色形状
	 * @param supportColorfulShape
	 */
	public void setSupportColorfulShape(boolean supportColorfulShape) {
		this.supportColorfulShape = supportColorfulShape;
		properties.setProperty("support_colorful_shape",
				String.valueOf(this.supportColorfulShape));
	}
	/**
	 * 是否支持彩色障碍物
	 * @param supportColorfulGround
	 */
	public void setSupportColorfulGround(boolean supportColorfulGround) {
		this.supportColorfulGround = supportColorfulGround;
		properties.setProperty("support_colorful_ground",
				String.valueOf(this.supportColorfulGround));
	}
	/**
	 * 是否支持彩色预览
	 * @param supportColorfulPreview
	 */
	public void setSupportColorfulPreview(
			boolean supportColorfulPreview) {
		this.supportColorfulPreview = supportColorfulPreview;
		properties.setProperty("support_colorful_preview",
				String.valueOf(this.supportColorfulPreview));
	}
	/**
	 * 是否支持声音
	 * @param supportSound
	 */
	public void setSupportSound(boolean supportSound) {
		this.supportSound = supportSound;
		properties.setProperty("support_sound",
				String.valueOf(this.supportSound));
	}
	/**
	 * 当前的级别集
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
	 * 初始级别
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
	 * 游戏开始/结束的键码
	 * @param startKey
	 */
	public void setStartKey(int startKey) {
		this.startKey = startKey;
		properties.setProperty("start_key", convertKeyCode(this.startKey));
	}
	/**
	 * 游戏暂停/继续的键码
	 * @param pauseKey
	 */
	public void setPauseKey(int pauseKey) {
		this.pauseKey = pauseKey;
		properties.setProperty("pause_key", convertKeyCode(this.pauseKey));
	}
	/**
	 * 形状左移的键码
	 * @param leftKey
	 */
	public void setLeftKey(int leftKey) {
		this.leftKey = leftKey;
		properties.setProperty("left_key", convertKeyCode(this.leftKey));
	}
	/**
	 * 形状右移的键码
	 * @param rightKey
	 */
	public void setRightKey(int rightKey) {
		this.rightKey = rightKey;
		properties.setProperty("right_key", convertKeyCode(this.rightKey));
	}
	/**
	 * 形状变形的键码
	 * @param rotateKey
	 */
	public void setRotateKey(int rotateKey) {
		this.rotateKey = rotateKey;
		properties.setProperty("rotate_key", convertKeyCode(this.rotateKey));
	}
	/**
	 * 形状加速的键码
	 * @param downKey
	 */
	public void setDownKey(int downKey) {
		this.downKey = downKey;
		properties.setProperty("down_key", convertKeyCode(this.downKey));
	}
	/**
	 * 形状直落到底的键码
	 * @param swiftKey
	 */
	public void setSwiftKey(int swiftKey) {
		this.swiftKey = swiftKey;
		properties.setProperty("swift_key", convertKeyCode(this.swiftKey));
	}
	/**
	 * 加载配置文件
	 */
	private void load() {
		try {
			// 加载外部配置文件
			properties.load(configFileName);
		} catch (Exception e) { // 不存在外部配置文件或加载外部文件出错
			if(!(e instanceof FileNotFoundException)) {
				e.printStackTrace();
			}
			// 加载内部默认配置文件
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

		// 逐一读取配置文件内容
		// 如果配置内容不规范, 会自动修改为规范内容, 由setter修改
		// 显示设置
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
		// 声音设置
		val = properties.getProperty("support_sound");
		if(val != null) setSupportSound("true".equalsIgnoreCase(val));
		// 关卡选择
		int num = parseInt(properties.getProperty("current_level_set"));
		setCurrentLevelSet(num >= 0 ? num : currentLevelSet);
		num = parseInt(properties.getProperty("init_level"));
		setInitLevel(num >= 0 ? num : initLevel);
		// 控制键设置
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
	 * 解析字符串为数字<br>
	 * 如果解析失败, 返回-1, 以便为其分配默认值
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
	 * 解析字符串为按键的键码<br>
	 * 如果解析失败, 返回-1, 以便为其分配默认值
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
	 * 将按键的键码转换为保存的文本<br>
	 * 先将键码转换为16进制, 再以括号加上键码的描述文本
	 * @param keyCode 按键的键码
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
		// 获取与自身JAR包名称相同的配置文件名
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
		return FILE_NAME + FILE_EXT; // 获取失败, 返回默认文件名
	}
}

