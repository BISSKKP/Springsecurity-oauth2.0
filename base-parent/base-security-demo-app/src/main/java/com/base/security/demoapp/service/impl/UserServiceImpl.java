package com.base.security.demoapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.pojo.sys.SysUser;
import com.base.security.demoapp.mapper.SysUserMapper;
import com.base.security.demoapp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>   implements UserService,  UserDetailsService,SocialUserDetailsService {
	
	@Autowired
	private SysUserMapper userMapper;
	
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		log.info("social 登录userId："+userId);
		
		
		boolean enabled=true;
		boolean accountNonExpired=true;
		boolean credentialsNonExpired=true;
		boolean accountNonLocked=true; //账户没有被锁住
		
		SocialUser socialUser=new SocialUser(userId, "123456", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		
		return socialUser;
	}

	/**
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("登陆。。");
		SysUser user=	userMapper.getUserByEmail(username);
		
//		User securityUser=	new User(username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		
		if(null==user){
			throw new UsernameNotFoundException("用户不存在");
		}
		
		boolean enabled=true;
		boolean accountNonExpired=true;
		boolean credentialsNonExpired=true;
		boolean accountNonLocked=true; //账户没有被锁住
		
		log.info("登录的账号信息:"+user);
		
		User	securityUser=new User(username, user.getPassword(), enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked,  AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN")) ;
				
		return securityUser;
	}

}

