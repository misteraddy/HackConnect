package com.codercrew.HackConnect.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @ManyToOne
    private Issue issue ;

    private String content ;

    private LocalDateTime createdDateTime;

    @ManyToOne
    private User user ;
}
