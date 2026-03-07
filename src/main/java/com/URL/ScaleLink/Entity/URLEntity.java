package com.URL.ScaleLink.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="url")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class URLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_code", unique = true)
    private String shortCode;

    private String OriginalUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean active=true;

    @Column(name = "click_count")
    private Long count=0L;
}
