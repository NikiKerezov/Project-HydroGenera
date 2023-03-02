package Processor.Services.ProcessPackageBoardVersionHHO2020BTests;

import Processor.Services.ProcessPackageBoardVersionHHO2020B;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessPackageTests {
    private static ProcessPackageBoardVersionHHO2020B processPackageBoardVersionHHO2020B;
    @BeforeAll
    public static void setUp() {
        processPackageBoardVersionHHO2020B = ProcessPackageBoardVersionHHO2020B.getInstance();
    }

    @Test
    public void test() {
        assertDoesNotThrow(() -> {
            processPackageBoardVersionHHO2020B.processPackage(new ArrayList<Character>(){
                {
                    add((char) 0x3D);
                    add((char) 0x36);
                    add((char) 0x2F);
                    add((char) 0xFF);
                    add((char) 0x1D);
                    add((char) 0xFE);
                    add((char) 0x60);
                    add((char) 0x02);
                    add((char) 0x36);
                    add((char) 0x01);
                    add((char) 0x1E);
                    add((char) 0x00);
                    add((char) 0xEA);
                    add((char) 0x1D);
                    add((char) 0x0D);
                    add((char) 0X0A);
                }
            });
        });

        //TODO - assert the data
    }

    @Test
    public void testWithInvalidData() {
        assertThrows(IllegalArgumentException.class, () -> {
            processPackageBoardVersionHHO2020B.processPackage(new ArrayList<Character>(){
                {
                    add((char) 0x3D);
                    add((char) 0x36);
                    add((char) 0x2F);
                    add((char) 0xFF);
                    add((char) 0x1D);
                    add((char) 0xFE);
                    add((char) 0x60);
                    add((char) 0x02);
                    add((char) 0x36);
                    add((char) 0x81);
                    add((char) 0x1E);
                    add((char) 0x00);
                    add((char) 0xEA);
                    add((char) 0x1D);
                    add((char) 0x0D);
                    add((char) 0X0A);
                }
            });
        });
    }
}
