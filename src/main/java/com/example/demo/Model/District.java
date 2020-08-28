package com.example.demo.Model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class District extends BaseEntity {
    private String name;
    private Long code;
}
