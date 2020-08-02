package com.komehyo.controller;

import com.komehyo.service.GoodToPdfService;
import com.komehyo.utils.ZipUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.List;


@Api(tags ={"template"})
@Slf4j
@RestController
public class TemplateController {


    @Value("${file.zip.path}")
    private String zipPath;

    @Value("${file.template.path}")
    private String templatePath;

    @Resource
    private GoodToPdfService goodToPdfService;


    @RequestMapping(value = "/imgZip",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity<InputStreamResource> imgZip(@ApiParam("ids") @RequestParam List<Integer> ids) throws Exception {
        ZipUtils.delDir(zipPath);
        String path = zipPath + "/goodImg/";
        File mainFile = new File(path);
        mainFile.mkdirs();
        List<String> codesByIds = goodToPdfService.getCodesByIds(ids);
        for (String id : codesByIds) {
            File file = new File(path+"/"+id);
            file.mkdirs();
        }
        String filePath = zipPath+"/images.zip";
        ZipUtils.toZip(path,filePath,true);
        String fileName = "images.zip";
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        InputStream inputStream = file.getInputStream();
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(inputStreamResource);
    }

    @RequestMapping(value = "/template/excel",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity<InputStreamResource> excelTemplate() throws Exception {
        String fileName = "good_import.xlsx";
        FileSystemResource file = new FileSystemResource(templatePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        InputStream inputStream = file.getInputStream();
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(inputStreamResource);
    }


}
