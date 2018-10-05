package zhh.game.tetris.global;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * ���ù�����
 * @author zhaohuihua
 */
public class Utilities {
    private Utilities() {
    }

    /**
     * ����������һ����<br>
     * ������ĩβ<br>
     * @param array Object[] ����
     * @param item Object �����ӵ���
     * @return Object[] ������������
     */
    public static Object[] arrayAddItem(Object[] array, Object item) {
        return arrayAddItem(array, item, false);
    }
    
    /**
     * ����������һ����<br>
     * @param array Object[] ����
     * @param item Object �����ӵ���
     * @param first boolean �Ƿ���������λ<br>
     *         true: ��������λ<br>
     *         false: ������ĩβ<br>
     * @return Object[] ������������
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
     * ������ɾ��һ����
     * @param array Object[] ����
     * @param item Object ��ɾ������
     * @return Object[] ɾ����������
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
     * �ж��ļ��ı����ʽ<br>
     * ��Դ: http://www.chsi.com.cn/xy/com/200902/20090218/17570775.html<br>
     * BOM�ο�: http://www.bitscn.com/pdb/java/200605/20811.html
     * @param file
     * @return
     */
    public static String getEncoding(File file) {
        String encoding = "GBK";
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0);

            // ��BOM��Ϣ�����, ����ǰ��λ�жϱ���
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

            // ����BOM��Ϣδ�жϳ���������
            bis.reset();
            int loc = 0;
            while ((read = bis.read()) != -1) {
                loc++;
                if (read >= 0xF0)
                    break;
                
                if (0x80 <= read && read <= 0xBF) // ��������BF���µ�, Ҳ����GBK
                    break;
                if (0xC0 <= read && read <= 0xDF) {
                    read = bis.read();
                    // ˫�ֽ� (0xC0 - 0xDF)(0x80 - 0xBF), Ҳ������GB������
                    if (0x80 <= read && read <= 0xBF)
                        continue;
                    else
                        break;
                }
                // Ҳ�п��ܳ���, ���Ǽ��ʽ�С
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


