package com.simplifi.school.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplifi.school.entity.ClassRoom;
import com.simplifi.school.entity.School;
import com.simplifi.school.entity.Student;
import com.simplifi.school.repository.ClassRoomRepository;
import com.simplifi.school.repository.SchoolRepository;
import com.simplifi.school.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final ClassRoomRepository classRoomRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, 
                        SchoolRepository schoolRepository,
                        ClassRoomRepository classRoomRepository) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.classRoomRepository = classRoomRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public List<Student> getStudentsBySchool(Long schoolId) {
        return studentRepository.findBySchoolId(schoolId);
    }

    public List<Student> getStudentsByClass(Long classId) {
        return studentRepository.findByClassRoomId(classId);
    }

    public Student createStudent(Student student, Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + schoolId));
        student.setSchool(school);
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setPhone(studentDetails.getPhone());
        student.setDateOfBirth(studentDetails.getDateOfBirth());
        student.setGender(studentDetails.getGender());
        student.setAddress(studentDetails.getAddress());
        student.setAdmissionDate(studentDetails.getAdmissionDate());
        return studentRepository.save(student);
    }

    public Student assignStudentToClass(Long studentId, Long classId) {
        Student student = getStudentById(studentId);
        ClassRoom classRoom = classRoomRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + classId));
        student.setClassRoom(classRoom);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}