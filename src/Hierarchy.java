import java.util.Collection;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.lang.reflect.Field;

// 
// Decompiled by Procyon v0.5.30
// 

public class Hierarchy
{
    private static final boolean UNICODE = true;
    private static final int SCREEN_WIDTH = 1200;
    private static final char BBVD;
    private static final char BBVL;
    private static final char BBHD;
    private static final char BBHL;
    private static final char BCRDD;
    private static final char BCRUD;
    private static final char BCLDD;
    private static final char BCLUD;
    private static final char BTRDL;
    private static final char BTLDL;
    private static final char BTUDL;
    private static final char BTDLL;
    private static final char CONTROL_LIMIT = ' ';
    private static final char PRINTABLE_LIMIT = '~';
    private static final char[] HEX_DIGITS;
    
    static {
        BBVD = '\u2551';
        BBVL = '\u2502';
        BBHD = '\u2550';
        BBHL = '\u2500';
        BCRDD = '\u2554';
        BCRUD = '\u255a';
        BCLDD = '\u2557';
        BCLUD = '\u255d';
        BTRDL = '\u255f';
        BTLDL = '\u2562';
        BTUDL = '\u2567';
        BTDLL = '\u252c';
        HEX_DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    }
    
    private static ArrayList<String> buildClassRow(final int maxlen, final int contentwidth, final int keywidth, final Object o, final Field f) throws IllegalArgumentException, IllegalAccessException {
        final ArrayList<String> s = new ArrayList<String>();
        ArrayList<String> a;
        if (Modifier.isStatic(f.getModifiers())) {
            a = new ArrayList<String>();
            a.add("<ST>");
        }
        else {
            a = itemRepr(f.get(o), maxlen - 2);
        }
        s.add(String.valueOf(Hierarchy.BBVD) + " " + pad(f.getName(), keywidth) + " " + Hierarchy.BBVL + " " + padtrim(a.get(0), contentwidth - keywidth - 3) + " " + Hierarchy.BBVD);
        for (final String l : a.subList(1, a.size())) {
            s.add(String.valueOf(Hierarchy.BBVD) + " " + rep(" ", keywidth) + " " + Hierarchy.BBVL + " " + padtrim(l, contentwidth - keywidth - 3) + " " + Hierarchy.BBVD);
        }
        return s;
    }
    
    private static ArrayList<String> buildDictFooter(final int contentwidth, final int keywidth) {
        final ArrayList<String> s = new ArrayList<String>();
        s.add(String.valueOf(Hierarchy.BCRUD) + rep(Hierarchy.BBHD, keywidth + 2) + Hierarchy.BTUDL + rep(Hierarchy.BBHD, contentwidth - keywidth - 1) + Hierarchy.BCLUD);
        return s;
    }
    
    private static ArrayList<String> buildDictHeader(final int contentwidth, final int keywidth, final String classname) {
        final ArrayList<String> s = new ArrayList<String>();
        s.add(String.valueOf(Hierarchy.BCRDD) + rep(Hierarchy.BBHD, contentwidth + 2) + Hierarchy.BCLDD);
        s.add(String.valueOf(Hierarchy.BBVD) + " " + pad(classname, contentwidth) + " " + Hierarchy.BBVD);
        s.add(String.valueOf(Hierarchy.BTRDL) + rep(Hierarchy.BBHL, keywidth + 2) + Hierarchy.BTDLL + rep(Hierarchy.BBHL, contentwidth - keywidth - 1) + Hierarchy.BTLDL);
        return s;
    }
    
    private static ArrayList<String> buildMapRow(final int maxlen, final int contentwidth, final int keywidth, final Object o, final Map.Entry f) throws IllegalArgumentException, IllegalAccessException {
        final ArrayList<String> s = new ArrayList<String>();
        final ArrayList<String> a = itemRepr(f.getValue(), maxlen - 2);
        final ArrayList<String> b = itemRepr(f.getKey(), keywidth);
        for (int i = 0; i < Math.max(a.size(), b.size()); ++i) {
            String v = "";
            String k = "";
            if (i < a.size()) {
                v = a.get(i);
            }
            if (i < b.size()) {
                k = b.get(i);
            }
            s.add(String.valueOf(Hierarchy.BBVD) + " " + pad(k, keywidth) + " " + Hierarchy.BBVL + " " + padtrim(v, contentwidth - keywidth - 3) + " " + Hierarchy.BBVD);
        }
        return s;
    }
    
    private static ArrayList<String> buildTableFooter(final int contentwidth) {
        final ArrayList<String> s = new ArrayList<String>();
        s.add(String.valueOf(Hierarchy.BCRUD) + rep(Hierarchy.BBHD, contentwidth + 2) + Hierarchy.BCLUD);
        return s;
    }
    
    private static ArrayList<String> buildTableHeader(final int contentwidth, final String classname) {
        final ArrayList<String> s = new ArrayList<String>();
        s.add(String.valueOf(Hierarchy.BCRDD) + rep(Hierarchy.BBHD, contentwidth + 2) + Hierarchy.BCLDD);
        s.add(String.valueOf(Hierarchy.BBVD) + " " + padtrim(classname, contentwidth) + " " + Hierarchy.BBVD);
        s.add(String.valueOf(Hierarchy.BTRDL) + rep(Hierarchy.BBHL, contentwidth + 2) + Hierarchy.BTLDL);
        return s;
    }
    
    private static ArrayList<String> buildTableRow(final int maxlen, final int contentwidth, final Object i) throws IllegalArgumentException, IllegalAccessException {
        final ArrayList<String> s = new ArrayList<String>();
        for (final String l : itemRepr(i, maxlen - 2)) {
            s.add(String.valueOf(Hierarchy.BBVD) + " " + pad(l, contentwidth) + " " + Hierarchy.BBVD);
        }
        return s;
    }
    
    public static String deepRepr(final Object o) {
        try {
            return String.join("\n", itemRepr(o, 1200));
        }
        catch (IllegalArgumentException | IllegalAccessException ex2) {
        	ex2.printStackTrace();
            return null;
        }
    }
    
    private static int getClassContentWidth(final Object o, final Field[] f, final int keywidth, final int maxlen, final String classname) throws IllegalAccessException {
        int longest = 0;
        for (final Field i : f) {
            if (Modifier.isStatic(i.getModifiers())) {
                longest = Math.max(longest, 4);
            }
            else {
                longest = Math.max(longest, maxlen(itemRepr(i.get(o), maxlen - 7)));
            }
        }
        longest += keywidth;
        longest += 3;
        longest = Math.max(longest, classname.length());
        return longest;
    }
    
    private static int getClassKeyWidth(final Field[] f) {
        int longest = 0;
        for (final Field i : f) {
            longest = Math.max(longest, i.getName().length());
        }
        return longest;
    }
    
    private static int getMapContentWidth(final Object o, final Set<Map.Entry> f, final int keywidth, final int maxlen, final String classname) throws IllegalAccessException {
        int longest = 0;
        for (final Map.Entry i : f) {
            longest = Math.max(longest, maxlen(itemRepr(i.getValue(), maxlen - 7)));
        }
        longest += keywidth;
        longest += 3;
        longest = Math.max(longest, classname.length());
        return longest;
    }
    
    private static int getMapKeyWidth(final Set<Map.Entry> f, final int maxlen) throws IllegalArgumentException, IllegalAccessException {
        int longest = 0;
        for (final Map.Entry i : f) {
            longest = Math.max(longest, maxlen(itemRepr(i.getKey(), maxlen / 3)));
        }
        return longest;
    }
    
    private static int getTableContentWidth(final Object[] o, final int maxlen, final String classname) throws IllegalAccessException {
        int longest = 0;
        for (final Object i : o) {
            longest = Math.max(longest, maxlen(itemRepr(i, maxlen - 4)));
        }
        longest = Math.max(longest, classname.length());
        return longest;
    }
    
    private static ArrayList<String> itemRepr(final Object o, final int maxlen) throws IllegalArgumentException, IllegalAccessException {
        ArrayList<String> s = new ArrayList<String>();
        if (o == null) {
            s = nullRepr();
        }
        else if (o.getClass().isArray() && o.getClass().getComponentType().isPrimitive()) {
            final String name;
            switch (name = o.getClass().getComponentType().getName()) {
                case "double": {
                    s = reprArray(ToObject.toObject((double[])o), maxlen);
                    break;
                }
                case "int": {
                    s = reprArray(ToObject.toObject((int[])o), maxlen);
                    break;
                }
                case "byte": {
                    s = reprArray(ToObject.toObject((byte[])o), maxlen);
                    break;
                }
                case "char": {
                    s = reprArray(ToObject.toObject((char[])o), maxlen);
                    break;
                }
                case "long": {
                    s = reprArray(ToObject.toObject((long[])o), maxlen);
                    break;
                }
                case "boolean": {
                    s = reprArray(ToObject.toObject((boolean[])o), maxlen);
                    break;
                }
                case "float": {
                    s = reprArray(ToObject.toObject((float[])o), maxlen);
                    break;
                }
                case "short": {
                    s = reprArray(ToObject.toObject((short[])o), maxlen);
                    break;
                }
                default:
                    break;
            }
        }
        else if (o.getClass().isArray()) {
            s = reprArray((Object[])o, maxlen);
        }
        else if (o instanceof String) {
            s = reprString(o, maxlen);
        }
        else if (o instanceof Number) {
            s = reprNumber(o, maxlen);
        }
        else if (o instanceof Collection) {
            s = reprList(o, maxlen);
        }
        else if (o instanceof Map) {
            s = reprMap(o, maxlen);
        }
        else {
            s = reprClass(o, maxlen);
        }
        if (maxlen(s) > maxlen) {
            s.clear();
            s.add("<!>");
        }
        return s;
    }
    
    private static int maxlen(final ArrayList<String> sa) {
        int maxlen = 0;
        for (final String s : sa) {
            maxlen = Math.max(maxlen, s.length());
        }
        return maxlen;
    }
    
    private static ArrayList<String> nullRepr() {
        final ArrayList<String> l = new ArrayList<String>();
        l.add("<NUL>");
        return l;
    }
    
    private static String pad(final String s, final int len) {
        if (s.length() > len) {
            return s;
        }
        return String.valueOf(s) + rep(" ", len - s.length());
    }
    
    private static String padtrim(final String s, final int len) {
        return pad(trim(s, len), len);
    }
    
    private static String rep(final char s, final int n) {
        return rep(new StringBuilder().append(s).toString(), n);
    }
    
    private static String rep(final String s, final int n) {
        return new String(new char[n]).replace("\u0000", s);
    }
    
    private static ArrayList<String> reprArray(final Object[] o, final int maxlen) throws IllegalArgumentException, IllegalAccessException {
        final ArrayList<String> s = new ArrayList<String>();
        String classname = o.getClass().getSimpleName();
        classname = String.valueOf(classname.substring(0, classname.length() - 1)) + o.length + ']';
        final int contentwidth = getTableContentWidth(o, maxlen, classname);
        s.addAll(buildTableHeader(contentwidth, classname));
        for (final Object i : o) {
            s.addAll(buildTableRow(maxlen, contentwidth, i));
        }
        s.addAll(buildTableFooter(contentwidth));
        return s;
    }
    
    private static ArrayList<String> reprClass(final Object o, final int maxlen) throws IllegalArgumentException, IllegalAccessException {
        final Field[] fields = o.getClass().getFields();
        final ArrayList<String> s = new ArrayList<String>();
        final String classname = o.getClass().getSimpleName();
        final int keywidth = getClassKeyWidth(fields);
        final int contentwidth = getClassContentWidth(o, fields, keywidth, maxlen, classname);
        s.addAll(buildDictHeader(contentwidth, keywidth, classname));
        Field[] array;
        for (int length = (array = fields).length, i = 0; i < length; ++i) {
            final Field f = array[i];
            s.addAll(buildClassRow(maxlen, contentwidth, keywidth, o, f));
        }
        s.addAll(buildDictFooter(contentwidth, keywidth));
        return s;
    }
    
    private static ArrayList<String> reprNumber(final Object o, final int maxlen) {
        final ArrayList<String> l = new ArrayList<String>();
        l.add(((Number)o).toString());
        return l;
    }
    
    private static ArrayList<String> reprList(final Object o, final int maxlen) throws IllegalArgumentException, IllegalAccessException {
        final Object[] arr = ((Collection)o).toArray();
        final ArrayList<String> s = new ArrayList<String>();
        String type;
        if (((Collection)o).size() == 0) {
            type = "?";
        }
        else {
            type = ((Collection)o).iterator().next().getClass().getSimpleName();
        }
        final String classname = String.valueOf(o.getClass().getSimpleName()) + '<' + type + '>';
        final int contentwidth = getTableContentWidth(arr, maxlen, classname);
        s.addAll(buildTableHeader(contentwidth, classname));
        Object[] array;
        for (int length = (array = arr).length, j = 0; j < length; ++j) {
            final Object i = array[j];
            s.addAll(buildTableRow(maxlen, contentwidth, i));
        }
        s.addAll(buildTableFooter(contentwidth));
        return s;
    }
    
    private static ArrayList<String> reprMap(final Object o, final int maxlen) throws IllegalArgumentException, IllegalAccessException {
        final Set<Map.Entry> entries = (Set<Map.Entry>)((Map)o).entrySet();
        final ArrayList<String> s = new ArrayList<String>();
        final String classname = o.getClass().getSimpleName();
        final int keywidth = getMapKeyWidth(entries, maxlen);
        final int contentwidth = getMapContentWidth(o, entries, keywidth, maxlen, classname);
        s.addAll(buildDictHeader(contentwidth, keywidth, classname));
        for (final Map.Entry f : entries) {
            s.addAll(buildMapRow(maxlen, contentwidth, keywidth, o, f));
        }
        s.addAll(buildDictFooter(contentwidth, keywidth));
        return s;
    }
    
    private static ArrayList<String> reprString(final Object o, final int maxlen) {
        final ArrayList<String> l = new ArrayList<String>();
        if (((String)o).length() < maxlen - 1) {
            l.add(toPrintableRepresentation((String)o));
        }
        else {
            l.add(String.valueOf(trim(toPrintableRepresentation((String)o), maxlen - 3)) + "...");
        }
        return l;
    }
    
    public static String toPrintableRepresentation(final String source) {
        if (source == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        final int limit = source.length();
        char[] hexbuf = null;
        int pointer = 0;
        sb.append('\"');
        while (pointer < limit) {
            int ch = source.charAt(pointer++);
            switch (ch) {
                case 0: {
                    sb.append("\\0");
                    continue;
                }
                case 9: {
                    sb.append("\\t");
                    continue;
                }
                case 10: {
                    sb.append("\\n");
                    continue;
                }
                case 13: {
                    sb.append("\\r");
                    continue;
                }
                case 34: {
                    sb.append("\\\"");
                    continue;
                }
                case 92: {
                    sb.append("\\\\");
                    continue;
                }
                default: {
                    if (32 <= ch && ch <= 126) {
                        sb.append((char)ch);
                        continue;
                    }
                    sb.append("\\u");
                    if (hexbuf == null) {
                        hexbuf = new char[4];
                    }
                    for (int offs = 4; offs > 0; hexbuf[--offs] = Hierarchy.HEX_DIGITS[ch & 0xF], ch >>>= 4) {}
                    sb.append(hexbuf, 0, 4);
                    continue;
                }
            }
        }
        return sb.append('\"').toString();
    }
    
    private static String trim(final String s, final int len) {
        return s.substring(0, Math.min(len, s.length()));
    }
}
