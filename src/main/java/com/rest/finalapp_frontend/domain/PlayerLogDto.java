package com.rest.finalapp_frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class PlayerLogDto {

    private Long id;
    private Date date;
    private Long playerId;
    private String operation;
    private String details;
}
