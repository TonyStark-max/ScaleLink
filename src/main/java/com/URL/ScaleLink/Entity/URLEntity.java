package com.URL.ScaleLink.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="url's")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class URLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String shortCode;

    private String OriginalUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean active=true;
}
