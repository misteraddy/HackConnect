package com.codercrew.HackConnect.controller;

import com.codercrew.HackConnect.DTO.IssueDTO;
import com.codercrew.HackConnect.model.Issue;
import com.codercrew.HackConnect.model.User;
import com.codercrew.HackConnect.request.IssueRequest;
import com.codercrew.HackConnect.response.MessageResponse;
import com.codercrew.HackConnect.service.IssueService;
import com.codercrew.HackConnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(
            @PathVariable Long issueId) throws Exception {

        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(
            @PathVariable Long projectId
    ) throws Exception {

        return  ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issue, @RequestHeader("Authorization") String token) throws Exception {
        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());

        Issue createdIssue = issueService.createIssue(issue, tokenUser);

        IssueDTO issueDT0=new IssueDTO();

        issueDT0.setDescription(createdIssue.getProject().getDescription());

        issueDT0.setDueDate (createdIssue.getDueDate());

        issueDT0.setId(createdIssue.getId());

        issueDT0.setPriority (createdIssue.getPriority());

        issueDT0.setProject(createdIssue.getProject());

        issueDT0.setProjectID (createdIssue.getProject().getId());

        issueDT0.setStatus(createdIssue.getStatus());

        issueDT0.setTitle(createdIssue.getTitle());

        issueDT0.setTags (createdIssue.getTags());

        issueDT0.setAssignee (createdIssue.getAssignee());

        return ResponseEntity.ok(issueDT0);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId, @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);

        issueService.deleteIssue(issueId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("Issue deleted");

        return ResponseEntity.ok(res);
    }


    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId, @PathVariable Long userId) throws Exception {
        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
            @PathVariable String status,
            @PathVariable Long issueId
    ) throws Exception {
        Issue issue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }

}
