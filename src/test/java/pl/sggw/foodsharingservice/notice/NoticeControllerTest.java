package pl.sggw.foodsharingservice.notice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sggw.foodsharingservice.base.IntegrationTestBase;


import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class NoticeControllerTest extends IntegrationTestBase {

    @BeforeEach
    void setUp(){
        this.clearDatabase();
        this.executeScript("notice/prepare-notice.sql");
    }

    @Test
    @DisplayName("Should return list of Notices after proper request")
    void shouldReturnListOfNoticesAfterProperRequest() throws Exception {
////        GIVEN
//
//        final int expectedNoticesNumber = 0;
////        WHEN
//        mvc.perform(
//          get("api/v1/notices")
////                THEN
//                  .andExpect(status().isOk()));
    }
}
