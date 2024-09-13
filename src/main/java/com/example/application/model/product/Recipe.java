package com.example.application.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "recipe", schema = "public")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "rec_id")
    private List<Product> products;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "date_creating")
    private LocalDate dateOfCreating;

    @Column(name = "user_login")
    private String userLogin;

    @Column(name = "is_moderated")
    private Boolean isModerated;

    @Column(name = "is_public")
    private Boolean isPublic;
}
