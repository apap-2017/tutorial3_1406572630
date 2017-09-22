package com.example.tutorial3.controller;

import com.example.tutorial3.model.StudentModel;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    private final StudentService studentService;

    public StudentController(){
        studentService = new InMemoryStudentService();
    }

    @RequestMapping("/student/add")
    public String add(@RequestParam(value = "npm", required = true) String npm,
                      @RequestParam(value = "name", required = true) String name,
                      @RequestParam(value = "gpa", required = true) double gpa){
        StudentModel student = new StudentModel(npm, name, gpa);
        studentService.addStudent(student);
        return "add";
    }

    @RequestMapping("/student/view")
    public String view (Model model, @RequestParam(value = "npm", required = false) String npm){
        if(npm == null){
            return "notfound";
        }

        StudentModel student = studentService.selectStudent(npm);
        model.addAttribute("student", student);
        return "view";
    }

    @RequestMapping("/student/viewall")
    public String viewAll(Model model){
        List<StudentModel> students = studentService.selectAllStudents();
        model.addAttribute("students", students);
        return "viewAll";
    }

    @RequestMapping("/student/view/{npm}")
    public String view (Model model, @PathVariable Optional<String> npm){
        StudentModel student = studentService.selectStudent(npm.get());
        if(student == null){
            return "notfound";
        }
        model.addAttribute("student", student);
        return "view";
    }
    
    @RequestMapping("/student/delete/{NPM}")
	public String deleteNPM(Model model, @PathVariable String NPM) {
		StudentModel student = studentService.selectStudent(NPM);
		if(student == null){
            return "notfound";
        }
			List<StudentModel> students = studentService.selectAllStudents();
			students.remove(student);
			model.addAttribute("student", student);
			return "delete";
	}
}
