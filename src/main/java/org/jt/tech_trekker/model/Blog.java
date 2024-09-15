package org.jt.tech_trekker.model;

import org.jt.tech_trekker.constant.BlogCatagory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String BlogId;
    @NotNull
    private String title;
    // @Lob
    @Column(columnDefinition = "mediumtext")
    @NotNull
    private String description;
    @Enumerated(EnumType.STRING)
    @NotNull
    private BlogCatagory catagory;
    private String banner;
}
