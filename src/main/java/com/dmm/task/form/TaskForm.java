package com.dmm.task.form;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TaskForm {
	@Size(min = 1, max = 50)
	private String title;
	
	@Size(min = 1, max = 200)
	private String text;
	
	private LocalDateTime date;
}
