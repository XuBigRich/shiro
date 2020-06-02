package top.piao888.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class SecondRealm extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("[SecondRealm] doGetAuthenticationInfo");
        System.out.println("AuthenticationInfo" + token);
        //1 . 把AuthenticationToken 转换为UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //2. 从UsernamePasswordToken中来获取username
        String username = upToken.getUsername();
        //3. 调用数据库方法，从数据库中查询username对应的用户记录
        System.out.println("从数据库中获取username:" + username + "所对应的用户信息。");
        //4. 若用户不存在，则可以抛出UnknownAccountException 异常
        if ("unknown".equals(username)) {
            throw new UnknownAccountException("用户不存在");
        }
        //5. 根据用户信息的情况，决定是否需要抛出其他的 AuthenticationException异常
        if ("monster".equals(username)) {
            throw new LockedAccountException("用户被锁定");
        }
        //6.根据用户的情况，来构建AuthenticationInfo 对象并返回
//		1). principal:认证的实体信息，可以是username,也可以是数据表对应的用户的实体类对象
        Object principl = username;
//        2).credentials: 数据库获取的密码
        Object credentials = null;//"fc1709d0a95a6be30bc5926fdb7f22f4";
        if ("admin".equals(username)) {
            credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
        } else if ("user".equals(username)) {
            credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
        }
//        3).realmName:当前realm 对象的name。调用父类的getName() 方法即可
        String realmName = getName();
//        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(principl,credentials,realmName);
        //4). 盐值  一般来说 MD5的盐值加密的 盐是唯一的
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principl, credentials, credentialsSalt, realmName);
        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "SHA1";
        Object credentials = "123456";
        ByteSource credentialsSalt = ByteSource.Util.bytes("admin");
        Object salt = credentialsSalt;
        int hashIterations = 1024;

        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(result);
    }


}
