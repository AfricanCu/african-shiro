package cn.african.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 认证demo
 * @author ZMJ
 *
 */
public class AuthenticationTest {
	SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

	@Before
	public void addUser() {
		simpleAccountRealm.addAccount("Mark", "123456");
	}

	@Test
	public void testAuthentication() {
		// 构建SecurityManager环境
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		securityManager.setRealm(simpleAccountRealm);
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
		subject.login(token);
		System.out.println(subject.isAuthenticated());
		subject.logout();
		System.out.println(subject.isAuthenticated());
	}
}
