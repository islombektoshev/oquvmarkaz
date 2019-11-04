package model.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.UnaryOperator;
import model.modelController.ModelController;

/**
 *
 * @author Islom
 */
public class BlockTest implements Comparable<BlockTest>{

    private String name = ModelController.NONE_NAME;
    private Date date = new Date();
    private List<Abiturient> abiturients = new ArrayList<>();
    private int id = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BlockTest(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public BlockTest(int id, String name) {
        this.id = id;
        this.name = name;
        this.date = new Date();
    }

    public BlockTest(int id) {
        this.id = id;
        this.name = ModelController.NONE_NAME;
        this.date = new  Date();
    }

    public BlockTest() {
        this.id = -1;
        this.name = ModelController.NONE_NAME;
        this.date = new Date();
    }

// METHODS OF LIST OF ABITURIENT ARE BEDGIN HERE
    public int size() {
        return abiturients.size();
    }

    public boolean isEmpty() {
        return abiturients.isEmpty();
    }

    public boolean contains(Abiturient o) {
        return abiturients.contains(o);
    }

    public boolean add(Abiturient e) {
        return abiturients.add(e);
    }

    public boolean remove(Abiturient o) {
        return abiturients.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return abiturients.containsAll(c);
    }

    public boolean addAll(Collection<? extends Abiturient> c) {
        return abiturients.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends Abiturient> c) {
        return abiturients.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return abiturients.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return abiturients.retainAll(c);
    }

    public void replaceAll(UnaryOperator<Abiturient> operator) {
        abiturients.replaceAll(operator);
    }

    public void sort(Comparator<? super Abiturient> c) {
        abiturients.sort(c);
    }

    public void clear() {
        abiturients.clear();
    }

    public Abiturient get(int index) {
        return abiturients.get(index);
    }

    public Abiturient set(int index, Abiturient element) {
        return abiturients.set(index, element);
    }

    public void add(int index, Abiturient element) {
        abiturients.add(index, element);
    }

    public Abiturient remove(int index) {
        return abiturients.remove(index);
    }

    public int indexOf(Object o) {
        return abiturients.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return abiturients.lastIndexOf(o);
    }

    public List<Abiturient> subList(int fromIndex, int toIndex) {
        return abiturients.subList(fromIndex, toIndex);
    }

// METHODS OF LIST OF ABITURIENT ARE END HERE
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Abiturient> getAbiturients() {
        return abiturients;
    }

    public void setAbiturients(List<Abiturient> abiturients) {
        this.abiturients = abiturients;
    }

    @Override
    public int compareTo(BlockTest o) {
        return (id>o.id)?1:-1;
    }

    @Override
    public String toString() {
        return String.format("%s\t\t%02d.%02d.%02d", name, date.getDate(),date.getMonth(), (date.getYear()+1900));
    }
    
}
