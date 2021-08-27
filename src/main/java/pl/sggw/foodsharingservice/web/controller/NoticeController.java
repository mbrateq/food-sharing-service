package pl.sggw.foodsharingservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notices")
@RequiredArgsConstructor
public class NoticeController implements NoticeOperations {

//    @GetMapping("/")
    @GetMapping
    public String home() {
        return "Hello";
    }
}
