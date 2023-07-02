package com.ex.newWeb.mapper;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.models.PlayList;

public class PlayListMapper {
    public static PlayList mapToPlayList(PlayListDto playListDto){
        PlayList playList = PlayList.builder()
                .id(playListDto.getId())
                .photoUrl(playListDto.getPhotoUrl())
                .name(playListDto.getName())
                .Ratio(playListDto.getRatio())
                .Text(playListDto.getText())
                .build();
        return playList;
    }
    public static PlayListDto mapToPlayListDto(PlayList playList){
        PlayListDto playListDto = PlayListDto.builder()
                .id(playList.getId())
                .photoUrl(playList.getPhotoUrl())
                .name(playList.getName())
                .Ratio(playList.getRatio())
                .Text(playList.getText())
                .build();
        return playListDto;
    }

}
