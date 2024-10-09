package com.omega.server.domain.user;

import com.omega.server.domain.company.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "User")
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String telephone;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(unique = true)
    private String username;

    private String password;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;


    public User(RegisterUser datos) {
        this.firstName = datos.firstName();
        this.lastName = datos.lastName();
        this.email = datos.email();
        this.telephone = datos.telephone();
        this.rol = datos.rol();
        this.isDeleted = datos.isDeleted();
        this.username = datos.username();
        this.password = datos.password();
    }
}

