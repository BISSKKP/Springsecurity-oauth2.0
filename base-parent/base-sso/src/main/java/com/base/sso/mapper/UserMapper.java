package com.base.sso.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.pojo.sys.SysUser;

@Mapper
public interface UserMapper extends BaseMapper<SysUser> {

}
