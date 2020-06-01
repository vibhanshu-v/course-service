package com.sample.model;

import java.util.List;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class Courses {

	private List<CourseModel> courses;
	@Data
	public static class CourseModel {
		
		@NonNull
		private String name;
		private int cid;
	}
}
