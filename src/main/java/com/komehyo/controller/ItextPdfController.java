package com.komehyo.controller;

import com.komehyo.service.GoodToPdfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
import java.io.InputStream;
import java.util.List;


@Api(tags ={"pdf"})
@Slf4j
@RestController
public class ItextPdfController {

    @Resource
    private GoodToPdfService goodToPdfService;



    @RequestMapping(value = "/getByIds",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity<InputStreamResource> getByIds(@ApiParam("ids") @RequestParam List<Integer> ids) throws Exception {
        String path;
        String fileName;

        if(ids.size()==1){
            path = goodToPdfService.goodToPdf(ids.get(0));
            String[] split = path.split("/");
            fileName = split[split.length-1];
        }else{
            path = goodToPdfService.goodToPdZip(ids);
            fileName = "pdf.zip";
        }


        FileSystemResource file = new FileSystemResource(path);
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
