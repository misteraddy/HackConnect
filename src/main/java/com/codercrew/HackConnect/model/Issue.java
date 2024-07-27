package com.codercrew.HackConnect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @ManyToOne
    private User assignee ;

    @JsonIgnore
    @ManyToOne
    private Project project ;

    @JsonIgnore
    @OneToMany(mappedBy = "issue" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
