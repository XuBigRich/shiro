package top.piao888.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;

public class ShiroRealm extends AuthenticatingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1 . 把AuthenticationToken 转换为UsernamePasswordToken
		UsernamePasswordToken upToken=(UsernamePasswordToken)token;
		//2. 从UsernamePasswordToken中来获取username
		String username=upToken.getUsername();
		//3. 调用数据库方法，从数据库中查询username对应的用户记录
		
		//4. 若用户不存在，则可以抛出UnknownAccountException 异常
		//5. 根据用户信息的情况，决定是否需要抛出其他的 AuthenticationException异常
		//6.根据用户的情况，来构建AuthenticationInfo 对象并返回
		System.out.println("AuthenticationInfo"+token);
		return null;
	}


}
