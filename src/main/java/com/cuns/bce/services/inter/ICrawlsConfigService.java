package com.cuns.bce.services.inter;

import com.cuns.bce.entities.CrawlsConfig;
import org.springframework.stereotype.Service;

@Service
public interface ICrawlsConfigService {
    // find by id
    CrawlsConfig findById(Long id);
}
