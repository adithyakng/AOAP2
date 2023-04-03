import java.io.*;
import java.time.Instant;
import java.time.Duration;
import java.util.*;

public class Experiment1 {

    public static void main(String args[]) throws IOException{

        int[] inputSizes = {10,20,50};
        String fileName;
        BufferedReader br;
        String input[];
        int m,n,h;
        int plot[][];
        for(int index = 0; index<inputSizes.length; index++){
            fileName = "input1_"+inputSizes[index]+".txt";
            br = new BufferedReader(new FileReader(fileName));
            input = br.readLine().trim().split(" ");
            m = Integer.parseInt(input[0]);
            n = Integer.parseInt(input[1]);
            h = Integer.parseInt(input[2]);
            plot = new int[m][n];
            for(int i=0; i<m;i++){
                input = br.readLine().trim().split(" ");
                for(int j=0;j<n; j++){
                    plot[i][j] = Integer.parseInt(input[j]);
                }
            }
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("m value: " +m);
            System.out.println("n value: " +n);
            System.out.println("h value: " +h);
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            // Perform each task
            //calculateMaxAreaForTask1(m,n,h,plot);
            calculateMaxAreaForTask2(m,n,h,plot);
            calculateMaxAreaForTask3(m,n,h,plot);
        }
    }

    public static void calculateMaxAreaForTask1(int m, int n, int h, int[][] plot){
        System.out.println("TASK 1");
        System.out.println();
        final Instant startTime = Instant.now();
        
        // Intially the max area of the largest subsquare possible is 0
        int maxArea = 0;
        int ans[] = new int[4];
        Arrays.fill(ans, -1);

        /**
         *  Iterate over each element of the plot considering it as top left corner and 
         *  for each top left corner consider all possible squares as bottom right corner
         *  and check if all elements are >=h
         *  if yes then change the maxArea and update the ans array with top left and bottom right corner.
         * */ 

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                for(int k = i; k<m; k++){
                    for(int l = j; l<n; l++){
                        // Check if the top left corner and bottom right corner form a square by checking the difference in their rows & columns
                        if(l-j == k-i){
                        
                            // Check if all values between (i,j) and (k,l) are >=h
                            boolean value = checkAllValues(i,j,k,l, h,plot);

                            // if all values are greater than or equal to h, update the area and the coordinates of top left(x1,y1) and bottom right corner (x2,y2)
                            if(value && (maxArea < (((k-i)+1) * ((k-i)+1)))){
                                maxArea = ((k-i)+1) * ((k-i)+1);
                                ans[0] = i+1; //x1
                                ans[1] = j+1; // y1
                                ans[2] = k+1; // x2
                                ans[3] = l+1; //y2
                            }
                        }
                    }
                }
            }
        }
        final Instant endTime = Instant.now();
        System.out.println("Total Plots: "+maxArea);
        System.out.println("Coordinates: x1: " +ans[0]+" y1: "+ans[1]+" x2: "+ans[2]+" y2: "+ans[3]);
        System.out.println("Total Time taken in nano seconds: "+(Duration.between(startTime,endTime).toNanos()));
        System.out.println("--------------------");
    }

    // Function to check if all values between (i,j) and (k,l) are >=h
    public static boolean checkAllValues(int i, int j, int k, int l, int h, int[][] plot){
        for(int a=i; a<=k; a++){
            for(int b=j; b<=l; b++){

                // if atleast one value is less than h, return false
                if(plot[a][b] < h){
                    return false;
                }
            }
        }

        // if all values are >=h return true
        return true;
    }

    public static void calculateMaxAreaForTask2(int m, int n, int h, int[][] plot){

        System.out.println("TASK 2");
        System.out.println();
        final Instant startTime = Instant.now();

        int ans[] = new int[4];
        Arrays.fill(ans,-1);

        // Intially the max area of the largest subsquare possible is 0
        int maxArea = 0;
        
        // Iterate through each and every plot considering it as top left corner
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                // We need to check this plot only if it satisfies the min tree condition
                if(plot[i][j] >= h){

                    //Now add and check each additional row and column
                    int k;
                    addRowColumnLoop:
                    for(k=1; ((i+k)<m && (j+k)<n);k++){
                        // Check and Add extra row
                        for(int r = j; r<=j+k; r++){
                            if(plot[i+k][r] <h){
                                break addRowColumnLoop;
                            }
                        }
                        // Check and Add extra column
                        for(int c = i; c<=i+k; c++){
                            if(plot[c][j+k] < h){
                                break addRowColumnLoop;
                            }
                        }
                    }
                    // Change maxArea and update the co-ordinates if required
                    if(k*k > maxArea){
                        maxArea = k*k;
                        // x1
                        ans[0] = i+1;
                        // x2
                        ans[1] = i+k;
                        // y1
                        ans[2] = j+1;
                        // y2
                        ans[3] = j+k;

                    }
                }
            }
        }
        final Instant endTime = Instant.now();
        System.out.println("Total Plots: "+maxArea);
        System.out.println("Coordinates: x1: " +ans[0]+" y1: "+ans[2]+" x2: "+ans[1]+" y2: "+ans[3]);
        System.out.println("Total Time taken in nano seconds: "+(Duration.between(startTime,endTime).toNanos()));
        System.out.println("--------------------");
    }

    public static void calculateMaxAreaForTask3(int m, int n, int h, int[][] plot){

        System.out.println("TASK 3");
        System.out.println();
        final Instant startTime = Instant.now();

        /**
         * Opt(i,j) denotes the max square length of the plot with h trees and that has (i,j) as the righmost bottom point.
         * Goal Find max of all Opt(i,j)
         * Belman's equation: 
         * Opt(i,j) = 0 if plot[i][j] < h
         * Opt(i,j) = min(Opt(i-1,j-1),Opt(i-1,j),Opt(i,j-1)) +1 if plot[i][j] >=h and i,j>0
         * Opt(i,j) = 1 if plot[i][j] >=h and i=0 or j=0
        */

        // Intialize the rightmost point to -1
        int x2 = -1;
        int y2 = -1;
        // Create a dp array to store Opt(i,j)
        int dp[][] = new int[m][n];

        // Perform operations as per bellmans equations
        for(int i=0; i<m; i++){
            Arrays.fill(dp[i],0);
        }
        int maxLength = 0;
        for(int i=0; i<n; i++){
            dp[0][i] = plot[0][i] < h ? 0 : 1;
            if(dp[0][i] > maxLength){
                maxLength = dp[0][i];
                x2 = 1;
                y2 = i+1;
            }
        }
        for(int i=0; i<m; i++){
            dp[i][0] = plot[i][0] < h ? 0 : 1;
            if(dp[i][0] > maxLength){
                maxLength = dp[i][0];
                x2 = i+1;
                y2 = 1;
            }
        }

        for(int i=1; i<m; i++){
            for(int j=1; j<n; j++){
                if(plot[i][j] >=h){
                    dp[i][j] = getMinimum(dp[i-1][j-1],dp[i-1][j],dp[i][j-1]) + 1;
                    if(dp[i][j] > maxLength){
                        maxLength = dp[i][j];
                        x2 = i+1;
                        y2 = j+1;
                    }
                }
                else{
                    dp[i][j] = 0;
                }
            }
        }

        // After getting the maxLength of the square and the rightmost point(x2,y2) of the square we can calcuate (x1,y1)
        int x1 = (maxLength < 1) ? x2 : (x2 - (maxLength - 1));
        int y1 = (maxLength < 1) ? y2 : (y2 -(maxLength - 1));
        final Instant endTime = Instant.now();
        System.out.println("Total Plots: "+(maxLength*maxLength));
        System.out.println("Coordinates: x1: " +x1+" y1: "+y1+" x2: "+x2+" y2: "+y2);
        System.out.println("Total Time taken in nano seconds: "+(Duration.between(startTime,endTime).toNanos()));
        System.out.println("--------------------");

    }

    // Function to return the minimum of three numbers
    public static int getMinimum(int a, int b, int c){
        return a > b ? ((b > c) ? c : b) :( (a > c) ? c : a);
    }

    
}
