package com.anitha.sms.controller;

import com.anitha.sms.entity.Student;
import com.anitha.sms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("studentCount", service.count());
        model.addAttribute("students", service.findAll().stream().limit(5).toList());
        return "dashboard";
    }

    @GetMapping("/students")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("students", service.search(keyword));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        return "students";
    }

    @GetMapping("/students/new")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Add Student");
        return "student-form";
    }

    @PostMapping("/students/save")
    public String save(@Valid @ModelAttribute("student") Student student,
                       BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("validationErrors", result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()).toList());
            model.addAttribute("pageTitle", student.getId() == null ? "Add Student" : "Edit Student");
            return "student-form";
        }
        try {
            service.save(student);
            redirect.addFlashAttribute("successMessage",
                    student.getId() == null ? "Student added successfully." : "Student details updated successfully.");
            return "redirect:/students";
        } catch (IllegalStateException ex) {
            result.rejectValue("email", "duplicate", ex.getMessage());
            model.addAttribute("validationErrors", result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()).toList());
            model.addAttribute("pageTitle", student.getId() == null ? "Add Student" : "Edit Student");
            return "student-form";
        }
    }

    @GetMapping("/students/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", service.findById(id));
        model.addAttribute("pageTitle", "Edit Student");
        return "student-form";
    }

    @PostMapping("/students/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        service.delete(id);
        redirect.addFlashAttribute("successMessage", "Student deleted successfully.");
        return "redirect:/students";
    }
}
