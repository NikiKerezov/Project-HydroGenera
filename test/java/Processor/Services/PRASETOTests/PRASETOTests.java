package Processor.Services.PRASETOTests;

import LocalData.Models.DataPackage;
import Processor.Services.PRASETO;
import Processor.Services.ProcessPackageBoardVersionHHO2020B;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PRASETOTests {
    private static PRASETO PRASETO;
    @BeforeAll
    public static void setUp() {
        PRASETO = PRASETO.getInstance();
    }

    @Test
    public void test() {

        assertDoesNotThrow(() -> {
            DataPackage processedPackage = PRASETO.processPackage(new ArrayList<Character>(){
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

            assertEquals(processedPackage.getAmp(), 58.5);
            assertEquals(processedPackage.getUin(), 28.23);
            assertEquals(processedPackage.getBar(), 0.393);
            assertEquals(processedPackage.getPwm(), 35.26);
            assertEquals(processedPackage.getTmp(), 29.0);
        });
    }

    @Test
    public void testWithInvalidData() {
        assertThrows(IllegalArgumentException.class, () -> {
            PRASETO.processPackage(new ArrayList<Character>(){
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
