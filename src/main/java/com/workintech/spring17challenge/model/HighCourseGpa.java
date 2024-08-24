package com.workintech.spring17challenge.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class HighCourseGpa implements CourseGpa{
    public int getGpa(){
        return 10;
    }
}
