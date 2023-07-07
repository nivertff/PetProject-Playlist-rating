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
@Entity
@Table(name = "PlayList")
public class PlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String singer;
    private String photoUrl;
    private Double ratio;
    private String text;
    private Double avgRatio;


    @ManyToOne
    @JoinColumn(name ="created_by", nullable = false)
    private UserEntity createdBy;
    @OneToMany(mappedBy = "playList", cascade = CascadeType.REMOVE)
    private List<Song> songSet = new ArrayList<>();

}
