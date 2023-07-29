package com.ex.newWeb.service;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.models.PlayList;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface PlayListService {
    List<PlayListDto> findAllPlayLists();

    PlayListDto findPlayListById(Long playListId);

    void delete(Long playListId);

    PlayList savePlayList(PlayListDto playListDto);

    void updatePlayList(PlayListDto playListDto);

    List<PlayListDto> searchAllPlayList(String query);

    List<PlayListDto> findYourPlayLists();

    List<PlayListDto> searchPlayList(String query);
    void processCSV(MultipartFile file) throws IOException;
    void saveCopyPlayList(Long playListId);
}
