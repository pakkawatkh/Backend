package com.example.backend.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Data
@MappedSuperclass
@EnableAutoConfiguration
public abstract class AutoID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
