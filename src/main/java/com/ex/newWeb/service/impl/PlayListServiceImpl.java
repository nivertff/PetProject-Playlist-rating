package com.ex.newWeb.service.impl;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.Repository.PlayListRepository;
import com.ex.newWeb.Repository.UserRepository;
import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.UserEntity;
import com.ex.newWeb.security.SecurityUtil;
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
    private UserRepository userRepository;

    @Autowired
    public PlayListServiceImpl(PlayListRepository playListRepository,UserRepository userRepository) {
        this.playListRepository = playListRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PlayListDto> findAllPlayLists() {
        List<PlayList> playLists = playListRepository.findAll();
        return playLists.stream().map((playList) -> mapToPlayListDto(playList)).collect(Collectors.toList());
    }

    @Override
    public PlayListDto findPlayListById(Long playListId) {
        PlayList playList = playListRepository.findById(playListId).get();
        Double f = playList.getSongSet().stream().map(son -> son.getRatio())
                .mapToDouble(Double::doubleValue).average().orElse(0.0);
        playList.setAvgRatio(Math.round(f * 100.0) / 100.0);
        playListRepository.save(playList);
        return mapToPlayListDto(playList);
    }


    @Override
    public void delete(Long playListId) {
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
    public List<PlayListDto> searchClubs(String query) {
        List<PlayList> playLists = playListRepository.searchClubs(query);
        return playLists.stream().map(playList -> mapToPlayListDto(playList)).collect(Collectors.toList());
    }


}
