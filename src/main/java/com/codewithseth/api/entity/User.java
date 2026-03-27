package com.codewithseth.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import com.codewithseth.api.dto.enums.Gender;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String uuid;

    @Column(name = "last_name_en", nullable = false, length = 50)
    private String lastNameEn;

    @Column(name = "last_name_km", nullable = false, length = 50)
    private String lastNameKm;

    @Column(name = "first_name_en", nullable = false, length = 50)
    private String firstNameEn;

    @Column(name = "first_name_km", nullable = false, length = 50)
    private String firstNameKm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(name = "notification_unread", nullable = false)
    private Integer notificationUnread = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_uuid", referencedColumnName = "uuid", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_uuid", referencedColumnName = "uuid")
    private Role role;

}
