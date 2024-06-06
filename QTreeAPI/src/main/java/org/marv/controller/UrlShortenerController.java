package org.marv.controller;

import org.marv.entities.AccessLog;
import org.marv.entities.QRCode;
import org.marv.entities.ShortUrl;
import org.marv.repository.AccessLogRepository;
import org.marv.responses.Response;
import org.marv.services.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private UrlShortenerService urlShortenerService;

//    @PostMapping("/shorten")
//    public ResponseEntity<Object> createShortUrl(@RequestBody String originalUrl) {
//        ShortUrl shortUrl = urlShortenerService.createShortUrl(originalUrl);
//        return ResponseEntity.ok("Short URL created: /api/url/" + shortUrl.getShortCode());
//    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<?> deleteShortUrl(@PathVariable Long shortCode) {
        try {
            urlShortenerService.deleteShortUrl(shortCode);
            return ResponseEntity.ok(new Response());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete Short URL: " + e.getMessage());
        }
    }

    @GetMapping("/{shortCode}")
    public RedirectView redirect(@PathVariable String shortCode) {
        ShortUrl shortUrl = urlShortenerService.getOriginalUrl(shortCode);
        if (shortUrl != null) {
            QRCode qrCode = shortUrl.getQrcode();
            if (qrCode.isActive()) {
                AccessLog log = new AccessLog(shortUrl, qrCode);
                accessLogRepository.save(log);
                return new RedirectView(shortUrl.getOriginalUrl());
            } else {
                return new RedirectView("/error");
            }
        } else {
            return new RedirectView("/error");
        }
    }

}
