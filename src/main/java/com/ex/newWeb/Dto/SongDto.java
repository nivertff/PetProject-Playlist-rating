package com.ex.newWeb.Dto;

import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

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
    private UserEntity createdBy;
    List<PlayListDto> playLists = new ArrayList<>();


}
