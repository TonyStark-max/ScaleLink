package com.URL.ScaleLink.Controller;


import com.URL.ScaleLink.Service.UrlService;
import com.URL.ScaleLink.ValidationDTO.ReqDTO;
import com.URL.ScaleLink.ValidationDTO.ResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shortenUrl")
    public ResponseEntity<ResDTO> shortcode(@RequestBody @jakarta.validation.Valid ReqDTO reqDTO){
        String shortCode=urlService.createShortUrl(reqDTO.getOriginalUrl());

        return ResponseEntity.ok(new ResDTO(shortCode));
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
