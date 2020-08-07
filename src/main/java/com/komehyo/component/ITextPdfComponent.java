package com.komehyo.component;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.komehyo.dao.entity.GoodsWithBLOBs;
import com.komehyo.utils.MoneyFormatUtils;
import com.komehyo.utils.StringLengthUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ITextPdfComponent {



    @Value("${itext.pdf.widthA}")
    private String widthA;

    @Value("${itext.pdf.widthB}")
    private String widthB;

    @Value("${itext.pdf.widthC}")
    private String widthC;

    @Value("${itext.pdf.widthD}")
    private String widthD;

    @Value("${itext.pdf.widthE}")
    private String widthE;

    @Value("${file.pdf.path}")
    private String pdfPath;

    @Value("${file.barcode.path}")
    private String barcodePath;

    @Resource
    private BarcodeComponent barcodeComponent;


    private String generateBarCode(String code) {
        File file = new File(barcodePath);
        if(!file.isDirectory()){
            boolean mkdirs = file.mkdirs();
            if (mkdirs)
                log.info("路径【"+barcodePath+"】不存在，新建文件夹");
        }
        if(!isInteger(code)){
            log.warn("code:【{}】包含非数字！无法打印！",code);
            code = "0";
        }
        log.info("条形码生成=="+ barcodeComponent.generateFile(code, barcodePath+"barcode.png"));
        return barcodePath+"barcode.png";
    }
    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public String generatePrintPdf(GoodsWithBLOBs param)
            throws IOException, DocumentException {

        File file=new File(pdfPath);
        if(!file.isDirectory()){
            boolean mkdirs = file.mkdirs();
            if (mkdirs)
                log.info("路径【"+pdfPath+"】不存在，新建文件夹");
        }
        String retuenPath = pdfPath + "/" + param.getCommodityCode() + ".pdf";

        // 1.新建document对象
        Document document = new Document(new RectangleReadOnly(370.0F, 660), 10F, 0F, 16F, 0F);
        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(retuenPath));

        // 3.打开文档
        document.open();


        float[] columnWidths = { Float.valueOf(widthA)
                ,Float.valueOf(widthB)
                ,Float.valueOf(widthC)
                ,Float.valueOf(widthD)
                ,Float.valueOf(widthE) };
//        BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
//        BaseFont bfChinese = BaseFont.createFont("C:\\Windows\\Fonts\\SIMHEI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//        BaseFont bfCode = BaseFont.createFont("C:\\Windows\\Fonts\\SIMHEI.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
       BaseFont bfChinese = BaseFont.createFont("C:\\Windows\\Fonts\\msyh.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        BaseFont bfCode = BaseFont.createFont("C:\\Windows\\Fonts\\msyh.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font fontCom = new Font(bfChinese);
        Font fontBrand = new Font(bfChinese);
        Font fontPrice = new Font(bfChinese);
        Font fontGoodName = new Font(bfChinese);
        Font fontCode = new Font(bfCode);
        Font fontOther = new Font(bfChinese);
        fontGoodName.setStyle(Font.BOLD);
        fontBrand.setStyle(Font.BOLD);
        fontPrice.setStyle(Font.BOLD);
        fontCode.setStyle(Font.BOLD);
        fontCom.setSize(Float.valueOf(19));
        fontGoodName.setSize(Float.valueOf(19));
        fontBrand.setSize(Float.valueOf(28));
        fontPrice.setSize(Float.valueOf(42));
        fontCode.setSize(Float.valueOf(25));
        fontOther.setSize(Float.valueOf(19));

        //中文字体,解决中文不能显示问题
//        BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
//        Font fontCom = new Font(bfChinese);
//        Font fontGoodName = new Font(bfChinese);
        fontCom.setColor(BaseColor.BLACK);
        fontGoodName.setColor(BaseColor.BLACK);
        fontBrand.setColor(BaseColor.BLACK);
        fontOther.setColor(BaseColor.BLACK);

        // 5列的表.
        PdfPTable table = new PdfPTable(5);
        //table.setWidthPercentage(100); // 宽度100%填充
        table.setPaddingTop(0);
        table.setSpacingBefore(0); // 前间距
        table.setSpacingAfter(0); // 后间距
        table.setWidthPercentage(100);
        //table.setTotalWidth(37);
        //table.setLockedWidth(true);
        List<PdfPRow> listRow = table.getRows();
        //设置列宽
        //float[] columnWidths = { 3.88f, 3.88f, 1f, 2.88f, 3.88f };
        table.setWidths(columnWidths);
        /*PdfPCell[] cells0 = initPdfCell(5);
        PdfPRow row0 = new PdfPRow(cells0);
        cells0[0].setPhrase(new Paragraph("",fontCom));*/
        PdfPCell[] cells1 = initPdfCell(5);
        PdfPRow row1 = new PdfPRow(cells1);
        //单元格
        cells1[0].setPhrase(new Paragraph("级别"+param.getSalesRank(),fontCom));
        if("jewelry".equals(param.getType())){
            cells1[3].setPhrase(new Paragraph(param.getCategory(),fontCom));
        }else{
            cells1[3].setPhrase(new Paragraph(param.getCategory2(),fontCom));
        }

        cells1[3].setColspan(2);

        //行2
        PdfPCell[] cells2= initPdfCell(5);
        for (PdfPCell pdfPCell : cells2) {
            pdfPCell.setFixedHeight(50);
        }
        String brandName = param.getBrandName();
        if(StringLengthUtils.length(brandName)>24){
            int size = StringLengthUtils.length(brandName)-24;
            fontBrand.setSize(fontBrand.getSize()-size);
        }
        cells2[0].setPhrase(new Paragraph(brandName,fontBrand));
        cells2[0].setColspan(5);
        PdfPRow row2 = new PdfPRow(cells2);

        PdfPCell[] cells3 = initPdfCell(5);
        String type = "";
        if("jewelry".equals(param.getType())){
            type = param.getGemstoneType();
        }else if("bags".equals(param.getType())){
            type = param.getBagModel();
        }else if("watch".equals(param.getType())){
            type = param.getWatchType();
        }else if("small_articles".equals(param.getType())){
            type = param.getSmallGoodsModel();
        }
        cells3[0].setPhrase(new Paragraph(type,fontCom));
        cells3[0].setColspan(2);
        String type1 = "";
        if("jewelry".equals(param.getType())){
            type1 = param.getJewelleryMaterial();
        }else if("watch".equals(param.getType())){
            type1 = param.getDialDiameter()+"mm";
        }
        cells3[3].setPhrase(new Paragraph(type1,fontCom));
        cells3[3].setColspan(2);
        PdfPRow row3 = new PdfPRow(cells3);

        PdfPCell[] cells4 = initPdfCell(5);
        if("jewelry".equals(param.getType())){
            cells4[0].setPhrase(new Paragraph(param.getGoodsName(),fontCom));
        }else if("bags".equals(param.getType())){
            cells4[0].setPhrase(new Paragraph(param.getBagGenericName(),fontCom));
        }else if("watch".equals(param.getType())){
            cells4[0].setPhrase(new Paragraph(param.getGoodsName(),fontCom));
        }else if("small_articles".equals(param.getType())){
            cells4[0].setPhrase(new Paragraph(param.getSmallGoodsGenericName(),fontCom));
        }
        cells4[0].setColspan(2);
        String size = "";
        if("jewelry".equals(param.getType())){
            size = param.getGemstoneSize1();
        }else if("bags".equals(param.getType())){
            size = param.getBagsSize();
        }else if("watch".equals(param.getType())){
            size = param.getWatchSize();
        }else if("small_articles".equals(param.getType())){
            size = param.getSmallgoodsSize();
        }
        cells4[3].setPhrase(new Paragraph(size,fontCom));
        cells4[3].setColspan(2);
        PdfPRow row4 = new PdfPRow(cells4);

        String material1="";
        String material2="";
        if("jewelry".equals(param.getType())){
            material1 = param.getGemstone();
            material2 = param.getGemstone2();
        }else if("bags".equals(param.getType())){
            material1 = param.getBagMaterial1();
            material2 = param.getBagMaterial2();
        }else if("watch".equals(param.getType())){
            material1 = param.getWatchCaseMaterial();
            material2 = param.getStrapMaterial();
        }else if("small_articles".equals(param.getType())){
            material1 = param.getSmallgoodsMaterial1();
            material2 = param.getSmallgoodsMaterial2();
        }
        PdfPCell[] cells5 = initPdfCell(5);
        cells5[0].setPhrase(new Paragraph(material1,fontCom));
        cells5[0].setColspan(2);
        cells5[3].setPhrase(new Paragraph(material2,fontCom));
        cells5[3].setColspan(2);
        PdfPRow row5 = new PdfPRow(cells5);

        PdfPCell[] cells6 = initPdfCell(5);
        if("jewelry".equals(param.getType())){
            cells6[0].setPhrase(new Paragraph(param.getGemstone1Weight()+param.getGemstone1WeightUnit(),fontCom));
            cells6[0].setColspan(2);
            cells6[3].setPhrase(new Paragraph(param.getGemstone2Weight()+param.getGemstone2WeightUnit(),fontCom));
            cells6[3].setColspan(2);
        }
        PdfPRow row6 = new PdfPRow(cells6);


        PdfPCell[] cells7 = initPdfCell(5);
        if("jewelry".equals(param.getType())){
            cells7[0].setPhrase(new Paragraph(param.getDiamondGrade(),fontCom));
            cells7[0].setColspan(2);
            String gemstoneSize2 = param.getGemstoneSize2() + "";
            gemstoneSize2 = gemstoneSize2.replaceAll("\\.00","");
            cells7[3].setPhrase(new Paragraph(gemstoneSize2+param.getJewelrySize2Unit(),fontCom));
            cells7[3].setColspan(2);
        }else if("watch".equals(param.getType())){
            cells7[3].setPhrase(new Paragraph(param.getWaterResistance(),fontCom));
            cells7[3].setColspan(2);
        }
        PdfPRow row7 = new PdfPRow(cells7);

        PdfPCell[] cells8 = new PdfPCell[5];
        for (int i = 0; i < cells8.length; i++) {
            cells8[i] = new PdfPCell();
            cells8[i].setBorder(0);
            cells8[i].setFixedHeight(52);
        }
        cells8[0].setPhrase(new Paragraph("￥"+ MoneyFormatUtils.formatChina3(param.getSalePrice()),fontPrice));
        cells8[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells8[0].setColspan(3);
        cells8[3].setPhrase(new Paragraph("市场参考价\n￥"+MoneyFormatUtils.formatChina3(param.getSetPrice()),fontCom));
        cells8[3].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells8[3].setColspan(2);
        PdfPRow row8 = new PdfPRow(cells8);


        PdfPCell[] cells10 = new PdfPCell[5];
        for (int i = 0; i < cells10.length; i++) {
            cells10[i] = new PdfPCell();
            cells10[i].setBorder(0);
            cells10[i].setFixedHeight(55);
        }
        Image image1 = Image.getInstance(generateBarCode(param.getCommodityCode()));
        cells10[0].setImage(image1);
        cells10[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells10[0].setColspan(5);
        PdfPRow row10 = new PdfPRow(cells10);

        PdfPCell[] cells11 = new PdfPCell[5];
        for (int i = 0; i < cells10.length; i++) {
            cells11[i] = new PdfPCell();
            cells11[i].setBorder(0);
            cells11[i].setFixedHeight(43);
        }
        cells11[0].setPhrase(new Paragraph(param.getCommodityCode(),fontCode));
        cells11[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells8[3].setColspan(2);
        cells11[0].setColspan(5);
        PdfPRow row11 = new PdfPRow(cells11);

        PdfPCell[] cells12 = initPdfCell(5);
        if("jewelry".equals(param.getType())){
            cells12[1].setPhrase(new Paragraph(param.getGemstoneDefect(),fontCom));
        }else if("bags".equals(param.getType())){
            cells12[1].setPhrase(new Paragraph(param.getBagDefect(),fontCom));
        }else if("watch".equals(param.getType())){
            cells12[1].setPhrase(new Paragraph(param.getWatchDefect(),fontCom));
        }else if("small_articles".equals(param.getType())){
            cells12[1].setPhrase(new Paragraph(param.getSmallGoodsDefect(),fontCom));
        }
        cells12[1].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells12[1].setColspan(4);
        PdfPRow row12 = new PdfPRow(cells12);

        PdfPCell[] cells13 = initPdfCell(5);
        if("jewelry".equals(param.getType())){
            cells13[0].setPhrase(new Paragraph(param.getGemstoneAccessoriesCheckType(),fontCom));
        }else if("bags".equals(param.getType())){
            cells13[0].setPhrase(new Paragraph(param.getBagAccessories(),fontCom));
        }else if("watch".equals(param.getType())){
            cells13[0].setPhrase(new Paragraph(param.getWatchAccessories(),fontCom));
        }else if("small_articles".equals(param.getType())){
            cells13[0].setPhrase(new Paragraph(param.getSmallgoodsAccessories(),fontCom));
        }
        cells13[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells13[0].setColspan(5);
        PdfPRow row13 = new PdfPRow(cells13);

        PdfPCell[] cells14 = initPdfCell(5);
        if("jewelry".equals(param.getType())){
            cells14[1].setPhrase(new Paragraph(param.getGemstoneManufactureNumber(),fontCom));
        }else if("bags".equals(param.getType())){
            cells14[1].setPhrase(new Paragraph(param.getBagsManufactureNumber(),fontCom));
        }else if("watch".equals(param.getType())){
            cells14[1].setPhrase(new Paragraph(param.getWatchManufactureNumber(),fontCom));
        }else if("small_articles".equals(param.getType())){
            cells14[1].setPhrase(new Paragraph(param.getSmallgoodsManufactureNumber(),fontCom));
        }
        cells14[1].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells14[1].setColspan(4);
        PdfPRow row14 = new PdfPRow(cells14);

        PdfPCell[] cells15 = initPdfCell(5);
        if("jewelry".equals(param.getType())){
            cells15[1].setPhrase(new Paragraph(param.getGemstoneGuaranteeNumber(),fontCom));
        }else if("bags".equals(param.getType())){
            cells15[1].setPhrase(new Paragraph(param.getBagRemark1(),fontCom));
        }else if("watch".equals(param.getType())){
            cells15[1].setPhrase(new Paragraph(param.getWatchRemark1(),fontCom));
        }else if("small_articles".equals(param.getType())){
            cells15[1].setPhrase(new Paragraph(param.getSmallGoodsRemark(),fontCom));
        }
        cells15[1].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells15[1].setColspan(4);
        PdfPRow row15 = new PdfPRow(cells15);


        PdfPCell[] cells16 = initPdfCell(5);
        if("jewelry".equals(param.getType())){
            cells16[1].setPhrase(new Paragraph(param.getGemstoneRemark1(),fontCom));
        }else if("bags".equals(param.getType())){
            cells16[1].setPhrase(new Paragraph(param.getBagRemark2(),fontCom));
        }else if("watch".equals(param.getType())){
            cells16[1].setPhrase(new Paragraph(param.getRepairText(),fontCom));
        }else if("small_articles".equals(param.getType())){
            cells16[1].setPhrase(new Paragraph(param.getSmallGoodsRemark2(),fontCom));
        }
        cells16[1].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells16[1].setColspan(4);
        PdfPRow row16 = new PdfPRow(cells16);


        PdfPCell[] cells17 = initPdfCell(5);

        cells17[0].setPhrase(new Paragraph(this.costMark(param.getPurchasePrice())+"/"+this.costMark(param.getRepairCost()),fontOther));
        cells17[0].setColspan(2);
        PdfPRow row17 = new PdfPRow(cells17);

        PdfPCell[] cells18 = initPdfCell(5);
        cells18[0].setPhrase(new Paragraph(param.getBuyer(),fontOther));
        cells18[0].setColspan(2);
        if("jewelry".equals(param.getType())){
            cells18[3].setPhrase(new Paragraph(param.getJewelleryBrandCode(),fontOther));
        }else if("bags".equals(param.getType())){
            cells18[3].setPhrase(new Paragraph(param.getBagsBrandCode(),fontOther));
        }else if("watch".equals(param.getType())){
            cells18[3].setPhrase(new Paragraph(param.getWatchBrandCode(),fontOther));
        }else if("small_articles".equals(param.getType())){
            cells18[3].setPhrase(new Paragraph(param.getSmallgoodsBrandCode(),fontOther));
        }

        cells18[1].setHorizontalAlignment(Element.ALIGN_CENTER);
        cells18[3].setColspan(2);
        PdfPRow row18 = new PdfPRow(cells18);


        //listRow.add(row0);
        listRow.add(row1);
        listRow.add(row2);
        listRow.add(row3);
        listRow.add(row4);
        listRow.add(row5);
        listRow.add(row6);
        listRow.add(row7);
        listRow.add(row8);
        //listRow.add(row9);
        listRow.add(row10);
        listRow.add(row11);
        listRow.add(row12);
        listRow.add(row13);
        listRow.add(row14);
        listRow.add(row15);
        listRow.add(row16);
        listRow.add(row17);
        listRow.add(row18);
        //把表格添加到文件中
        document.add(table);
        // 5.关闭文档
        document.close();
        log.info("pdf生成完成;");
        return retuenPath;
    }

    private String costMark(BigDecimal totalCost) {
        if(totalCost==null){
            return "";
        }
        String costString = totalCost.divide(new BigDecimal(100),4, RoundingMode.DOWN)+"";
        char[] chars = costString.toCharArray();
        String returnString = "";
        for (int i = 0; i < chars.length; i++) {
            String s = "";
            switch (chars[i]){
                case '1': s = "U"; break;
                case '2': s = "R"; break;
                case '3': s = "K"; break;
                case '4': s = "E"; break;
                case '5': s = "S"; break;
                case '6': s = "I"; break;
                case '7': s = "T"; break;
                case '8': s = "N"; break;
                case '9': s = "Y"; break;
                case '0': s = "H"; break;
                case '.': s = "."; break;
            }


            if(i!=0 && StringUtils.isNotBlank(returnString)){
                String substring = returnString.substring(returnString.length() - 1);
                if(".".equals(substring)){
                    substring = returnString.substring(returnString.length() - 2);
                }
                if(s.equals(substring)){
                    s = "O";
                }
            }
            returnString+=s;
        }
        return returnString;
    }

    private static PdfPCell[] initPdfCell(int i) {
        List<PdfPCell> cells = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            PdfPCell cell = new PdfPCell();
            cell.setBorder(0);
            cell.setFixedHeight(32);
            cell.setNoWrap(true);
            cells.add(cell);
        }
        return cells.toArray(new PdfPCell[i]);
    }

}
