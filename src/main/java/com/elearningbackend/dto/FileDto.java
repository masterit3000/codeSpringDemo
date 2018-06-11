package com.elearningbackend.dto;

import lombok.*;

@Value
@Builder
public class FileDto {
    String originalFileName;
    String url;
    String contentType;
    long size;
}
