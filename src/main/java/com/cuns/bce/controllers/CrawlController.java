package com.cuns.bce.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


@Controller
@RequestMapping("/crawl")
public class CrawlController {
    private final String nettruyenUrl = "https://www.nettruyenmax.com/";

//    @RequestMapping("/picture")
//    public ResponseEntity<StreamingResponseBody> crawlPicture(@RequestParam String src, HttpServletResponse response){
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<byte[]> imageResponse = restTemplate.getForEntity(new URI(src), byte[].class);
//            if (imageResponse.getStatusCode() == HttpStatus.OK) {
//                InputStream imageInputStream = new ByteArrayInputStream(imageResponse.getBody());
//
//                response.setContentType("image/jpeg"); // Set the response content type
//
//                StreamingResponseBody responseBody = outputStream -> {
//                    try {
//                        byte[] buffer = new byte[1024];
//                        int bytesRead;
//                        while ((bytesRead = imageInputStream.read(buffer)) != -1) {
//                            outputStream.write(buffer, 0, bytesRead);
//                        }
//                        imageInputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                };
//
//                return new ResponseEntity<>(responseBody, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (HttpClientErrorException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
