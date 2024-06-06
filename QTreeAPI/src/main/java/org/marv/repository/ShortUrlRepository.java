package org.marv.repository;

import org.marv.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    ShortUrl findByShortCode(String shortCode);

    @Transactional
    void deleteByShortCode(String shortUrlId);
}