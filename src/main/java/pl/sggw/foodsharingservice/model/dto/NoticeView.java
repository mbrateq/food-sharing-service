package pl.sggw.foodsharingservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeView {
    private long noticeId;
    private String title;
    private String content;
    private LocalDateTime publicationDateTime;
    private LocalDate expirationDate;
}
