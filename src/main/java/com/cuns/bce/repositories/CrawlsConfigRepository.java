package com.cuns.bce.repositories;

import com.cuns.bce.entities.CrawlsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrawlsConfigRepository extends JpaRepository<CrawlsConfig, Long>, JpaSpecificationExecutor<CrawlsConfig> {
}