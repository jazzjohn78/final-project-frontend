package com.rest.finalapp_frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class LoginLogDto {

    private Long id;
    private Date date;
    private String username;
    private String result;
}
