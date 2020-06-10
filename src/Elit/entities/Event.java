/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author megapc
 */
public class Event {
    int id;
    String title;
    Date startDate;
    Date startHour;
    String description;
    String logo;
    Club club;
    Classroom classroom;
    List<Equipment> equipements;
    
    String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
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
        final Event other = (Event) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.logo, other.logo)) {
            return false;
        }
        if (!Objects.equals(this.startDate, other.startDate)) {
            return false;
        }
        if (!Objects.equals(this.startHour, other.startHour)) {
            return false;
        }
        if (!Objects.equals(this.club, other.club)) {
            return false;
        }
        if (!Objects.equals(this.classroom, other.classroom)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.title);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.logo);
        hash = 53 * hash + Objects.hashCode(this.club);
        return hash;
    }

    public Event() {
        this.equipements = new ArrayList<Equipment>();
    }

    public Event(int id, String title, Date startDate, Date startHour, String description, String logo, Club club, Classroom classroom,String searchKey) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.startHour = startHour;
        this.description = description;
        this.logo = logo;
        this.club = club;
        this.classroom = classroom;
        this.searchKey=searchKey;
    }

    public Event(int id, String title, Date startDate, Date startHour, String description, String logo, Club club, Classroom classroom, List<Equipment> equipements) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.startHour = startHour;
        this.description = description;
        this.logo = logo;
        this.club = club;
        this.classroom = classroom;
        this.equipements = equipements;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartHour() {
        return startHour;
    }

    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Override
    public String toString() {
      
     //   String format1 = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(this.startHour);
        
        
        return "\nEvent{" + "id=" + id + ", title=" + title + ", startDate=" + startDate + ", startHour=" + startHour + ", description=" + description + ", logo=" + logo + ", club=" + club + ", classroom=" + classroom + ", equipements=" + equipements + "}";
    }

    public List<Equipment> getEquipements() {
        return equipements;
    }

    public void setEquipements(List<Equipment> equipements) {
        this.equipements = equipements;
    }
    
}
