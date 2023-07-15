package com.ex.newWeb.service.impl;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.Repository.PlayListRepository;
import com.ex.newWeb.Repository.UserRepository;
import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.Song;
import com.ex.newWeb.models.UserEntity;
import com.ex.newWeb.security.SecurityUtil;
import com.ex.newWeb.service.PlayListService;
import com.ex.newWeb.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ex.newWeb.mapper.PlayListMapper.mapToPlayList;
import static com.ex.newWeb.mapper.PlayListMapper.mapToPlayListDto;

@Service
public class PlayListServiceImpl implements PlayListService {


    private PlayListRepository playListRepository;
    private UserRepository userRepository;
    private SongService songService;

    @Autowired
    public PlayListServiceImpl(SongService songService,PlayListRepository playListRepository,UserRepository userRepository) {
        this.playListRepository = playListRepository;
        this.userRepository = userRepository;
        this.songService = songService;
    }

    @Override
    public List<PlayListDto> findAllPlayLists() {
        List<PlayList> playLists = playListRepository.findAll();
        return playLists.stream().map((playList) -> mapToPlayListDto(playList)).collect(Collectors.toList());
    }

    @Override
    public PlayListDto findPlayListById(Long playListId) {
        PlayList playList = playListRepository.findById(playListId).get();
        Double f = playList.getSong().stream().map(son -> son.getRatio())
                .mapToDouble(Double::doubleValue).average().orElse(0.0);
        playList.setAvgRatio(Math.round(f * 100.0) / 100.0);
        playListRepository.save(playList);
        return mapToPlayListDto(playList);
    }

    @Override
    public List<PlayListDto> findYourPlayLists() {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        List<PlayList> playLists = playListRepository.findByCreatedBy(user);
        return playLists.stream().map((playList) -> mapToPlayListDto(playList)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long playListId) {
        PlayList playList = playListRepository.findById(playListId).get();
        if(playList.getSong() != null) {
            List<Song> songs = playList.getSong();
            for (Song song : songs) {
                List<PlayList> playLists = song.getPlayLists();
                playLists.remove(playList);
                song.setPlayLists(playLists);
            }
            playList.setSong(new ArrayList<>());
            playListRepository.save(playList);
        }
        playListRepository.deleteById(playListId);
    }

    @Override
    public PlayList savePlayList(PlayListDto playListDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        PlayList playList = mapToPlayList(playListDto);
        playList.setCreatedBy(user);
        return playListRepository.save(playList);
    }

    @Override
    public void updatePlayList(PlayListDto playListDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        PlayList playList = mapToPlayList(playListDto);
        playList.setCreatedBy(user);
        playListRepository.save(playList);
    }


    @Override
    public List<PlayListDto> searchPlayList(String query) {
        List<PlayList> playLists = playListRepository.searchClubs(query);
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        playLists = playLists.stream().filter(playList -> playList.getCreatedBy() == user).collect(Collectors.toList());
        return playLists.stream().map(playList -> mapToPlayListDto(playList)).collect(Collectors.toList());
    }
    @Override
    public List<PlayListDto> searchAllPlayList(String query) {
        List<PlayList> playLists = playListRepository.searchClubs(query);
        return playLists.stream().map(playList -> mapToPlayListDto(playList)).collect(Collectors.toList());
    }


}
