/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.model;

/**
 *
 * @author User
 */
public class Student {
    
    private int studentId;
    
    private String name;
    private String surename;
    
    private Group group;
    private int sbId1;
    private int sbId2;
    private int sbId3;
    private int grId;

    public Student(int studentId, String name, String surename, Group group, int sbId1, int sbId2, int sbId3, int grId) {
        this.studentId = studentId;
        this.name = name;
        this.surename = surename;
        this.group = group;
        this.sbId1 = sbId1;
        this.sbId2 = sbId2;
        this.sbId3 = sbId3;
        this.grId = grId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getSbId1() {
        return sbId1;
    }

    public void setSbId1(int sbId1) {
        this.sbId1 = sbId1;
    }

    public int getSbId2() {
        return sbId2;
    }

    public void setSbId2(int sbId2) {
        this.sbId2 = sbId2;
    }

    public int getSbId3() {
        return sbId3;
    }

    public void setSbId3(int sbId3) {
        this.sbId3 = sbId3;
    }

    public int getGrId() {
        return grId;
    }

    public void setGrId(int grId) {
        this.grId = grId;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
}
