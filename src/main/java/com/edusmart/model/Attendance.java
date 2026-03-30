package com.edusmart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "attendance")
public class Attendance {

    @Id
    private String id;

    private String teacherId;
    private String teacherName;
    private String className;
    private String subject;
    private String date;

    private List<AttendanceRecord> records;

    @Data
    public static class AttendanceRecord {
        private String studentId;
        private String studentName;
        private boolean present;
    }
}