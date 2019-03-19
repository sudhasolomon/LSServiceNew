package com.financyear.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.financyear.dto.UserDto;
import com.financyear.model.User;
import com.financyear.service.UserService;
import com.financyear.utils.Constants;
import com.financyear.utils.StatusMessage;
import com.financyear.utils.TransformDtoToEntity;
import com.financyear.utils.TransformEntityToDto;
import com.financyear.utils.Utils;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	UserService userService;
	
	Log log = LogFactory.getLog(LoginController.class);
	@RequestMapping(value="saveOrUpdateUser", method=RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdateUser(HttpServletRequest request,
			@RequestBody UserDto dto){
		UserDto userDto = null;
		StatusMessage status = new StatusMessage();
		if(!Utils.isBlank(dto.getUserId())){
			User user= userService.getUserById(Integer.parseInt(dto.getUserId()));
			TransformDtoToEntity.saveOrUpdateUser(user,dto);
			User userObj = userService.update(user);
			status.setStatusCode(String.valueOf(200));
			status.setStatusMessage(" ** User Updated SuccessFully");
			
		}else{
		if(!Utils.isBlank(dto.getUserName())){
			userDto = userService.getUserByCredentials(dto.getUserName(), null);
			if(!Utils.isBlank(userDto.getUserName())){
				status.setStatusCode(String.valueOf(500));
				status.setStatusMessage(dto.getUserName()+" ** User already exists please create new");
			}else{
				User user= new User();
				TransformDtoToEntity.saveOrUpdateUser(user,dto);
				User userObj = userService.saveUser(user);
				status.setStatusCode(String.valueOf(200));
				status.setStatusMessage(" ** User Created SuccessFully");
			}
		}
		}
		log.info("save or update user "+dto);
		
		return new ResponseEntity<StatusMessage>(status, HttpStatus.OK);
	}
	
	@RequestMapping(value="loginAction", method=RequestMethod.POST)
	public ResponseEntity<?> getloginAction(HttpServletRequest request,
			@RequestBody UserDto dto){
		StatusMessage status = new StatusMessage();
		if(!Utils.isBlank(dto.getUserName())&& !Utils.isBlank(dto.getPassword())){
			UserDto userDto = userService.getUserByCredentials(dto.getUserName(),dto.getPassword());
			if(!Utils.isBlank(userDto.getUserId())){
				HttpSession session = request.getSession();
				session.setAttribute(Constants.LOGIN_USER, userDto);
				status.setStatusCode(String.valueOf(200));
				return new ResponseEntity<StatusMessage>(status, HttpStatus.OK);
			}else{
				status.setStatusCode(String.valueOf(500));
				status.setStatusMessage("User "+dto.getUserName()+" Not Found");
				return new ResponseEntity<StatusMessage>(status, HttpStatus.OK);
			}
			
		}
		else{
			return new ResponseEntity<List<?>>(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="getUserById", method=RequestMethod.POST)
	public ResponseEntity<?> getUserById(HttpServletRequest request,
			@RequestParam("userId") String userId){
		StatusMessage status = new StatusMessage();
		if(Utils.getLoginUserName(request) != null){
			UserDto dto = new UserDto();
		if(!Utils.isBlank(userId)){
				User user = userService.getUserById(Integer.parseInt(userId));
				TransformEntityToDto.getLogedInUserDto(dto,user);
		}
		return new ResponseEntity<UserDto>(dto, HttpStatus.OK);
		}else{
			status.setStatusCode(String.valueOf(500));
			status.setStatusMessage("User  Not Found");
			return new ResponseEntity<StatusMessage>(status, HttpStatus.FORBIDDEN);
	 }
	}
	
	
	@RequestMapping(value="getLoggedInPersonInfo", method=RequestMethod.GET)
	public ResponseEntity<?> getLoggedInPersonInfo(HttpServletRequest request){
		HttpSession session = request.getSession();
			UserDto userDto = null;
					userDto = (UserDto) session.getAttribute(Constants.LOGIN_USER);
				return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="logoutAction", method=RequestMethod.GET)
	public ResponseEntity<?> getlogoutAction(HttpServletRequest request){
		StatusMessage status = new StatusMessage();
		 
		UserDto dto = null;
		HttpSession session = request.getSession();
		dto = (UserDto) session.getAttribute(Constants.LOGIN_USER);
		if(dto != null){
			session.removeAttribute(Constants.LOGIN_USER);
			session.invalidate();
			status.setStatusCode(String.valueOf(200));
			return new ResponseEntity<StatusMessage>(status, HttpStatus.OK);
		}else{
			return new ResponseEntity<List<?>>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	@RequestMapping(value="changePassword", method=RequestMethod.POST)
	public ResponseEntity<?> changePassowrd(HttpServletRequest request,
			@RequestBody UserDto dto){
		StatusMessage status = new StatusMessage();
		if(Utils.getLoginUser(request) != null){
			User user = userService.getUserById(Integer.parseInt(Utils.getLoginUser(request).getUserId()));
			if(dto.getPassword().equals(user.getPassword())){
				 
				user.setPassword(dto.getNewPassword() != null ? dto.getNewPassword() : user.getPassword());
				userService.update(user);
				status.setStatusCode(String.valueOf(200));
				return new ResponseEntity<StatusMessage>(status, HttpStatus.OK);
			}else{
				status.setStatusCode(String.valueOf(500));
				status.setStatusMessage("User "+user.getUserName()+" Psswords dosn't match");
				return new ResponseEntity<StatusMessage>(status, HttpStatus.OK);
			}
			
		}
		else{
			return new ResponseEntity<List<?>>(HttpStatus.FORBIDDEN);
		}
	}
	
	
}
