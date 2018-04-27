package cn.african.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import cn.african.realm.EncryptRealm;

/**
 * 加密
 * @author ZMJ
 *
 */
public class EncryptTest {
	
	@Test
	public void testCustomRealm() {
		EncryptRealm encryptRealm = new EncryptRealm();
		// 构建SecurityManager环境
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		securityManager.setRealm(encryptRealm);
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("md5");
		matcher.setHashIterations(1);
		encryptRealm.setCredentialsMatcher(matcher);
		
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
		subject.login(token);
		System.out.println(subject.isAuthenticated());
	}
}
