package com.cuns.bce.dto.response.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * DTO for {@link com.cuns.bce.entities.Profile}
 */

@Data
@RequiredArgsConstructor
public class RAUserProfileDto {
    Long id;
    String bcId;
    String fullname;

    public RAUserProfileDto(Long id, String bcId) {
        this.id = id;
        this.bcId = bcId;
    }
}
