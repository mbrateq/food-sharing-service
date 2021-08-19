package pl.sggw.foodsharingservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ADS", description = "Ogłoszenia")
public interface AdsOperations {

    @Operation(summary = "Wyświetlenie komunikatu testowego")
    String home();
}
