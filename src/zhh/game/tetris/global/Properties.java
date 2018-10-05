package zhh.game.tetris.global;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * 属性表类(支持设置字符编码, 支持以原格式保存文件)<br>
 * 修改自JDK1.4.2的java.util.Properties<br>
 * 从属性文件中加载, 及保存属性文件时, 支持根据设置的字符编码写入文件<br>
 * 同时会尽量保持原有的行信息及注释信息<br>
 * 但原有的换行符无法恢复
 * @author fuyunliang
 */
public class Properties {

    /**
     * 串行版本标识
     */
    @SuppressWarnings("unused")
	private static final long serialVersionUID = 1646288880214479727L;

    /**
     * 数据
     */
    @SuppressWarnings("rawtypes")
	protected HashMap data = new HashMap();
    /**
     * 默认数据
     */
    protected Properties defaults;

    /**
     * 原始数据信息
     */
    @SuppressWarnings("rawtypes")
	protected ArrayList infos = new ArrayList();
    /**
     * 读取文件和写入文件时采用的编码方式
     */
    protected String encoding;

    public Properties() {
        this(null);
    }

    public Properties(Properties defaults) {
        this.defaults = defaults;
        this.encoding = "UTF-8";
    }

    private static final String keyValueSeparators = "=: \t\r\n\f";

    private static final String strictKeyValueSeparators = "=:";

    private static final String specialSaveChars = "=: \t\r\n\f#!";

    private static final String whiteSpaceChars = " \t\r\n\f";

    /**
     * 设置字符编码, 读取文件和写入文件时采用该编码
     * @param encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    /**
     * 获取字符编码, 读取文件和写入文件时采用该编码
     * @return
     */
    public String getEncoding() {
        return encoding;
    }
    /**
     * 设置属性键值对, 在文档的最后增加
     * @param key
     * @param value
     * @return 如果是新增返回null, 如果是替换返回原来的value
     */
    @SuppressWarnings("unchecked")
	public synchronized String setProperty(String key, String value) {
        if(!data.containsKey(key)) {
            infos.add(new Key(key));
        }
        return (String)data.put(key, value);
    }
    /**
     * 增加属性键值对, 在文档的最后增加
     * @param key
     * @param value
     * @return 如果是新增, 返回null, 如果是替换返回原来的value
     */
    public synchronized String addProperty(String key, String value) {
        return setProperty(key, value);
    }
    /**
     * 增加空行, 在文档的最后增加
     */
    @SuppressWarnings("unchecked")
	public synchronized void addLine() {
        infos.add(Line.ONE);
    }
    /**
     * 增加多个空行, 在文档的最后增加
     * @param count
     */
    @SuppressWarnings("unchecked")
	public synchronized void addLine(int count) {
        infos.add(count == 1 ? Line.ONE : new Line(count));
    }
    /**
     * 增加注释, 在文档的最后增加
     * @param comment
     */
    @SuppressWarnings("unchecked")
	public synchronized void addComment(String comment) {
        infos.add(new Comment(comment));
    }
    /**
     * 增加多行注释, 在文档的最后增加
     */
    @SuppressWarnings("unchecked")
	public synchronized void addComment(String[] comments) {
        for(int i = 0; i < comments.length; i++) {
            infos.add(new Comment(comments[i]));
        }
    }
    /**
     * 插入属性键值对, 在目标位置的上一行插入
     * @param pointer 目标位置的key
     * @param key
     * @param value
     * @return 如果是新增, 返回null, 如果是替换返回原来的value
     */
    public synchronized String insertProperty(String pointer,
            String key, String value) {
        return addProperty(pointer, key, value, false);
    }
    /**
     * 插入空行, 在目标位置的上一行插入
     * @param pointer 目标位置的key
     */
    public synchronized void insertLine(String pointer) {
        addLine(pointer, 1, false);
    }
    /**
     * 插入多个空行, 在目标位置的上一行插入
     * @param pointer 目标位置的key
     * @param count
     */
    public synchronized void insertLine(String pointer, int count) {
        addLine(pointer, count, false);
    }
    /**
     * 插入注释, 在目标位置的上一行插入
     * @param pointer 目标位置的key
     * @param comment
     */
    public synchronized void insertComment(String pointer, String comment) {
        addComment(pointer, new String[]{comment}, false);
    }
    /**
     * 插入多行注释, 在目标位置的上一行插入
     * @param pointer 目标位置的key
     * @param comments
     */
    public synchronized void insertComment(String pointer, String[] comments) {
        addComment(pointer, comments, false);
    }
    /**
     * 附加属性键值对, 在目标位置的下一行插入
     * @param pointer 目标位置的key
     * @param key
     * @param value
     * @return
     */
    public synchronized String appendProperty(String pointer,
            String key, String value) {
        return addProperty(pointer, key, value, true);
    }
    /**
     * 附加空行, 在目标位置的下一行插入
     * @param pointer 目标位置的key
     */
    public synchronized void appendLine(String pointer) {
        addLine(pointer, 1, true);
    }
    /**
     * 附加多个空行, 在目标位置的下一行插入
     * @param pointer 目标位置的key
     * @param count
     */
    public synchronized void appendLine(String pointer, int count) {
        addLine(pointer, count, true);
    }
    /**
     * 附加注释, 在目标位置的下一行插入
     * @param pointer 目标位置的key
     * @param comment
     */
    public synchronized void appendComment(String pointer, String comment) {
        addComment(pointer, new String[]{comment}, true);
    }
    /**
     * 附加多行注释, 在目标位置的下一行插入
     * @param pointer 目标位置的key
     * @param comments
     */
    public synchronized void appendComment(String pointer, String[] comments) {
        addComment(pointer, comments, true);
    }
    /**
     * 增加属性键值对
     * @param pointer 目标位置的key
     * @param key
     * @param value
     * @param append 追加标志: true.在目标下一行插入|false.在目标上一行插入
     * @return
     */
    @SuppressWarnings("unchecked")
	private synchronized String addProperty(String pointer,
            String key, String value, boolean append) {
        if(!data.containsKey(pointer)) { // 目标位置不存在, 增加至最后
            return setProperty(key, value);
        }
        if(data.containsKey(key)) { // 不是新增项, 直接返回
            return setProperty(key, value);
        }
        // 查找目标位置
        int index = infos.indexOf(new Key(pointer));
        if(index == -1) { // 目标位置不存在, 增加至最后
            return setProperty(key, value);
        }
        if(append) index++;
        infos.add(index, new Key(key)); // insert至上一个KEY的下面
        return (String)data.put(key, value);
    }
    /**
     * 增加空行
     * @param pointer 目标位置的key
     * @param count
     * @param append 追加标志: true.在目标下一行插入|false.在目标上一行插入
     */
    private synchronized void addLine(String pointer, int count,
            boolean append) {
        if(!data.containsKey(pointer)) { // 目标位置不存在, 增加至最后
            addLine(count);
            return;
        }
        // 查找目标位置
        int index = infos.indexOf(new Key(pointer));
        if(index == -1) { // 目标位置不存在, 增加至最后
            addLine(count);
            return;
        }
        if(append) index++;
        infos.add(index, count == 1 ? Line.ONE : new Line(count));
    }
    /**
     * 增加注释
     * @param pointer 目标位置的key
     * @param comments
     * @param append 追加标志: true.在目标下一行插入|false.在目标上一行插入
     */
    @SuppressWarnings("unchecked")
	private synchronized void addComment(String pointer, String[] comments,
            boolean append) {
        if(!data.containsKey(pointer)) { // 目标位置不存在, 增加至最后
            addComment(comments);
            return;
        }
        // 查找目标位置
        int index = infos.indexOf(new Key(pointer));
        if(index == -1) { // 目标位置不存在, 增加至最后
            addComment(comments);
            return;
        }
        if(append) index++;
        for(int i = comments.length - 1; i >= 0; i--) {
            infos.add(index, new Comment(comments[i]));
        }
    }
    /**
     * 获取上一个key
     * @param pointer 目标位置的key
     * @return
     */
    public synchronized String getPrevKey(String pointer) {
        if(!data.containsKey(pointer)) { // 目标位置不存在, 增加至最后
            return null;
        }
        // 查找目标位置
        int index = infos.indexOf(new Key(pointer));
        if(index == -1) { // 目标位置不存在, 增加至最后
            return null;
        }
        for(index--; index >= 0; index--) { // 从目标位置向上查找
            Object info = infos.get(index);
            if(info instanceof Key) { // 直至找到上一个KEY
                return ((Key)info).getKey();
            }
        }
        return null;
    }
    /**
     * 获取下一个key
     * @param pointer 目标位置的key
     * @return
     */
    public synchronized String getNextKey(String pointer) {
        if(!data.containsKey(pointer)) { // 目标位置不存在, 增加至最后
            return null;
        }
        // 查找目标位置
        int index = infos.indexOf(new Key(pointer));
        if(index == -1) { // 目标位置不存在, 增加至最后
            return null;
        }
        for(index++; index < infos.size(); index++) { // 从目标位置向上查找
            Object info = infos.get(index);
            if(info instanceof Key) { // 直至找到上一个KEY
                return ((Key)info).getKey();
            }
        }
        return null;
    }
    /**
     * 获取属性值
     * @param key
     * @return 属性值
     */
    public String getProperty(String key) {
        Object oval = data.get(key);
        String sval = (oval instanceof String) ? (String)oval : null;
        boolean def = sval == null && defaults != null;
        return def ? defaults.getProperty(key) : sval;
    }
    /**
     * 获取属性值
     * @param key
     * @param defaultValue
     * @return 属性值, 如果为空返回默认值
     */
    public String getProperty(String key, String defaultValue) {
        String val = getProperty(key);
        return (val == null) ? defaultValue : val;
    }
    /**
     * 加载属性文件, 支持根据设置的字符编码读取文件<br>
     * 同时会记录下行信息及注释信息, 用于恢复时保持原格式
     * @param      FileName   the file name.
     * @exception  IOException  if an error occurred when reading from the
     *             input stream.
     * @throws     IllegalArgumentException if the input stream contains a
     *             malformed Unicode escape sequence.
     */
    public synchronized void load(String FileName) throws IOException {
    	load(new FileInputStream(new File(FileName)));
    }
    /**
     * 加载属性文件, 支持根据设置的字符编码读取文件<br>
     * 同时会记录下行信息及注释信息, 用于恢复时保持原格式
     * @param      inStream   the input stream.
     * @exception  IOException  if an error occurred when reading from the
     *             input stream.
     * @throws     IllegalArgumentException if the input stream contains a
     *             malformed Unicode escape sequence.
     */
    @SuppressWarnings("unchecked")
	public synchronized void load(InputStream inStream) throws IOException {

        InputStreamReader inReader = new InputStreamReader(inStream, encoding);
        BufferedReader bffReader = new BufferedReader(inReader);

        while (true) {
            // 获取下一行数据
            String line = bffReader.readLine();
            if (line == null)
                return;

            if (line.trim().length() == 0) {
                addLine();
                continue;
            }

            // 查找Key的开始位置
            int len = line.length();
            int keyStart;
            for (keyStart=0; keyStart<len; keyStart++)
                if (whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1)
                    break;

            // 忽略空白行
            if (keyStart == len){
                addLine();
                continue;
            }

            char firstChar = line.charAt(keyStart);
            // 注释行, 记录下整行信息
            if ((firstChar == '#') || (firstChar == '!')) {
                addComment(line);
                continue;
            }

            // 如果一行以\结尾, 表示换行, 需要继续读取下一行
            while (continueLine(line)) {
                String nextLine = bffReader.readLine();
                if (nextLine == null)
                    nextLine = "";
                String loppedLine = line.substring(0, len-1);
                // Advance beyond whitespace on new line
                int startIndex;
                for (startIndex=0; startIndex<nextLine.length(); startIndex++) {
                    char currentChar = nextLine.charAt(startIndex);
                    if (whiteSpaceChars.indexOf(currentChar) == -1)
                        break;
                }
                nextLine = nextLine.substring(startIndex,nextLine.length());
                line = new String(loppedLine+nextLine);
                len = line.length();
            }

            // 查找Key与Value之间的分隔符
            int separatorIndex;
            for (separatorIndex=keyStart; separatorIndex<len; separatorIndex++) {
                char currentChar = line.charAt(separatorIndex);
                if (currentChar == '\\')
                    separatorIndex++;
                else if (keyValueSeparators.indexOf(currentChar) != -1)
                    break;
            }

            // 跳过Key后面的空白字符
            int valueIndex;
            for (valueIndex=separatorIndex; valueIndex<len; valueIndex++)
                if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
                    break;

            // 跳过一个Key与Value之间的分隔符
            if (valueIndex < len) {
                char currentChar = line.charAt(valueIndex);
                if (strictKeyValueSeparators.indexOf(currentChar) != -1)
                    valueIndex++;
            }

            // 跳过分隔符后的空白字符
            while (valueIndex < len) {
                char currentChar = line.charAt(valueIndex);
                if (whiteSpaceChars.indexOf(currentChar) == -1)
                    break;
                valueIndex++;
            }

            String key = line.substring(keyStart, separatorIndex);
            String value = "";
            String keyData = line;
            if(separatorIndex < len) {
                value = line.substring(valueIndex, len);
                keyData = line.substring(0, valueIndex);
            }

            // Convert then store key and value
            key = loadConvert(key);
            value = loadConvert(value);

            data.put(key, value);
            infos.add(new Key(key, keyData));
        }
    }

    private boolean continueLine(String line) {
        int slashCount = 0;
        int index = line.length() - 1;
        while ((index >= 0) && (line.charAt(index--) == '\\'))
            slashCount++;
        return (slashCount % 2 == 1);
    }

    private String loadConvert(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x=0; x<len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value=0;
                    for (int i=0; i<4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0': case '1': case '2': case '3': case '4':
                            case '5': case '6': case '7': case '8': case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a': case 'b': case 'c':
                            case 'd': case 'e': case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A': case 'B': case 'C':
                            case 'D': case 'E': case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed \\uxxxx encoding.");
                        }
                    }
                    outBuffer.append((char)value);
                } else {
                    if (aChar == 't') aChar = '\t';
                    else if (aChar == 'r') aChar = '\r';
                    else if (aChar == 'n') aChar = '\n';
                    else if (aChar == 'f') aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    private String saveConvert(String theString, boolean escapeSpace) {
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len*2);

        for(int x=0; x<len; x++) {
            char aChar = theString.charAt(x);
            switch(aChar) {
                case ' ':
                    if (x == 0 || escapeSpace) 
                        outBuffer.append('\\');

                    outBuffer.append(' ');
                    break;
                case '\\':outBuffer.append('\\'); outBuffer.append('\\');
                        break;
                case '\t':outBuffer.append('\\'); outBuffer.append('t');
                        break;
                case '\n':outBuffer.append('\\'); outBuffer.append('n');
                        break;
                case '\r':outBuffer.append('\\'); outBuffer.append('r');
                        break;
                case '\f':outBuffer.append('\\'); outBuffer.append('f');
                        break;
                default:
                    if (specialSaveChars.indexOf(aChar) != -1)
                        outBuffer.append('\\');
                    outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * 保存属性文件, 支持根据设置的字符编码写入文件<br>
     * 同时会尽量保持原有的行信息及注释信息<br>
     * 但原有的换行符无法恢复
     * @param      fileName the file name.
     * @exception  IOException if writing this property list to the specified
     *             output stream throws an <tt>IOException</tt>.
     * @exception  ClassCastException  if this <code>Properties</code> object
     *             contains any keys or values that are not <code>Strings</code>.
     * @exception  NullPointerException  if <code>out</code> is null.
     */
    public synchronized void store(String fileName) throws IOException {
        store(new FileOutputStream(new File(fileName)), null);
    }
    /**
     * 保存属性文件, 支持根据设置的字符编码写入文件<br>
     * 同时会尽量保持原有的行信息及注释信息<br>
     * 但原有的换行符无法恢复
     * @param      out    an output stream.
     * @exception  IOException if writing this property list to the specified
     *             output stream throws an <tt>IOException</tt>.
     * @exception  ClassCastException  if this <code>Properties</code> object
     *             contains any keys or values that are not <code>Strings</code>.
     * @exception  NullPointerException  if <code>out</code> is null.
     */
    public synchronized void store(OutputStream out) throws IOException {
        store(out, null);
    }

    /**
     * 保存属性文件, 支持根据设置的字符编码写入文件<br>
     * 同时会尽量保持原有的行信息及注释信息<br>
     * 但原有的换行符无法恢复
     * @param      fileName the file name.
     * @param      header   a description of the property list.
     * @exception  IOException if writing this property list to the specified
     *             output stream throws an <tt>IOException</tt>.
     * @exception  ClassCastException  if this <code>Properties</code> object
     *             contains any keys or values that are not <code>Strings</code>.
     * @exception  NullPointerException  if <code>out</code> is null.
     */
    public synchronized void store(String fileName, String header)
    		throws IOException {
        store(new FileOutputStream(new File(fileName)), null);
    }

    /**
     * 保存属性文件, 支持根据设置的字符编码写入文件<br>
     * 同时会尽量保持原有的行信息及注释信息<br>
     * 但原有的换行符无法恢复
     * @param      out    an output stream.
     * @param      header a description of the property list.
     * @exception  IOException if writing this property list to the specified
     *             output stream throws an <tt>IOException</tt>.
     * @exception  ClassCastException  if this <code>Properties</code> object
     *             contains any keys or values that are not <code>Strings</code>.
     * @exception  NullPointerException  if <code>out</code> is null.
     */
    public synchronized void store(OutputStream out, String header)
            throws IOException {

        BufferedWriter awriter;
        awriter = new BufferedWriter(new OutputStreamWriter(out, encoding));
        if (header != null)
            writeln(awriter, "#" + header);
        Iterator iterator = infos.iterator();
        while(iterator.hasNext()) {
            Object info = iterator.next();
            if(info instanceof Line) {
                Line line = (Line)info;
                for(int i = 0; i < line.getCount(); i++) {
                    awriter.newLine();
                }
            } else if(info instanceof Comment) {
                Comment comment = (Comment)info;
                writeln(awriter, comment.getComment());
            } else if(info instanceof Key) {
                Key k = (Key)info;
                String key = k.getKey();
                String keyData = k.getOriginal();
                String val = getProperty(k.getKey());
                keyData = keyData.replaceFirst(key, saveConvert(key, true));

                // No need to escape embedded and trailing spaces for value, 
                // hence pass false to flag.
                val = saveConvert(val, false);
                writeln(awriter, keyData + val);
            }
        }
        awriter.flush();
    }

    private static void writeln(BufferedWriter bw, String s)
            throws IOException {

        bw.write(s);
        bw.newLine();
    }
    /**
     * 空行
     */
    private static class Line {
        /**
         * 一个空行
         */
        public static final Line ONE = new Line();

        /**
         * 空行行数
         */
        private final int count;

        public Line() {
            this.count = 1;
        }
        public Line(int count) {
            if(count <= 0) throw new IllegalArgumentException("count <= 0");
            this.count = count;
        }

        /**
         * 空行行数
         * @return
         */
        public int getCount() {
            return count;
        }
        
    }
    /**
     * 注释
     */
    private static class Comment {
        /**
         * 判断是否以#或!开头的正则表达式
         */
        private static final Pattern PTN = Pattern.compile("^[ ]*[#!]");
        /**
         * 注释信息
         */
        private final String comment;

        public Comment(String comment) {
            this.comment = comment;
        }
        /**
         * 注释信息
         * @return
         */
        public String getComment() {
            if(!PTN.matcher(comment).find())
                return "# " + comment;
            return comment;
        }
    }
    /**
     * 关键字
     */
    private static class Key {
        /**
         * 关键字
         */
        private final String key;
        /**
         * 关键字原始信息
         */
        private final String original;
        public Key(String key, String original) {
            if(key == null) throw new NullPointerException("key is null");
            this.key = key;
            this.original = original;
        }
        public Key(String key) {
            this(key, null);
        }
        /**
         * 关键字
         * @return
         */
        public String getKey() {
            return key;
        }
        /**
         * 关键字原始信息
         * @return
         */
        public String getOriginal() {
            if(original == null) 
                return key + " = ";
            return original;
        }
        public boolean equals(Object obj) {
            if(obj instanceof Key) {
                return key.equals(((Key)obj).getKey());
            }
            return super.equals(obj);
        }
        public int hashCode() {
            return key.hashCode();
        }
    }
}


