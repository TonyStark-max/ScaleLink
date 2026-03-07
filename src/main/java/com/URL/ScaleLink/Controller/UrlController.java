package com.URL.ScaleLink.Controller;


import com.URL.ScaleLink.Entity.URLEntity;
import com.URL.ScaleLink.Service.Producer;
import com.URL.ScaleLink.Service.RateLimiter;
import com.URL.ScaleLink.Service.UrlService;
import com.URL.ScaleLink.ValidationDTO.ReqDTO;
import com.URL.ScaleLink.ValidationDTO.ResDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private final RateLimiter rateLimiter;
    private final Producer rabbitProducer;

    @PostMapping("/shortenUrl")
    public ResponseEntity<ResDTO> shortcode(@RequestBody @Valid ReqDTO reqDTO,
    HttpServletRequest request){

        String ip=request.getRemoteAddr();

        boolean allowed= rateLimiter.isAllowed("rate_limit:"+ip);

        if(!allowed){
            throw new RuntimeException("Too many requests. Try again later.");
        }
        String shortCode=urlService.createShortUrl(reqDTO.getOriginalUrl());

        return ResponseEntity.ok(new ResDTO(shortCode));
    }

    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode,HttpServletResponse response) throws IOException {

        URLEntity url=urlService.getOriginalUrl(shortCode)
                        .orElseThrow(()->new RuntimeException("Short URL not found"));

        rabbitProducer.sendClickEvent(shortCode);
        response.sendRedirect(url.getOriginalUrl());

    }

}
