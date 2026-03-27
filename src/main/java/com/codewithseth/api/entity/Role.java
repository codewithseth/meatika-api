package com.codewithseth.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String uuid;

    @Column(nullable = false, unique = true, length = 50)
    private String alias;

    @Column(name = "name_en", nullable = false, length = 50)
    private String nameEn;

    @Column(name = "name_km", nullable = false, length = 50)
    private String nameKm;

    @Column(name = "description_en", columnDefinition = "TEXT")
    private String descriptionEn;

    @Column(name = "description_km", columnDefinition = "TEXT")
    private String descriptionKm;

}
