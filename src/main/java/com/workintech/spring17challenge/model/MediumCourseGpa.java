package com.workintech.spring17challenge.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class MediumCourseGpa implements CourseGpa{
    public int getGpa(){
        return 5;
    }
}
