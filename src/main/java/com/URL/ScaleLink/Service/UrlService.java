package com.URL.ScaleLink.Service;


import com.URL.ScaleLink.Entity.URLEntity;
import com.URL.ScaleLink.Repository.UrlRepo;
import com.URL.ScaleLink.Utils.Base62Encoding;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepo urlRepo;
    private final RedisTemplate<String,String> redisTemplate;

    private static final String CACHE_PREFIX="shortUrl:";

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
        String cacheKey=CACHE_PREFIX+shortCode;

        String cachedUrl=redisTemplate.opsForValue().get(cacheKey);

        if(cachedUrl!=null){
            URLEntity cachedEntity=new URLEntity();
            cachedEntity.setShortCode(shortCode);
            cachedEntity.setOriginalUrl(cachedUrl);
            cachedEntity.setActive(true);

            return Optional.of(cachedEntity);
        }

        Optional<URLEntity> urlEntityOptional=
                urlRepo.findByShortCode(shortCode);

        if(urlEntityOptional.isEmpty()){
            return Optional.empty();
        }

        URLEntity url=urlEntityOptional.get();

        redisTemplate.opsForValue().set(
                cacheKey,
                url.getOriginalUrl(),
                60,TimeUnit.MINUTES

        );

        return Optional.of(url);
    }
}
