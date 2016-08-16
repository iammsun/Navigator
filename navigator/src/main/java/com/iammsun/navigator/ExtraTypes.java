package com.iammsun.navigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunmeng on 16/8/15.
 */
public class ExtraTypes {
    public static final int UNKNOWN = -1;
    public static final int STRING = 0;
    public static final int INT = 1;
    public static final int LONG = 2;
    public static final int BOOL = 3;
    public static final int SHORT = 4;
    public static final int FLOAT = 5;
    public static final int DOUBLE = 6;
    public static final int BYTE = 7;
    public static final int CHAR = 8;
    public static final int DATA = 9;
    private String[] stringExtra;
    private String[] intExtra;
    private String[] longExtra;
    private String[] booleanExtra;
    private String[] shortExtra;
    private String[] floatExtra;
    private String[] doubleExtra;
    private String[] byteExtra;
    private String[] charExtra;
    private String[] dataExtra;

    public void setStringExtra(String[] stringExtra) {
        this.stringExtra = stringExtra;
    }

    public void setIntExtra(String[] intExtra) {
        this.intExtra = intExtra;
    }

    public void setLongExtra(String[] longExtra) {
        this.longExtra = longExtra;
    }

    public void setBooleanExtra(String[] booleanExtra) {
        this.booleanExtra = booleanExtra;
    }

    public void setShortExtra(String[] shortExtra) {
        this.shortExtra = shortExtra;
    }

    public void setFloatExtra(String[] floatExtra) {
        this.floatExtra = floatExtra;
    }

    public void setDoubleExtra(String[] doubleExtra) {
        this.doubleExtra = doubleExtra;
    }

    public void setByteExtra(String[] byteExtra) {
        this.byteExtra = byteExtra;
    }

    public void setCharExtra(String[] charExtra) {
        this.charExtra = charExtra;
    }

    public void setDataExtra(String[] dataExtra) {
        this.dataExtra = dataExtra;
    }

    public List<String> getExtras() {
        List<String> extras = new ArrayList<>();
        add2Array(intExtra, extras);
        add2Array(longExtra, extras);
        add2Array(booleanExtra, extras);
        add2Array(shortExtra, extras);
        add2Array(floatExtra, extras);
        add2Array(doubleExtra, extras);
        add2Array(byteExtra, extras);
        add2Array(charExtra, extras);
        add2Array(stringExtra, extras);
        add2Array(dataExtra, extras);
        return extras;
    }

    public int getType(String name) {
        if (arrayContain(intExtra, name)) {
            return INT;
        }
        if (arrayContain(longExtra, name)) {
            return LONG;
        }
        if (arrayContain(booleanExtra, name)) {
            return BOOL;
        }
        if (arrayContain(shortExtra, name)) {
            return SHORT;
        }
        if (arrayContain(floatExtra, name)) {
            return FLOAT;
        }
        if (arrayContain(doubleExtra, name)) {
            return DOUBLE;
        }
        if (arrayContain(byteExtra, name)) {
            return BYTE;
        }
        if (arrayContain(charExtra, name)) {
            return CHAR;
        }
        if (arrayContain(stringExtra, name)) {
            return STRING;
        }
        if (arrayContain(dataExtra, name)) {
            return DATA;
        }
        return UNKNOWN;
    }

    private static boolean arrayContain(String[] array, String value) {
        if (array == null) {
            return false;
        }
        for (String s : array) {
            if (s.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private static void add2Array(String[] extras, List<String> array) {
        if (extras == null || array == null) {
            return;
        }
        for (String s : extras) {
            array.add(s);
        }
    }
}
