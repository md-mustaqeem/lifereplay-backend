package com.life.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class HeatmapResponse {
    private LocalDate date;
    private int value;
}
