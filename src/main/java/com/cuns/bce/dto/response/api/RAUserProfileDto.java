package com.cuns.bce.dto.response.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RAUserProfileDto {
    Long id;

    public RAUserProfileDto(Long id) {
        this.id = id;
    }
}
