package com.URL.ScaleLink.Repository;


import com.URL.ScaleLink.Entity.URLEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepo extends JpaRepository<URLEntity,Long> {
    Optional<URLEntity> findByShortCode(String shortCode);

    @Modifying
    @Transactional
    @Query("""
            
            UPDATE URLEntity u
            SET u.count=u.count+1
            WHERE u.shortCode = :shortCode
            """)
    void incrementClickCount(@Param("shortCode") String shortCode);
}
