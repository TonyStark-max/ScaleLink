package com.URL.ScaleLink.Repository;


import com.URL.ScaleLink.Entity.URLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepo extends JpaRepository<URLEntity,Long> {
    Optional<URLEntity> findByShortCode(String shortCode);
}
