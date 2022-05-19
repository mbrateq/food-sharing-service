package pl.sggw.foodsharingservice.model.dto;

import lombok.Builder;
import lombok.Value;
import pl.sggw.foodsharingservice.model.types.CategoryType;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Value
public class UpdateNoticeDto {

    @NotNull(message = "Title cannot be empty")
    @Size(min = 5, max = 30, message = "Title must be between 5 and 30 chars.")
    private String title;

    @NotNull(message = "Content cannot be empty")
    @Size(min = 10, max = 400, message = "Content must be between 10 and 400 chars.")
    private String content;

    @Future(message = "Expiration is too short")
    private LocalDate expirationDate;
}
