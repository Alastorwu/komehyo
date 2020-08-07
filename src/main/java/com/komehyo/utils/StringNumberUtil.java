package com.komehyo.utils;

import org.apache.commons.lang3.StringUtils;

public class StringNumberUtil {


    public static String replaceNum(String s) {
        if(StringUtils.isBlank(s)){
            return s;
        }
        s = s.replaceAll("１","1");
        s = s.replaceAll("２","2");
        s = s.replaceAll("３","3");
        s = s.replaceAll("４","4");
        s = s.replaceAll("５","5");
        s = s.replaceAll("６","6");
        s = s.replaceAll("７","7");
        s = s.replaceAll("８","8");
        s = s.replaceAll("９","9");
        s = s.replaceAll("０","0");
        return s;
    }


}
