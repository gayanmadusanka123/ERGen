package com.generated.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import java.util.List;

@Entity
@Table(name = "desk")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Desk{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToOne(mappedBy = "desk")
    private Employee employee;
}
