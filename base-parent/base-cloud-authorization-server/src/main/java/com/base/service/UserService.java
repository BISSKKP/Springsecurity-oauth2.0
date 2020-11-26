package com.base.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.mapper.UserMapper;
import com.base.pojo.sys.SysUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("登陆。。");
		
		if(StringUtils.isBlank(username)) {
				throw new UsernameNotFoundException("请输入账户信息");
		}
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
		return mapper.selectOne(queryWrapper);
	}

}
