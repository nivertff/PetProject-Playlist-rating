package com.ex.newWeb.Dto;

import com.ex.newWeb.models.PlayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {
    private Long id;
    private String name;
    private String singer;
    private Double ratio;
    private String text;
    private PlayList playList;

}
