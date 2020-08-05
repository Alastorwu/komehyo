package com.komehyo.service;

import com.itextpdf.text.DocumentException;
import com.komehyo.component.ITextPdfComponent;
import com.komehyo.dao.entity.*;
import com.komehyo.dao.mapper.app.*;
import com.komehyo.dao.mapper.config.InternationalMapper;
import com.komehyo.utils.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GoodToPdfService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private InternationalMapper internationalMapper;

    @Resource
    private ITextPdfComponent pdfComponent;

    @Value("${file.zip.path}")
    private String zipPath;

    @Value("${file.pdf.path}")
    private String pdfPath;

    @Transactional(rollbackFor = Exception.class)
    public String goodToPdf(int goodId) throws IOException, DocumentException {
        GoodsWithBLOBs good = goodsMapper.selectByPrimaryKey(goodId);
        good.setPrinted(1);
        goodsMapper.updateByPrimaryKey(good);
        this.formatGoodInternational(good);
        return pdfComponent.generatePrintPdf(good);
    }

    @Transactional(rollbackFor = Exception.class)
    public String goodToPdZip(List<Integer> ids) throws Exception {
        ZipUtils.delDir(pdfPath);
        ZipUtils.delDir(zipPath);
        File file = new File(pdfPath);
        file.mkdirs();
        file = new File(zipPath);
        file.mkdirs();
        for (Integer id : ids) {
            GoodsWithBLOBs good = goodsMapper.selectByPrimaryKey(id);
            if(good==null){
                continue;
            }
            good.setPrinted(1);
            goodsMapper.updateByPrimaryKey(good);
            good = this.formatGoodInternational(good);
            pdfComponent.generatePrintPdf(good);
        }
        String filePath = zipPath+"test.zip";
        ZipUtils.toZip(pdfPath,zipPath+"test.zip",true);
        return filePath;

    }

    @Resource
    private SaleRankMapper saleRankMapper;
    @Resource
    private MiddleTypeMapper middleTypeMapper;
    @Resource
    private GoodBrandMapper goodBrandMapper;
    @Resource
    private MaterialMapper materialMapper;
    @Resource
    private BigTypeMapper bigTypeMapper;
    @Resource
    private JewelrySizeMapper jewelrySizeMapper;
    @Resource
    private BagSizeMapper bagSizeMapper;
    @Resource
    private WatchSizeMapper watchSizeMapper;
    @Resource
    private SmallgoodsSizeMapper smallgoodsSizeMapper;
    @Resource
    private GemstoneMapper gemstoneMapper;
    @Resource
    private Material1Mapper material1Mapper;
    @Resource
    private CaseMaterialMapper caseMaterialMapper;
    @Resource
    private BeltMaterialMapper beltMaterialMapper;
    @Resource
    private SmallgoodsMaterial1Mapper smallgoodsMaterial1Mapper;
    @Resource
    private WaterproofMapper waterproofMapper;
    @Resource
    private GemAccessoriesMapper gemAccessoriesMapper;
    @Resource
    private BagsAccessoriesMapper bagsAccessoriesMapper;
    @Resource
    private WatchAccessoriesMapper watchAccessoriesMapper;
    @Resource
    private SmallgoodsBagsAccessoriesMapper smallgoodsBagsAccessoriesMapper;
    @Resource
    private GemstoneUnitMapper gemstoneUnitMapper;
    @Resource
    private JewelrySize2Mapper jewelrySize2Mapper;

    private String toCnInternational(String id){
        if(StringUtils.isBlank(id)){
            return id;
        }
        International international = internationalMapper.selectById(id);
        if(international==null){
            return id;
        }
        return international.getCn();
    }

    private GoodsWithBLOBs formatGoodInternational(GoodsWithBLOBs good) {

        good.setGoodsName(toCnInternational(good.getGoodsName()));
        SaleRank saleRank = saleRankMapper.selectByPrimaryKey(good.getSalesRank());
        if(saleRank!=null){
            good.setSalesRank(saleRank.getLevel());
        }
        if(StringUtils.isNoneBlank(good.getCategory())){
            BigType bigType = bigTypeMapper.selectByPrimaryKey(Integer.valueOf(good.getCategory()));
            if(bigType!=null){
                good.setCategory(toCnInternational(bigType.getName()));
            }
        }
        if(StringUtils.isNoneBlank(good.getCategory2())){
            MiddleType middleType
                    = middleTypeMapper.selectByPrimaryKey(Integer.valueOf(good.getCategory2()));
            if(middleType!=null){
                good.setCategory2(toCnInternational(middleType.getName()));
            }
        }
        if(StringUtils.isNoneBlank(good.getBrandName())){
            GoodBrand goodBrand = goodBrandMapper.selectByPrimaryKey(good.getBrandName());
            if(goodBrand!=null){
                good.setBrandName(toCnInternational(goodBrand.getName()));
            }
        }
        if(StringUtils.isNoneBlank(good.getJewelleryMaterial())){
            Material material = materialMapper.selectByPrimaryKey(good.getJewelleryMaterial());
            if(material!=null){
                good.setJewelleryMaterial(material.getMaterial());
            }
        }
        if(StringUtils.isNoneBlank(good.getGemstoneSize1())){
            JewelrySize jewelrySize = jewelrySizeMapper.selectByPrimaryKey(good.getGemstoneSize1());
            if(jewelrySize!=null){
                good.setGemstoneSize1(toCnInternational(jewelrySize.getJewelrySize()));
            }
        }
        if(StringUtils.isNoneBlank(good.getBagsSize())){
            BagSize bagSize = bagSizeMapper.selectByPrimaryKey(good.getBagsSize());
            if(bagSize!=null){
                good.setBagsSize(toCnInternational(bagSize.getSize()));
            }
        }
        if(StringUtils.isNoneBlank(good.getWatchSize())){
            WatchSize watchSize = watchSizeMapper.selectByPrimaryKey(good.getWatchSize());
            if(watchSize!=null){
                good.setWatchSize(watchSize.getSize());
            }
        }
        if(StringUtils.isNoneBlank(good.getSmallgoodsSize())){
            SmallgoodsSize smallgoodsSize = smallgoodsSizeMapper.selectByPrimaryKey(good.getSmallgoodsSize());
            if(smallgoodsSize!=null){
                good.setSmallgoodsSize(toCnInternational(smallgoodsSize.getName()));
            }
        }
        if(StringUtils.isNoneBlank(good.getGemstone())){
            Gemstone gemstone = gemstoneMapper.selectByPrimaryKey(good.getGemstone());
            if(gemstone!=null){
                good.setGemstone(toCnInternational(gemstone.getName()));
            }
        }
        if(StringUtils.isNoneBlank(good.getGemstone2())){
            Gemstone gemstone = gemstoneMapper.selectByPrimaryKey(good.getGemstone2());
            if(gemstone!=null){
                good.setGemstone2(toCnInternational(gemstone.getName()));
            }
        }
        if(StringUtils.isNoneBlank(good.getBagMaterial1())){
            Material1 material1 = material1Mapper.selectByPrimaryKey(good.getBagMaterial1());
            if(material1!=null){
                good.setBagMaterial1(toCnInternational(material1.getMaterial1()));
            }
        }
        if(StringUtils.isNoneBlank(good.getBagMaterial2())){
            Material1 material1 = material1Mapper.selectByPrimaryKey(good.getBagMaterial2());
            if(material1!=null){
                good.setBagMaterial2(toCnInternational(material1.getMaterial1()));
            }
        }
        if(StringUtils.isNoneBlank(good.getWatchCaseMaterial())){
            CaseMaterial caseMaterial = caseMaterialMapper.selectByPrimaryKey(good.getWatchCaseMaterial());
            if(caseMaterial!=null){
                good.setWatchCaseMaterial(toCnInternational(caseMaterial.getCaseMaterial()));
            }
        }
        if(StringUtils.isNoneBlank(good.getStrapMaterial())){
            BeltMaterial beltMaterial = beltMaterialMapper.selectByPrimaryKey(good.getStrapMaterial());
            if(beltMaterial!=null){
                good.setStrapMaterial(toCnInternational(beltMaterial.getBeltMaterial()));
            }
        }
        if(StringUtils.isNoneBlank(good.getSmallgoodsMaterial1())){
            SmallgoodsMaterial1 smallgoodsMaterial1 = smallgoodsMaterial1Mapper.selectByPrimaryKey(good.getSmallgoodsMaterial1());
            if(smallgoodsMaterial1!=null){
                good.setSmallgoodsMaterial1(toCnInternational(smallgoodsMaterial1.getName()));
            }
        }
        if(StringUtils.isNoneBlank(good.getSmallgoodsMaterial2())){
            SmallgoodsMaterial1 smallgoodsMaterial1 = smallgoodsMaterial1Mapper.selectByPrimaryKey(good.getSmallgoodsMaterial2());
            if(smallgoodsMaterial1!=null){
                good.setSmallgoodsMaterial2(toCnInternational(smallgoodsMaterial1.getName()));
            }
        }
        if(StringUtils.isNoneBlank(good.getWaterResistance())){
            Waterproof waterproof = waterproofMapper.selectByPrimaryKey(good.getWaterResistance());
            if(waterproof!=null){
                good.setWaterResistance(toCnInternational(waterproof.getWaterproof()));
            }
        }
        if(StringUtils.isNoneBlank(good.getGemstoneAccessoriesCheckType())){
            String gemstoneAccessoriesCheckType = good.getGemstoneAccessoriesCheckType();
            if(StringUtils.isNotBlank(gemstoneAccessoriesCheckType)){
                String name = "";
                String[] split = gemstoneAccessoriesCheckType.split(",");
                if(split.length>0){
                    for (int i = 0; i < split.length; i++) {
                        String s=split[i];
                        GemAccessories gemAccessories = gemAccessoriesMapper.selectByPrimaryKey(s);
                        if(gemAccessories!=null){
                            if(i!=0){
                                name+=",";
                            }
                            name+=toCnInternational(gemAccessories.getName());
                        }
                    }
                }
                good.setGemstoneAccessoriesCheckType(name);
            }

        }
        if(StringUtils.isNoneBlank(good.getBagAccessories())){
            String bagAccessories = good.getBagAccessories();
            if(StringUtils.isNotBlank(bagAccessories)){
                String name = "";
                String[] split = bagAccessories.split(",");
                if(split.length>0){
                    for (int i = 0; i < split.length; i++) {
                        String s=split[i];
                        BagsAccessories bagsAccessories = bagsAccessoriesMapper.selectByPrimaryKey(s);
                        if(bagsAccessories!=null){
                            if(i!=0){
                                name+=",";
                            }
                            name+=toCnInternational(bagsAccessories.getBagsAccessories());
                        }
                    }
                }
                good.setBagAccessories(name);
            }
        }
        if(StringUtils.isNoneBlank(good.getWatchAccessories())){
            String watchAccessories = good.getWatchAccessories();
            if(StringUtils.isNotBlank(watchAccessories)){
                String name = "";
                String[] split = watchAccessories.split(",");
                if(split.length>0){
                    for (int i = 0; i < split.length; i++) {
                        String s=split[i];
                        WatchAccessories watchAccessoriesB = watchAccessoriesMapper.selectByPrimaryKey(s);
                        if(watchAccessoriesB!=null){
                            if(i!=0){
                                name+=",";
                            }
                            name+=toCnInternational(watchAccessoriesB.getWatchAccessories());
                        }
                    }
                }
                good.setWatchAccessories(name);
            }
        }
        if(StringUtils.isNoneBlank(good.getSmallgoodsAccessories())){
            String smallgoodsAccessories = good.getSmallgoodsAccessories();
            if(StringUtils.isNotBlank(smallgoodsAccessories)){
                String name = "";
                String[] split = smallgoodsAccessories.split(",");
                if(split.length>0){
                    for (int i = 0; i < split.length; i++) {
                        String s=split[i];
                        SmallgoodsBagsAccessories smallgoodsBagsAccessories = smallgoodsBagsAccessoriesMapper.selectByPrimaryKey(s);
                        if(smallgoodsBagsAccessories!=null){
                            if(i!=0){
                                name+=",";
                            }
                            name+=toCnInternational(smallgoodsBagsAccessories.getName());
                        }
                    }
                }
                good.setSmallgoodsAccessories(name);
            }
        }
        if(StringUtils.isNoneBlank(good.getGemstone1WeightUnit())){
            GemstoneUnit gemstoneUnit = gemstoneUnitMapper.selectByPrimaryKey(good.getGemstone1WeightUnit());
            if(gemstoneUnit!=null){
                good.setGemstone1WeightUnit(gemstoneUnit.getName());
            }
        }
        if(StringUtils.isNoneBlank(good.getGemstone2WeightUnit())){
            GemstoneUnit gemstoneUnit = gemstoneUnitMapper.selectByPrimaryKey(good.getGemstone2WeightUnit());
            if(gemstoneUnit!=null){
                good.setGemstone2WeightUnit(gemstoneUnit.getName());
            }
        }
        if(StringUtils.isNoneBlank(good.getJewelrySize2Unit())){
            JewelrySize2 jewelrySize2 = jewelrySize2Mapper.selectByPrimaryKey(good.getJewelrySize2Unit());
            if(jewelrySize2!=null){
                good.setJewelrySize2Unit(jewelrySize2.getName());
            }
        }

        return good;
    }

    public List<String> getCodesByIds(List<Integer> ids) {
        List<String> codes = new ArrayList<>();
        for (Integer id : ids) {
            GoodsWithBLOBs good = goodsMapper.selectByPrimaryKey(id);
            if(good!=null){
                codes.add(good.getCommodityCode());
            }
        }
        return codes;
    }
}
