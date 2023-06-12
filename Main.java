import java.util.*;
import java.util.concurrent.*;

import org.knowm.xchart.*;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;


import static java.lang.Math.*;


class Bruteforce{
    //called function
    public static int bruteforce(String text,String tobematched){
        int length = text.length();//length of the text
        int plength = tobematched.length();//length of the pattern;
        int pos=0;

//loop condition
        for(int i=0;i<length-plength;i++){
//initialization of j
            int j=0;
            while((j < plength) && (text.charAt(i+j) == tobematched.charAt(j))){
                j++;
            }
            if(j == plength){
                return i;
            }
        }

        return -1;
    }
    public void res(String txt, String ptr){

        int position=bruteforce(txt,ptr);
        if(position == -1){
            System.out.println("Pattern is not matched in the text");
        }else{
           // System.out.println("Found at position:" + (position));
// System.out.println("End at the position:" + (endindex + tobematched.length()));
        }

    }


}
class Boyer_Moore{

    static int NO_OF_CHARS = 256;

    //A utility function to get maximum of two integers
    static int max (int a, int b) { return (a > b)? a: b; }

    //The preprocessing function for Boyer Moore's
    //bad character heuristic
    static void badCharHeuristic( char []str, int size,int badchar[])
    {

        // Initialize all occurrences as -1
        for (int i = 0; i < NO_OF_CHARS; i++)
            badchar[i] = -1;

        // Fill the actual value of last occurrence
        // of a character (indices of table are ascii and values are index of occurrence)
        for (int i = 0; i < size; i++)
            badchar[(int) str[i]] = i;
    }

    /* A pattern searching function that uses Bad
    Character Heuristic of Boyer Moore Algorithm */
    static void search( String txt_, String pat_)
    {
        char[]txt=txt_.toCharArray();
        char[]pat=pat_.toCharArray();
        int m = pat.length;
        int n = txt.length;

        int badchar[] = new int[NO_OF_CHARS];

      /* Fill the bad character array by calling
         the preprocessing function badCharHeuristic()
         for given pattern */
        badCharHeuristic(pat, m, badchar);

        int s = 0; // s is shift of the pattern with
        // respect to text
        //there are n-m+1 potential alignments
        while(s <= (n - m))
        {
            int j = m-1;

          /* Keep reducing index j of pattern while
             characters of pattern and text are
             matching at this shift s */
            while(j >= 0 && pat[j] == txt[s+j])
                j--;

          /* If the pattern is present at current
             shift, then index j will become -1 after
             the above loop */
            if (j < 0)
            {
               // System.out.println("Patterns occur at shift = " + s);

              /* Shift the pattern so that the next
                 character in text aligns with the last
                 occurrence of it in pattern.
                 The condition s+m < n is necessary for
                 the case when pattern occurs at the end
                 of text */
                //txt[s+m] is character after the pattern in text
                s += (s+m < n)? m-badchar[txt[s+m]] : 1;

            }

            else
              /* Shift the pattern so that the bad character
                 in text aligns with the last occurrence of
                 it in pattern. The max function is used to
                 make sure that we get a positive shift.
                 We may get a negative shift if the last
                 occurrence of bad character in pattern
                 is on the right side of the current
                 character. */
                s += max(1, j - badchar[txt[s+j]]);
        }
    }

}
class KMP_String_Matching {
    void KMPSearch(String pat, String txt)
    {
        int M = pat.length();
        int N = txt.length();

        // create lps[] that will hold the longest
        // prefix suffix values for pattern
        int lps[] = new int[M];
        int j = 0; // index for pat[]

        // Preprocess the pattern (calculate lps[]
        // array)
        computeLPSArray(pat, M, lps);

        int i = 0; // index for txt[]
        while ((N - i) >= (M - j)) {
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                //System.out.println("Found pattern + "at index " + (i - j));
                j = lps[j - 1];
            }

            // mismatch after j matches
            else if (i < N
                    && pat.charAt(j) != txt.charAt(i)) {
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
    }

    void computeLPSArray(String pat, int M, int lps[])
    {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            }
            else // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment
                    // i here
                }
                else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }
}

public class Main {

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);



        System.out.println("Please enter your doc num");
        Double num = sc.nextDouble();
        String randomStr[] = usingRandomUUID(num);
        if(randomStr==null)
        {
            System.out.println("Please insert a valid value ");
            System.exit(0);
        }
        check_Algorithims(randomStr,num);




    }

    /* try to run data on 3 algorithms*/
    static void check_Algorithims(String [] randomStr,Double num) {
        int num_of_try=5;
        TimeUnit time = TimeUnit.SECONDS;
        HashMap<String, Long> map = new HashMap<>();
        HashMap<Double,List<Long>> to_draw_chart = new HashMap<Double, List<Long>>();
        String ptr;
        String txt = null;
        ArrayList<Long > times1 = new ArrayList<Long>();
        double[] xData = new double[num_of_try];
        // List ydata;

        Scanner sc2 = new Scanner(System.in);

        // Run on data 5 time and add 100 string every time

        List<String> names_of_algo = null;
        names_of_algo = new ArrayList<>();
        long [] times=new long[3];
        names_of_algo.add("KMP algp");
        names_of_algo.add("Boyer_Moore algo");
        names_of_algo.add("Brute Force ");
        for (int i = 0; i < num_of_try; i++) {
            //convert data to String
            xData[i] = num;
            for (int j = 0; j < num; j++) {
                txt += randomStr[j];
            }

            // try to delay data to read answer of the last time
            try {
                time.sleep(3L);
            } catch (InterruptedException e) {
                System.out.println("hhhhhhhh" + e);

            }

            // appear data to choose pattern from it
            System.out.println("A random string of 32 characters: " + txt);

            // ask youser to enter pattern
            System.out.println("Please enter your pattern");
            ptr = sc2.nextLine();



            long start = System.nanoTime();
            new KMP_String_Matching().KMPSearch(ptr, txt);
            long elapsed = System.nanoTime() - start;
            map.put("KMP algo", elapsed);
             times1.add((Long) elapsed);

            //times[x]=elapsed;
            //to_draw_chart.put(num, new long[]{elapsed});

            start = System.nanoTime();
            new Boyer_Moore().search(txt, ptr);
            elapsed = System.nanoTime() - start;
            map.put("Boyer_Moore algo", elapsed);

            times1.add((Long) elapsed);
          //  to_draw_chart.put(num, new long[]{elapsed});

            start = System.nanoTime();
            new Bruteforce().res(txt, ptr);
            elapsed = System.nanoTime() - start;
            map.put("Bruteforce algo", elapsed);

            times1.add((Long) elapsed);


            check_performance(map);
            to_draw_chart.put((Double)num, new ArrayList<Long>());
          //  map.put(num, new ArrayList<Long>());
            for(int l=0;l<3;l++)
            {
                to_draw_chart.get((Double) num).add(times1.get(l));
                //to_draw_chart.put(num,new long[]{times[l]});


            }

            //to_draw_chart.put(num, times);
            num += 100;
            randomStr = usingRandomUUID(num);
        }
        System.out.println( "   X data"+ to_draw_chart);



        chart(xData, to_draw_chart, names_of_algo);



    }
   //check which smallest time aglorithims takes
    static void check_performance(HashMap map)
    {
        long valu1=(long)map.get("KMP algo");
        long value2=(long)map.get("Boyer_Moore algo");
        long value3=(long)map.get("Bruteforce algo");

        long smallest = Math.min(valu1, Math.min(value2,value3));


        System.out.println(map+"All algo");
        System.out.println("Smallest value is "+smallest);

    }

    //method for generating doc
    static String [] usingRandomUUID(Double num) {

        String arr[]=new String[10000];
        UUID randomUUID ;
        if(num<10000){
            for(int i=0;i<num;i++)
            {

                randomUUID = UUID.randomUUID();
                arr[i]=randomUUID.toString();


            }
            return arr;
        }
        return null;


    }
    // function that show data in chart
    static void chart(  double[]  xData,HashMap<Double,List<Long>> ydata,List<String> names)
                        //double[] y1data,double[] y2data, double[]y3data)
    {
        XYChart chart = new XYChartBuilder().width(900).height(800).title("chart").xAxisTitle("size").yAxisTitle("time").build();
        List<Long> y_long;
        double[] y1 = new double[5];
        double[] y2 = new double[5 ];
        double[] y3 = new double[5 ];
        double[] y4 = new double[3 ];
        double[] y5 = new double[3 ];




           int k=0;
           int j=0;
           int t=0;
            for (int i = 0; i <5; i++) {
                y_long = ydata.get(xData[i]);
                k=0;
                while(k<3)
                {
                    if(j>=5)
                    {
                        j=0;
                    }
                    //System.out.println(y_long + "uuuuuuuuuuuuuuuu" + xData[j]);
               //     for (int k = 0; k < 5; k++) {
                        if (t < 5) {
                            y1[j] = y_long.get(k);

                        } else if (t < 10 && t>=5) {
                            y2[j] = y_long.get(k);

                        } else if (t < 15 && t>=10) {
                            y3[j] = y_long.get(k);
                        }

                        t++;
                        j++;
                        k++;
                  //  }
                }

            }

        chart.addSeries(names.get(0), xData,y1);
        chart.addSeries(names.get(1), xData,y2);
        chart.addSeries(names.get(2), xData,y3);






//        // Show it
//        double[] y1 = new double[20];
//        double[] y2 = new double[20 ];
//        double[] y3 = new double[20 ];
//
//
//           int k=0;
//           int j=0;
//           int t=0;
//            for (int i = 0; i <20; i++) {
//                y_long = ydata.get(xData[i]);
//                k=0;
//                while(k<3)
//                {
//                    if(j>=20)
//                    {
//                        j=0;
//                    }
//                    //System.out.println(y_long + "uuuuuuuuuuuuuuuu" + xData[j]);
//               //     for (int k = 0; k < 5; k++) {
//                        if (t < 20) {
//                            y1[j] = y_long.get(k);
//
//                        } else if (t < 40 && t>=20) {
//                            y2[j] = y_long.get(k);
//
//                        } else if (t < 50 && t>=40) {
//                            y3[j] = y_long.get(k);
//                        }
//
//                        t++;
//                        j++;
//                        k++;
//                  //  }
//                }
//
//            }
//         xData[0]=0;
//            y1[0]=0;
//            y2[0]=0;
//            y3[0]=0;
//        chart.addSeries(names.get(0), xData,y1);
//        chart.addSeries(names.get(1), xData,y2);
//        chart.addSeries(names.get(2), xData,y3);


        new SwingWrapper(chart).displayChart();

    }
}

