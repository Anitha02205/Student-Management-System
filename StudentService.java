package com.anitha.sms.service;

import com.anitha.sms.entity.Student;
import com.anitha.sms.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class StudentService {
    private final StudentRepository repository;
    public StudentService(StudentRepository repository) { this.repository = repository; }

    @Transactional(readOnly = true)
    public List<Student> findAll() { return repository.findAll(); }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Student> search(String keyword) {
        return (keyword == null || keyword.isBlank()) ? findAll() : repository.search(keyword.trim());
    }

    public Student save(Student student) {
        boolean duplicate = student.getId() == null
                ? repository.existsByEmailIgnoreCase(student.getEmail())
                : repository.existsByEmailIgnoreCaseAndIdNot(student.getEmail(), student.getId());
        if (duplicate) throw new IllegalStateException("A student with this email already exists.");
        return repository.save(student);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new IllegalArgumentException("Student not found: " + id);
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long count() { return repository.count(); }
}
