package model.model;

/**
 *
 * @author Islom
 */
public class Abiturient extends Student{

    private int f1 = -1;

    private int f2 = -1;
    private int f3 = -1;
    private String individualIzoh = "";
    private int id;

    public Abiturient(int id, int studentId, String name, String surename, int sbId1, int sbId2, int sbId3, int grId, String tel) {
        super(studentId, name, surename, sbId1, sbId2, sbId3, grId, tel);
        setId(id);
    }
    public Abiturient(int id, Student s) {
        super(s.getStudentId(), s.getName(), s.getSurename(), s.getSbId1(), s.getSbId2(), s.getSbId3(), s.getGrId(), s.getTel());
        setId(id);
    }
    public Abiturient(int id, String name, String surename, int sbId1, int sbId2, int sbId3, int grId, String tel) {
        super(-1, name, surename, sbId1, sbId2, sbId3, grId, tel);
        setId(id);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getF1() {
        return f1;
    }

    public void setF1(int f1) {
        this.f1 = f1;
    }

    public int getF2() {
        return f2;
    }

    public void setF2(int f2) {
        this.f2 = f2;
    }

    public int getF3() {
        return f3;
    }

    public void setF3(int f3) {
        this.f3 = f3;
    }

    public String getIndividualIzoh() {
        return individualIzoh;
    }

    public void setIndividualIzoh(String individualIzoh) {
        this.individualIzoh = individualIzoh;
    }

    @Override
    public int compareTo(Student o) {
        final Abiturient a = (Abiturient) o;
        return (id > a.getId()) ? 1 : -1;
    }
    
}