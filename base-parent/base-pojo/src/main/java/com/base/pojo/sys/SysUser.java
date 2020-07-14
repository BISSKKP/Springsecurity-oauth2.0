/*
*
* SysUser.java
* Copyright(C) 2019-2020 ACID
* @date 2019-04-10
*/
package com.base.pojo.sys;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {
	
	@TableId(type=IdType.UUID)
	private String id;
	
	/**
	 * 姓
	 */
	private String name;

	/**
	 * 名
	 */
	private String firstName;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 账户/邮箱
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;
	
	@TableField(exist = false)
	private String repassword;

	/**
	 * 头像
	 */
	private String headImage;

	/**
	 * 头像id
	 */
	private String headImageId;

	/**
	 * 公司名称
	 */
	private String companyName;
	
	
	private String createBy;
	
	private Date createDate;
	
	private Date updateDate;
	
	private String updateBy;


	/**
	 * 账户状态
	 */
	private String status;

	@TableField(exist = false)
	private String token; // 秘钥

	/**
	 * 角色ids
	 */
	@TableField(exist = false)
	private String roleIds;
	
	/**
	 * 上次登录ip
	 */
	private String ip;
	
	/**
	 * 上次登录时间
	 */
	private Date loginDate;
	
	/**
	 * 准备删除的角色id
	 */
	@TableField(exist = false)
	private String roleToDelIds;

	private static final long serialVersionUID = 1L;

	public SysUser(String id) {
		this.id = id;
	}

	
	
}