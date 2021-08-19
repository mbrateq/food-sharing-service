package pl.sggw.foodsharingservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ads")
@RequiredArgsConstructor
public class AdsController implements AdsOperations {

    @GetMapping("/")
    public String home() {
        return "Hello";
    }
}
