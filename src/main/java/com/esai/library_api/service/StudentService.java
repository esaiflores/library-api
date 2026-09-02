package com.esai.library_api.service;

import com.esai.library_api.model.Student;
import com.esai.library_api.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class StudentService {


    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updateStudent) {
        return studentRepository.findById(id).map(existing -> {
            existing.setName(updateStudent.getName());
            existing.setNotes(updateStudent.getNotes());
            existing.setClassName(updateStudent.getClassName());
            return studentRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
