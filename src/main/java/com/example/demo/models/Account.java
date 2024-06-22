package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String firstname;

    private String role;

    @ManyToMany
    @JoinTable(name = "account_authority",joinColumns = {@JoinColumn(name="account_id",referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name="authority_id",referencedColumnName = "id")})
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "account")
    private List<Post> posts;

    @Override
    public String toString() {
        return "Account [id=" + id + ", email=" + email + ", password=" + password + ", firstname=" + firstname
                + " role=" + role + ", posts=" + posts + ", authorities=" + authorities
                + "]";
    }

}
