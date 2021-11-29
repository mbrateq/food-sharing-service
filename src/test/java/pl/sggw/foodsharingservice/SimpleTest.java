package pl.sggw.foodsharingservice;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleTest {

  @Test
  void simpleTest() throws IOException {
    File file = File.createTempFile("test", ".tmp");
    System.out.println(file.exists());
    try (InputStream inputStream =
        Files.newInputStream(file.toPath(), StandardOpenOption.DELETE_ON_CLOSE)) {
      System.out.println(file.exists());
    } catch (Exception e) {
    }
    System.out.println(file.exists());
  }
}
