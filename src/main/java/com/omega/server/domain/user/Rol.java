package com.omega.server.domain.user;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;


public enum Rol {
    @JsonAlias({"ADMIN", "admin"}) ADMIN,
    @JsonAlias({"USER", "user"}) USER;

    Rol() {
    }
}

