package com.financyear.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financyear.dto.ThemeDto;
import com.financyear.model.Themes;
import com.financyear.service.ThemeService;
import com.financyear.utils.StatusMessage;
import com.financyear.utils.TransformDtoToEntity;
import com.financyear.utils.TransformEntityToDto;
import com.financyear.utils.Utils;

@Controller
@RequestMapping("/theme")
public class ThemeController {

	@Autowired
	private ThemeService themeService;
	
	
	@RequestMapping(value="saveOrUpdateTheme", method = RequestMethod.POST)
	 public ResponseEntity<?> saveOrUpdateTheme(HttpServletRequest request,
			 @RequestBody ThemeDto dto){
		 System.out.println(dto.getId());
		 StatusMessage  statusmessage = new StatusMessage();
		 if(Utils.getLoginUserName(request) != null){
			 Themes theme = null;
			 if(!Utils.isBlank(dto.getThemeName()) && !Utils.isBlank(dto.getId())){
				 theme = themeService.getCurrentTheme(request);
				 TransformDtoToEntity.updateCurrentTheme(theme, dto, request);
				 themeService.updateCurrentTheme(theme);
				 
				 theme = themeService.getThemeByName(dto.getNewTheme()!=null?dto.getNewTheme():dto.getThemeName(), request);
				 TransformDtoToEntity.updateNewTheme(theme, dto, request);
				 themeService.updateCurrentTheme(theme);
			 }else{
				 theme = new Themes();
				 TransformDtoToEntity.saveTheme(theme, dto, request);
				 themeService.saveTheme(theme);
			 }
			 statusmessage.setStatusCode(String.valueOf(200));
			 return new ResponseEntity<StatusMessage>(statusmessage, HttpStatus.OK);
		 }
		 else{
			 statusmessage.setStatusCode(String.valueOf(500));
				statusmessage.setStatusMessage("Please Log in agian");
				return new ResponseEntity<StatusMessage>(statusmessage, HttpStatus.FORBIDDEN);
		 }
		
	 }
	
	@RequestMapping(value="getCurrentTheme", method=RequestMethod.GET)
	public ResponseEntity<?> getCurrentTheme(HttpServletRequest request){
		Themes theme = null;
		ThemeDto dto = new ThemeDto();
		StatusMessage  statusmessage = new StatusMessage();
		if(!Utils.isBlank(Utils.getLoginUserName(request))){
			theme = themeService.getCurrentTheme(request);
			TransformEntityToDto.getTheme(theme,dto);
			return new ResponseEntity<ThemeDto>(dto, HttpStatus.OK);
		}else{
			 statusmessage.setStatusCode(String.valueOf(500));
				statusmessage.setStatusMessage("Please Log in agian");
				return new ResponseEntity<StatusMessage>(statusmessage, HttpStatus.FORBIDDEN);
		 }
		
	}
	
}
