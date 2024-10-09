package com.omega.server.domain.company;

import com.omega.server.domain.user.ResponseUser;
import com.omega.server.domain.user.UserDTO;

import java.util.List;

public record ResponseCompanyWithUsers(
        Long id,
        String name,
        String email,
        String telephone,
        String nameContact,
        List<UserDTO> users
) {
}
