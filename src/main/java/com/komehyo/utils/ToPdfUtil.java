package com.komehyo.utils;


import com.spire.xls.*;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class ToPdfUtil {
    public static void main(String[] args) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("static/标签样例.xls");
        InputStream inputStream =classPathResource.getInputStream();
        //加载Excel文档
        Workbook wb = new Workbook();
        wb.loadFromStream(inputStream);

        //获取第2个工作表
        Worksheet sheet = wb.getWorksheets().get(0);

        sheet.saveToImage("D:\\OneDrive\\work\\KOMEHYO\\ToImg.png");
    }
}
