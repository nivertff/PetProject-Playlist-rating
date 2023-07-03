package com.ex.newWeb.Controller;

import com.ex.newWeb.Dto.PlayListDto;
import com.ex.newWeb.models.PlayList;
import com.ex.newWeb.service.impl.PlayListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PlayListController {
    private PlayListServiceImpl playListService;

    @Autowired
    public PlayListController(PlayListServiceImpl playListService) {
        this.playListService = playListService;
    }


    @GetMapping("/playLists")
    public String listPlayList(Model model){
        List<PlayListDto> playLists = playListService.findAllPlayLists();
        model.addAttribute("playLists", playLists);
        return "playList-list";
    }
    @GetMapping("/playLists/{playListId}")
    public String playListDetail(@PathVariable("playListId") Long playListId, Model model){
        PlayListDto playListDto = playListService.findPlayListById(playListId);
        model.addAttribute("playList",playListDto);
        return "playList-detail";
    }

    @GetMapping("/playLists/new")
    public String createPlayList(Model model){
        PlayList playList = new PlayList();
        model.addAttribute("playList", playList);
        return "playList-create";
    }

    @GetMapping("/playLists/{playListId}/delete")
    public String playListDelete(@PathVariable("playListId") Long playListId, Model model){
        playListService.delete(playListId);
        return "redirect:/playLists";
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
    public String updatePlayList(@PathVariable("playListId") Long clubId,
                             @Valid @ModelAttribute("club") PlayListDto playListDto,
                             BindingResult result){
        if(result.hasErrors()){
            return "playList-edit";
        }
        playListDto.setId(clubId);
        playListService.updatePlayList(playListDto);
        return "redirect:/playLists";
    }



}
