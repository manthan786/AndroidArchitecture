package com.architecture.utils;

/**
 * Created by hemraj on 29-12-2014.
 */
public class StringUtils {

    public static final String COMMA = ",";
    public static final String DASH = "-";
    public static final String DOT = ".";
    public static final String EMPTY = "";
    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String SAPCE = " ";
    public static final String SLASH = "/";
    public static final String SPACE = " ";
    public static final String ZERO = "0";

    /**
     *
     * @param str String to be verified
     * @return true if string is not emty
     */
    public static boolean isNotEmpty(String str)
    {
        return (str != null) && (!EMPTY.equals(str));
    }
}
