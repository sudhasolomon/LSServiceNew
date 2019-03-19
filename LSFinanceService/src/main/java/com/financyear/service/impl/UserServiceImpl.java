package com.financyear.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financyear.dao.UserDao;
import com.financyear.dto.UserDto;
import com.financyear.model.User;
import com.financyear.service.UserService;
import com.financyear.utils.TransformEntityToDto;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	public User saveUser(User user) {

		User userobj = userDao.save(user);
		 return userobj;
	}

	public UserDto getUserByCredentials(String userName, String password) {
		UserDto dto = new UserDto();
	User user = userDao.getUserByCredentials(userName, password); 
	TransformEntityToDto.getLogedInUserDto(dto,user);
		return dto;
	}

	public User  getUserById(Integer userId) {
		 
		return userDao.getUserByCredentials(userId); 
			 
	}

	public User update(User user) {
		 
		return userDao.update(user);
	}

}
