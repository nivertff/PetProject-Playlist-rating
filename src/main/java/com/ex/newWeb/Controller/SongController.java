package com.ex.newWeb.Controller;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.Dto.SongDto;
import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.Song;
import com.ex.newWeb.models.UserEntity;
import com.ex.newWeb.security.SecurityUtil;
import com.ex.newWeb.service.SongService;
import com.ex.newWeb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SongController {
    private SongService songService;
    private UserService userService;

    public SongController(SongService songService, UserService userService) {
        this.songService = songService;
        this.userService = userService;
    }


    @GetMapping("/songs")
    public String songsList(Model model){
        UserEntity user = new UserEntity();
        List<SongDto> songs = songService.findAllSongs();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("songs", songs);
        return "songs-list";
    }
    @GetMapping("/songs/{songId}")
    public String viewSongs(@PathVariable("songId") Long songId, Model model){
        UserEntity user = new UserEntity();
        SongDto songDto = songService.findPlayListById(songId);
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("playList",songDto);
        model.addAttribute("song",songDto);
        return "song-detail";
    }
    @GetMapping("/songs/{playListId}/new")
    public String newSongForm(@PathVariable("playListId") Long playListId,Model model){
        Song song = new Song();
        model.addAttribute("playListId", playListId);
        model.addAttribute("song", song);
        return "song-create";
    }
    @PostMapping("/songs/{playListId}")
    public String saveSong(@PathVariable("playListId")Long playListId,
                           @ModelAttribute("song") SongDto songDto,
                               BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("song", songDto);
            return "playList-create";
        }
        songService.createSong(playListId,songDto);
        return "redirect:/playLists/" + playListId;
    }
    @GetMapping("/songs/{songId}/edit")
    public String editSong(@PathVariable("songId") Long songId, Model model){
        SongDto songDto = songService.findBySongId(songId);
        model.addAttribute("song", songDto);
        return "song-edit";
    }
    @PostMapping("/songs/{songId}/edit")
    public String updateEvent(@PathVariable("songId") Long songId,
                              @Valid @ModelAttribute("club") SongDto songDto,
                              BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("song", songDto);
            return "song-edit";
        }
        SongDto eventDto = songService.findBySongId(songId);
        songDto.setId(songId);
        songDto.setPlayList( eventDto.getPlayList());
        songService.updateSong(songDto);
        return "redirect:/songs";
    }
    @GetMapping("/songs/{songId}/delete")
    public String songDelete(@PathVariable("songId") Long songId){
        songService.deleteSong(songId);
        return "redirect:/songs";
    }



}
