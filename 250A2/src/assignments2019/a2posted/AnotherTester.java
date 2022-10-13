package assignments2019.a2posted;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * A slightly better tester that superset the original TesterPosted.java.
 *
 * This code is distributed in the hope that it will be useful, but without any warranty. So please
 * do not take this test seriously. You are good to go if you passed TesterPosted.
 *
 * @author Zhangyuan Nie
 */
public class AnotherTester {

    public static String red = "\u001B[31m";
    public static String green = "\u001B[32m";
    public static String reset = "\u001B[0m";

    public static void printRed(String str) {
        System.out.println(red + str + reset);
    }

    public static void printGreen(String str) {
        System.out.println(green + str + reset);
    }

    public static double printStats(long refStart, long refEnd, long myStart, long myEnd) {
        double myInterval = myEnd - myStart;
        double refInterval = refEnd - refStart;
        boolean useMs = myInterval > 1000000;
        System.out.printf("  Time (Yours):     %s%n",
                useMs ? String.format("%.1f ms", myInterval) : myInterval + " ns");
        System.out.printf("  Time (Reference): %s%n",
                useMs ? String.format("%.1f ms", refInterval) : refInterval + " ns");
        boolean isSlower = myInterval > refInterval;
        System.out.printf("  Your implementation seems to be %.1fx %s than the reference.%n",
                isSlower ? myInterval / refInterval : refInterval / myInterval,
                isSlower ? "slower" : "faster");
        return myInterval / refInterval;
    }

    public static double gradeSchoolTester(int base, String s1, String s2) {

        double timesSlower = -1;

        BigInteger java1 = new BigInteger(s1, base);
        BigInteger java2 = new BigInteger(s2, base);

        MyBigInteger mine1;
        MyBigInteger mine2;
        try {
            mine1 = new MyBigInteger(s1, base);
            mine2 = new MyBigInteger(s2, base);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        System.out.println("Dividend: " + mine1);
        System.out.println("Divisor:  " + mine2);
        System.out.println();

        // BigInteger divide
        long refStart = System.nanoTime();
        BigInteger refQuotient = java1.divide(java2);
        long refEnd = System.nanoTime();
        String refStr = "(" + refQuotient.toString(base) + ")_" + base;

        // MyBigInteger divide
        long myStart = System.nanoTime();
        MyBigInteger mineQuotient;
        try {
            mineQuotient = mine1.dividedBy(mine2);
        } catch (Exception e) {
            printRed("dividedBy() failed:");
            e.printStackTrace();
            return -1;
        }

        long myEnd = System.nanoTime();
        String myStr = mineQuotient.toString();

        if (refStr.contentEquals(myStr)) {
            printGreen("Test passed for dividedBy().");
            timesSlower = printStats(refStart, refEnd, myStart, myEnd);
        } else {
            printRed("Test failed for dividedBy().");
            System.out.println("  Expected: " + refStr);
            System.out.println("  Returned: " + myStr);
            return -1;
        }


        // mod
        String javaMod = "(" + java1.mod(java2).toString(base) + ")_" + base;
        String mineMod;
        try {
            mineMod = mine1.mod(mine2).toString();
        } catch (Exception e) {
            printRed("mod() failed:");
            e.printStackTrace();
            return -1;
        }

        if (javaMod.contentEquals(mineMod)) {
            printGreen("Test passed for mod().");
        } else {
            printRed("Test failed for mod().");
            System.out.println("  Expected: " + javaMod);
            System.out.println("  Returned: " + mineMod);
            return -1;
        }

        return timesSlower;
    }

    public static double convertTester(String number, int base, int newBase) {
        System.out.println("Convert " + number + " from base " + base + " to base " + newBase);
        BigInteger refInt = new BigInteger(number, base);
        MyBigInteger myInt;
        try {
            myInt = new MyBigInteger(number, base);
        } catch (Exception e1) {
            printRed("Failed to construct new MyBigInteger");
            e1.printStackTrace();
            return -1;
        }

        long refStart = System.nanoTime();
        String refNew = "(" + refInt.toString(newBase) + ")_" + newBase;
        long refEnd = System.nanoTime();

        long myStart = System.nanoTime();
        String myNew;
        try {
            myNew = myInt.convert(newBase).toString();
        } catch (Exception e1) {
            printRed("Failed to convert");
            e1.printStackTrace();
            return -1;
        }
        long myEnd = System.nanoTime();

        if (refNew.contentEquals(myNew)) {
            printGreen("Passed");
            return printStats(refStart, refEnd, myStart, myEnd);
        } else {
            printRed("Failed");
            System.out.println("  Expected: " + refNew);
            System.out.println("  Returned: " + myNew);
            return -1;
        }

    }

    public static double factorizationTester(MyBigInteger n1,
            ArrayList<MyBigInteger> answer_expected) {
        /*
         * No reference for this test since I can't think of a way to have it without spoiling the
         * answer. The efficiency of this method is mostly base on dividedBy anyways.
         */
        System.out.println("Testing primeFactors() method on : " + n1);
        long start = System.currentTimeMillis();
        ArrayList<MyBigInteger> ans;
        try {
            ans = n1.primeFactors();
        } catch (Exception e1) {
            printRed("Failed to run primeFactors()");
            e1.printStackTrace();
            return -1;
        }
        long end = System.currentTimeMillis();

        String ansStr = Arrays.toString(ans.toArray());
        String expected = Arrays.toString(answer_expected.toArray());

        if (expected.contentEquals(ansStr)) {
            printGreen("Passed");
            System.out.println("Time required: " + (end - start) + "ms");
            return 1;
        }

        printRed("Failed");
        System.out.println("Expected: " + expected);
        System.out.println("Returned: " + ansStr);
        return -1;
    }

    public static int divisionPart() {
        int count = 0;
        System.out.println("\n Division part\n");
        System.out.println("================================");

        /*
         * We can test the correctness of the dividedBy() method in MyBigInteger class by comapring
         * the output to that of Java's BigInteger class.
         */

        if (gradeSchoolTester(10, "3956", "27") != -1) {
            count++;
        }
        System.out.println("----------------");
        if (gradeSchoolTester(7, "624261025332633", "3245") != -1) {
            /*
             * Grade school algorithms - base 7 The majority of the marks for divide will be based
             * on integers of this scale. (Note: The grader will timeout after 10s of computation)
             */
            count++;
        }
        System.out.println("----------------");
        double s2 = gradeSchoolTester(4,
                "123203120302301230120321030123012301230123"
                        + "12332131230123012031203120301203120301203012030120301"
                        + "12332131230123012031203120301203120301203012030120301"
                        + "12332131230123012031203120301203120301203012030120301"
                        + "12332131230123012031203120301203120301203012030120301"
                        + "12332131230123012031203120301203120301203012030120301"
                        + "12332131230123012031203120301203120301203012030120301"
                        + "12332131230123012031203120301203120301203012030120301"
                        + "12332131230123012031203120301203120301203012030120301"
                        + "12332131230123012031203120301203120301203012030120301"
                        + "12332131230123012031203120301203120301203012030120301"
                        + "12332131230123012031203120301203120301203012030120301",
                "1231321021023012301203120301203012301203012301200202010");
        if (s2 != -1) {
            /*
             * Grade school algorithms - base 4 The few remaining marks for divide will be based on
             * numbers of this scale. (Note: The grader will timeout after 10s of computation)
             */
            count++;
        } else {
            System.out.println("Hint: you may need to add 0 to the quotient in some cases");
        }
        System.out.println("----------------");
        if (gradeSchoolTester(8, "625456", "134567543") != -1) {
            count++;
        }
        System.out.println("----------------");
        if (gradeSchoolTester(10,
                "111111111111111111111111111144444444444411"
                        + "39485724098621345671234512345678901679999999999999999",
                "922337203685477580712321") != -1) {
            count++;
        }
        System.out.println("----------------");
        if (gradeSchoolTester(2, "100000000000000", "1111111") != -1) {
            count++;
        }
        double s3 = gradeSchoolTester(9,
                "725553850267850267602572735026750220765207"
                        + "17547835207382078037820301785281735637523785237856077"
                        + "42735378521678521672572530275076342717157265472072057"
                        + "76547652765206785647526725067205672678507865027865027"
                        + "78352783205738507350502873502750237850735073257385207"
                        + "77777777775555555555555555555588888888888888888888888"
                        + "70238508732508735207385208735208732508308308732508200"
                        + "00000000000000000000000000000000000005425000000040101"
                        + "01010101010106752738520370820785777777777777777777777",
                "8888888567635445388868025343242324523453576887876528888");
        if (s3 != -1) {
            count++;
        }
        System.out.println("----------------");
        if (gradeSchoolTester(5, "412130", "3") != -1) {
            count++;
        } else {
            System.out.println("Hint: the remainder may be zero in cases like 21666/21");
        }
        System.out.println("----------------");

        if (gradeSchoolTester(10, "5000000000", "2") != -1) {
            count++;
        }
        System.out.println("----------------");
        boolean allPassed = count == 9;

        System.out.println((allPassed ? green : red) + count + "/9 passed!" + reset);
        int score = (int) ((s2 + s3) / 2);
        if (allPassed) {
            System.out.printf("Score: %s%d%s (avg. times slower for big numbers)%n",
                    score < 800 ? green : red, score, reset);
            System.out.println("================================");
            return score;
        }
        System.out.println("================================");
        return -1;
    }

    public static int conversionPart() throws Exception {
        System.out.println("\n Conversion part\n");
        System.out.println("================================");
        int count = 0;
        /*
         * Same base
         */
        if (convertTester("423", 8, 8) != -1) {
            count++;
        }
        System.out.println("----------------");

        /*
         * Convert from base 10
         */
        if (convertTester("513", 10, 2) != -1) {
            count++;
        }
        System.out.println("----------------");

        if (convertTester("1234", 5, 7) != -1) {
            count++;
        }
        System.out.println("----------------");

        if (convertTester("1234", 7, 5) != -1) {
            count++;
        }
        System.out.println("----------------");

        /*
         * Convert from base 5 The majority of the marks for convert will be based on integers of
         * this scale. (Note: The grader will timeout after 10000 ms of computation)
         */

        if (convertTester("412130", 5, 3) != -1) {
            count++;
        }
        System.out.println("----------------");


        if (convertTester("412130", 5, 7) != -1) {
            count++;
        }
        System.out.println("----------------");


        /*
         * Convert from base 5 The few remaining marks for convert will be based on integers of this
         * scale. (Note: The grader will timeout after 10000 ms of computation)
         */
        String nb = "11234123401234012301423031243130420212343440230412341203341201341203";
        double s1 = convertTester(nb, 5, 3);
        if (s1 != -1) {
            count++;
        }
        System.out.println("----------------");
        double s2 = convertTester(nb, 5, 7);
        if (s2 != -1) {
            count++;
        }
        System.out.println("----------------");

        if (convertTester(nb, 5, 10) != -1) {
            count++;
        }
        System.out.println("----------------");

        String nb2 = "1723465231431264632146126747654163166546145321646123612346327154172346";
        double s3 = convertTester(nb2, 8, 4);
        if (s3 != -1) {
            count++;
        }
        System.out.println("----------------");
        double s4 = convertTester(nb2, 8, 9);
        if (s4 != -1) {
            count++;
        }
        System.out.println("----------------");

        if (convertTester(nb2, 10, 6) != -1) {
            count++;
        }
        System.out.println("----------------");

        boolean allPassed = count == 12;

        System.out.println((allPassed ? green : red) + count + "/12 passed!" + reset);
        int score = (int) ((s1 + s2 + s3 + s4) / 4);
        if (allPassed) {
            System.out.printf("Score: %s%d%s (avg. times slower for big numbers)%n",
                    score < 250 ? green : red, score, reset);
            System.out.println("================================");
            return score;
        }
        System.out.println("================================");
        return -1;
    }

    public static int primePart() throws Exception {
        System.out.println("\n Prime part\n");
        System.out.println("================================");
        MyBigInteger m;
        int count = 0;
        int total = 4;

        // test 1
        int base = 10;
        ArrayList<MyBigInteger> expected_factors = new ArrayList<MyBigInteger>();

        m = new MyBigInteger("5000000000", base);
        for (int i = 0; i < 9; i++) {
            expected_factors.add(new MyBigInteger("2", base));
        }
        for (int i = 0; i < 10; i++) {
            expected_factors.add(new MyBigInteger("5", base));
        }
        if (factorizationTester(m, expected_factors) != -1) {
            count++;
        }
        System.out.println("----------------");

        // test 2
        base = 5;
        m = new MyBigInteger("13143241", base);
        expected_factors.clear();
        expected_factors.add(new MyBigInteger("13143241", base));
        if (factorizationTester(m, expected_factors) != -1) {
            count++;
        }
        System.out.println("----------------");

        // test 3
        base = 6;
        m = new MyBigInteger("1114510444205521", base);
        expected_factors.clear();
        expected_factors.add(new MyBigInteger("5", base));

        expected_factors.add(new MyBigInteger("11", base));
        expected_factors.add(new MyBigInteger("11", base));
        expected_factors.add(new MyBigInteger("11", base));
        expected_factors.add(new MyBigInteger("11", base));

        expected_factors.add(new MyBigInteger("15", base));

        expected_factors.add(new MyBigInteger("35", base));
        expected_factors.add(new MyBigInteger("35", base));

        expected_factors.add(new MyBigInteger("101531", base));
        if (factorizationTester(m, expected_factors) != -1) {
            count++;
        }
        System.out.println("----------------");

        // test 4
        base = 4;
        m = new MyBigInteger("321321313333321213", base);
        expected_factors.clear();
        expected_factors.add(new MyBigInteger(5, base));
        expected_factors.add(new MyBigInteger(1163, base));
        expected_factors.add(new MyBigInteger(10692049, base));
        if (factorizationTester(m, expected_factors) != -1) {
            count++;
        }
        System.out.println("----------------");

        // test 5
        base = 10;
        m = new MyBigInteger("59873467431895", base);
        expected_factors.clear();
        expected_factors.add(new MyBigInteger("5", base));
        expected_factors.add(new MyBigInteger("13", base));
        expected_factors.add(new MyBigInteger("612727", base));
        expected_factors.add(new MyBigInteger("1503329", base));


        System.out.println("Test case skipped since it takes too long to compute.");
        // total++;
        // if (factorizationTester(m, expected_factors) != -1) {
        //     count++;
        // }

        System.out.println("----------------");

        System.out
                .println((count == total ? green : red) + count + "/" + total + " passed!" + reset);
        return count == total ? 1 : -1;
    }

    public static void main(String[] args) throws Exception {
        // Disable colour if on Windows
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            red = "";
            green = "";
            reset = "";
        }
        gradeSchoolTester(10, "5000000000", "2");

        System.out.println("================================");
        System.out.println(" Extended test for MyBugInteger");
        System.out.println("================================");
        System.out.println("This tester contains all the test included in TesterPosted.");
        System.out.println("The time shown in this test may or may not be correct, "
                + "don't worry too much about it");
        System.out.println("You can do `java AnotherTester divide` if you only want to run "
                + "part of the test. Same for 'convert' and 'prime'.");
        System.out.println("Don't take my word for anything. When in doubt, read the source code.");
        System.out.println("================================");

        switch (args.length > 0 ? args[0] : "") {
            case "divide":
                divisionPart();
                break;
            case "convert":
                conversionPart();
                break;
            case "prime":
                primePart();
                break;
            default: {

                int divScore = divisionPart();
                int convertScore = conversionPart();
                int primePass = primePart();
                System.out.println("================================");
                if (divScore != -1) {
                    printGreen("Divide:  ALL PASSED (score: " + divScore + ")");
                } else {
                    printRed("Some tests failed for divide()");
                }
                if (convertScore != -1) {
                    printGreen("Convert: ALL PASSED (score: " + convertScore + ")");
                } else {
                    printRed("Some tests failed for convert()");
                }
                if (primePass != -1) {
                    printGreen("Prime:   ALL PASSED");
                } else {
                    printRed("Some tests failed for primeFactors()");
                }
                break;
            }
        }

    }
}
