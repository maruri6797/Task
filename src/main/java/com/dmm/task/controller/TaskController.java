package com.dmm.task.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.EditTaskForm;
import com.dmm.task.form.TaskForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class TaskController {
	@Autowired
	private TasksRepository repo;
	
	@GetMapping("/main")
	public String main(Model model) {
		List<Tasks> list = repo.findAll();
		Collections.reverse(list);
		model.addAttribute("tasks", list);
		return "/main";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		TaskForm taskForm = new TaskForm();
		model.addAttribute("taskForm", taskForm);
		return "create";
	}
	
	@PostMapping("/main/create")
	public String mainCreate(@Validated TaskForm taskForm, BindingResult bindingResult, 
			@AuthenticationPrincipal AccountUserDetails user, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("taskForm", taskForm);
			return "/create";
		}
		
		Tasks task = new Tasks();
		task.setName(user.getUserName());
		task.setTitle(taskForm.getTitle());
		task.setText(taskForm.getText());
		task.setDate(taskForm.getDate());
		
		repo.save(task);
		return "redirect:/main";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model, EditTaskForm editTaskForm) {
		model.addAttribute(repo.findById(id));
		model.addAttribute("editTaskForm", editTaskForm);
		return "/edit/{id}";
	}
	
	
	@PostMapping("/tasks/edit/{id}")
	public String update(@Validated EditTaskForm editTaskForm, @PathVariable Integer id, BindingResult bindingResult, @AuthenticationPrincipal AccountUserDetails user, Model model) {
		if (bindingResult.hasErrors()) {
			return "edit/{id}";
		}
		Tasks task = new Tasks();
		task.setTitle(editTaskForm.getTitle());
		task.setText(editTaskForm.getText());
		task.setDate(editTaskForm.getDate());
		task.setDone(editTaskForm.getDone());
		repo.save(task);
		return "redirect:/main";
	}
	
	@PostMapping("/tasks/delete/{id}")
	public String delete(@PathVariable Integer id) {
		repo.deleteById(id);
		return "redirect:/main";
	}
}
