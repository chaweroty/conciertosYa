package com.chaweDev.conciertosYa.service.Visual;

import com.chaweDev.conciertosYa.dto.ReqRes;
import com.chaweDev.conciertosYa.entity.OurUsers;

public interface IUsersManagementService {
    ReqRes register(ReqRes reg);

    ReqRes login(ReqRes req);

    ReqRes refreshToken(ReqRes req);

    ReqRes getAllUsers();

    ReqRes getUsersById(Integer userId);

    ReqRes updateUser(Integer userId, OurUsers reqres);

    ReqRes getMyInfo(String email);

    ReqRes deleteUser(Integer userId);
}
