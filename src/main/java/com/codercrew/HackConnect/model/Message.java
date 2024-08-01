package com.codercrew.HackConnect.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private String content ;

    private LocalDateTime createdAt ;

    @ManyToOne
    private Chat chat ;

    @ManyToOne
    private User sender ;
}
