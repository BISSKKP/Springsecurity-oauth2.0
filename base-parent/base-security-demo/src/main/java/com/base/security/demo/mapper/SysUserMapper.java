/*
*
* SysUserMapper.java
* Copyright(C) 2019-2020 ACID
* @date 2019-04-10
*/
package com.base.security.demo.mapper;


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