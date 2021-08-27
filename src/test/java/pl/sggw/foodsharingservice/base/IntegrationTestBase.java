package pl.sggw.foodsharingservice.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.RunScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTestBase {

    @Autowired
    public MockMvc mvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    private DataSource source;

    public void executeScript(String fileName){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try(Connection dbConnection = source.getConnection();
            InputStream scriptStream = classLoader.getResourceAsStream("scripts/"+ fileName);
            Reader scriptReader = new InputStreamReader(scriptStream, StandardCharsets.UTF_8);
        ) {
            RunScript.execute(dbConnection, scriptReader);
        } catch (SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clearDatabase(){this.executeScript("clear-database.sql");}
}
