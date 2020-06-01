package com.sample.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.model.Courses;
import com.sample.parser.CourseParser;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping("/services/api/")
public class CourseController {

	@Autowired
	private Executor executor;
	@Value("${api.timeout}")
	private int apiTimeout;
	@Autowired
	private CourseParser parser;

	// As DB layer is not available used for storing data
	private final static Map<Integer, Courses.CourseModel> courseDetails = new ConcurrentHashMap<>();
	private AtomicInteger id = new AtomicInteger(0);

	/**
	 * This method is responsible for creation of student
	 * @param studentData
	 * @return
	 */
	@PostMapping(value = {
			"/courses/create" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<String>> create(@NotNull @RequestBody final String studentData) {

		Courses.CourseModel courses = parser.getObjectsfromJson(studentData, Courses.CourseModel.class);
		
		if(StringUtils.isEmpty(courses.getName())) {
			throw new ConstraintViolationException("Failed to provide course name", new HashSet<ConstraintViolation<?>>());
		}
		courses.setCid(id.incrementAndGet());

		return CompletableFuture.supplyAsync(() -> {
			courseDetails.put(Integer.valueOf(courses.getCid()), courses);
			return new ResponseEntity<String>("Course created Successfully", HttpStatus.OK);

		}, executor).orTimeout(apiTimeout, TimeUnit.MILLISECONDS);

	}

	/**
	 * This method returns all the available courses
	 * @return
	 */
   @GetMapping(value = {"/courses" },consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public Courses getAllStudents(){
		
	    Courses students = new Courses();
	    List<Courses.CourseModel> model = new ArrayList<>();
	    
	    courseDetails.forEach((key, value) -> {
	    	model.add(value);
	    });
	    
	    model.sort( (s1, s2) -> s1.getName().compareTo(s2.getName()));
	    students.setCourses(model);
		
		return students;
	}
   
   /**
    * This method returns course based on course id
    * @param id
    * @return
    */
   @GetMapping(value = {"/course/{id:[\\d]+}" },consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public Courses.CourseModel getCourseById(@NotNull @PathVariable("id") final Integer id){
	    return courseDetails.get(id);
   }
   
   /**
    * This method remove course details based on course id
    * @param id
    * @return
    */
	@GetMapping(value = {
			"/course/remove/{id:[\\d]+}" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> removeCourseById(@NotNull @PathVariable("id") final Integer id) {

		courseDetails.remove(id);
		return new ResponseEntity<String>("Course removed successfully", HttpStatus.OK);
	}
	
	@GetMapping(value = {
			"/course/{name:[a-zA-Z\\s]+}" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Courses.CourseModel getCourseByName(@NotNull @PathVariable("name") final String name) {
		return courseDetails.values().stream().filter(s -> s.getName().equalsIgnoreCase(name)).findAny().orElseThrow();
	}

}