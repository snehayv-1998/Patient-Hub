package com.example.patientHub.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    private Long id;
    private String name;
    @Column(name = "created_date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public Patient(Long patientId, String patientName) {
        this.id=patientId;
        this.name=patientName;
    }
}
