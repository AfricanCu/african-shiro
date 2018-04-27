package cn.african.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

public class JdbcRealmTest {
	
	DruidDataSource dataSource = new DruidDataSource();
	{
		dataSource.setUrl("jdbc:mysql://localhost:3306/african-shiro");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
	}
	
	/**
	 * 这个方法中使用了JdbcRealm中内置的sql查询语句
	 * 也可以设置使用自定义的sql查询语句
	 */
	@Test
	public void testJdbcRealm() {
		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		//使用JdbcRealm时，要设置权限开关，默认为false
		jdbcRealm.setPermissionsLookupEnabled(true);
		
		// 构建SecurityManager环境
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		securityManager.setRealm(jdbcRealm);
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
		subject.login(token);
		System.out.println(subject.isAuthenticated());
		subject.checkRole("admin");
		subject.checkRoles("admin","user");
		subject.checkPermission("user:select");
		subject.checkPermissions("user:select", "user:delete");
	}
}
