package com.URL.ScaleLink.Service;


import com.URL.ScaleLink.Entity.URLEntity;
import com.URL.ScaleLink.Repository.UrlRepo;
import com.URL.ScaleLink.Utils.Base62Encoding;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepo urlRepo;

    @Transactional
    public String createShortUrl(String originalUrl){
        if(!originalUrl.startsWith("http://") &&
        !originalUrl.startsWith("https://")){
            originalUrl="https://"+originalUrl;
        }
        URLEntity url=new URLEntity();
        url.setOriginalUrl(originalUrl);
        url.setCreatedAt(LocalDateTime.now());
        url.setActive(true);
        urlRepo.save(url);

        String shortCode= Base62Encoding.encode(url.getId());
        url.setShortCode(shortCode);

        return shortCode;
    }

    public Optional<URLEntity> getOriginalUrl(String shortCode){
        Optional<URLEntity> urlEntityOptional=urlRepo.findByShortCode(shortCode);

        if(urlEntityOptional.isEmpty()){
            return Optional.empty();
        }

        URLEntity url=urlEntityOptional.get();

        if(!url.isActive()){
            return Optional.empty();
        }

        if(url.getExpiresAt()!=null &&
        url.getExpiresAt().isBefore(java.time.LocalDateTime.now())){
            return Optional.empty();
        }

        return Optional.of(url);
    }
}
