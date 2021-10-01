package pl.sggw.foodsharingservice.model.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateUserDto {
    private String username;
    private CharSequence password;
}
