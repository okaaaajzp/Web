package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "type")
public class Type {
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
