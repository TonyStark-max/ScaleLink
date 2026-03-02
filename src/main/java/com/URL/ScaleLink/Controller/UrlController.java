package com.URL.ScaleLink.Controller;


import com.URL.ScaleLink.Service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shortenUrl")
    public ResponseEntity<String> shortcode(@RequestParam String originalUrl){
        String shortCode=urlService.createShortUrl(originalUrl);

        return ResponseEntity.ok(shortCode);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode){
        System.out.println("Redirect endpoint triggered with :"+shortCode);
        return urlService.getOriginalUrl(shortCode)
                .map(url->ResponseEntity
                        .status(302)
                        .location(java.net.URI.create(url.getOriginalUrl()))
                        .build())
                .orElse(ResponseEntity.notFound().build());
    }

}
