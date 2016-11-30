package it.polimi.group08;

import org.junit.Test;
import it.polimi.group08.TestForUser;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void TestExample() throws Exception {
        assertEquals(TestForUser.turnTest("W" +
                        "000000" +
                        "GK00sa" +
                        "DS00km" +
                        "MK00sd" +
                        "AS00kg" +
                        "000000" +
                        "5675434334345765" +
                        "000000" +
                        "FHRTFHRT" +
                        "M1343" +
                        "M5242" +
                        "A4353" +
                        "M4243" +
                        "F6400"),
                "B" +
                        "000000" +
                        "GK000a" +
                        "0S0Dkm" +
                        "MK00sd" +
                        "AS00kg" +
                        "000000" +
                        "5754343513457650" +
                        "000643" +
                        "0HRTFHRT" );


    }
}