package top.piao888.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {
	public LinkedHashMap<String, String> builderFilterChainDefinitionMap() {
		LinkedHashMap<String, String> map=new LinkedHashMap<String, String>();
		map.put("/login.jsp","anon");
		map.put("/shiro/login","anon");
		map.put("/shiro/logout","logout");
		map.put("/admin.jsp","authc,roles[admin]");
		map.put("/user.jsp","authc,roles[user]");
		map.put("/**","authc");
		map.put("/user.jsp","anon");
		return map;
	}
	
	

}
