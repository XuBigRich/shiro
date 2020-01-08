package top.piao888.shiro.handlers;


import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import top.piao888.shiro.service.ShiroService;
@Controller
@RequestMapping("/shiro")
public class ShiroHandlers {
	@Autowired
	private ShiroService shiroService;
	@RequestMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		Subject currentUser =SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		token.setRememberMe(true);
		try {
			currentUser.login(token);
		} 
		// 若没有指定账户 则shiro将会抛出UnknownAccountException 异常
//		catch (UnknownAccountException e) {
//			System.out.println("没有指定的账户");
//		}
		// 若账户存在 ，但密码不匹配 则shiro将会抛出IncorrectCredentialsException 异常
		catch (IncorrectCredentialsException e) {
			System.out.println("密码不正确");
		}
		// 用户被锁定异常LockedAccountException
		catch (LockedAccountException e) {
			System.out.println("账户被锁定");
		}
		//所有异常认证的父类异常
		catch (AuthenticationException e) {
			System.out.println("登录失败："+ e.getMessage());
		}
		return "redirect:/index.jsp";
	}
	@RequestMapping("/testsession")
	public String testSession(HttpSession session) {
		session.setAttribute("key", "session");
		shiroService.testMethod();
		return "redirect:/list.jsp";
	}
}
