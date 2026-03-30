package com.edusmart.service;

import com.edusmart.model.Attendance;
import com.edusmart.model.User;
import com.edusmart.repository.AttendanceRepository;
import com.edusmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    // Teacher saves attendance for a session
    public Attendance saveAttendance(Attendance attendance) {
        attendance.setDate(LocalDate.now().toString());
        return attendanceRepository.save(attendance);
    }

    // Get all attendance sessions for a class and subject
    public List<Attendance> getAttendanceByClassAndSubject(String className, String subject) {
        return attendanceRepository.findByClassNameAndSubject(className, subject);
    }

    // Get all attendance sessions for a class
    public List<Attendance> getAttendanceByClass(String className) {
        return attendanceRepository.findByClassName(className);
    }

    // Get attendance summary for a specific student
    public List<Map<String, Object>> getStudentAttendanceSummary(String studentId, String className) {
        List<Attendance> allSessions = attendanceRepository.findByClassName(className);

        System.out.println("=== ATTENDANCE DEBUG ===");
        System.out.println("Looking for studentId: " + studentId);
        System.out.println("Total sessions found: " + allSessions.size());

        Map<String, Map<String, Integer>> subjectStats = new HashMap<>();

        for (Attendance session : allSessions) {
            String subject = session.getSubject();
            subjectStats.putIfAbsent(subject, new HashMap<>());
            subjectStats.get(subject).putIfAbsent("total", 0);
            subjectStats.get(subject).putIfAbsent("absences", 0);
            subjectStats.get(subject).putIfAbsent("presences", 0);

            subjectStats.get(subject).put("total",
                    subjectStats.get(subject).get("total") + 1);

            // Find this student's record in the session
            boolean found = false;
            for (Attendance.AttendanceRecord record : session.getRecords()) {
                System.out.println("Comparing record studentId: " + record.getStudentId() + " with: " + studentId);
                if (record.getStudentId().equals(studentId)) {
                    found = true;
                    System.out.println("Found! Present: " + record.isPresent());
                    if (!record.isPresent()) {
                        subjectStats.get(subject).put("absences",
                                subjectStats.get(subject).get("absences") + 1);
                    } else {
                        subjectStats.get(subject).put("presences",
                                subjectStats.get(subject).get("presences") + 1);
                    }
                    break;
                }
            }
            if (!found) {
                System.out.println("Student NOT found in this session records");
            }
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : subjectStats.entrySet()) {
            Map<String, Object> subjectSummary = new HashMap<>();
            subjectSummary.put("subject", entry.getKey());
            subjectSummary.put("total", entry.getValue().get("total"));
            subjectSummary.put("absences", entry.getValue().get("absences"));
            subjectSummary.put("presences", entry.getValue().get("presences"));
            subjectSummary.put("eliminated", entry.getValue().get("absences") >= 4);
            result.add(subjectSummary);
        }

        System.out.println("=== RESULT: " + result + " ===");
        return result;
    }

    // Get students by class for attendance taking
    public List<User> getStudentsByClass(String className) {
        return userRepository.findByClassName(className);
    }
}