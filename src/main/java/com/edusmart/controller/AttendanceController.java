package com.edusmart.controller;

import com.edusmart.model.Attendance;
import com.edusmart.model.User;
import com.edusmart.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:4200")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Teacher saves attendance
    @PostMapping
    public ResponseEntity<Attendance> saveAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.saveAttendance(attendance));
    }

    // Get all sessions for a class and subject
    @GetMapping("/class/{className}/subject/{subject}")
    public ResponseEntity<List<Attendance>> getByClassAndSubject(
            @PathVariable String className,
            @PathVariable String subject) {
        return ResponseEntity.ok(attendanceService.getAttendanceByClassAndSubject(className, subject));
    }

    // Get all sessions for a class
    @GetMapping("/class/{className}")
    public ResponseEntity<List<Attendance>> getByClass(@PathVariable String className) {
        return ResponseEntity.ok(attendanceService.getAttendanceByClass(className));
    }

    // Get attendance summary for a student
    @GetMapping("/student/{studentId}/class/{className}")
    public ResponseEntity<List<Map<String, Object>>> getStudentSummary(
            @PathVariable String studentId,
            @PathVariable String className) {
        return ResponseEntity.ok(attendanceService.getStudentAttendanceSummary(studentId, className));
    }

    // Get students list by class
    @GetMapping("/students/{className}")
    public ResponseEntity<List<User>> getStudentsByClass(@PathVariable String className) {
        return ResponseEntity.ok(attendanceService.getStudentsByClass(className));
    }

}