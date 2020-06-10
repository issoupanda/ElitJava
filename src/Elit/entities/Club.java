/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.entities;

import java.util.Date;
import java.util.Objects;



/**
 *
 * @author megapc
 */
public class Club {
    int id; 
   String title;
   String description;
   String category;
   Date creationDate;
   String logo;
   User president;

    public Club(int id, String title, String description, String category, Date creationDate, String logo, User president) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.creationDate = creationDate;
        this.logo = logo;
        this.president = president;
    }

    public Club() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.title);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.category);
        hash = 71 * hash + Objects.hashCode(this.creationDate);
        hash = 71 * hash + Objects.hashCode(this.logo);
        hash = 71 * hash + Objects.hashCode(this.president);
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
        final Club other = (Club) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.logo, other.logo)) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        if (!Objects.equals(this.president, other.president)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Club{" + "id=" + id + ", title=" + title + ", description=" + description + ", category=" + category + ", creationDate=" + creationDate + ", logo=" + logo + ", president=" + president + "}\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public User getPresident() {
        return president;
    }

    public void setPresident(User president) {
        this.president = president;
    }
   
   
   
    
}
