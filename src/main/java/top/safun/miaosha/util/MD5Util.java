package top.safun.miaosha.util;

import org.springframework.util.DigestUtils;

public class MD5Util {

    //将给定的字符串加密
    public static String md5(String src){
        return DigestUtils.md5DigestAsHex(src.getBytes());
    }

    private static final String salt="1a2b3c4d";

    public static String inputPassFormPass(String inputPass){
        String  str = ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass,String salt){
        String  str = ""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String salt){
        String formPass= inputPassFormPass(inputPass);
        String dbPass= formPassToDBPass(formPass,salt);
        return dbPass;
    }
}
