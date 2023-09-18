package com.rest.finalapp_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkinDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private Double priceUSD;

    @JsonProperty("img")
    private String imageSrc;

    @JsonProperty("pricePLN")
    private Double pricePLN;
}
