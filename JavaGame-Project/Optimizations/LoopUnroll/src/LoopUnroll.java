import java.util.Random;

public class LoopUnroll {

    interface ITimeMe {
        double timedMethod(int howMany);
    }

    interface ITimeMeArray {
        int[] timedMethod(int[] array1, int[] array2);
    }

    private static final int DEGREES_TO_PROCESS = 10000000;
    private static final int ARRAY_ELEMENTS_TO_PROCESS = 9000000;

    public static void main(String[] args) {
        // This is to make sure the compiler can't anticipate the number
        // of items to process and do a super loop unroll optimization.
        Random rnd = new Random();
        int howMany = (int) Math.max((double) DEGREES_TO_PROCESS / 2, Math.ceil(rnd.nextDouble() * DEGREES_TO_PROCESS));

        System.out.println("-- processSin --");
        System.out.printf("time: %d\n", timeMe(LoopUnroll::processSin, howMany));

        System.out.println("-- processSinUnrolled --");
        System.out.printf("time: %d\n", timeMe(LoopUnroll::processSinUnrolled, howMany));

        howMany = (int) Math.max((double) ARRAY_ELEMENTS_TO_PROCESS / 2, Math.ceil(rnd.nextDouble() * ARRAY_ELEMENTS_TO_PROCESS));
        int[] array1 = new int[howMany];
        int[] array2 = new int[howMany];
        for (int i = 0; i < array1.length; i++) {
            array1[i] = rnd.nextInt();
            array2[i] = rnd.nextInt();
        }

        System.out.println("-- addArrays --");
        System.out.printf("time: %d\n", timeMe(LoopUnroll::addArrays, array1, array2));

        System.out.println("-- addArraysUnrolled --");
        System.out.printf("time: %d\n", timeMe(LoopUnroll::addArraysUnrolled, array1, array2));
    }

    private static long timeMe(ITimeMe method, int howMany) {

        long start = System.currentTimeMillis();
        var result = method.timedMethod(howMany);
        long end = System.currentTimeMillis();

        // Have to report the result so ensure the code isn't optimized away
        System.out.printf("result: %.4f\n", result);

        return end - start;
    }

    private static long timeMe(ITimeMeArray method, int[] array1, int[] array2) {
        long start = System.currentTimeMillis();
        int[] result = method.timedMethod(array1, array2);
        long end = System.currentTimeMillis();

        System.out.printf("result: %d\n", result[0]);

        return end - start;
    }

    private static double processSin(int howMany) {
        double total = 0;

        for (int degree = 0; degree < howMany; degree++) {
            total += Math.sin(Math.toRadians(degree));
        }

        return total;
    }

    private static double processSinUnrolled(int howMany) {
        final int LOOP_UNROLL_SIZE = 5;

        double total = 0;
        int extra = howMany % LOOP_UNROLL_SIZE;
        int loopCount = howMany - extra;
        for (int degree = 0; degree < loopCount; degree += LOOP_UNROLL_SIZE) {
            total += Math.sin(Math.toRadians(degree + 0));
            total += Math.sin(Math.toRadians(degree + 1));
            total += Math.sin(Math.toRadians(degree + 2));
            total += Math.sin(Math.toRadians(degree + 3));
            total += Math.sin(Math.toRadians(degree + 4));
        }
        if (extra-- > 0) total += Math.sin(Math.toRadians(howMany - (extra + 1)));
        if (extra-- > 0) total += Math.sin(Math.toRadians(howMany - (extra + 1)));
        if (extra-- > 0) total += Math.sin(Math.toRadians(howMany - (extra + 1)));
        if (extra-- > 0) total += Math.sin(Math.toRadians(howMany - (extra + 1)));

        return total;
    }

    private static int[] addArrays(int[] array1, int[] array2) {
        int[] result = new int[array1.length];

        for (int i = 0; i < array1.length; i++) {
            result[i] = array1[i] + array2[i];
        }

        return result;
    }

    private static int[] addArraysUnrolled(int[] array1, int[] array2) {
        final int LOOP_UNROLL_SIZE = 5;
        int[] result = new int[array1.length];

        int extra = array1.length % LOOP_UNROLL_SIZE;
        int loopCount = array1.length - extra;
        for (int i = 0; i < loopCount; i += LOOP_UNROLL_SIZE) {
            result[i + 0] = array1[i + 0] + array2[i + 0];
            result[i + 1] = array1[i + 1] + array2[i + 1];
            result[i + 2] = array1[i + 2] + array2[i + 2];
            result[i + 3] = array1[i + 3] + array2[i + 3];
            result[i + 4] = array1[i + 4] + array2[i + 4];
        }
        if (extra-- > 0)
            result[array1.length - (extra + 1)] = array1[array1.length - (extra + 1)] + array2[array1.length - (extra + 1)];
        if (extra-- > 0)
            result[array1.length - (extra + 1)] = array1[array1.length - (extra + 1)] + array2[array1.length - (extra + 1)];
        if (extra-- > 0)
            result[array1.length - (extra + 1)] = array1[array1.length - (extra + 1)] + array2[array1.length - (extra + 1)];
        if (extra-- > 0)
            result[array1.length - (extra + 1)] = array1[array1.length - (extra + 1)] + array2[array1.length - (extra + 1)];

        return result;
    }

}
