package cn.african.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import cn.african.realm.CustomRealm;

public class CustomRealmTest {

	@Test
	public void testCustomRealm() {
		CustomRealm customRealm = new CustomRealm();
		// 构建SecurityManager环境
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		securityManager.setRealm(customRealm);
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
		subject.login(token);
		System.out.println(subject.isAuthenticated());
		subject.checkRole("admin");
		subject.checkRoles("admin", "user");
		subject.checkPermissions("user:delete","user.add");
	}
}
