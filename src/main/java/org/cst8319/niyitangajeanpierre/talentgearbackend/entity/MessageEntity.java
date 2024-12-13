package org.cst8319.niyitangajeanpierre.talentgearbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable=false)
    private String content;

    @ManyToOne
    @JoinColumn (name="sender_id")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name="recipient_id")
    private UserEntity recipient;

    @JoinColumn(name="sent_at")
    private LocalDateTime sentAt;

    private boolean isRead = false;
}
