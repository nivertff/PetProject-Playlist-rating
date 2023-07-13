package com.ex.newWeb.mapper;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.Dto.SongDto;
import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.Song;

import java.util.stream.Collectors;

import static com.ex.newWeb.mapper.PlayListMapper.mapToPlayList;
import static com.ex.newWeb.mapper.PlayListMapper.mapToPlayListDto;

public class SongMapper {
    public static Song mapToSong(SongDto songDto){
        Song song = Song.builder()
                .name(songDto.getName())
                .id(songDto.getId())
                .ratio(songDto.getRatio())
                .singer(songDto.getSinger())
                .text(songDto.getText())
                .createdBy(songDto.getCreatedBy())
                .build();
        return song;
    }
    public static SongDto mapToSongDto(Song song){
        SongDto songDto = SongDto.builder()
                .name(song.getName())
                .id(song.getId())
                .ratio(song.getRatio())
                .singer(song.getSinger())
                .text(song.getText())
                .createdBy(song.getCreatedBy())
                .build();
        return songDto;
    }


}
