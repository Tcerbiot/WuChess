package zhh.game.tetris.global;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * 公用工具类
 * @author zhaohuihua
 */
public class Utilities {
    private Utilities() {
    }

    /**
     * 向数组增加一个项<br>
     * 增加至末尾<br>
     * @param array Object[] 数组
     * @param item Object 待增加的项
     * @return Object[] 增加项后的数组
     */
    public static Object[] arrayAddItem(Object[] array, Object item) {
        return arrayAddItem(array, item, false);
    }
    
    /**
     * 向数组增加一个项<br>
     * @param array Object[] 数组
     * @param item Object 待增加的项
     * @param first boolean 是否增加至首位<br>
     *         true: 增加至首位<br>
     *         false: 增加至末尾<br>
     * @return Object[] 增加项后的数组
     */
    public static Object[] arrayAddItem(Object[] array, Object item, boolean first) {
        if(array == null)
            return null;
        int length = array.length;
        Class clazz = array.getClass().getComponentType();
        Object[] result = (Object[])Array.newInstance(clazz, length + 1);
        if(length != 0)
            System.arraycopy(array, 0, result, first ? 1 : 0, length);
        Array.set(result, first ? 0 : length, item);
        return result;
    }

    /**
     * 从数组删除一个项
     * @param array Object[] 数组
     * @param item Object 待删除的项
     * @return Object[] 删除项后的数组
     */
    public static Object[] arrayRemoveItem(Object[] array, Object item) {
        int length = array == null ? 0 : array.length;
        if(length == 0) return array;
        int count = 0;
        Class clazz = array.getClass().getComponentType();
        Object[] temp = (Object[])Array.newInstance(clazz, length);
        for(int i = 0; i < length; i++) {
            if(array[i] != item) {
                temp[count] = array[i];
                count++;
            }
        }
        if(count == length)
            return array;
        Object[] result = (Object[])Array.newInstance(clazz, count);
        System.arraycopy(temp, 0, result, 0, count);
        array = result;
        return result;
    }

    /**
     * 判断文件的编码格式<br>
     * 来源: http://www.chsi.com.cn/xy/com/200902/20090218/17570775.html<br>
     * BOM参考: http://www.bitscn.com/pdb/java/200605/20811.html
     * @param file
     * @return
     */
    public static String getEncoding(File file) {
        String encoding = "GBK";
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0);

            // 带BOM信息的情况, 根据前三位判断编码
            byte[] bytes = new byte[3];
            int read = bis.read(bytes, 0, 3);
            if (read == -1)
                return encoding;
            if (bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xFE) {
                return "UTF-16LE"; // Little-Endian
            } else if (bytes[0] == (byte) 0xFE && bytes[1] == (byte) 0xFF) {
                return "UTF-16BE"; // Big-Endian
            } else if (bytes[0] == (byte) 0xEF
                    && bytes[1] == (byte) 0xBB
                    && bytes[2] == (byte) 0xBF) {
                return "UTF-8";
            }

            // 根据BOM信息未判断出编码的情况
            bis.reset();
            int loc = 0;
            while ((read = bis.read()) != -1) {
                loc++;
                if (read >= 0xF0)
                    break;
                
                if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的, 也算是GBK
                    break;
                if (0xC0 <= read && read <= 0xDF) {
                    read = bis.read();
                    // 双字节 (0xC0 - 0xDF)(0x80 - 0xBF), 也可能在GB编码内
                    if (0x80 <= read && read <= 0xBF)
                        continue;
                    else
                        break;
                }
                // 也有可能出错, 但是几率较小
                else if (0xE0 <= read && read <= 0xEF) {
                    read = bis.read();
                    if (0x80 <= read && read <= 0xBF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            encoding = "UTF-8";
                            break;
                        } else
                            break;
                    } else
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return encoding;
    }
}


