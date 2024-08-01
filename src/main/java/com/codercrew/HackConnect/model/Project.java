package com.codercrew.HackConnect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name ;

    private String description ;

    private String category ;

    private User owner ;


    private List<String> tags = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "project" , cascade = CascadeType.ALL , orphanRemoval = true)
    private Chat chat ;

    @ManyToOne
    private User user ;

    @OneToMany(mappedBy = "project" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Issue> issues = new ArrayList<>();

    @ManyToMany
    private List<User> team = new ArrayList<>();
}
