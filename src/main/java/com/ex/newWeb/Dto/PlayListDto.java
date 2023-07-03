package com.ex.newWeb.Dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class PlayListDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NotEmpty(message = "RatioList name should not be empty")
    private String name;
    @NotEmpty(message = "RatioList photoUrl should not be empty")
    private String photoUrl;
    private Double ratio;
    private String text;

}
