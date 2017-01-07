package it.polimi.group08;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    TestFile instance = new TestFile();

    @Test
    public void addition_isCorrect() throws Exception {
        FileInputStream fstream = new FileInputStream("/Users/gengdongjie/noSpellRight.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        String str1;
        String str2;
        int lineNum = 0;

        while ((strLine = br.readLine()) != null) {
            lineNum++;
            System.out.print("lineNum=" + lineNum + "  ");
            str1 = strLine.substring(strLine.indexOf("(") + 20, strLine.indexOf(")") - 1);
            str2 = strLine.substring(strLine.indexOf(",") + 2, strLine.indexOf(";") - 2);
            assertEquals(str2, instance.turnTest(str1));

        }
//Close the
//        assertEquals(instance.turnTest("B0000000000s0000m0000GkD000000g0Md0sa14121151130000000004320HRTFHRTM5655M5442M6563M3414M3626M1425M6364M2514M6646M4234M4342M1423M4433M2333M4624M3453M2432M5342M5545A4252M6462M3322M6263M4243M3251M4332M4546M3253M4656M5352A5152M5263M5141M2213M5666M1315M6656M1535M4142M3555M5666M5564M6656M6466M4261M6665M5646M6555M6151M5556M5131M5654M3123M5434M4656M3414M2341M1425M4122M2523A2223"),"W0000000a00000000000000000000000000s031000000000000000000000HRTFHRTBLACK");
        assertEquals(4, 2 + 2);
        assertEquals(instance.turnTest("B0000000000000000000000000000K000000k1200000000000000000000F0RT0HRTA6655"),"W00000000000000000000000000000000000k2000000000000000000000F0RT0HRTBLACK");
    }
}