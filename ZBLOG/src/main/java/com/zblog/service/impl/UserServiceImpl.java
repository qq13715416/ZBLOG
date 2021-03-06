package com.zblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zblog.dao.UserDao;
import com.zblog.dmo.User;
import com.zblog.service.UserService;
import com.zblog.util.CryptUtils;

/**
 * 用户服务实现类
 * 
 * @author  Ziv
 * @version  [版本号, 2014-2-12]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User getUser(User user) {
		
		if (user.getPassword() != null) {				
			//对密码加密，然后进行匹配查询
			user.setPassword(CryptUtils.
					encryptString(user.getPassword()));
		}
		return userDao.getUser(user);
	}

	@Override
	public boolean updateUser(User user) {
		
		if (user.getPassword() != null) {	
			//对密码加密
			user.setPassword(CryptUtils.
					encryptString(user.getPassword()));
		}
		int result = userDao.updateUser(user);
		return result > 0;
	}
     
}
