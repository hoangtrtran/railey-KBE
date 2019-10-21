package htwb.ai.TEAMNAME;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import htwb.ai.TEAMNAME.App;

public class AppTest {
    
    @Test
    public void returnsHelloWorldShouldReturnHelloWorld() {
        assertEquals( App.returnsHelloWorld(), "Hello World!" );
    }
}
