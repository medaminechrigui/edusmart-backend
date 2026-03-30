package com.edusmart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rooms")
public class Room {

    @Id
    private String id;

    private String name;
    private String type;
    private int capacity;
    private String department;
    private boolean available = true;
}