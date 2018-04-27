package cn.african.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 检查主体的角色
 * @author ZMJ
 *
 */
public class AuthorizerTest {
	
	SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

	@Before
	public void addUser() {
//		simpleAccountRealm.addAccount("Mark", "123456", "admin");
		simpleAccountRealm.addAccount("Mark", "123456", "admin","user");
	}

	@Test
	public void testAuthorizer() {
		// 构建SecurityManager环境
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		securityManager.setRealm(simpleAccountRealm);
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
		subject.login(token);
		//检查主体是否拥有该角色
		subject.checkRole("admin");
		//检查主体是否拥有这些角色
		subject.checkRoles("admin", "user");
	}
}
