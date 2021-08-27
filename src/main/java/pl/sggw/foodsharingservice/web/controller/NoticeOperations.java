package pl.sggw.foodsharingservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "NOTICES", description = "Ogłoszenia")
public interface NoticeOperations {

    @Operation(summary = "Wyświetlenie komunikatu testowego")
    String home();
}
