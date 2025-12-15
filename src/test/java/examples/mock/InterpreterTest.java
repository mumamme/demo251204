package examples.mock;

/*
 * InterpreterTest.java Created by Jtest on 7/3/14 2:47:55 PM.
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.mockito.MockedConstruction;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;
/**
 * InterpreterTest is a test class for Interpreter
 *
 * @see Interpreter
 * @author Parasoft Jtest 10.0
 */
public class InterpreterTest
{
    @Test(timeout = 10000)
    public void testAdd34() throws Throwable
    {
        try (MockedConstruction<DataInputStream> mocked = mockConstruction(DataInputStream.class, (mock, context) -> {
            when(mock.readUTF()).thenReturn("ADD");
            when(mock.readInt()).thenReturn(3, 4);
        }
        )) {
            Interpreter itp = new Interpreter(null);
            try {
                int value = itp.getNext().intValue();
                assertTrue(value == 7, "value is:" + value); // failing due to BUG inside getNext method logic
            } catch (IOException e) {
                fail(e.toString());
            }
        }
    }

    /**
     * Parasoft Jtest UTA: Test for getNext()
     *
     * @see examples.mock.Interpreter#getNext()
     * @author shwang
     */
    @Test(timeout = 5000)
    public void testGetNext() throws Throwable
    {
        // Given
        InputStream stream = mock(InputStream.class);
        Interpreter underTest = new Interpreter(stream);

        // When
        Integer result = underTest.getNext();

        // Then - 针对 方法 getNext() 的结果 的断言
        assertNull(result);

    }
}
