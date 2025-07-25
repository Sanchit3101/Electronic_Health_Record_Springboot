package com.ehrapp.ehr_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user_account")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    private String role;

    @Column(unique = true)
    private String email;  // ✅ NEW FIELD

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; } // ✅
    public void setEmail(String email) { this.email = email; } // ✅
}
