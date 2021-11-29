package pl.sggw.foodsharingservice;

import org.junit.jupiter.api.Test;
import pl.sggw.foodsharingservice.test.Bar;
import pl.sggw.foodsharingservice.test.Foo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FooBarTest {

  @Test
  void simpleTest() throws IOException {
    Foo foo = new Foo();
    System.out.println(foo.getBar());

    foo.setBar(Bar.val1);
    System.out.println(foo.getBar());

    foo.setBar(null);
    System.out.println(foo.getBar());
  }
}
