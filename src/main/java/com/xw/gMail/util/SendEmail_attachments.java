package com.xw.gMail.util;

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
 * @Description 发送包含附件的邮件
 * @Created Date 2019/9/19 14:18
 **/
public class SendEmail_attachments {
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

    /**
     * 发送附件的路径
     */
    private static String fileSrc = null;

    private static String projectPath = System.getProperty("user.dir")+"\\";
    private static String imgPath ="src\\main\\resources\\static\\img\\";
    private static String filePath = "src\\main\\resources\\static\\file\\";
    private static String imgSuffix =".jpg";
    private static String encord = "text/html;charset=UTF-8";
    private static String imgContent = "<img src='cid:";
    private static String rightBracket = "'>";
    private static String imgName = "";

    public static void sendEmail_attachments() throws Exception {



        mailFrom = "1747735007@qq.com";
        password_mailFrom="cexyvzzafbynfaja";
        mailTo = "13283813740@163.com";
        mailTittle="节日快乐2！";
        mailText = "这是一个简单的邮件";
        mail_host="smtp.qq.com";

        Properties prop = new Properties();
        prop.setProperty("mail.host", mail_host);// 需要修改
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
        ts.connect(mail_host, mailFrom, password_mailFrom);// 需要修改
        // 4、创建邮件
        Message message = createAttachMail(session);
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * @Method: createAttachMail
     * @Description: 创建一封带附件的邮件
     */
    public static MimeMessage createAttachMail(Session session) throws Exception {
        MimeMessage message = new MimeMessage(session);

        // 设置邮件的基本信息

        message.setFrom(new InternetAddress(mailFrom));	// 发件人

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));// 收件人
        // 邮件标题
        message.setSubject(mailTittle);

        // 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(mailText, encord);

        // 创建邮件附件
        List<MimeBodyPart> attachList = new ArrayList<MimeBodyPart>();
        for(int i=1;i<3;i++){
            photoSrc = projectPath+filePath+i+imgSuffix;

            MimeBodyPart attach = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource(photoSrc));// 需要修改
            attach.setDataHandler(dh);
            attach.setFileName(dh.getName());
            attachList.add(attach);

        }

        // 创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        //将所有的附件加入
        for(MimeBodyPart attach:attachList){
            mp.addBodyPart(attach);
        }

        mp.setSubType("mixed");

        message.setContent(mp);
        message.saveChanges();
        // 将创建的Email写入到F盘存储
//        message.writeTo(new FileOutputStream("F:/Program Files/TestMail/ImageMail.eml"));// 需要修改
        // 返回生成的邮件
        return message;
    }
}
