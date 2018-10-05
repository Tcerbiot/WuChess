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
 * ���Ա���(֧�������ַ�����, ֧����ԭ��ʽ�����ļ�)<br>
 * �޸���JDK1.4.2��java.util.Properties<br>
 * �������ļ��м���, �����������ļ�ʱ, ֧�ָ������õ��ַ�����д���ļ�<br>
 * ͬʱ�ᾡ������ԭ�е�����Ϣ��ע����Ϣ<br>
 * ��ԭ�еĻ��з��޷��ָ�
 * @author fuyunliang
 */
public class Properties {

    /**
     * ���а汾��ʶ
     */
    @SuppressWarnings("unused")
	private static final long serialVersionUID = 1646288880214479727L;

    /**
     * ����
     */
    @SuppressWarnings("rawtypes")
	protected HashMap data = new HashMap();
    /**
     * Ĭ������
     */
    protected Properties defaults;

    /**
     * ԭʼ������Ϣ
     */
    @SuppressWarnings("rawtypes")
	protected ArrayList infos = new ArrayList();
    /**
     * ��ȡ�ļ���д���ļ�ʱ���õı��뷽ʽ
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
     * �����ַ�����, ��ȡ�ļ���д���ļ�ʱ���øñ���
     * @param encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    /**
     * ��ȡ�ַ�����, ��ȡ�ļ���д���ļ�ʱ���øñ���
     * @return
     */
    public String getEncoding() {
        return encoding;
    }
    /**
     * �������Լ�ֵ��, ���ĵ����������
     * @param key
     * @param value
     * @return �������������null, ������滻����ԭ����value
     */
    @SuppressWarnings("unchecked")
	public synchronized String setProperty(String key, String value) {
        if(!data.containsKey(key)) {
            infos.add(new Key(key));
        }
        return (String)data.put(key, value);
    }
    /**
     * �������Լ�ֵ��, ���ĵ����������
     * @param key
     * @param value
     * @return ���������, ����null, ������滻����ԭ����value
     */
    public synchronized String addProperty(String key, String value) {
        return setProperty(key, value);
    }
    /**
     * ���ӿ���, ���ĵ����������
     */
    @SuppressWarnings("unchecked")
	public synchronized void addLine() {
        infos.add(Line.ONE);
    }
    /**
     * ���Ӷ������, ���ĵ����������
     * @param count
     */
    @SuppressWarnings("unchecked")
	public synchronized void addLine(int count) {
        infos.add(count == 1 ? Line.ONE : new Line(count));
    }
    /**
     * ����ע��, ���ĵ����������
     * @param comment
     */
    @SuppressWarnings("unchecked")
	public synchronized void addComment(String comment) {
        infos.add(new Comment(comment));
    }
    /**
     * ���Ӷ���ע��, ���ĵ����������
     */
    @SuppressWarnings("unchecked")
	public synchronized void addComment(String[] comments) {
        for(int i = 0; i < comments.length; i++) {
            infos.add(new Comment(comments[i]));
        }
    }
    /**
     * �������Լ�ֵ��, ��Ŀ��λ�õ���һ�в���
     * @param pointer Ŀ��λ�õ�key
     * @param key
     * @param value
     * @return ���������, ����null, ������滻����ԭ����value
     */
    public synchronized String insertProperty(String pointer,
            String key, String value) {
        return addProperty(pointer, key, value, false);
    }
    /**
     * �������, ��Ŀ��λ�õ���һ�в���
     * @param pointer Ŀ��λ�õ�key
     */
    public synchronized void insertLine(String pointer) {
        addLine(pointer, 1, false);
    }
    /**
     * ����������, ��Ŀ��λ�õ���һ�в���
     * @param pointer Ŀ��λ�õ�key
     * @param count
     */
    public synchronized void insertLine(String pointer, int count) {
        addLine(pointer, count, false);
    }
    /**
     * ����ע��, ��Ŀ��λ�õ���һ�в���
     * @param pointer Ŀ��λ�õ�key
     * @param comment
     */
    public synchronized void insertComment(String pointer, String comment) {
        addComment(pointer, new String[]{comment}, false);
    }
    /**
     * �������ע��, ��Ŀ��λ�õ���һ�в���
     * @param pointer Ŀ��λ�õ�key
     * @param comments
     */
    public synchronized void insertComment(String pointer, String[] comments) {
        addComment(pointer, comments, false);
    }
    /**
     * �������Լ�ֵ��, ��Ŀ��λ�õ���һ�в���
     * @param pointer Ŀ��λ�õ�key
     * @param key
     * @param value
     * @return
     */
    public synchronized String appendProperty(String pointer,
            String key, String value) {
        return addProperty(pointer, key, value, true);
    }
    /**
     * ���ӿ���, ��Ŀ��λ�õ���һ�в���
     * @param pointer Ŀ��λ�õ�key
     */
    public synchronized void appendLine(String pointer) {
        addLine(pointer, 1, true);
    }
    /**
     * ���Ӷ������, ��Ŀ��λ�õ���һ�в���
     * @param pointer Ŀ��λ�õ�key
     * @param count
     */
    public synchronized void appendLine(String pointer, int count) {
        addLine(pointer, count, true);
    }
    /**
     * ����ע��, ��Ŀ��λ�õ���һ�в���
     * @param pointer Ŀ��λ�õ�key
     * @param comment
     */
    public synchronized void appendComment(String pointer, String comment) {
        addComment(pointer, new String[]{comment}, true);
    }
    /**
     * ���Ӷ���ע��, ��Ŀ��λ�õ���һ�в���
     * @param pointer Ŀ��λ�õ�key
     * @param comments
     */
    public synchronized void appendComment(String pointer, String[] comments) {
        addComment(pointer, comments, true);
    }
    /**
     * �������Լ�ֵ��
     * @param pointer Ŀ��λ�õ�key
     * @param key
     * @param value
     * @param append ׷�ӱ�־: true.��Ŀ����һ�в���|false.��Ŀ����һ�в���
     * @return
     */
    @SuppressWarnings("unchecked")
	private synchronized String addProperty(String pointer,
            String key, String value, boolean append) {
        if(!data.containsKey(pointer)) { // Ŀ��λ�ò�����, ���������
            return setProperty(key, value);
        }
        if(data.containsKey(key)) { // ����������, ֱ�ӷ���
            return setProperty(key, value);
        }
        // ����Ŀ��λ��
        int index = infos.indexOf(new Key(pointer));
        if(index == -1) { // Ŀ��λ�ò�����, ���������
            return setProperty(key, value);
        }
        if(append) index++;
        infos.add(index, new Key(key)); // insert����һ��KEY������
        return (String)data.put(key, value);
    }
    /**
     * ���ӿ���
     * @param pointer Ŀ��λ�õ�key
     * @param count
     * @param append ׷�ӱ�־: true.��Ŀ����һ�в���|false.��Ŀ����һ�в���
     */
    private synchronized void addLine(String pointer, int count,
            boolean append) {
        if(!data.containsKey(pointer)) { // Ŀ��λ�ò�����, ���������
            addLine(count);
            return;
        }
        // ����Ŀ��λ��
        int index = infos.indexOf(new Key(pointer));
        if(index == -1) { // Ŀ��λ�ò�����, ���������
            addLine(count);
            return;
        }
        if(append) index++;
        infos.add(index, count == 1 ? Line.ONE : new Line(count));
    }
    /**
     * ����ע��
     * @param pointer Ŀ��λ�õ�key
     * @param comments
     * @param append ׷�ӱ�־: true.��Ŀ����һ�в���|false.��Ŀ����һ�в���
     */
    @SuppressWarnings("unchecked")
	private synchronized void addComment(String pointer, String[] comments,
            boolean append) {
        if(!data.containsKey(pointer)) { // Ŀ��λ�ò�����, ���������
            addComment(comments);
            return;
        }
        // ����Ŀ��λ��
        int index = infos.indexOf(new Key(pointer));
        if(index == -1) { // Ŀ��λ�ò�����, ���������
            addComment(comments);
            return;
        }
        if(append) index++;
        for(int i = comments.length - 1; i >= 0; i--) {
            infos.add(index, new Comment(comments[i]));
        }
    }
    /**
     * ��ȡ��һ��key
     * @param pointer Ŀ��λ�õ�key
     * @return
     */
    public synchronized String getPrevKey(String pointer) {
        if(!data.containsKey(pointer)) { // Ŀ��λ�ò�����, ���������
            return null;
        }
        // ����Ŀ��λ��
        int index = infos.indexOf(new Key(pointer));
        if(index == -1) { // Ŀ��λ�ò�����, ���������
            return null;
        }
        for(index--; index >= 0; index--) { // ��Ŀ��λ�����ϲ���
            Object info = infos.get(index);
            if(info instanceof Key) { // ֱ���ҵ���һ��KEY
                return ((Key)info).getKey();
            }
        }
        return null;
    }
    /**
     * ��ȡ��һ��key
     * @param pointer Ŀ��λ�õ�key
     * @return
     */
    public synchronized String getNextKey(String pointer) {
        if(!data.containsKey(pointer)) { // Ŀ��λ�ò�����, ���������
            return null;
        }
        // ����Ŀ��λ��
        int index = infos.indexOf(new Key(pointer));
        if(index == -1) { // Ŀ��λ�ò�����, ���������
            return null;
        }
        for(index++; index < infos.size(); index++) { // ��Ŀ��λ�����ϲ���
            Object info = infos.get(index);
            if(info instanceof Key) { // ֱ���ҵ���һ��KEY
                return ((Key)info).getKey();
            }
        }
        return null;
    }
    /**
     * ��ȡ����ֵ
     * @param key
     * @return ����ֵ
     */
    public String getProperty(String key) {
        Object oval = data.get(key);
        String sval = (oval instanceof String) ? (String)oval : null;
        boolean def = sval == null && defaults != null;
        return def ? defaults.getProperty(key) : sval;
    }
    /**
     * ��ȡ����ֵ
     * @param key
     * @param defaultValue
     * @return ����ֵ, ���Ϊ�շ���Ĭ��ֵ
     */
    public String getProperty(String key, String defaultValue) {
        String val = getProperty(key);
        return (val == null) ? defaultValue : val;
    }
    /**
     * ���������ļ�, ֧�ָ������õ��ַ������ȡ�ļ�<br>
     * ͬʱ���¼������Ϣ��ע����Ϣ, ���ڻָ�ʱ����ԭ��ʽ
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
     * ���������ļ�, ֧�ָ������õ��ַ������ȡ�ļ�<br>
     * ͬʱ���¼������Ϣ��ע����Ϣ, ���ڻָ�ʱ����ԭ��ʽ
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
            // ��ȡ��һ������
            String line = bffReader.readLine();
            if (line == null)
                return;

            if (line.trim().length() == 0) {
                addLine();
                continue;
            }

            // ����Key�Ŀ�ʼλ��
            int len = line.length();
            int keyStart;
            for (keyStart=0; keyStart<len; keyStart++)
                if (whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1)
                    break;

            // ���Կհ���
            if (keyStart == len){
                addLine();
                continue;
            }

            char firstChar = line.charAt(keyStart);
            // ע����, ��¼��������Ϣ
            if ((firstChar == '#') || (firstChar == '!')) {
                addComment(line);
                continue;
            }

            // ���һ����\��β, ��ʾ����, ��Ҫ������ȡ��һ��
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

            // ����Key��Value֮��ķָ���
            int separatorIndex;
            for (separatorIndex=keyStart; separatorIndex<len; separatorIndex++) {
                char currentChar = line.charAt(separatorIndex);
                if (currentChar == '\\')
                    separatorIndex++;
                else if (keyValueSeparators.indexOf(currentChar) != -1)
                    break;
            }

            // ����Key����Ŀհ��ַ�
            int valueIndex;
            for (valueIndex=separatorIndex; valueIndex<len; valueIndex++)
                if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
                    break;

            // ����һ��Key��Value֮��ķָ���
            if (valueIndex < len) {
                char currentChar = line.charAt(valueIndex);
                if (strictKeyValueSeparators.indexOf(currentChar) != -1)
                    valueIndex++;
            }

            // �����ָ�����Ŀհ��ַ�
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
     * ���������ļ�, ֧�ָ������õ��ַ�����д���ļ�<br>
     * ͬʱ�ᾡ������ԭ�е�����Ϣ��ע����Ϣ<br>
     * ��ԭ�еĻ��з��޷��ָ�
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
     * ���������ļ�, ֧�ָ������õ��ַ�����д���ļ�<br>
     * ͬʱ�ᾡ������ԭ�е�����Ϣ��ע����Ϣ<br>
     * ��ԭ�еĻ��з��޷��ָ�
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
     * ���������ļ�, ֧�ָ������õ��ַ�����д���ļ�<br>
     * ͬʱ�ᾡ������ԭ�е�����Ϣ��ע����Ϣ<br>
     * ��ԭ�еĻ��з��޷��ָ�
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
     * ���������ļ�, ֧�ָ������õ��ַ�����д���ļ�<br>
     * ͬʱ�ᾡ������ԭ�е�����Ϣ��ע����Ϣ<br>
     * ��ԭ�еĻ��з��޷��ָ�
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
     * ����
     */
    private static class Line {
        /**
         * һ������
         */
        public static final Line ONE = new Line();

        /**
         * ��������
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
         * ��������
         * @return
         */
        public int getCount() {
            return count;
        }
        
    }
    /**
     * ע��
     */
    private static class Comment {
        /**
         * �ж��Ƿ���#��!��ͷ��������ʽ
         */
        private static final Pattern PTN = Pattern.compile("^[ ]*[#!]");
        /**
         * ע����Ϣ
         */
        private final String comment;

        public Comment(String comment) {
            this.comment = comment;
        }
        /**
         * ע����Ϣ
         * @return
         */
        public String getComment() {
            if(!PTN.matcher(comment).find())
                return "# " + comment;
            return comment;
        }
    }
    /**
     * �ؼ���
     */
    private static class Key {
        /**
         * �ؼ���
         */
        private final String key;
        /**
         * �ؼ���ԭʼ��Ϣ
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
         * �ؼ���
         * @return
         */
        public String getKey() {
            return key;
        }
        /**
         * �ؼ���ԭʼ��Ϣ
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


