package com.cuns.bce.services.impl;

import com.cuns.bce.entities.CrawlsConfig;
import com.cuns.bce.repositories.CrawlsConfigRepository;
import com.cuns.bce.services.inter.ICrawlsConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlsConfigService implements ICrawlsConfigService {
    final private CrawlsConfigRepository crawlsConfigRepository;
    @Override
    public CrawlsConfig findById(Long id) {
        return crawlsConfigRepository.findById(id).orElse(null);
    }
}
