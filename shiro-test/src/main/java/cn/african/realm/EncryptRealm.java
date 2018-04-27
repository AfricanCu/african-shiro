package cn.african.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class EncryptRealm extends AuthorizingRealm{
	// 模拟数据库
	Map<String, String> userMap = new HashMap<>();
	{
		//md5加密
//		userMap.put("Mark", "e10adc3949ba59abbe56e057f20f883e");
		//md5加盐加密
		userMap.put("Mark", "f51703256a38e6bab3d9410a070c32ea");
		super.setName("customRealm");
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		Set<String> roles = getRolesByUsername(username);
		Set<String> permissions = getPermissionsByUsername(username);

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roles);
		authorizationInfo.setStringPermissions(permissions);
		return authorizationInfo;
	}

	/**
	 * 模拟从数据库中获取权限数据
	 * 
	 * @param username
	 * @return
	 */
	private Set<String> getPermissionsByUsername(String username) {
		Set<String> sets = new HashSet<>();
		sets.add("user:delete");
		sets.add("user.add");
		return sets;
	}

	/**
	 * 模拟从数据库中获取角色数据
	 * 
	 * @param username
	 * @return
	 */
	private Set<String> getRolesByUsername(String username) {
		Set<String> sets = new HashSet<>();
		sets.add("admin");
		sets.add("user");
		return sets;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 1. 从主体传过来的信息中得到用户名
		Object username = token.getPrincipal();
		// 2. 通过用户名从数据库中取得凭证
		String password = getPasswordByUserName(username);
		if (password == null) {
			return null;
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("Mark", password, "customRealm");
		//加盐之后需要加上这句代码
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("salt"));
		return authenticationInfo;
	}

	/**
	 * 该方法模拟从数据库中读取密码
	 * 
	 * @param username
	 * @return
	 */
	private String getPasswordByUserName(Object username) {
		return userMap.get(username);
	}
	
	public static void main(String[] args) {
		Md5Hash md5Hash = new Md5Hash("123456","salt");
		System.out.println(md5Hash.toString());
	}

}
