package com.financyear.dao;

import com.financyear.model.User;

public interface UserDao extends GenericDao<User,Integer>{

	public User getUserByCredentials(String userName, String password);

	public User getUserByCredentials(Integer userId);

}
