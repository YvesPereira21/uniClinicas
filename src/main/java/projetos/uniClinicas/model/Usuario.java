package com.projeto.uniClinicas.model;

import com.projeto.uniClinicas.enums.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @OneToOne(mappedBy = "usuario")
    private Clinica clinica;
    @OneToOne(mappedBy = "usuario")
    private UsuarioComum usuarioComum;

    public Usuario() {}

    public Usuario(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (this.role) {
            case ADMIN -> List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
            case CLINICA -> List.of(new SimpleGrantedAuthority("ROLE_CLINICA"));
            default -> List.of(new SimpleGrantedAuthority("ROLE_USER"));
        };
    }
}