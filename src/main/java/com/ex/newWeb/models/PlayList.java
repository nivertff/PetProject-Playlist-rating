package com.ex.newWeb.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name ="playLists")
@Table(name = "PlayLists")
public class PlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String singer;
    private String photoUrl;
    private Double ratio;
    private Double avgRatio;


    @ManyToOne
    @JoinColumn(name ="created_by", nullable = false)
    private UserEntity createdBy;

    @ManyToMany(mappedBy = "playLists")
    private List<Song> song = new ArrayList<>();

}
