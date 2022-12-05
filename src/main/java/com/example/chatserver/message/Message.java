package com.example.chatserver.message;

import com.example.chatserver.appuser.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Message {
    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_sequence"
    )
    @Column(name = "message_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private AppUser sender;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    private AppUser receiver;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Boolean isRead;

    public Message(AppUser sender,
                   AppUser receiver,
                   LocalDateTime timestamp,
                   String content,
                   Boolean isRead) {
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.content = content;
        this.isRead = isRead;
    }
}
