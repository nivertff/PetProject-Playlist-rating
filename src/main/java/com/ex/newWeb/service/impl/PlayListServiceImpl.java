package com.ex.newWeb.service.impl;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.Dto.SongDto;
import com.ex.newWeb.Repository.PlayListRepository;
import com.ex.newWeb.Repository.SongRepository;
import com.ex.newWeb.Repository.UserRepository;
import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.Song;
import com.ex.newWeb.models.UserEntity;
import com.ex.newWeb.security.SecurityUtil;
import com.ex.newWeb.service.PlayListService;
import com.ex.newWeb.service.SongService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ex.newWeb.mapper.PlayListMapper.mapToPlayList;
import static com.ex.newWeb.mapper.PlayListMapper.mapToPlayListDto;
import static com.ex.newWeb.mapper.SongMapper.mapToSong;

@Service
public class PlayListServiceImpl implements PlayListService {


    private PlayListRepository playListRepository;
    private UserRepository userRepository;
    private SongService songService;
    private SongRepository songRepository;

    @Autowired
    public PlayListServiceImpl(SongRepository songRepository,SongService songService,PlayListRepository playListRepository,UserRepository userRepository) {
        this.playListRepository = playListRepository;
        this.userRepository = userRepository;
        this.songService = songService;
        this.songRepository = songRepository;
    }

    @Override
    public List<PlayListDto> findAllPlayLists() {
        List<PlayList> playLists = playListRepository.findAll();
        return playLists.stream().map((playList) -> mapToPlayListDto(playList)).collect(Collectors.toList());
    }

    @Override
    public PlayListDto findPlayListById(Long playListId) {
        PlayList playList = playListRepository.findById(playListId).get();
        try {
            Double f = playList.getSong().stream().map(son -> son.getRatio())
                    .mapToDouble(Double::doubleValue).average().orElse(0.0);
            playList.setAvgRatio(Math.round(f * 100.0) / 100.0);
        }finally {
            playListRepository.save(playList);
            return mapToPlayListDto(playList);
        }
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
        String username = SecurityUtil.getSessionUser();
        PlayList playList = playListRepository.findById(playListId).get();
        if(username.equals(playList.getCreatedBy().getUsername()) ) {
            if (playList.getSong() != null) {
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
    public void saveCopyPlayList(Long playListId) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);


        PlayList playList = playListRepository.findById(playListId).get();

        PlayList playList1 = PlayList.builder()
                .name(playList.getName())
                .singer(playList.getSinger())
                .createdBy(user)
                .photoUrl(playList.getPhotoUrl())
                .build();
        playList1 = playListRepository.save(playList1);

        for(Song s:playList.getSong()) {
            Song song = songService.saveCopySong(s.getId());
            songService.addSongPlayList(song.getId(),playList1.getId());
        }
        playListRepository.save(playList1);



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


    @Override
    public void processCSV(MultipartFile file) throws IOException {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);

        List<String> name = new ArrayList<>();
        List<String> singer = new ArrayList<>();
        String playListName = "";
        int count = 0;
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (String value : nextLine) {
                    if((count) % 6 == 0){
                        name.add(value);
                    }
                    if((count - 1) % 6 == 0){
                        singer.add(value);
                    }
                    if(count == 9){
                        playListName = value;
                    }
                    count++;
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        name.remove(0);
        singer.remove(0);


        PlayList playList = new PlayList();
        playList.setName(playListName);
        playList.setCreatedBy(user);
        playList = playListRepository.save(playList);

        for(int i = 0; i < name.size(); i++){
            Song song = new Song();
            song.setName(name.get(i));
            song.setSinger(singer.get(i));
            song.setCreatedBy(user);
            song = songRepository.save(song);

            songService.addSongPlayList(song.getId(),playList.getId());
        }

        playListRepository.save(playList);
    }


}
