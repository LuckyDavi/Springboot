package com.xw.gMail.controller;


import com.xw.gMail.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author XieWei
 * @Description TODO
 * @Created Date 2019/9/18 20:07
 **/
@RestController
@RequestMapping("/sendEmail")
public class SendEmailController {

    @RequestMapping("/sendEmail_photo")
    public void sendEmail_photo(){
        try{
            SendEmail_photo.sendEmail_photo();
            System.out.println("发送成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @RequestMapping("/sendEmail_attach")
    public void sendEmail_attach(){
        try{
            SendEmail_attachments.sendEmail_attachments();
            System.out.println("发送成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @RequestMapping("/sendEmail_Photo_Attachments")
    public void sendEmail_Photo_Attachments(){
        try{
            SendEmail_Photo_Attachments.sendEmail_Photo_Attachments();
            System.out.println("发送成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    @RequestMapping("/sendEmail")
    public void sendEmail(){
        String mailTo = "13283813740@163.com";
        String mailTittle = "Have a good day";
        String mailText = "文件内容<br/>第一张图片<br/><img src='cid:1.jpg'>第二张图片<br/><img src='cid:2.jpg'>";
        int type = 4;
        try{
            EmailUtil.sendEmail(mailTo,mailTittle,mailText,type);
            System.out.println("发送成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
