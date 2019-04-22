package com.nevergetme.nevergetmeweb.controller;

import com.nevergetme.nevergetmeweb.sensitiveword.SensitivewordFilter;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class WebController {
    //ApplicationContext

//    private static final SensitivewordFilter filter = new SensitivewordFilter();
//    @RequestMapping("/hello")
//    public String index(){
//        return "Hello World";
//    }
//    @CrossOrigin
//    @RequestMapping(value = "/sensitiveword",method = RequestMethod.POST)
//    public @ResponseBody
//    Map<String,String> getSensitiveWord(String words, HttpServletRequest request){
//        Map<String,String> map=new HashMap<>();
//        String output=filter.replaceSensitiveWord(words,1,"*");
//        map.put("state","1");
//        map.put("output",output);
//        map.put("input",words);
//        map.put("ip",request.getRemoteAddr());
//        return map;
//    }
//    @CrossOrigin
//    @RequestMapping(value = "/uploadTxt",method=RequestMethod.POST)
//    public @ResponseBody Map<String,String> seneitiveWordTxt(@RequestParam(value="file")MultipartFile file)throws Exception{
////        String originFilename=file.getOriginalFilename();
//        Map<String,String> map=new HashMap<>();
//        if(file.isEmpty()){
//            map.put("state","false");
//            map.put("output","");
//            return map;
//        }
//        System.out.println(file.getOriginalFilename());
//        BufferedReader reader=new BufferedReader(new InputStreamReader(file.getInputStream()));
//        StringBuffer sb=new StringBuffer();
//        String line=null;
//        String output="";
//        try {
//            while ((line=reader.readLine())!=null){
//                sb.append(line);
//            }
//            System.out.println(sb.toString());
//            output=filter.replaceSensitiveWord(sb.toString(),1,"*");
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            if(output.length()>0){
//                map.put("output",output);
//                map.put("state","true");
//            }else{
//                map.put("state","false");
//                map.put("output","");
//            }
//            return map;
//        }
//
//    }
}
