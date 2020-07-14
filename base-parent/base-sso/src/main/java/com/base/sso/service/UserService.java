package com.base.sso.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.base.pojo.sys.SysUser;

public interface UserService extends  IService<SysUser>{
	
	public SysUser getByEmail(String email);
	
}
