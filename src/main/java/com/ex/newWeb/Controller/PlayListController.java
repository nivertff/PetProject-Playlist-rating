package com.ex.newWeb.Controller;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.Dto.SongDto;
import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.models.UserEntity;
import com.ex.newWeb.security.SecurityUtil;
import com.ex.newWeb.service.PlayListService;
import com.ex.newWeb.service.SongService;
import com.ex.newWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PlayListController {
    private PlayListService playListService;
    private UserService userService;
    private SongService songService;


    @Autowired
    public PlayListController(SongService songService, PlayListService playListService, UserService userService) {
        this.playListService = playListService;
        this.userService = userService;
        this.songService = songService;
    }




    @GetMapping("/home")
    public String listAllPlayList(Model model){

        UserEntity user = new UserEntity();
        List<PlayListDto> playLists = playListService.findAllPlayLists();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("playLists", playLists);
        return "all-playlist-list";
    }
    @GetMapping("/playLists")
    public String listPlayList(Model model){
        List<PlayListDto> playLists = playListService.findYourPlayLists();
        model.addAttribute("playLists", playLists);
        return "playList-list";
    }

    @GetMapping("/home/search")
    public String searchAllPlayList(@RequestParam(value = "query") String query, Model model){
        List<PlayListDto> playLists = playListService.searchAllPlayList(query);
        model.addAttribute("playLists", playLists);
        return "all-playlist-list";
    }

    @GetMapping("/playLists/{playListId}")
    public String playListDetail(@PathVariable("playListId") Long playListId, Model model){
        UserEntity user = new UserEntity();
        PlayListDto playListDto = playListService.findPlayListById(playListId);
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("playList",playListDto);
        return "playList-detail";
    }

    @GetMapping("/playLists/search")
    public String searchPlayList(@RequestParam(value = "query") String query, Model model){
        List<PlayListDto> playLists = playListService.searchPlayList(query);
        model.addAttribute("playLists", playLists);
        return "playList-list";
    }



    @GetMapping("/playLists/{playListId}/delete")
    public String deletePlayList(@PathVariable("playListId") Long playListId){
        playListService.delete(playListId);
        return "redirect:/playLists";
    }
//
    @GetMapping("/playLists/{playListId}/copy")
    public String addCopyPlayList(@PathVariable("playListId") Long playListId){
        playListService.saveCopyPlayList(playListId);
        return "redirect:/playLists";
    }

    @GetMapping("/playLists/new")
    public String createPlayList(Model model){
        PlayList playList = new PlayList();
        model.addAttribute("playList", playList);
        return "playList-create";
    }
    @PostMapping("/playLists/new")
    public String savePlayList(@Valid @ModelAttribute("playList") PlayListDto playListDto,
                           BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("playList", playListDto);
            return "playList-create";
        }
        playListService.savePlayList(playListDto);
        return "redirect:/playLists";
    }

    @GetMapping("/playLists/{playListId}/edit")
    public String editPlayListForm(@PathVariable("playListId") Long playListId, Model model){
        PlayListDto playListDto = playListService.findPlayListById(playListId);
        model.addAttribute("playList", playListDto);
        return "playList-edit";
    }


    @PostMapping("/playLists/{playListId}/edit")
    public String updatePlayList(@PathVariable("playListId") Long playListId,
                             @Valid @ModelAttribute("playList") PlayListDto playListDto,
                             BindingResult result,Model model){
        if(result.hasErrors()){
            model.addAttribute("playList", playListDto);
            return "playList-edit";
        }
        playListDto.setId(playListId);
        playListService.updatePlayList(playListDto);
        return "redirect:/playLists";
    }




    @PostMapping("addFile")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() ) {
            return "redirect:/playLists?error";
        }

        try {
            playListService.processCSV(file);
            return "redirect:/playLists?success";
        } catch (Exception e) {
            return "redirect:/playLists?error";
        }
    }




}
