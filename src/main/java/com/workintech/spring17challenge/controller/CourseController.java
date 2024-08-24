package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.model.Course;
import com.workintech.spring17challenge.model.CourseGpa;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseGpa lowCourseGpa;
    private final CourseGpa mediumCourseGpa;
    private final CourseGpa highCourseGpa;
    private Map<Integer, Course> courses;

    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") CourseGpa lowCourseGpa,
                            @Qualifier("mediumCourseGpa") CourseGpa mediumCourseGpa,
                            @Qualifier("highCourseGpa") CourseGpa highCourseGpa){
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @PostConstruct
    public void init(){
        courses = new HashMap<>();
    }

    @GetMapping
    public List<Course> seeCourses(){
        return courses.values().stream().toList();
    }
    @GetMapping("/{name}")
    public Course seeCourse(@PathVariable String name){
        List<Course> list = courses.values().stream().toList();
        for(Course c: list){
            if(c.getName().equals(name)){
                return c;
            }
        }
        throw new ApiException("The class with the given name does not exist", HttpStatus.NOT_FOUND);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<Course, Integer> addCourse(@RequestBody Course course){
        Map<Course, Integer> result = new HashMap<>();
        if(course.getCredit()<0 || course.getCredit() > 4 || course.getName()==null){
            throw new ApiException("Ders kredisi 0 dan küçük ve ya 4 ten büyük olamaz.", HttpStatus.BAD_REQUEST);
        }
        courses.put(course.getId(), course);
        result.put(course, totalGpa(course));
        return result;
    }
    @PutMapping("/{id}")
    public Map<Integer, Course> updateCourse(@PathVariable int id, @RequestBody Course course){
        courses.put(id, course);
        return courses;
    }
    @DeleteMapping("/{id}")
    public Course delete(@PathVariable int id){
        return courses.remove(id);
    }

    public int totalGpa(Course course){
        int totalGpa = 0;
        if(course.getCredit()<=2){
            totalGpa = course.getGrade().getCoefficient()*course.getCredit()*lowCourseGpa.getGpa();
        } else if(course.getCredit()==3){
            totalGpa = course.getGrade().getCoefficient()*course.getCredit()*mediumCourseGpa.getGpa();
        } else if (course.getCredit()==4){
            totalGpa = course.getGrade().getCoefficient()*course.getCredit()*highCourseGpa.getGpa();
        }
        return totalGpa;
    }
}
