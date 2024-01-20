package com.ocsoares.advancedcrudspringboot.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

// Entidade de Usuário que VAI ser Salva no BANCO de DADOS, por isso tem o "id", por exemplo!!!
@Entity
@Table(name = "users")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class UserPersistenceEntity implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(unique = true, nullable = false)
    @NonNull
    private String email;

    @Column(nullable = false)
    @NonNull
    private String password;

    // Método responsável por Atribuir as ROLES de PERMISSÕES para cada TIPO de USUÁRIO!!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.email; // Vou autenticar por EMAIL e SENHA
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
}
