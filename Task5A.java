

import java.io.*;
import java.util.Arrays;
public class Task5A {
    public static void main(String args[]) throws IOException{

        int m,n,h;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input[] = br.readLine().trim().split(" ");

        // Number or rows
        m = Integer.parseInt(input[0]);
        // Number of columns
        n = Integer.parseInt(input[1]);
        h = Integer.parseInt(input[2]);
        int plot[][] = new int[m][n];
        for(int i=0; i<m;i++){
            input = br.readLine().trim().split(" ");
            for(int j=0;j<n; j++){
                plot[i][j] = Integer.parseInt(input[j]);
            }
        }
        calculateMaxAreaForTask5A(m,n,h,plot);
    }

    public static void calculateMaxAreaForTask5A(int m, int n, int h, int plot[][]){

         /*Precompute the number of valid plots present from (0,0) to each point (i,j) and store it in valid[i][j]
        * Add a extra row and coloum for easy implentation
        */
        int valid[][] = new int[m+1][n+1];

        // Fill the first row and coloum with 0 as this is the extra row and coloum added for easy implementation
        Arrays.fill(valid[0],0);
        for(int i=0;i<m+1;i++){
            valid[i][0] = 0;
        }
        int left,diagonal,top;
        // Precopmute the number of valid plots from (0,0) to (i,j) for all other points
        for(int i=1; i<=m; i++){
            for(int j=1; j<=n; j++){
                left = valid[i][j-1];
                diagonal = valid[i-1][j-1];
                top = valid[i-1][j];
                 /*  Number of valid plots from (0,0) to (i,j) is the sum of number of valid plots from (0,0) to (i,j-1)
                *  (0,0) TO (i-1,j) minus (0,0) to (i-1,j-1) as this sum is counted two times
                */
                valid[i][j] = (plot[i-1][j-1] >=h ) ? (left+top-diagonal)+1 : (left+top-diagonal);
            }
        }

        // Now construct the DP matrix in Top Down manner using recursion
        int dp[][] = new int[m][n];

        // For memoization, fill the dp matrix with 0
        for(int i=0; i<m;i++){
            Arrays.fill(dp[i],0);
        }
        int ans[] = new int[4];
        calculateMaxArea(valid, dp, m-1, n-1,plot, h);

        // Now we have the DP array populated, iterate through it and findMaxSize and the correspoinding top left and bottom right coordinates
        int maxSize = -1;
        for(int i=0; i<m;i++){
            for(int j=0;j<n; j++){
                // If only 1 row or 1 column is present then the first element is the answer
                if( (i == 0 || j ==0) && maxSize == -1){
                    maxSize = 1;
                    ans[0] = ans[1] = ans[2] = ans[3] = i+1;
                }

                else if(dp[i][j] > maxSize){
                    maxSize = dp[i][j];
                    ans[0] = i-(maxSize-1)+1;  // x1
                    ans[1] = i+1; //x2
                    ans[2] = j-(maxSize-1)+1; //y1
                    ans[3] = j+1; //y2
                }
            }
        }
        System.out.println(ans[0] + " " + ans[2] + " " + ans[1] + " " + ans[3]);


    }

    public static int calculateMaxArea(int valid[][],int dp[][], int i, int j,int plot[][],int h){
        if(i <0 || j<0){
            return 0;
        }
        if( i == 0 || j ==0 ){
            // If the plot is in the first row or first coloumn then it is a plots will act as a corner plot and the area will be 1
            dp[i][j] = 1;
            return 1;
        }

        if(dp[i-1][j-1] == 0) {
            dp[i-1][j-1] = calculateMaxArea(valid,dp,i-1,j-1,plot, h);
        }
        if(dp[i-1][j] == 0) {
            dp[i-1][j] = calculateMaxArea(valid, dp, i-1, j,plot, h);
        }
        if(dp[i][j-1] == 0){
            dp[i][j-1] = calculateMaxArea(valid, dp, i, j-1,plot, h);
        }
        int min = getMinimum(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]);
        int validPlotCount = getValidCount(i+1,j+1,min, valid);
        int invalidPlotCount = ((min+1) * (min+1)) - validPlotCount;
        int cornerInvalidCount;
        if(invalidPlotCount > 4){
            dp[i][j] = 2;
            return 2;
        }
        else{
            // Check all 4 corners for invalid plots
            cornerInvalidCount = 0;
            if(plot[i][j] < h){
                cornerInvalidCount++;
            }
            if(plot[i][j-min] <h){
                cornerInvalidCount++;
            }
            if(plot[i-min][j] <h){
                cornerInvalidCount++;
            }
            if(plot[i-min][j-min] <h){
                cornerInvalidCount++;
            }
            // If the 4 corners are not contributing to the invalid count then this cannot be our answer
            if(cornerInvalidCount != invalidPlotCount){
                dp[i][j] = 2; // check this once adithya
                return 2;
            }
            dp[i][j] = min+1;
            return dp[i][j];
        }


    }

    // Function to return the minimum of three numbers
    public static int getMinimum(int a, int b, int c){
        return a > b ? ((b > c) ? c : b) :( (a > c) ? c : a);
    }

    // Function to return validCount from (i,j) to (i-min,j-min)
    public static int getValidCount(int i,int j, int min, int valid[][]){
        int top, diagonal, left;
        int validCount = valid[i][j];
        top = valid[i-min-1][j];
        left = valid[i][j-min-1];
        diagonal = valid[i-min-1][j-min-1];
        validCount = validCount - (top+left-diagonal);
        return validCount;
    }
    
}
