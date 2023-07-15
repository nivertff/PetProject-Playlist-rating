package com.ex.newWeb.models;

import com.ex.newWeb.Dto.PlayListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String singer;
    private Double ratio;



    @ManyToOne
    @JoinColumn(name ="created_by", nullable = false)
    private UserEntity createdBy;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "songs_playLists",
            joinColumns = {@JoinColumn(name = "song_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "playList_id", referencedColumnName = "id")}
    )
    private List<PlayList> playLists = new ArrayList<>();

}
