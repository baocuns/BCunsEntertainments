package com.cuns.bce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @Column(name = "title", length = Integer.MAX_VALUE)
    private String title;

    @Column(name = "url", length = Integer.MAX_VALUE)
    private String url;

    @Column(name = "server")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> server;

    public void setPhotoByConfig(CrawlsConfig crawlsConfig) {
        List<String> list = new ArrayList<>();
        if (this.server != null) {
            for (String s : this.server) {
                for (String dsp : crawlsConfig.getDomainServerPicture()) {
                    list.add("https://" + dsp + s);
                }
            }
        }
        this.server = list;
    }

}