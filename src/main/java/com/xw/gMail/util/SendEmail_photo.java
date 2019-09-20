package com.xw.gMail.util;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @Author XieWei
 * @Description 发送包含内嵌图片的邮件
 * @Created Date 2019/9/18 11:14
 **/
public class SendEmail_photo {
    /**
     * 指明邮件的发件人账号，比如：123456@qq.com
     */
    private static String mailFrom = null;

    /**
     * 指明邮件的发件人授权码，不是注册时登录密码
     */
    private static String password_mailFrom = null;

    /**
     * 指明邮件的收件人
     */
    private static String mailTo = null;

    /**
     * 邮件的标题
     */
    private static String mailTittle = null;

    /**
     * 邮件的文本内容
     */
    private static String mailText =null;

    /**
     * 邮件的发送方的服务器域名，例如：smtp.qq.com
     */
    private static String mail_host =null;

    /**
     *  发送图片的路径
     */
    private static String photoSrc = null;

    private static String projectPath = System.getProperty("user.dir")+"\\";
    private static String imgPath ="src\\main\\resources\\static\\img\\";
    private static String encord = "text/html;charset=UTF-8";
    private static String imgContent = "<img src='cid:";
    private static String rightBracket = "'>";


    public static void sendEmail_photo() throws Exception {
        mailFrom = "1747735007@qq.com";
        password_mailFrom="cexyvzzafbynfaja";
        mailTo = "13283813740@163.com";
        mailTittle="节日快乐2！";
        mailText = "这是一个简单的邮件";
        mail_host="smtp.qq.com";

        //发送邮件的一些配置
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
        // 3、连上邮件服务器，需要发件人提供邮箱的用户名和授权码进行验证
        ts.connect(mail_host, mailFrom, password_mailFrom);
        // 4、创建邮件
        Message message = createImageMail(session);
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * @Method: createImageMail
     * @Description: 生成一封邮件正文带图片的邮件
     * @param session
     * @return
     * @throws Exception
     */
    public static MimeMessage createImageMail(Session session) throws Exception {
        // 创建邮件
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息

        // 发件人
        message.setFrom(new InternetAddress(mailFrom));
        // 收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        // 邮件标题
        message.setSubject(mailTittle);

        // 准备邮件数据
        // 准备邮件正文数据
        MimeBodyPart text = new MimeBodyPart();
        //获取所有的图片文件名字
        File file = new File(projectPath+imgPath);
        String [] imgNames = file.list();
        String imgSrc = "";
        for(String imgName:imgNames) {
            imgSrc += imgContent + imgName + rightBracket;
        }
        //设置图片正文内容，通过<img src='cid:xxx.jpg'>标签读取图片
        text.setContent(mailText + imgSrc, encord);

        // 准备图片数据
        List<MimeBodyPart> imgList = new ArrayList<MimeBodyPart>();
        for(String imgName:imgNames){
            MimeBodyPart image = new MimeBodyPart();
            photoSrc = projectPath + imgPath +imgName;
            DataHandler dh = new DataHandler(new FileDataSource(photoSrc));
            image.setDataHandler(dh);
            //设置图片id，以便于上面的<img>标签读取
            image.setContentID(dh.getName());
            imgList.add(image);
        }
        // 描述数据关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        for(MimeBodyPart image:imgList){
            mm.addBodyPart(image);
        }
        mm.setSubType("related");

        message.setContent(mm);
        message.saveChanges();

        // 返回创建好的邮件
        return message;
    }

}
