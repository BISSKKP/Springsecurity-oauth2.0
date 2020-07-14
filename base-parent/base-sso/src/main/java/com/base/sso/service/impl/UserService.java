package com.base.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.pojo.sys.SysUser;
import com.base.sso.mapper.UserMapper;
import com.base.sso.service.UserService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional(readOnly=true)
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser>  implements UserService, UserDetailsService {

	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("登录： "+username);
		
		SysUser  user=getByEmail(username);
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
	
	/**
	 * 根据you'xin
	 * @param email
	 * @return
	 */
	@Override
	public SysUser getByEmail(String email){
		
		QueryWrapper< SysUser> queryWrapper=new QueryWrapper<>();
		queryWrapper.lambda().eq(SysUser::getEmail, email);
		
		return this.getOne(queryWrapper);
	}
	
	

}
