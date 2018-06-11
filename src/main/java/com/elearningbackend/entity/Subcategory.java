package com.elearningbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "subcategory")
public class Subcategory implements Serializable{
    private String subcategoryCode;
    private String displayName;
    private String subcategoryIntro;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;

    private Category category;

    private Set<QuestionBank> questionBanks;

    @Id
    @Column(name = "subcategory_code")
    public String getSubcategoryCode() {
        return subcategoryCode;
    }

    public void setSubcategoryCode(String subcategoryCode) {
        this.subcategoryCode = subcategoryCode;
    }

    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName){
        this.displayName = displayName;
    }

    @Column(name = "subcategory_intro")
    public String getSubcategoryIntro() {
        return subcategoryIntro;
    }

    public void setSubcategoryIntro(String subcategoryIntro) {
        this.subcategoryIntro = subcategoryIntro;
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

        Subcategory that = (Subcategory) o;

        if (subcategoryCode != null ? !subcategoryCode.equals(that.subcategoryCode) : that.subcategoryCode != null)
            return false;
        if (subcategoryIntro != null ? !subcategoryIntro.equals(that.subcategoryIntro) : that.subcategoryIntro != null)
            return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null)
            return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = subcategoryCode != null ? subcategoryCode.hashCode() : 0;
        result = 31 * result + (subcategoryIntro != null ? subcategoryIntro.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "category_code", referencedColumnName = "category_code", nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category categoryByCategoryCode) {
        this.category = categoryByCategoryCode;
    }

    @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<QuestionBank> getQuestionBanks() {
        return questionBanks;
    }

    public void setQuestionBanks(Set<QuestionBank> questionBanks) {
        this.questionBanks = questionBanks;
    }
}
