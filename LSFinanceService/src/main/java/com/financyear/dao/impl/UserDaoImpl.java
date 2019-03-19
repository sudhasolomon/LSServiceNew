package com.financyear.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.financyear.dao.UserDao;
import com.financyear.model.User;
import com.financyear.utils.Utils;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements UserDao{

	public UserDaoImpl() {
		super(User.class);
	}

	@SuppressWarnings("unchecked")
	public User getUserByCredentials(String userName, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<User> user = null;
		if(!Utils.isBlank(userName)){
			StringBuilder hql = new StringBuilder("from User u where ");
			hql.append("u.userName = :userName ");
			params.put("userName", userName);
			if(!Utils.isBlank(password)){
			hql.append(" and u.password = :password");
			params.put("password",password);}
			user = (List<User>)findHqlListByParams(hql.toString(), params);
		}
		return (user.size() > 0 && !user.isEmpty())? user.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public User getUserByCredentials(Integer userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<User> user = null;
		if(userId != null){
			StringBuilder hql = new StringBuilder("from User u where ");
			hql.append("u.id = :userId ");
			params.put("userId", userId);
			user = (List<User>)findHqlListByParams(hql.toString(), params);
		}
		return (user.size() > 0 && !user.isEmpty())? user.get(0) : null;
	}

}
