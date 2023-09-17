package com.rest.finalapp_frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamLogDto {

    private Long id;
    private Long teamId;
    private String operation;
    private String details;
}
