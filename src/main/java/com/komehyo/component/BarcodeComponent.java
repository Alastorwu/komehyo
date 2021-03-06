package com.komehyo.component;

import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.impl.codabar.CodabarBean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.*;

import static org.krysalis.barcode4j.HumanReadablePlacement.HRP_NONE;

@Component
public class BarcodeComponent {

    @Value("${itext.image.width}")
    private String width;


    /**
     * 生成文件
     *
     * @param msg
     * @param path
     * @return
     */

    public File generateFile(String msg, String path) {
        File file = new File(path);

        try {
            generate(msg, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 生成字节
     *
     * @param msg
     * @return
     */
    public byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }

    /**
     * 生成到流
     *
     * @param msg
     * @param ous
     */
    public void generate(String msg, OutputStream ous) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }

        CodabarBean bean = new CodabarBean();

        // 精细度
        final int dpi = 150;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(Float.valueOf(width) / dpi);

        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setWideFactor(3);
        bean.doQuietZone(false);
        bean.setMsgPosition(HRP_NONE);

        String format = "image/png";
        try {

            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);

            // 生成条形码
            bean.generateBarcode(canvas, msg);

            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   /* public static void main(String[] args) {
        String msg = "123456789";
//        String path = "barcode.png";
        String path = "D:\\OneDrive\\work\\barcode.png";
        //generateFile(msg, path);
        System.out.println("条形码生成=="+generateFile(msg, path,0.17F));

    }*/
}
