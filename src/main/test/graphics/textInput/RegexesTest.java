package graphics.textInput;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class RegexesTest {


    @Test
    public void vector() {
    }

    @Test
    public void matrix() {
    }

    @Test
    public void complexReIm() {
        String complex = "2i+3";
        Assertions.assertEquals(Regexes.complexReIm().matches(complex), true);
    }
}