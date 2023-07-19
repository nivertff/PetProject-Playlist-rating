package com.ex.newWeb.Dto;

import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {
    private Long id;
    @NotEmpty(message = "Song Name should not be empty")
    private String name;
    @NotEmpty(message = "Song Singer should not be empty")
    private String singer;
    private Double ratio;
    private UserEntity createdBy;
    List<PlayListDto> playLists = new ArrayList<>();


}
