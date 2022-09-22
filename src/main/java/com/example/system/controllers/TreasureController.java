package com.example.system.controllers;

import com.example.system.dto.treasure.CreateTreasureDto;
import com.example.system.dto.treasure.CreateTreasureFindingDto;
import com.example.system.dto.treasure.EditTreasureDto;
import com.example.system.entity.Treasure;
import com.example.system.jsonviews.TreasureView;
import com.example.system.services.CommentService;
import com.example.system.services.TreasureService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/treasure")
public class TreasureController {
    private final TreasureService treasureService;
    private final CommentService commentService;

    @GetMapping("/all")
    @JsonView(TreasureView.MinimalView.class)
    public List<Treasure> allTreasures(){
        return treasureService.getAllTreasures();
    }
    @PostMapping("/create")
    @JsonView(TreasureView.CompleteView.class)
    public Treasure createTreasure(@RequestBody CreateTreasureDto createTreasureDto){
        return treasureService.createTreasure(createTreasureDto);
    }
    @GetMapping("/{id}")
    @JsonView(TreasureView.MinimalView.class)
    public Treasure oneTreasure(@PathVariable int id){
        return treasureService.getTreasureById(id);
    }
    @PutMapping("/{id}/like")
    @JsonView(TreasureView.MinimalView.class)
    public Treasure likeTreasure(@PathVariable int id){
        return treasureService.likeTreasure(id);
    }
    @PutMapping("/{tid}/edit")
    @JsonView(TreasureView.MinimalView.class)
    public Treasure editTreasure(@PathVariable Integer tid, @RequestBody EditTreasureDto editTreasureDto){
        return treasureService.editTreasure(tid, editTreasureDto);
    }
    // TODO: Replace DTO w/ image array as request body
    @PutMapping("/{id}/find")
    @JsonView(TreasureView.CompleteView.class)
    public Treasure findTreasure(@PathVariable Integer id, @RequestBody CreateTreasureFindingDto findTreasureDto){
        return treasureService.findTreasure(id, findTreasureDto);
    }
    @PostMapping("/{id}/comment")
    @JsonView(TreasureView.MinimalView.class)
    public Treasure createComment(@PathVariable int id, @RequestBody String text){
        return commentService.createComment(id, text);
    }
    @PutMapping("/{treasureId}/comment/{commentId}/like")
    @JsonView(TreasureView.MinimalView.class)
    public Treasure likeComment(@PathVariable int treasureId, @PathVariable int commentId){
        return commentService.likeCommentSwitch(treasureId, commentId);
    }
}
