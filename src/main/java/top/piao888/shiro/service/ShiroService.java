package top.piao888.shiro.service;


import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroService {
	public void testMethod() {
		Session session=SecurityUtils.getSubject().getSession();
		System.out.println(session.getAttribute("key"));
	}
}
