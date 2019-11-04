/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.model;

import model.modelController.ModelController;

/**
 *
 * @author User
 */
public class Student implements Comparable<Student>{
    
    private int studentId = -1;
    private int sbId1 = -1;
    private int sbId2 = -1;
    private int sbId3 = -1;
    private int grId = -1;
    private String name = model.modelController.ModelController.NONE_NAME;
    private String surename = model.modelController.ModelController.NONE_NAME;
    private String tel = model.modelController.ModelController.NONE_NAME;
    private String sb1 = model.modelController.ModelController.NONE_NAME;
    private String sb2 = model.modelController.ModelController.NONE_NAME;
    private String sb3 = model.modelController.ModelController.NONE_NAME;
    private String gr = model.modelController.ModelController.NONE_NAME;
    
    model.modelController.ModelController mc = new ModelController();

    public Student(int studentId, String name, String surename, int sbId1, int sbId2, int sbId3, int grId,String tel) {
        this.studentId = studentId;
        this.name = name;
        this.surename = surename;
        this.sbId1 = sbId1;
        this.sbId2 = sbId2;
        this.sbId3 = sbId3;
        this.grId = grId;
        this.tel = tel;
    }
    public Student( String name, String surename, int sbId1, int sbId2, int sbId3, int grId,String tel) {
        this.studentId = -1;
        this.name = name;
        this.surename = surename;
        this.sbId1 = sbId1;
        this.sbId2 = sbId2;
        this.sbId3 = sbId3;
        this.grId = grId;
        this.tel = tel;

    }
    public Student( String name, String surename, int sbId1, int sbId2, int sbId3,String tel) {
        this.studentId = -1;
        this.name = name;
        this.surename = surename;
        this.sbId1 = sbId1;
        this.sbId2 = sbId2;
        this.sbId3 = sbId3;
        this.grId = -1;
        this.tel = tel;

    }
    public Student( String name, String surename, int grId ,String tel) {
        this.studentId = -1;
        this.name = name;
        this.surename = surename;
        this.sbId1 = -1;
        this.sbId2 = -1;
        this.sbId3 = -1;
        this.grId = grId;
        this.tel = tel;
    }
    public Student( String name, String surename, int grId ) {
        this.studentId = -1;
        this.name = name;
        this.surename = surename;
        this.sbId1 = -1;
        this.sbId2 = -1;
        this.sbId3 = -1;
        this.grId = grId;
    }

    public Student( String name, String surename) {
        this.studentId = -1;
        this.name = name;
        this.surename = surename;
        this.sbId1 = -1;
        this.sbId2 = -1;
        this.sbId3 = -1;
        this.grId = -1;
    }
    

   

    @Override
    public String toString() {
        return name;
    }

//    @Override
//    public boolean equals(Object obj) {
//        try {
//            System.out.println();
//            return ((Student) obj).getStudentId() == studentId;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    @Override
    public int compareTo(Student o) {
        return (studentId>o.getStudentId())?1:-1;
    }
    
    public String getTel() {
        return tel==null?ModelController.NONE_NAME:tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
        if(tel==null)this.tel = model.modelController.ModelController.NONE_NAME;
    }
        public String getSb1() {
        return mc.getSubject(sbId1).getSubjectName();
    }

    public void setSb1(String sb1) {
        this.sb1 = sb1;
    }

    public String getSb2() {
        return mc.getSubject(sbId2).getSubjectName();
    }

    public void setSb2(String sb2) {
        this.sb2 = sb2;
    }

    public String getSb3() {
        return mc.getSubject(sbId3).getSubjectName();
    }

    public void setSb3(String sb3) {
        this.sb3 = sb3;
    }

    public String getGr() {
        
        return mc.getGroup(grId).getGroupName();
    }

    public void setGr(String gr) {
        this.gr= gr;
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
        return Data.groups.get(grId);
    }

    public void setGroup(Group group) {
       this.grId = group.getGroupId();
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
}
