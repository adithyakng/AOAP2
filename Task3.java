import java.io.*;
import java.util.Arrays;

public class Task3 {

    public static void main(String args[]) throws IOException{

        int m,n,h;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input[] = br.readLine().trim().split(" ");
        m = Integer.parseInt(input[0]);
        n = Integer.parseInt(input[1]);
        h = Integer.parseInt(input[2]);
        int plot[][] = new int[m][n];
        for(int i=0; i<m;i++){
            input = br.readLine().trim().split(" ");
            for(int j=0;j<n; j++){
                plot[i][j] = Integer.parseInt(input[j]);
            }
        }
        calculateMaxAreaForTask3(m,n,h,plot);
    }

    public static void calculateMaxAreaForTask3(int m, int n, int h, int[][] plot){

        /**
         * Opt(i,j) denotes the max square length of the plot that has (i,j) as the righmost bottom point.
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
        System.out.println(x1+" "+y1+ " "+x2+" "+y2);
    }

    public static int getMinimum(int a, int b, int c){
        return a > b ? ((b > c) ? c : b) :( (a > c) ? c : a);
    }
    
}
