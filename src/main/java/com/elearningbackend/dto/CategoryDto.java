package com.elearningbackend.dto;

import com.elearningbackend.entity.Subcategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CategoryDto {
    private String categoryCode;
    private String categoryIntro;
    private List<String> subcategoriesName;
    private List<String> subcategoriesCode;
    private int subcategoriesCount;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;

    public CategoryDto(String categoryCode, String categoryIntro) {
        this.categoryCode = categoryCode;
        this.categoryIntro = categoryIntro;
    }
}
