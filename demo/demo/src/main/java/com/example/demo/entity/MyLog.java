package com.example.demo.entity;

import javax.persistence.*;

@Table(name = "log")
public class MyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "sellerId")
    private Integer sellerId;
    @Column(name = "userId")
    private Integer userId;
    @Column(name = "cId")
    private Integer cId;
    @Column(name = "action")
    private String action;

    @Transient
    private String userName;

    @Transient
    private String cName;

    public MyLog(Integer sellerId, Integer userId, Integer cId, String action) {
        this.sellerId = sellerId;
        this.userId = userId;
        this.cId = cId;
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
