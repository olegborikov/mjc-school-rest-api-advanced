package com.epam.esm.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GiftCertificate {
    private long id;
    private String name;
    private String description;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<Tag> tags;
}
