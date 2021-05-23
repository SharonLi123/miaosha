package top.safun.miaosha.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private static Pattern mobile_pattern=Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");

    public static boolean isMobile(String src){
        if(StringUtils.isEmpty(src)){
            return false;
        }

        Matcher matcher=mobile_pattern.matcher(src);
        return matcher.matches();
    }
}
