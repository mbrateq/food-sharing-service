package pl.sggw.foodsharingservice.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sggw.foodsharingservice.base.IntegrationTestBase;
import pl.sggw.foodsharingservice.web.controller.NoticeController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NoticeControllerTest extends IntegrationTestBase {

  @BeforeEach
  void setUp() {
//    todo optimize
    this.clearDatabase();
    this.executeScript("/init-users.sql");
    this.executeScript("notice/notice-controller-it.sql");
  }

  @Test
  @DisplayName("Should return list of Notices after proper request")
  @WithMockUser("user")
  void shouldReturnListOfNoticesAfterProperRequest() throws Exception {
    //        WHEN
    mvc.perform(get("/api/v1/notices/"))
//        //        THEN
            .andDo(print())
            .andExpect(status().isOk());
  }
}
