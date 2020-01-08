package top.piao888.shiro.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class ShiroRealm extends AuthorizingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[FirstRealm] doGetAuthenticationInfo");
		System.out.println("AuthenticationInfo" + token);
		// 1 . 把AuthenticationToken 转换为UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		// 2. 从UsernamePasswordToken中来获取username
		String username = upToken.getUsername();
		// 3. 调用数据库方法，从数据库中查询username对应的用户记录
		System.out.println("从数据库中获取username:" + username + "所对应的用户信息。");
		// 4. 若用户不存在，则可以抛出UnknownAccountException 异常
		if ("unknown".equals(username)) {
			throw new UnknownAccountException("用户不存在");
		}
		// 5. 根据用户信息的情况，决定是否需要抛出其他的 AuthenticationException异常
		if ("monster".equals(username)) {
			throw new LockedAccountException("用户被锁定");
		}
		// 6.根据用户的情况，来构建AuthenticationInfo 对象并返回
//		1). principal:认证的实体信息，可以是username,也可以是数据表对应的用户的实体类对象
		Object principl = username;
//        2).credentials: 数据库获取的密码
		Object credentials = null;// "fc1709d0a95a6be30bc5926fdb7f22f4";
		if ("admin".equals(username)) {
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
		} else if ("user".equals(username)) {
			credentials = "098d2c478e9c11555ce2823231e02ec1";
		}
//        3).realmName:当前realm 对象的name。调用父类的getName() 方法即可
		String realmName = getName();
//        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(principl,credentials,realmName);
		// 4). 盐值 一般来说 MD5的盐值加密的 盐是唯一的
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principl, credentials, credentialsSalt, realmName);
		return info;
	}

	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";
		Object credentials = "123456";
		ByteSource credentialsSalt = ByteSource.Util.bytes("user");
		Object salt = credentialsSalt;
		int hashIterations = 1024;

		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

	/*
	 * 授权信息会被shiro 回调方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 1.从principals中获取登录的用户信息
		Object principal = principals.getPrimaryPrincipal();
		//2.利用登录的用户信息来获取当前用户的角色或权限（一般需要查询数据库）
		Set<String> roles=new HashSet<String>();
		roles.add("user");
		if ("admin".equals(principal)) {
			roles.add("admin");
		}
		//3.创建SimpleAuthorizationInfo,并设置其reles属性。
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo(roles);
		//4.返回SimpleAuthorizationInfo对象
		return info;
	}

}
