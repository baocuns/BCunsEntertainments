package com.cuns.bce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "crawls_configs")
public class CrawlsConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "domain")
    private String domain;

    @Column(name = "domain_server")
    private String domainServer;

    @Column(name = "domain_server_picture")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> domainServerPicture;

}