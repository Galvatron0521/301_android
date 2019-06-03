package com.rilintech.fragment_301_huxike_android.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rilintech on 16/2/18.
 */
public class StringUtil {

    public static boolean isNumber(String str){
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        if(m.matches() ){
            return true;
        }else {
            return false;
        }

    }

    /*
   * 验证号码 手机号 固话均可
   *
   */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;

        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches() ) {
            isValid = true;
        }

        return isValid;

    }

    private static final String USERNAME_PATTERN = "^[A-Za-z0-9_-]{3,15}$";

    //判断是否是下划线，数字，字母
    public static boolean isUser(String str) {

        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();

    }

    //判断邮箱格式
    public static boolean isEmail(String str) {
        String reg = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(str);
        return mat.matches();
    }


}
