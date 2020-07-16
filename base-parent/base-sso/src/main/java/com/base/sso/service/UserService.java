package com.base.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.pojo.sys.SysUser;
import com.base.sso.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("登陆。。1");
		SysUser user=	getUserByEmail(username);
		
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

	private SysUser getUserByEmail(String username) {
		QueryWrapper< SysUser> queryWrapper=new QueryWrapper<SysUser>();
		queryWrapper.lambda().eq(SysUser::getEmail, username);
		return userMapper.selectOne(queryWrapper);
	}

}
