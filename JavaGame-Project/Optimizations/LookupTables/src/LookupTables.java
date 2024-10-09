public class LookupTables {

    interface ITimeMe {
        double timedMethod();
    }

    private static final int DEGREES_TO_PROCESS = 10000000;

    // I'm a highly trained computer scientist, I know what I'm doing here,
    // you shouldn't do this, even in the privacy of your own home!
    private static final double[] lutSin = preComputeSin();
    private static final double[][] lutSinCos = preComputeSinCos();

    public static void main(String[] args) {

        System.out.println("-- processSin --");
        System.out.printf("time: %d\n", timeMe(LookupTables::processSin));

        System.out.println("-- processSinLUT --");
        System.out.printf("time: %d\n", timeMe(LookupTables::processSinLUT));

        System.out.println("-- processAnySin --");
        System.out.printf("time: %d\n", timeMe(LookupTables::processAnySin));

        System.out.println("-- processAnySinLUT --");
        System.out.printf("time: %d\n", timeMe(LookupTables::processAnySinLUT));
//
//        System.out.println("-- processSinCos --");
//        System.out.printf("time: %d\n", timeMe(LookupTables::processSinCos));
//
//        System.out.println("-- processSinCosLUT --");
//        System.out.printf("time: %d\n", timeMe(LookupTables::processSinCosLUT));

    }

    private static long timeMe(ITimeMe method) {

        long start = System.currentTimeMillis();
        var result = method.timedMethod();
        long end = System.currentTimeMillis();

        // Have to report the result so ensure the code isn't optimized away
        System.out.printf("result: %.4f\n", result);

        return end - start;
    }

    private static double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }

    private static double[] preComputeSin() {
        double[] lutSin = new double[361];

        for (int degree = 0; degree <= 360; degree++) {
            double radian = Math.toRadians(degree);
            lutSin[degree] = Math.sin(radian);
        }

        return lutSin;
    }

    private static double[][] preComputeSinCos() {
        double[][] lutSinCos = new double[361][361];

        for (int degreeSin = 0; degreeSin <= 360; degreeSin++) {
            double radianSin = Math.toRadians(degreeSin);
            for (int degreeCos = 0; degreeCos <= 360; degreeCos++) {
                double radianCos = Math.toRadians(degreeCos);
                lutSinCos[degreeSin][degreeCos] = Math.sin(radianSin) * Math.cos(radianCos);
            }
        }

        return lutSinCos;
    }

    private static double processSin() {
        double total = 0;

        for (int degree = 0; degree <= DEGREES_TO_PROCESS; degree++) {
            total += Math.sin(Math.toRadians(degree % 360));
        }

        return total;
    }

    private static double processSinLUT() {
        double total = 0;

        for (int degree = 0; degree <= DEGREES_TO_PROCESS; degree++) {
            total += lutSin[degree % 360];
        }

        return total;
    }

    private static double processAnySin() {
        double total = 0;

        for (double degree = 0; degree <= 360; degree += 0.01) {
            total += Math.sin(Math.toRadians(degree));
        }

        return total;
    }

    private static double processAnySinLUT() {
        double total = 0;

        for (double degree = 0; degree <= 360; degree += 0.01) {
            total += lerp(lutSin[(int) Math.floor(degree)], lutSin[(int) Math.ceil(degree)], degree % 1);
        }

        return total;
    }

    private static double processSinCos() {
        double total = 0;

        for (int degree = 0; degree <= DEGREES_TO_PROCESS; degree++) {
            double radian = Math.toRadians(degree % 360);
            total += Math.sin(radian) * Math.cos(radian);
        }

        return total;
    }

    private static double processSinCosLUT() {
        double total = 0;

        for (int degree = 0; degree <= DEGREES_TO_PROCESS; degree++) {
            total += lutSinCos[degree % 360][degree % 360];
        }

        return total;
    }
}
