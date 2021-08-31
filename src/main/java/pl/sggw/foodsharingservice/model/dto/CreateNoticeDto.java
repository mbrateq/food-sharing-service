package pl.sggw.foodsharingservice.model.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class CreateNoticeDto {
    private String title;
    private String content;
    private LocalDate expirationDate;
}
