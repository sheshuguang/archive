package com.yapu.system.util;

import sun.misc.BASE64Decoder;

/**
 * Created with IntelliJ IDEA.
 * User: ssg
 * Date: 12-9-10
 * Time: 上午12:15
 * To change this template use File | Settings | File Templates.
 */
public class Base64Utils {
    public static String getBASE64(String s) {
        if (s == null) return null;
        return (new sun.misc.BASE64Encoder()).encode( s.getBytes() );
    }
    public static String getFromBASE64(String s) {
        if (s == null) return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }


}


