package com.codercrew.HackConnect.controller;

import com.codercrew.HackConnect.model.Comment;
import com.codercrew.HackConnect.model.User;
import com.codercrew.HackConnect.repository.CommentRepository;
import com.codercrew.HackConnect.request.CreateCommentRequest;
import com.codercrew.HackConnect.response.MessageResponse;
import com.codercrew.HackConnect.service.CommentService;
import com.codercrew.HackConnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository ;

    @PostMapping()
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Comment createdComment = commentService.createComment(
                req.getIssueId(),
                user.getId(),
                req.getContent()
        );

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        commentService.deleteComment(commentId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("Comment deleted successfully");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueId(@PathVariable Long issueId) {
        List<Comment> comments = commentRepository.findByIssueId(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


}
