package com.ex.newWeb.service.impl;

import com.ex.newWeb.Dto.SongDto;
import com.ex.newWeb.Repository.PlayListRepository;
import com.ex.newWeb.Repository.SongRepository;
import com.ex.newWeb.Repository.UserRepository;
import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.Song;
import com.ex.newWeb.models.UserEntity;
import com.ex.newWeb.security.SecurityUtil;
import com.ex.newWeb.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ex.newWeb.mapper.SongMapper.mapToSong;
import static com.ex.newWeb.mapper.SongMapper.mapToSongDto;

@Service
public class SongServiceImpl implements SongService {
    private SongRepository songRepository;
    private UserRepository userRepository;
    private PlayListRepository playListRepository;

    @Autowired
    public SongServiceImpl(PlayListRepository playListRepository, SongRepository songRepository,UserRepository userRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.playListRepository = playListRepository;
    }



    @Override
    public List<SongDto> findAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songs.stream().map((song) -> mapToSongDto(song)).collect(Collectors.toList());
    }

    @Override
    public SongDto findSongById(Long songId) {
        Song song = songRepository.findById(songId).get();
        songRepository.save(song);
        return mapToSongDto(song);
    }
    @Override
    public List<SongDto> findYourSongs() {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        List<Song> songs = songRepository.findByCreatedBy(user);
        return songs.stream().map((song) -> mapToSongDto(song)).collect(Collectors.toList());
    }
    @Override
    public void deleteSong(Long songId) {
        Song song = songRepository.findById(songId).get();
        song.setPlayLists(new ArrayList<>());
        songRepository.save(song);
        songRepository.deleteById(songId);
    }

    @Override
    public Song saveSong(SongDto songDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Song song = mapToSong(songDto);
        song.setCreatedBy(user);
        return songRepository.save(song);
    }


    @Override
    public void updateSong(SongDto songDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Song song = mapToSong(songDto);
        song.setCreatedBy(user);
        songRepository.save(song);
    }

    @Override
    public List<SongDto> searchSong(String query) {
        List<Song> songs = songRepository.searchSong(query);
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        songs = songs.stream().filter(playList -> playList.getCreatedBy() == user).collect(Collectors.toList());
        return songs.stream().map(song -> mapToSongDto(song)).collect(Collectors.toList());
    }
    @Override
    public List<SongDto> searchAllSong(String query) {
        List<Song> songs = songRepository.searchSong(query);
        return songs.stream().map(song -> mapToSongDto(song)).collect(Collectors.toList());
    }


    @Override
    public void addSongPlayList(Long songId, Long playListId) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);

        Song song = songRepository.findById(songId).get();
        PlayList playList = playListRepository.findById(playListId).get();

        List<PlayList> playLists = song.getPlayLists();
        playLists.add(playList);
        song.setPlayLists(playLists);

        List<Song> songs = playList.getSong();
        songs.add(song);
        playList.setSong(songs);


        song.setCreatedBy(user);
        playList.setCreatedBy(user);
        playListRepository.save(playList);
        songRepository.save(song);


    }
    @Override
    public void deleteSongPlayList(Long songId, Long playListId) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);

        Song song = songRepository.findById(songId).get();
        PlayList playList = playListRepository.findById(playListId).get();

        List<PlayList> playLists = song.getPlayLists();
        playLists.remove(playList);
        song.setPlayLists(playLists);

        List<Song> songs = playList.getSong();
        songs.remove(song);
        playList.setSong(songs);


        song.setCreatedBy(user);
        playList.setCreatedBy(user);
        playListRepository.save(playList);
        songRepository.save(song);


    }



}
