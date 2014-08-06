package collegescoring;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.inference.TTest;

public class CollegeScoring {

    String[] scoreKey = {"year", "GPA", "SAT", "ACT", "WGPA"};
    //declare original data:
    static final double[][] CAL_SCORES
            = {{2008, 3.05, 2320, 30, 4.2},
            {2008, 3.90, 2400, 30, 4.2},
            {2008, 3.80, 2220, 30, 4.2},
            {2008, 4.00, 2320, 30, 4.2},
            {2008, 4.00, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 4.00, 2320, 30, 4.2},
            {2008, 3.90, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 3.75, 2320, 30, 4.2},
            {2008, 4.00, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 4.00, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 3.90, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 3.75, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 3.90, 2320, 30, 4.2},
            {2008, 3.98, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 4.00, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 3.91, 2320, 30, 4.2},
            {2008, 3.90, 2320, 30, 4.2},
            {2008, 3.75, 2320, 30, 4.2},
            {2008, 3.90, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 3.90, 2320, 30, 4.2},
            {2008, 3.91, 2320, 30, 4.2}};

    String[] CalDeprt = new String[30];
    double[][] LA_SCORES
            = {{2008, 2.98, 2320, 30, 4.2},
            {2008, 3.90, 2220, 30, 4.2},
            {2008, 3.80, 2400, 30, 4.2},
            {2008, 4.00, 1900, 30, 4.2},
            {2008, 4.00, 1920, 30, 4.2},
            {2008, 3.50, 1820, 30, 4.2},
            {2008, 3.00, 2120, 30, 4.2},
            {2008, 3.97, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 3.75, 2320, 30, 4.2},
            {2008, 3.69, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 4.00, 2320, 30, 4.2},
            {2008, 3.68, 2320, 30, 4.2},
            {2008, 3.90, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 3.75, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 3.90, 2320, 30, 4.2},
            {2008, 3.98, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 4.00, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 3.91, 2320, 30, 4.2},
            {2008, 3.90, 2320, 30, 4.2},
            {2008, 3.75, 2320, 30, 4.2},
            {2008, 3.45, 2320, 30, 4.2},
            {2008, 3.80, 2320, 30, 4.2},
            {2008, 3.90, 2320, 30, 4.2},
            {2008, 3.91, 2320, 30, 4.2}};
    double[][][] data = {CAL_SCORES, LA_SCORES};
    static final int CA = 0;
    static final int LA = 1;
    double[][] LaNORM = new double[30][4];
    String[] LaDeprt = new String[30];

    double[][][] weightAverages = new double[2][9][30];

    public static void main(String[] args) {
        CollegeScoring collegeScoring = new CollegeScoring();
        collegeScoring.calc();
    }

    public enum Weights {

        _10_90(.1, .9),
        _20_80(.2, .8),
        _30_70(.3, .7),
        _40_60(0.4, .6),
        _50_50(.5, .5),
        _60_40(.6, .4),
        _70_30(.7, .3),
        _80_20(0.8, .2),
        _90_10(.9, .1);

        double gPercent;
        double sPercent;

        Weights(double gPercent, double sPercent) {
            this.gPercent = gPercent;
            this.sPercent = sPercent;
        }
    }

    public void calc() {
        double[][] CalNORM = new double[CAL_SCORES.length][4];
        double[][][] normData = {CalNORM, LaNORM};
        int[] constant = {4, 2400, 36, 5};
        //SETTING VALUES FOR NORMALIZED DATA
        for (int state = 0; state < 2; state++) {
            for (int i = 0; i < CAL_SCORES.length; i++) {
                for (int j = 0; j < constant.length; j++) {
                    normData[state][i][j] = ((data[state][i][j + 1]) / constant[j]);
                }
            }
        }
        //SETTING VALUES FOR WEIGHTED DATA
        for (int state = 0; state < 2; state++) {
            for (int i = 0; i < CAL_SCORES.length; i++) {
                for (Weights w : Weights.values()) {
                    weightAverages[state][w.ordinal()][i]
                            = (w.sPercent * normData[state][i][1])
                            + (w.gPercent * normData[state][i][0]);
                }
            }
        }
        //CREATING GPA DATAPOINTS

        double UCalGPA = 0;
        double SxCalGPA = 0;
        double ULaGPA = 0;
        double SxLaGPA = 0;
        for (int i = 0; i < 30; i++) {
            UCalGPA = UCalGPA + CAL_SCORES[i][1];
        }
        UCalGPA = UCalGPA / 30;

        for (int i = 0; i < 30; i++) {
            SxCalGPA = SxCalGPA + (((CAL_SCORES[i][1]) - UCalGPA) * ((CAL_SCORES[i][1]) - UCalGPA));
        }
        SxCalGPA = SxCalGPA / 29;
        SxCalGPA = Math.sqrt(SxCalGPA);

        for (int i = 0; i < 30; i++) {
            ULaGPA = ULaGPA + LA_SCORES[i][1];
        }
        ULaGPA = ULaGPA / 30;

        for (int i = 0; i < 30; i++) {
            SxLaGPA = SxLaGPA + (((LA_SCORES[i][1]) - ULaGPA) * ((LA_SCORES[i][1]) - ULaGPA));
        }
        SxLaGPA = SxLaGPA / 29;
        SxLaGPA = Math.sqrt(SxLaGPA);
        //CREATING SAT DATAPOINTS
        double UCalSAT = 0;
        double SxCalSAT = 0;
        double ULaSAT = 0;
        double SxLaSAT = 0;
        for (int i = 0; i < 30; i++) {
            UCalSAT = UCalSAT + CAL_SCORES[i][2];
        }
        UCalSAT = UCalSAT / 30;

        for (int i = 0; i < 30; i++) {
            SxCalSAT = SxCalSAT + (((CAL_SCORES[i][2]) - UCalSAT) * ((CAL_SCORES[i][2]) - UCalSAT));
        }
        SxCalSAT = SxCalSAT / 29;
        SxCalSAT = Math.sqrt(SxCalSAT);

        for (int i = 0; i < 30; i++) {
            ULaSAT = ULaSAT + LA_SCORES[i][2];
        }
        ULaSAT = ULaSAT / 30;

        for (int i = 0; i < 30; i++) {
            SxLaSAT = SxLaSAT + (((LA_SCORES[i][2]) - ULaSAT) * ((LA_SCORES[i][2]) - ULaSAT));
        }
        SxLaSAT = SxLaSAT / 29;
        SxLaSAT = Math.sqrt(SxLaSAT);
        //DATA FOR WEIGHTED AVERAGES
        //UC BERKLEY  
        double[][] sx = new double[2][9];
        double[][] u = new double[2][9];

        for (int state = 0; state < 2; state++) {
            for (Weights w : Weights.values()) {
                for (int i = 0; i < CAL_SCORES.length; i++) {
                    u[state][w.ordinal()] = u[state][w.ordinal()] + weightAverages[state][w.ordinal()][i];
                }
                u[state][w.ordinal()] = u[state][w.ordinal()] / CAL_SCORES.length;
            }
        }

        for (int state = 0; state < 2; state++) {
            for (Weights w : Weights.values()) {
                for (int i = 0; i < CAL_SCORES.length; i++) {
                    sx[state][w.ordinal()] += ((weightAverages[state][w.ordinal()][i] - u[state][w.ordinal()]) * (weightAverages[state][w.ordinal()][i] - u[state][w.ordinal()]));
                }
                sx[state][w.ordinal()] = Math.sqrt((sx[state][w.ordinal()]) / (CAL_SCORES.length - 1));
            }
        }

        //METHOD TO FIND T-VALUES 
        double[] t = new double[9];
        for (Weights w : Weights.values()) {
            t[w.ordinal()] = ((u[CA][w.ordinal()] - u[LA][w.ordinal()])
                    / (Math.sqrt((sx[CA][w.ordinal()] * sx[CA][w.ordinal()] / CAL_SCORES.length)
                            + (sx[LA][w.ordinal()] * sx[LA][w.ordinal()] / CAL_SCORES.length))));
        }
        double TGPAonly = (UCalGPA - ULaGPA) / (Math.sqrt((SxCalGPA * SxCalGPA / 30) + (SxLaGPA * SxLaGPA / 30)));
        double TSATonly = (UCalSAT - ULaSAT) / (Math.sqrt((SxCalSAT * SxCalSAT / 30) + (SxLaSAT * SxLaSAT / 30)));

        p = new double[9];
        TDistribution test = new TDistribution(LA_SCORES.length - 1);
        pSATonly = 1 - test.cumulativeProbability(TSATonly);
        pGPAonly = 1 - test.cumulativeProbability(TGPAonly);
        System.out.println("P = " + pSATonly + " - 100% SAT");
        for (Weights w : Weights.values()) {
            p[w.ordinal()] = 1 - test.cumulativeProbability(t[w.ordinal()]);
            System.out.println("P = " + p[w.ordinal()] + " - " + w.gPercent + "% GPA and " + w.sPercent + "% SAT");
        }
        System.out.println("P = " + pGPAonly + " - 100% GPA");
        System.out.println(TSATonly);
    }
    public double pGPAonly;
    public double pSATonly;
    public double[] p;
}
