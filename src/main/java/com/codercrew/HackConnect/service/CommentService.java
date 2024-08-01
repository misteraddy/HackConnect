package com.codercrew.HackConnect.service;

import com.codercrew.HackConnect.model.Comment;
import com.codercrew.HackConnect.model.Issue;
import com.codercrew.HackConnect.model.User;
import com.codercrew.HackConnect.repository.CommentRepository;
import com.codercrew.HackConnect.repository.IssueRepository;
import com.codercrew.HackConnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private IssueRepository issueRepository ;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private CommentRepository commentRepository ;

    public Comment createComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (issueOptional.isEmpty()) {
            throw new Exception("Issue not found with id " + issueId);
        }

        if (userOptional.isEmpty()) {
            throw new Exception("User not found with id " + userId);
        }

        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment comment = new Comment();
        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreatedDateTime(LocalDateTime.now());
        comment.setContent(content);

        Comment savedComment = commentRepository.save(comment);

        issue.getComments().add(savedComment);

        return savedComment;
    }

    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (commentOptional.isEmpty()) {
            throw new Exception("Comment not found with id " + commentId);
        }

        if (userOptional.isEmpty()) {
            throw new Exception("User not found with id " + userId);
        }

        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if (comment.getUser().equals(user)) {
            commentRepository.delete(comment);
        } else {
            throw new Exception("User does not have permission to delete this comment!");
        }
    }


    List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }

}
