package com.ex.newWeb.Dto;

import com.ex.newWeb.models.Song;
import com.ex.newWeb.models.UserEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
public class PlayListDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NotEmpty(message = "Playlist Name should not be empty")
    private String name;
    private String singer;
    private String photoUrl;
    private Double ratio;
    private String text;
    private List<SongDto> songs;
    private UserEntity createdBy;


}
