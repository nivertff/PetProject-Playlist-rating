package com.ex.newWeb.service;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.models.PlayList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayListService {
    List<PlayListDto> findAllPlayLists();

    PlayListDto findPlayListById(Long playListId);

    void delete(Long playListId);

    PlayList savePlayList(PlayListDto playListDto);

    void updatePlayList(PlayListDto playListDto);
}
