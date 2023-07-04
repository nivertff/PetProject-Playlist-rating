package com.ex.newWeb.service.impl;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.Repository.PlayListRepository;
import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ex.newWeb.mapper.PlayListMapper.mapToPlayList;
import static com.ex.newWeb.mapper.PlayListMapper.mapToPlayListDto;

@Service
public class PlayListServiceImpl implements PlayListService {


    private PlayListRepository playListRepository;

    @Autowired
    public PlayListServiceImpl(PlayListRepository playListRepository) {
        this.playListRepository = playListRepository;
    }

    @Override
    public List<PlayListDto> findAllPlayLists() {
        List<PlayList> playLists = playListRepository.findAll();
        return playLists.stream().map((playList) -> mapToPlayListDto(playList)).collect(Collectors.toList());
    }

    @Override
    public PlayListDto findPlayListById(Long playListId) {
        PlayList playList = playListRepository.findById(playListId).get();
        return mapToPlayListDto(playList);
    }

    @Override
    public void delete(Long playListId) {
        playListRepository.deleteById(playListId);
    }

    @Override
    public PlayList savePlayList(PlayListDto playListDto) {
        return playListRepository.save(mapToPlayList(playListDto));
    }

    @Override
    public void updatePlayList(PlayListDto playListDto) {
        PlayList playList = mapToPlayList(playListDto);
        playListRepository.save(playList);
    }

    @Override
    public List<PlayListDto> searchClubs(String query) {
        List<PlayList> playLists = playListRepository.searchClubs(query);
        return playLists.stream().map(playList -> mapToPlayListDto(playList)).collect(Collectors.toList());
    }
}
