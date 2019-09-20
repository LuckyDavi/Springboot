package com.xw.gMail.util;



import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * @Author XieWei
 * @Description 发送包含内嵌图片和附件的复杂邮件
 * @Created Date 2019/9/20 10:40
 **/
public class SendEmail_Photo_Attachments {
    /**  设置邮件的发件人账号，比如：123456@qq.com   **/
    private static String mailFrom = "1747735007@qq.com";

    /**  设置邮件的发件人授权码，不是注册时登录密码  **/
    private static String password_mailFrom = "cexyvzzafbynfaja";

    /**  邮件的发送方的服务器域名，例如：smtp.qq.com    **/
    private static String mail_host ="smtp.qq.com";

    /**  邮件编码格式  **/
    private static String encording = "text/html;charset=UTF-8";

    /**  存放图片的文件夹在项目中的路径  **/
    private static String imgFolderPath = "src\\main\\resources\\static\\img\\";

    /**  存放附件的文件夹在项目中的路径  **/
    private static String fileFolderPath = "src\\main\\resources\\static\\file\\";

    /** 指明邮件的收件人 **/
    private static String mailTo = null;

    /**  邮件的标题 **/
    private static String mailTittle = null;

    /** 邮件的文本内容 **/
    private static String mailText =null;

    public static void sendEmail_Photo_Attachments() throws Exception {
        mailTo = "13283813740@163.com";
        mailTittle="Have a good day!";
        mailText = "这是一个简单的邮件";

        Properties prop = new Properties();
        prop.setProperty("mail.host", mail_host);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");

        // 使用JavaMail发送邮件的5个步骤
        // 1、创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        // 2、通过session得到transport对象
        Transport ts = session.getTransport();
        // 3、连上邮件服务器，需要发件人提供邮箱的用户名和密码进行验证
        ts.connect(mail_host, mailFrom, password_mailFrom);
        // 4、创建邮件
        Message message = createMixedMail(session);
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * @Method: createMixedMail
     * @Description: 生成一封带附件和带图片的邮件
     */
    public static MimeMessage createMixedMail(Session session) throws Exception {
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息
        // 发件人
        message.setFrom(new InternetAddress(mailFrom));
        // 收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        message.setSubject(mailTittle);
        // 文本节点
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(mailText,encording);

        // 创建图片节点
        List<MimeBodyPart> imgList = new ArrayList<>();
        //获取项目路径
        String projectPath = System.getProperty("user.dir") + "\\";
        //获取所有的图片文件名字
        File attachFile = new File(projectPath + imgFolderPath);
        String[] imgNames = attachFile.list();

        for(String imgName:imgNames){
            MimeBodyPart image = new MimeBodyPart();
            //通过绝对路径获取图片
            DataHandler dh = new DataHandler(new FileDataSource(projectPath + imgFolderPath +imgName));
            image.setDataHandler(dh);
            //设置图片id，以便于正文中的<img>标签获取
            image.setContentID(MimeUtility.decodeText(dh.getName()));
            imgList.add(image);
        }

        // 创建附件节点
        List<MimeBodyPart> attachList = new ArrayList<>();

        //获取所有的图片文件名字
        File imgFile = new File(projectPath + fileFolderPath);
        String[] fileNames = imgFile.list();

        for(String fileName:fileNames){
            MimeBodyPart attach = new MimeBodyPart();
            //通过绝对路径获取附件
            DataHandler dh = new DataHandler(new FileDataSource(projectPath+fileFolderPath+fileName));
            attach.setDataHandler(dh);
            attach.setFileName(MimeUtility.decodeText(dh.getName()));
            attachList.add(attach);
        }

        // 描述关系:正文和图片，将图片和文本组合成复合节点
        MimeMultipart imgText = new MimeMultipart();
        imgText.addBodyPart(text);
        for(MimeBodyPart image:imgList){
            imgText.addBodyPart(image);
        }
        //设置图片和文本关系：关联关系
        imgText.setSubType("related");
        //将文本和图片的复合节点变成单节点
        MimeBodyPart imgTextSimple = new MimeBodyPart();
        imgTextSimple.setContent(imgText);


        //将附件节点（单节点）与文本图片组成的复合节点（已转为单节点）组合为一个复合节点
        MimeMultipart allPart = new MimeMultipart();
        for(MimeBodyPart attach:attachList){
            allPart.addBodyPart(attach);
        }
        allPart.addBodyPart(imgTextSimple);
        //设置附件与文本图片关系：混合关系
        allPart.setSubType("mixed");

        message.setContent(allPart);
        message.saveChanges();

        // 返回创建好的的邮件
        return message;
    }

}
