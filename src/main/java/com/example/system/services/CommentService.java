package com.example.system.services;

import com.example.system.entity.Comment;
import com.example.system.entity.Treasure;
import com.example.system.exceptions.badrequest.CommentNotFoundException;
import com.example.system.repository.CommentRepository;
import com.example.system.services.interfaces.ISocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ISocialService socialService;
    private final TreasureService treasureService;

    public Treasure likeCommentSwitch(int treasureId, int commentId) {
//        Treasure treasure = treasureRepository.findById(tid).orElseThrow(()->new TreasureNotFoundException(tid));
//
//        Optional<Comment> oComment = treasure.getComments()
//                .stream()
//                .filter((c)->c.getId() == cid)
//                .findFirst();
        Treasure treasure = treasureService.getTreasureById(treasureId);
        Optional<Comment> oComment =  commentRepository.findById(commentId);
        if(!oComment.isPresent()) throw new CommentNotFoundException(commentId);
        if(!treasure.getComments().contains(oComment.get())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment with id "+commentId+" does not belong to treasure with "+treasureId+" id");

        Comment comment = oComment.get();
        boolean liked = comment.getLikedIds().contains(userService.getCurrentUser().getId());
        socialService.likeEntity(userService.getCurrentUser().getId(), comment, commentRepository, !liked);

        commentRepository.saveAndFlush(comment);
        return treasure;
    }

    public Treasure createComment(int id, String text) {
        Treasure treasure = treasureService.getTreasureById(id);

        Comment comment = commentRepository.saveAndFlush(new Comment(text, userService.getCurrentUser()));
        treasure.getComments().add(comment);

        return treasureService.saveTreasure(treasure);
    }
}
