package com.xw.gMail.util;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author XieWei
 * @Description Email工具类，支持发送正文里内嵌图片，带附件的复杂邮件，
 *              请自定义以下参数：
 *                 mailFrom           发件人账号
 *                 password_mailFrom  发件人账号的授权码，不是注册时的登录密码
 *                 mail_host          邮件发送方的服务器域名
 *                 encording          邮件的编码格式
 *                 imgFolderPath      存放图片的文件夹在项目中的路径
 *                 fileFolderPath     存放附件的文件夹在项目中的路径
 * @Created Date 2019/9/20 11:52
 **/
public class EmailUtil {
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

    /**
     * 发送邮件
     * @param mailTo 邮件接收人的邮箱地址
     * @param mailTittle 邮件主题
     * @param mailText 邮件内容
     * @param type 1纯文本，2文本内嵌图片无附件，3文本无图片带附件，4文本内嵌图片带附件
     * @throws Exception
     */
    public static void sendEmail(String mailTo,String mailTittle,String mailText, Integer type) throws Exception {
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
        Message message = null;
        //type 1纯文本，2文本内嵌图片无附件，3文本无图片带附件，4文本内嵌图片带附件
        switch (type){
            case 1:
                message = createTextMail(session,mailTo,mailTittle,mailText);
                break;
            case 2:
                message = createImageMail(session,mailTo,mailTittle,mailText,imgFolderPath);
                break;
            case 3:
                message = createAttachmentMail(session,mailTo,mailTittle,mailText,fileFolderPath);
                break;
            case 4:
                message = createImageAndAttachmentMail(session,mailTo,mailTittle,mailText);
                break;
        }
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * 创建一封只包含文本的邮件
     * @param session
     * @param mailTo 邮件接收人的邮箱地址
     * @param mailTittle 邮件主题
     * @param mailText 邮件内容
     * @return
     * @throws Exception
     */
    public static MimeMessage createTextMail(Session session, String mailTo, String mailTittle,
                                               String mailText) throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(mailFrom,"中科闻歌","UTF-8"));
        // 指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        // 邮件的标题
        message.setSubject(mailTittle);
        // 邮件的文本内容
        message.setContent(mailText, encording);
        // 返回创建好的邮件对象
        return message;
    }

    /**
     * 生成一封邮件正文带图片的邮件
     * @param session
     * @param mailTo 邮件接收人的邮箱地址
     * @param mailTittle 邮件主题
     * @param mailText 邮件内容
     * @param imgFolderPath 存放邮件中的图片的文件夹的路径
     * @return
     * @throws Exception
     */
    public static MimeMessage createImageMail(Session session, String mailTo, String mailTittle,
                                              String mailText,String imgFolderPath) throws Exception {
        // 创建邮件
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息

        // 发件人
        message.setFrom(new InternetAddress(mailFrom,"中科闻歌","UTF-8"));
        // 收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        // 邮件标题
        message.setSubject(mailTittle);

        // 准备邮件数据
        // 准备邮件正文数据
        MimeBodyPart text = new MimeBodyPart();
        //获取项目路径
        String projectPath = System.getProperty("user.dir")+"\\";
        //获取所有的图片文件名字
        File file = new File(projectPath+imgFolderPath);
        String [] imgNames = file.list();
        /******************** 要求在短信内容mailText中，将图片的位置用<img src='cid:xxx.jpg'>替换，
        String imgSrc = "";
        for(String imgName:imgNames) {
            imgSrc += imgContent + imgName + rightBracket;
        }
         text.setContent(mailText+imgSrc , encording);
        ***************************************/
        //设置图片正文内容，通过<img src='cid:xxx.jpg'>标签读取图片
        text.setContent(mailText , encording);

        // 准备图片数据
        List<MimeBodyPart> imgList = new ArrayList<MimeBodyPart>();
        for(String imgName:imgNames){
            MimeBodyPart image = new MimeBodyPart();
            //通过绝对路径获取图片数据
            DataHandler dh = new DataHandler(new FileDataSource(projectPath + imgFolderPath +imgName));
            image.setDataHandler(dh);
            //设置图片id，以便于mailText中的<img>标签读取
            image.setContentID(MimeUtility.decodeText(dh.getName()));
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

    /**
     * 创建一封带附件的邮件
     * @param session
     * @param mailTo 邮件接收人的邮箱地址
     * @param mailTittle 邮件主题
     * @param mailText 邮件内容
     * @param fileFolderPath 保存附件的文件夹的路径
     * @return
     * @throws Exception
     */
    public static MimeMessage createAttachmentMail(Session session, String mailTo, String mailTittle,
                                                   String mailText,String fileFolderPath) throws Exception {
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息
        // 发件人
        message.setFrom(new InternetAddress(mailFrom,"中科闻歌","UTF-8"));
        // 收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        // 邮件标题
        message.setSubject(mailTittle);

        // 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(mailText, encording);
        //获取项目路径
        String projectPath = System.getProperty("user.dir") + "\\";
        //获取所有的图片文件名字
        File file = new File(projectPath + fileFolderPath);
        String[] fileNames = file.list();
        // 创建邮件附件
        List<MimeBodyPart> attachList = new ArrayList<>();
        for (String fileName : fileNames) {
            MimeBodyPart attach = new MimeBodyPart();
            //根据绝对路径获取附件
            DataHandler dh = new DataHandler(new FileDataSource(projectPath + fileFolderPath + fileName));
            attach.setDataHandler(dh);
            attach.setFileName(MimeUtility.decodeText(dh.getName()));
            attachList.add(attach);
        }

        // 创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        //将所有的附件加入
        for (MimeBodyPart attach : attachList) {
            mp.addBodyPart(attach);
        }
        mp.setSubType("mixed");

        message.setContent(mp);
        message.saveChanges();
        // 返回生成的邮件
        return message;
    }

    /**
     * @Method: createImageAndAttachmentMail
     * @Description: 生成一封带附件和带图片的邮件
     */
    public static MimeMessage createImageAndAttachmentMail(Session session, String mailTo, String mailTittle,
                                                           String mailText) throws Exception {
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的基本信息
        // 发件人
        message.setFrom(new InternetAddress(mailFrom,"中科闻歌","UTF-8"));
        // 收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        message.setSubject(mailTittle);

        // 正文
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(mailText,encording);

        // 图片
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

        // 附件
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
