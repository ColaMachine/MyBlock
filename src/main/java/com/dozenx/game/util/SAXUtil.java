package com.dozenx.game.util;

import org.xml.sax.SAXException;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 15:40 2019/10/11
 * @Modified By:
 */


public final class SAXUtil {

    /**
     * Parses an integer from a string, if the string is null returns
     * def.
     *
     * @param i   The string to parse
     * @param def The default value if the string is null
     * @return the parsed value or def
     * @throws SAXException
     */
    public static int parseInt(String i, int def) throws SAXException {
        if (i == null)
            return def;
        else {
            try {
                return Integer.parseInt(i);
            } catch (NumberFormatException ex) {
                throw new SAXException("Expected an integer, got '" + i + "'");
            }
        }
    }

    public static int parseInt(String i) throws SAXException {
        if (i == null)
            throw new SAXException("Expected an integer");
        else {
            try {
                return Integer.parseInt(i);
            } catch (NumberFormatException ex) {
                throw new SAXException("Expected an integer, got '" + i + "'");
            }
        }
    }

    public static float parseFloat(String f, float def) throws SAXException {
        if (f == null)
            return def;
        else {
            try {
                return Float.parseFloat(f);
            } catch (NumberFormatException ex) {
                throw new SAXException("Expected a decimal, got '" + f + "'");
            }
        }
    }

    public static float parseFloat(String f) throws SAXException {
        if (f == null)
            throw new SAXException("Expected a decimal");
        else {
            try {
                return Float.parseFloat(f);
            } catch (NumberFormatException ex) {
                throw new SAXException("Expected a decimal, got '" + f + "'");
            }
        }
    }

    public static boolean parseBool(String bool, boolean def) throws SAXException {
        if (bool == null || bool.equals(""))
            return def;
        else
            return Boolean.valueOf(bool);
        //else
        //else
        //    throw new SAXException("Expected a boolean, got'"+bool+"'");
    }

    public static String parseString(String str, String def) {
        if (str == null)
            return def;
        else
            return str;
    }

    public static String parseString(String str) throws SAXException {
        if (str == null)
            throw new SAXException("Expected a string");
        else
            return str;
    }

//    public static Vector3f parseVector3(Attributes attribs) throws SAXException {
//        float x = parseFloat(attribs.getValue("x"));
//        float y = parseFloat(attribs.getValue("y"));
//        float z = parseFloat(attribs.getValue("z"));
//        return new Vector3f(x, y, z);
//    }
//
//    public static ColorRGBA parseColor(Attributes attribs) throws SAXException {
//        float r = parseFloat(attribs.getValue("r"));
//        float g = parseFloat(attribs.getValue("g"));
//        float b = parseFloat(attribs.getValue("b"));
//        return new ColorRGBA(r, g, b, 1f);
//    }

}

