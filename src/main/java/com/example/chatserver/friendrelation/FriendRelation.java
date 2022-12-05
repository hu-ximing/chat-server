package com.example.chatserver.friendrelation;

import com.example.chatserver.appuser.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class FriendRelation {

    @Id
    @SequenceGenerator(
            name = "friend_relation_sequence",
            sequenceName = "friend_relation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "friend_relation_sequence"
    )
    private Long id;

    @ManyToOne
    private AppUser appUser;

    @ManyToOne
    private AppUser friend;

    private LocalDateTime latestInteractionTime;

    public FriendRelation(AppUser appUser,
                          AppUser friend,
                          LocalDateTime latestInteractionTime) {
        this.appUser = appUser;
        this.friend = friend;
        this.latestInteractionTime = latestInteractionTime;
    }
}
