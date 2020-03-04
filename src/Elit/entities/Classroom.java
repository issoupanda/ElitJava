/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.entities;

/**
 *
 * @author kinga
 */
public class Classroom {

    private int id;
    private String name;
    private int capacity;
    private String bloc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getBloc() {
        return bloc;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
    }

    @Override
    public String toString() {
        return "Classroom{" + "id=" + id + ", name=" + name + ", capacity=" + capacity + ", bloc=" + bloc + '}';
    }

    public Classroom(int id, String name, int capacity, String bloc) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.bloc = bloc;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Classroom other = (Classroom) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
