package com.rest.finalapp_frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginLogDto {

    private Long id;
    private String username;
    private String result;
}
