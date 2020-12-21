package com.two.vote.controller;


import com.two.vote.config.Utils;
import com.two.vote.entity.User;
import com.two.vote.service.UserService;
import com.two.vote.utils.CommonUtil;
import com.two.vote.utils.EmailCodeUtils;
import com.two.vote.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Properties;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSenderImpl MailSender;

    @Value("${spring.mail.username}")
    private String username;

    //注册
    @RequestMapping("registertest")
    public String registertest(Model model,User user,HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String password = request.getParameter("pwd");
        String password2 = request.getParameter("pwd2");
        String username = request.getParameter("name");

        String yanzhengma = request.getParameter("yanzhengma");
        String numbers = (String) request.getSession().getAttribute("numbers");

        if (yanzhengma.equals(numbers)){
            String md5password = MD5Util.string2MD5(password);
            String email = (String) request.getSession().getAttribute("email");
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(md5password);
            userService.save(user);
            model.addAttribute("msg","恭喜注册成功，请返回登录");
        }else {
            model.addAttribute("msg","验证码错误，请重新输入");
        }
        return "register";
    }

    //邮箱验证激活
    @PostMapping("registerTemplates")
    public String registerTemplates(Model model,HttpServletRequest request,HttpServletResponse response)throws MessagingException {

        String email = request.getParameter("em");

        //查询email是否被注册
        User user = userService.queryByEmail(email);

        if (user != null){
            model.addAttribute("msg","该邮箱已被注册");
        }else {
            String numbers = EmailCodeUtils.getNumber();
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("numbers",numbers);
            httpSession.setAttribute("email",email);
            String from = username;
            String to = email;
            String subject = "用户激活";//标题
            String body = "验证码为："+numbers;
            String smtpHost = "smtp.qq.com";
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", smtpHost); // 发件人的邮箱的 SMTP服务器地址
            props.setProperty("mail.smtp.auth", "true"); // 请求认证，参数名称与具体实现有关
            // 创建Session实例对象
            Session session = Session.getDefaultInstance(props);
            // 创建MimeMessage实例对象
            MimeMessage message = new MimeMessage(session);
            // 设置发件人
            message.setFrom(new InternetAddress(from));
            // 设置收件人
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            // 设置发送日期
            message.setSentDate(new Date());
            // 设置邮件主题
            message.setSubject(subject);
            // 设置纯文本内容的邮件正文
            message.setText(body);
            // 保存并生成最终的邮件内容
            message.saveChanges();
            // 设置为debug模式, 可以查看详细的发送 log
            session.setDebug(true);
            // 获取Transport对象
            Transport transport = session.getTransport("smtp");
            // 第2个参数需要填写的是QQ邮箱的SMTP的授权码，什么是授权码，它又是如何设置？
            transport.connect(from, "dzhaifpacfpfggac");
            // 发送，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            model.addAttribute("msg2","🐕邮箱可用，请点击注册用户");

        }
        return "registerTemplates";
    }



    //登录
    @RequestMapping("logintest")
    public String logintest(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String email = request.getParameter("name");
        String password = request.getParameter("pwd");
        String remember = request.getParameter("remember");

        //查询该username是否存在，实际上该username是数据库中的email
        User user1 = userService.queryByEmail(email);

        if (user1 == null) {
            model.addAttribute("lmsg","该用户尚未注册");
        } else {
            String password2 = MD5Util.string2MD5(password);    //经过一次加密
            String password1 = user1.getPassword(); //从数据库中获得的用户密码
            if (!password1.equals(password2)) {
                model.addAttribute("pmsg","用户密码错误");
            } else {
                Integer userid = user1.getId();
                Cookie cookieId = new Cookie("userid",userid.toString());
                Cookie cookikeName = new Cookie("username",user1.getUsername());
                response.addCookie(cookieId);
                response.addCookie(cookikeName);
//                session.setAttribute("user",user1);
//                session.setAttribute("user",user1);
                //将用户的type修改成1，说明登录成功
                userService.updateType1(userid);
                if ("true".equals(remember)) {
                    //点击了记住密码,并且添加cookie，寿命为60*60
                    Cookie rememberck = new Cookie("remember", remember);
                    rememberck.setMaxAge(60 * 60);
                    response.addCookie(rememberck);
                    Cookie emailck = new Cookie("name", email);
                    emailck.setMaxAge(60 * 60);
                    response.addCookie(emailck);
                    Cookie passwordck = new Cookie("pwd", password);
                    passwordck.setMaxAge(60 * 60);
                    response.addCookie(passwordck);
                } else {
                    //清除cookie
                    Utils.closeCookie(request, response);
                }
                //获取该id的用户名存入到model中
                String username = user1.getUsername();
                Cookie userNameCookie = new Cookie("username",username);
                Cookie userIdCookie = new Cookie("userid",userid.toString());
                userNameCookie.setPath("/");
                userNameCookie.setPath("/");
                response.addCookie(userIdCookie);
                response.addCookie(userNameCookie);
                request.getSession().setAttribute("username",username);
                request.getSession().setAttribute("userid",userid);
                model.addAttribute("username",user1.getUsername());
                model.addAttribute("userid",user1.getId());
                return "index";
            }
        }
        return "login";
    }

    @RequestMapping("test")
    public String test(HttpServletRequest request){
        request.getSession().setAttribute("user","user");
        return "index";
    }


    //注销
    @RequestMapping("outLogin")
    public String outLogin(HttpServletRequest request,HttpServletResponse response){
//        String username = (String)request.getSession().getAttribute("username");
        Integer userid = (Integer) request.getSession().getAttribute("userid");
//        //将该用户的type修改成0，表示该用户注销退出登录
//        userService.updateType0(userid);
        //删除session中用户的值
//        request.getSession().removeAttribute("userid");
//        request.getSession().removeAttribute("user");
//        request.getSession().removeAttribute("username");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        //用户注销之后返回游客页面
        return "index";
    }


    @RequestMapping("send")
    public String RePassword(HttpServletRequest request, HttpServletResponse response) throws MessagingException {

        //获取前端的email，接收者
        String email = request.getParameter("email");
        User user = userService.queryByEmail(email);

        if(user != null) {
            //说明该邮箱存在
            //发送随机验证码
            String numbers = EmailCodeUtils.getNumber();
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("numbers",numbers);
            httpSession.setAttribute("email",email);
            httpSession.setAttribute("user",user);

            String from = username;
            String to = email;
            String subject = "我是测试";//标题
            String body = "验证码为："+numbers;
            String smtpHost = "smtp.qq.com";
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", smtpHost); // 发件人的邮箱的 SMTP服务器地址
            props.setProperty("mail.smtp.auth", "true"); // 请求认证，参数名称与具体实现有关
            // 创建Session实例对象
            Session session = Session.getDefaultInstance(props);
            // 创建MimeMessage实例对象
            MimeMessage message = new MimeMessage(session);
            // 设置发件人
            message.setFrom(new InternetAddress(from));
            // 设置收件人
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            // 设置发送日期
            message.setSentDate(new Date());
            // 设置邮件主题
            message.setSubject(subject);
            // 设置纯文本内容的邮件正文
            message.setText(body);
            // 保存并生成最终的邮件内容
            message.saveChanges();
            // 设置为debug模式, 可以查看详细的发送 log
            session.setDebug(true);
            // 获取Transport对象
            Transport transport = session.getTransport("smtp");
            // 第2个参数需要填写的是QQ邮箱的SMTP的授权码，什么是授权码，它又是如何设置？
            transport.connect(from, "dzhaifpacfpfggac");
            // 发送，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            return "repassword";
        }else {
            return "false";
        }

    }


    //找回密码
    @PostMapping("repassword")
    @ResponseBody
    public String rePassword(HttpServletRequest request, HttpServletResponse response){
        String yanzhengma = request.getParameter("yanzhengma");
        String newpassword = request.getParameter("newpassword");
        String repeatpassword = request.getParameter("repeatpassword");

        String numbers = (String) request.getSession().getAttribute("numbers");

        if (yanzhengma.equals(numbers)){
            if (newpassword.equals(repeatpassword)){
                String email = (String) request.getSession().getAttribute("email");
                //密码加密
                String md5password = MD5Util.string2MD5(newpassword);
                //更新数据库中的密码
                userService.updatePassword(email,md5password);

                return "密码修改成功";
            }else {
                return "两次密码输入不同";
            }
        }else {
            return "验证码错误！";
        }
    }

    //修改用户名跳转页面
    @RequestMapping("User")
    public String User(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String username = null;
        String email = null;
        if (user!=null){
            username = user.getUsername();
            email = user.getEmail();
        }

        session.setAttribute("username",username);
        session.setAttribute("email",email);
        return "User";
    }

    @PostMapping("updateUser")
    public String updateUser(HttpServletRequest request,User user,HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        userService.updateUsername(username,email);
        request.getSession().setAttribute("username",username);
        return "success";
    }












    @GetMapping("index")
    public String index(HttpServletRequest request,Model model){
        CommonUtil.setUserNameIdByCookie(request,model);
        return "index";
    }


    @GetMapping("add")
    public String add(HttpServletRequest request,Model model){
        CommonUtil.setUserNameIdByCookie(request,model);
        return "addNewVt";
    }


}
