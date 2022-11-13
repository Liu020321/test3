package org.example.index;

import java.util.Arrays;

import org.example.common.model.Student;
import org.example.student.StudentService;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfplugin.mail.MailKit;

/**
 * @Author www.hiai.top
 * @Email goodsking@163.com
 */
public class IndexController extends Controller {

	StudentService service = new StudentService();

	// 前台渲染登录界面
	public void index() {
		render("index.html");
	}

	/**
	 * 用户登录验证
	 */
	@ActionKey("/login")
	public void login() {
		// 获取用户输入的账号和密码
		String account = getPara("account");
		String password = getPara("password");

		// render("index.html");
		// 从数据库中获取数据
		Student userMassage = service.findUserByNameAndPassword(account);
		// Student userMassage = service.findById(1);

		// 验证用户名和密码是否正确
		if (userMassage != null) {
			if (password.equals(userMassage.getStr("password"))) {// 判断数据库中的密码与用户输入的密码是否一致
				// flag = true;
				// getSession().setAttribute("nickname",
				// userMassage.getStr("account"));//设置session，保存登录用户的昵称
				// 保存Cookie
				setSessionAttr("loginUser", userMassage);
				redirect("/student");
			} else {
				setAttr("errorMsg", "用户名或密码错误,请重新输入。");
				render("index.html");
				return;
			}
		} else {
			setAttr("errorMsg", "该用户名不存在,请重新输入。");
			render("index.html");
			return;
		}

	}

	/**
	 * 用户退出
	 */
	@ActionKey("logout")
	public void logout() {

		// String loginUser = String.valueOf(getSessionAttr("loginUser"));
		// 移除 cookie
		// if (getSession().getAttribute("account") != null) {
		// 清除session

		// }
		getSession().removeAttribute("loginUser");
		render("index.html");
	}

	/**
	 * 前台渲染找回密码界面
	 */
	@ActionKey("redme")
	public void redme() {
		render("redme.html");
	}

	/**
	 * 发送邮箱
	 */
	@ActionKey("emailsend")
	public void emailsend() {
		// 收件人地址
		String addresseeEmail = getPara("xueEmail");
		// 邮件标题
		String emailTitle = "拾光分享网密码找回邮件";
		// 邮件内容
		String emailContent = "";

		// 验证用户的邮箱是否注册
		Student emailMassage = service.findEmail(addresseeEmail);

		// 判断是否从数据库中获取了数据
		if (emailMassage != null) {
			// 随机生成一个6位数的密码，后期可以用MD5密码，这里只做测试使用
			int newNum = (int) ((Math.random() * 9 + 1) * 100000);
			// newNum = Integer.parseInt(s
			emailContent = "您在拾光分享网中的账号邮箱为" + addresseeEmail + "重置了为密码为 ：" + newNum + "，如果不是您的操作，请忽视。";
			// 更新数据库中的密码
			service.useEmailUpdatePassword(newNum, addresseeEmail);
			try {
				MailKit.send(addresseeEmail, Arrays.asList(addresseeEmail), emailTitle, emailContent);
				setAttr("errorMsg", "邮箱发送成功，请注意查收。");
				// System.out.println("成功");
				render("redme.html");
				return;
			} catch (Exception e) {
				e.printStackTrace();
				setAttr("errorMsg", "邮箱发送失败，请联系管理员。");
				render("redme.html");
				return;
			}
		} else {
			setAttr("errorMsg", "该邮箱不存在,请重新输入。");
			render("redme.html");
			return;
		}
	}

	@ActionKey("/test")
	public void test() {
		String account = "js001";
		// Student users = Student.dao.findFirst("select * from t_student where account
		// = ? ", account);
		// Student userMassage = service.findUserByNameAndPassword(account);
		Student userMassage = service.findById(2);
		// setAttr("users", users);
		renderJson(userMassage);
	}

}
