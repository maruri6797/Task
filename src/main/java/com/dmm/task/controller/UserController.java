package com.dmm.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.repository.UsersRepository;
import com.dmm.task.form.UserForm;

@Controller
public class UserController {
	@Autowired
	private UsersRepository usersRepository;
	
	@GetMapping("/signup")
	public String signUp(Model model) {
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
		return "signup";
	}
	
	@PostMapping("/signup")
	public String registerUser(UserForm userForm) {
		User user = new User();
		user.setName(userForm.getUserName());
		user.setPassword(userForm.getPassword());
		usersRepository.save(user);
		return "redirect:/main";
	}
}
