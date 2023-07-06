package com.ex.newWeb.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String singer;
    private Double ratio;
    private String text;

    @ManyToOne
    @JoinColumn(name="playList_id", nullable = false)
    private PlayList playList;

}
