package com.komehyo.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MoneyFormatUtils {

    public static String formatChina3(Object obj) {
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");
        if(obj==null){return null;}
        if(obj instanceof  String){
            if(StringUtils.isNotBlank(obj+"") && !"null".equals(obj)){
                BigDecimal b = new BigDecimal(obj+"");
                return replace(numberFormat.format(b));
            }
        }else{
            return replace(numberFormat.format(obj));
        }
        return null;
    }

    private static String replace(String format){
        if(StringUtils.isBlank(format)){
            return format;
        }
        return format.replaceAll("\\.00","");
    }

    public static void main(String[] args) {
        String s = MoneyFormatUtils.formatChina3(new BigDecimal(1110.00));
        System.out.println(s);
    }
}
