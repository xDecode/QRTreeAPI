package org.marv.services;

import org.springframework.transaction.annotation.Transactional;
import org.marv.entities.ShortUrl;
import org.marv.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    public ShortUrl saveShortUrl(ShortUrl shortUrl) {
        return shortUrlRepository.save(shortUrl);
    }

    public ShortUrl createShortUrl(String originalUrl) {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortCode(generateShortCode());
        return shortUrlRepository.save(shortUrl);
    }

    public ShortUrl getOriginalUrl(String shortCode) {
        return shortUrlRepository.findByShortCode(shortCode);
    }

    @Transactional
    public Optional<ShortUrl> getShortUrl(Long id) {
        Optional<ShortUrl> s = shortUrlRepository.findById(id);
        return s;
    }

    @Transactional
    public void deleteShortUrl(Long shortUrlId) {
        Optional<ShortUrl> shortUrl = shortUrlRepository.findById(shortUrlId);
        if (shortUrl.isPresent()) {
            shortUrlRepository.delete(shortUrl.get());
        } else {
            throw new RuntimeException("Short URL not found with ID: " + shortUrlId);
        }
    }

    @Transactional
    public void findAndDeleteShortUrl(String shortCode) {
        shortUrlRepository.deleteByShortCode(shortCode);

    }

    private String generateShortCode() {
        // Simple random short code generator
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}