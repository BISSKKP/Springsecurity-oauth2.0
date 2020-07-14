package com.base.security.demoapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.pojo.sys.SysUser;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
	
	/**
	 * 根据邮箱查询
	 * @param sysUser
	 * @return
	 */
	public SysUser getUserByEmail(@Param("email")String  email);
	
	
	
	
}