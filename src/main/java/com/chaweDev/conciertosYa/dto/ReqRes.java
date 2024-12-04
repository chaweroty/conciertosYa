package com.chaweDev.conciertosYa.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.chaweDev.conciertosYa.entity.OurUsers;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes extends DTO{

    private String token;
    private String refreshToken;
    private String expirationTime;
    private Integer id;
    private String name;
    private String city;
    private String role;
    private String email;
    private String password;
    private OurUsers ourUsers;

    private List<OurUsers> ourUsersList;

}