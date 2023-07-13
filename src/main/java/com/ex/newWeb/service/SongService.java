package com.ex.newWeb.service;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.Dto.SongDto;
import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.Song;
import java.util.List;

public interface SongService {
    List<SongDto> findAllSongs();
    SongDto findSongById(Long songId);
    List<SongDto> findYourSongs();
    void deleteSong(Long songId);
    Song saveSong(SongDto songDto);
    void updateSong(SongDto songDto);
    List<SongDto> searchSong(String query);
    List<SongDto> searchAllSong(String query);

    void saveSongPlayList(SongDto songDto, Long playListId);
}

