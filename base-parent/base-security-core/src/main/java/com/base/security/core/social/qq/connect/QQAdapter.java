package com.base.security.core.social.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.base.security.core.social.qq.api.QQ;
import com.base.security.core.social.qq.api.QQUserInfo;

public class QQAdapter implements ApiAdapter<QQ> {

	@Override
	public boolean test(QQ api) {
		// 判断qq服务提供商是否可用
		return false;
	}

	@Override
	public void setConnectionValues(QQ api, ConnectionValues values) {
		QQUserInfo qqUserInfo = null;
		qqUserInfo = api.getQQUserInfo();

		if (qqUserInfo == null) {
			return;
		}

		values.setDisplayName(qqUserInfo.getNickname());
		values.setImageUrl(qqUserInfo.getFigureurl_qq_1());
		values.setProfileUrl(null);// 个人主页
		values.setProviderUserId(qqUserInfo.getOpenId());
	}

	@Override
	public UserProfile fetchUserProfile(QQ api) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStatus(QQ api, String message) {
		// TODO Auto-generated method stub
			
	}

}
