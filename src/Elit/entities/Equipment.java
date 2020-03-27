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
public class Equipment {

    private int id;
    private String label;
    private String category;
    private float qte;
    private float qteInit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Equipment(int id, float qte) {
        this.id = id;
        this.qte = qte;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getQte() {
        return qte;
    }

    public void setQte(float qte) {
        this.qte = qte;
    }

    public float getQteInit() {
        return qteInit;
    }

    public void setQteInit(float qteInit) {
        this.qteInit = qteInit;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.id;
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
        final Equipment other = (Equipment) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Equipment{" + "id=" + id + ", label=" + label + ", category=" + category + ", qte=" + qte + ", qteInit=" + qteInit + '}';
    }

    public Equipment(int id, String label, String category, float qte, float qteInit) {
        this.id = id;
        this.label = label;
        this.category = category;
        this.qte = qte;
        this.qteInit = qteInit;
    }

}
