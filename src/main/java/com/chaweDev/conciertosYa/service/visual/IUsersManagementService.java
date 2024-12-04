package com.chaweDev.conciertosYa.service.visual;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.ReqRes;
import com.chaweDev.conciertosYa.entity.OurUsers;

public interface IUsersManagementService {
    ReqRes register(DTO dto);

    ReqRes login(DTO dto);

    ReqRes refreshToken(ReqRes req);

    ReqRes getAllUsers();

    ReqRes getUsersById(Integer userId);

    ReqRes updateUser(Integer userId, OurUsers reqres);

    ReqRes getMyInfo(String email);

    ReqRes deleteUser(Integer userId);
}
