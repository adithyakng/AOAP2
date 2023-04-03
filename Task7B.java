
import java.io.*;
import java.util.Arrays;
public class Task7B{

    public static void main(String args[]) throws IOException{

        int m,n,h,k;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input[] = br.readLine().trim().split(" ");
        // Number of rows
        m = Integer.parseInt(input[0]);
        // Number of columns
        n = Integer.parseInt(input[1]);
        h = Integer.parseInt(input[2]);
        k = Integer.parseInt(input[3]);

        // Store the input in a 2D array of size m*n
        int plot[][] = new int[m][n];
        for(int i=0; i<m;i++){
            input = br.readLine().trim().split(" ");
            for(int j=0;j<n; j++){
                plot[i][j] = Integer.parseInt(input[j]);
            }
        }
        calculateMaxAreaForTask7B(m,n,h,k, plot);
    }

    public static void calculateMaxAreaForTask7B(int m, int n, int h, int k, int[][] plot){

        /*
         * Precompute the number of invalid plots from index (0,0) to (i,j).
         * Added a new row and column to the array to make the computation easier.
         */
        int invalidArray[][] = new int[m+1][n+1];
        // Fill the first row and coloum with 0 as this is the extra row and coloum added for easy implementation
        Arrays.fill(invalidArray[0],0);
        for(int i=0;i<m+1;i++){
            invalidArray[i][0] = 0;
        }

        // Precopmute the number of valid plots from (0,0) to (i,j) for all other points
        int left,diagonal,top;
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                left = invalidArray[i][j-1];
                diagonal = invalidArray[i-1][j-1];
                top = invalidArray[i-1][j];
                 /*  Number of valid plots from (0,0) to (i,j) is the sum of number of invalid plots from (0,0) to (i,j-1)
                *  (0,0) TO (i-1,j) minus (0,0) to (i-1,j-1) as this sum is counted two times
                */
                invalidArray[i][j] = (plot[i-1][j-1] < h ) ? (left+top-diagonal)+1 : (left+top-diagonal);
            }
        }

        /*  Now fix the top left corner at all possible points and find the maximum area
        *   that can be formed by a square with bottom right corner at (i,j).
        *   Make use of binary search to find the maximum size of the square that can be formed.
        */
        int maxArea = 0;
        int ans[] =  new int[4];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                // Lowest possible size of the square is the element itself so digonal length is 0
                int low = 0;
                // Highest possible size of the square is the minimum of the number of rows and columns
                int high = Math.min(m-i,n-j) - 1;
                // Now using binary search concept check if we can find a valid square region whose digonal is between low and high
                while(low <= high){
                    int mid = (low+high)/2;
                    int topLeft = invalidArray[i][j];
                    int bottomRight = invalidArray[i+1+mid][j+1+mid];
                    // InValid plots from (i,j) to (i+mid,j+mid) is the sum of invalid plots from (0,0) to (i+mid,j+mid) minus the sum of invalid plots from (0,0) to (i,0) & 
                    // (0,0) to (i-1,j-1) is deducted twice so add it back
                    int invalidPlotCount = bottomRight - invalidArray[i][j+mid+1] - invalidArray[i+mid+1][j] + topLeft;
                    // Invalid count is totalArea - validCount;
                    if(invalidPlotCount > k){
                        high = mid - 1;
                    }
                    else{
                        // Now as the invalidPlotCount <=k, this is a valid square region so check if we can find a larger square region
                        if(((mid+1)*(mid+1)) > maxArea){
                            maxArea = (mid+1) * (mid+1);
                            ans[0] = (i+1); //x1
                            ans[1] = (i+1+mid); //x2
                            ans[2] = (j+1); // y1
                            ans[3] = (j+1+mid); // y2
                        }
                        low = mid + 1;
                    }
                }
            }
        }

        System.out.println(ans[0]+" "+ans[2]+" "+ans[1]+" "+ans[3]);
        
    }
}