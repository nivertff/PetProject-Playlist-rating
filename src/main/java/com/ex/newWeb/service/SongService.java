package com.ex.newWeb.service;

import com.ex.newWeb.Dto.SongDto;
import com.ex.newWeb.models.Song;
import java.util.List;

public interface SongService {
    List<SongDto> findAllSongs();

    SongDto findPlayListById(Long songId);


    void createSong(Long playListId, SongDto songDto);

    SongDto findBySongId(Long songId);

    void updateSong(SongDto songDto);

    void deleteSong(Long songId);
}
