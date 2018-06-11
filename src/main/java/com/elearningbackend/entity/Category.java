package com.elearningbackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category implements Serializable {
    private String categoryCode;
    private String categoryIntro;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;

    @Id
    @Column(name = "category_code")
    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Column(name = "category_intro")
    public String getCategoryIntro() {
        return categoryIntro;
    }

    public void setCategoryIntro(String categoryIntro) {
        this.categoryIntro = categoryIntro;
    }

    @Column(name = "creation_date")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "last_update_date")
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Category category = (Category) o;

        if (categoryCode != null ? !categoryCode.equals(category.categoryCode) : category.categoryCode != null)
            return false;
        if (categoryIntro != null ? !categoryIntro.equals(category.categoryIntro) : category.categoryIntro != null)
            return false;
        if (creationDate != null ? !creationDate.equals(category.creationDate) : category.creationDate != null)
            return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(category.lastUpdateDate) : category.lastUpdateDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = categoryCode != null ? categoryCode.hashCode() : 0;
        result = 31 * result + (categoryIntro != null ? categoryIntro.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        return result;
    }
}
