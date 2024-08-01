package com.codercrew.HackConnect.service;

import com.codercrew.HackConnect.model.Issue;
import com.codercrew.HackConnect.model.Project;
import com.codercrew.HackConnect.model.User;
import com.codercrew.HackConnect.repository.IssueRepository;
import com.codercrew.HackConnect.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    public Issue getIssueById(Long issueId) throws Exception {
        Optional<Issue> issue = issueRepository.findById(issueId);

        if(issue.isPresent())
        {
            return issue.get();
        }

        throw new Exception("No issue found with issueId " + issueId);
    }

    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProjectId(projectId);
    }

    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        // Get the project associated with the issue
        Project project = projectService.getProjectById(issueRequest.getProjectID());

        if (project == null) {
            throw new Exception("Project not found");
        }

        // Create a new issue
        Issue issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus(issueRequest.getStatus());
        issue.setProjectID(issueRequest.getProjectID());
        issue.setPriority(issueRequest.getPriority());
        issue.setDueDate(issueRequest.getDueDate());
        issue.setProject(project);

        // Save the issue to the repository
        return issueRepository.save(issue);
    }

    public void deleteIssue(Long issueId,Long userid) throws Exception {

        getIssueById(issueId);

        issueRepository.deleteById(issueId);
    }

    public Issue addUserToIssue(Long issueId,Long userId) throws Exception {

        User user = userService.findUserById(userId);

        Issue issue = getIssueById(issueId);

        issue.setAssignee(user);

        return issueRepository.save(issue);
    }

    public Issue updateStatus(Long issueId,String status) throws Exception {

        Issue issue = getIssueById(issueId);

        issue.setStatus(status);

        return issueRepository.save(issue);
    }
}
