package com.ex.newWeb.service.impl;

import com.ex.newWeb.Dto.SongDto;
import com.ex.newWeb.Repository.PlayListRepository;
import com.ex.newWeb.Repository.SongRepository;
import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.Song;
import com.ex.newWeb.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ex.newWeb.mapper.PlayListMapper.mapToPlayListDto;
import static com.ex.newWeb.mapper.SongMapper.mapToSong;
import static com.ex.newWeb.mapper.SongMapper.mapToSongDto;

@Service
public class SongServiceImpl implements SongService {
    private SongRepository songRepository;
    private PlayListRepository playListRepository;

    public SongServiceImpl(SongRepository songRepository,PlayListRepository playListRepository) {
        this.songRepository = songRepository;
        this.playListRepository = playListRepository;
    }



    @Override
    public List<SongDto> findAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream().map((song) -> mapToSongDto(song)).collect(Collectors.toList());
    }

    @Override
    public SongDto findPlayListById(Long songId) {
        Song song = songRepository.findById(songId).get();
        return mapToSongDto(song);
    }

    @Override
    public void createSong(Long playListId, SongDto songDto) {
        PlayList playList = playListRepository.findById(playListId).get();
        Song song = mapToSong(songDto);
        song.setPlayList(playList);
        songRepository.save(song);
    }

    @Override
    public SongDto findBySongId(Long songId) {
        Song song = songRepository.findById(songId).get();
        return mapToSongDto(song);
    }

    @Override
    public void updateSong(SongDto songDto) {
        Song song = mapToSong(songDto);
        songRepository.save(song);
    }

    @Override
    public void deleteSong(Long songId) {
        songRepository.deleteById(songId);
    }


}
