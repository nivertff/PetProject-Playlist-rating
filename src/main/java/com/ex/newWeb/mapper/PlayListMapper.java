package com.ex.newWeb.mapper;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.models.PlayList;

public class PlayListMapper {
    public static PlayList mapToPlayList(PlayListDto playListDto){
        PlayList playList = PlayList.builder()
                .id(playListDto.getId())
                .photoUrl(playListDto.getPhotoUrl())
                .name(playListDto.getName())
                .singer(playListDto.getSinger())
                .ratio(playListDto.getRatio())
                .text(playListDto.getText())
                .songSet(playListDto.getSongs())
                .build();
        return playList;
    }
    public static PlayListDto mapToPlayListDto(PlayList playList){
        PlayListDto playListDto = PlayListDto.builder()
                .id(playList.getId())
                .photoUrl(playList.getPhotoUrl())
                .name(playList.getName())
                .singer(playList.getSinger())
                .ratio(playList.getRatio())
                .text(playList.getText())
                .songs(playList.getSongSet())
                .build();
        return playListDto;
    }

}
