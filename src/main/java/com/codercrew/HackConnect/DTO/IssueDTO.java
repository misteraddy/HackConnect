package com.codercrew.HackConnect.DTO;

import com.codercrew.HackConnect.model.Project;
import com.codercrew.HackConnect.model.User;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private Long projectID;
    private String priority;
    private LocalDate dueDate;
    private List<String> tags = new ArrayList<>();
    private Project project;
    // Exclude assignee during serialization
    private User assignee;

}