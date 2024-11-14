package com.omega.server.domain.user;

import com.omega.server.domain.company.Company;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


    public User(UserDTO datos) {
        this.firstName = datos.firstName();
        this.lastName = datos.lastName();
        this.email = datos.email();
        this.rol = datos.rol();
        this.isDeleted = datos.isDeleted();
        this.username = datos.username();
        this.password = datos.password();
    }


    public void updateUserAttributes(UpdateUserDTO updateUserDTO) {
        if (updateUserDTO.firstName() != null) {
            this.firstName = updateUserDTO.firstName();
        }
        if (updateUserDTO.lastname() != null) {
            this.lastName = updateUserDTO.lastname();
        }
        if (updateUserDTO.email() != null) {
            this.email = updateUserDTO.email();
        }

        if (updateUserDTO.rol() != null) {
            this.rol = updateUserDTO.rol();
        }
        if (updateUserDTO.isDeleted() != null) {
            this.isDeleted = updateUserDTO.isDeleted();
        }
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

//    public String getUsername() {
//        return username;
//    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

