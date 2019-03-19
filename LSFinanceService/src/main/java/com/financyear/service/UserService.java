package com.financyear.service;

import com.financyear.dto.UserDto;
import com.financyear.model.User;

public interface UserService {

	public User saveUser(User user);

	public UserDto getUserByCredentials(String userName, String password);

	public User getUserById(Integer userId);

	public User update(User user);

}
