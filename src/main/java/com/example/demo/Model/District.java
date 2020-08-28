package com.example.demo.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "district")
@Data
public class District extends BaseEntity {
    private String nameEn;
    private String nameBn;
    private int bbsCode;
    private String lat;
    private String lon;
}
