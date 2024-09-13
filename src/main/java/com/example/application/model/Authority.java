package com.example.application.model;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "authorities", schema = "public")
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@RequiredArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username") // login
    private final String username;

    @Column(name = "authority")
    private final String authority;
}
