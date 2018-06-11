package com.elearningbackend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "lession", catalog = "e_learning")
public class Lession implements Serializable {

    private static final long serialVersionUID = 1L;
    private String lessionCode;
    private User user;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;
    private Integer isFinish;
    private Set<LessionReport> mappedLessionReports = new TreeSet<>();

    public Lession() {
    }

    public Lession(String lessionCode, User user) {
        this.lessionCode = lessionCode;
        this.user = user;
    }

    public Lession(String lessionCode, User user, Timestamp creationDate, Timestamp lastUpdateDate,
            Set<LessionReport> mappedLessionReports) {
        this.lessionCode = lessionCode;
        this.user = user;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.mappedLessionReports = mappedLessionReports;
    }

    @Id
    @Column(name = "lession_code", unique = true, nullable = false, length = 100)
    public String getLessionCode() {
        return this.lessionCode;
    }

    public void setLessionCode(String lessionCode) {
        this.lessionCode = lessionCode;
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
        return this.lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @ManyToOne
    @JoinColumn(name = "lession_username", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "is_finish")
    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    @OneToMany(mappedBy = "mappedLession")
    public Set<LessionReport> getMappedLessionReports() {
        return mappedLessionReports;
    }

    public void setMappedLessionReports(Set<LessionReport> mappedLessionReports) {
        this.mappedLessionReports = mappedLessionReports;
    }
}
