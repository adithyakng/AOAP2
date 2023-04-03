import java.io.*;
import java.util.*;
import java.time.Instant;
import java.time.Duration;

public class Experiment3 {

    public static void main(String args[]) throws IOException{

        int[] inputSizes = {10,50,100};
        String fileName;
        BufferedReader br;
        String input[];
        int m,n,h,k;
        int plot[][];
        for(int index = 0; index<inputSizes.length; index++){
            fileName = "input3_"+inputSizes[index]+".txt";
            br = new BufferedReader(new FileReader(fileName));
            input = br.readLine().trim().split(" ");
            m = Integer.parseInt(input[0]);
            n = Integer.parseInt(input[1]);
            h = Integer.parseInt(input[2]);
            k = Integer.parseInt(input[3]);
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
            System.out.println("k value: " +k);
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
            // Perform each task
            calculateMaxAreaForTask6(m,n,h,k,plot);
            calculateMaxAreaForTask7A(m,n,h,k,plot);
            calculateMaxAreaForTask7B(m,n,h,k,plot);
        }
    }

    public static void calculateMaxAreaForTask6(int m, int n, int h, int minH, int[][] plot){
        
        System.out.println("TASK 6");
        System.out.println();
        final Instant startTime = Instant.now();

        // Intially the max area of the largest subsquare possible is 0
        int maxArea = 0;

        int ans[] = new int[4];
        Arrays.fill(ans, -1);

        /**
         *  Iterate over each element of the plot considering it as top left corner and 
         *  for each top left corner consider all possible squares as bottom right corner
         *  and check the number of elements that are < h. 
         *  if the value <= minH then change the maxArea and update the ans array with top left and bottom right corner.
         * */ 
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                for(int k = i; k<m; k++){
                    for(int l = j; l<n; l++){
                        // Check if the top left corner and bottom right corner form a square by checking the difference in their rows & columns
                        if(l-j == k-i){
                        
                        // Check if values between (i,j) and (k,l) that are <h is <=minH
                            boolean value = checkAllValues(i, j, k, l, h, minH, plot);

                            // If yes, then check if the area of the square is greater than the maxArea and update the maxArea and answer coordinates
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

    // Function to check the number of values between (i,j) and (k,l) that are <h is <=minH
    public static boolean checkAllValues(int i, int j, int k, int l, int h, int minH, int[][] plot){
        int count = 0;
        for(int a=i; a<=k; a++){
            for(int b=j; b<=l; b++){
                if(plot[a][b] < h){
                    count ++;
                }
            }
        }
        // If the values that are <h is <=minH then return true else false
        return (count <= minH) ? true : false;
    }

    public static void calculateMaxAreaForTask7A(int m, int n, int h, int invalid,int[][] plot){

        System.out.println("TASK 7A");
        System.out.println();
        final Instant startTime = Instant.now();

        /*Precompute the number of valid plots present from (0,0) to each point (i,j) and store it in valid[i][j]
        * Add a extra row and coloum for easy implentation
        */
        int valid[][] = new int[m+1][n+1];

        // Fill the first row and coloum with 0 as this is the extra row and coloum added for easy implementation
        Arrays.fill(valid[0],0);
        for(int i=0;i<m+1;i++){
            valid[i][0] = 0;
        }

        // Precopmute the number of valid plots from (0,0) to (i,j) for all other points
        int left,diagonal,top;
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                left = valid[i][j-1];
                diagonal = valid[i-1][j-1];
                top = valid[i-1][j];
                /*  Number of valid plots from (0,0) to (i,j) is the sum of number of valid plots from (0,0) to (i,j-1)
                *  (0,0) TO (i-1,j) minus (0,0) to (i-1,j-1) as this sum is counted two times
                */
                valid[i][j] = (plot[i-1][j-1] >=h ) ? (left+top-diagonal)+1 : (left+top-diagonal);
            }
        }

        // Now using the precomputation fix a point(i,j) at the top left and expand it along the diagnoal and verify all 4 corner plots
        int maxArea = 0;
        int ans[] = new int[4];
        int topLeft,bottomRight;
        int validPlotCount, invalidPlotCount;
        for(int i =0; i<m; i++){
            for(int j=0; j<n; j++){
                // Increment along the diagonal
                for(int k=0; k<(Math.min(m-i, n-j)); k++){
                    topLeft = valid[i][j];
                    bottomRight = valid[i+1+k][j+1+k];
                    // Valid plots from (i,j) to (i+k,j+k) is the sum of valid plots from (0,0) to (i+k,j+k) minus the sum of valid plots from (0,0) to (i,0) & 
                    // (0,0) to (i-1,j-1) is deducted twice so add it back
                    validPlotCount = bottomRight - valid[i][j+k+1] - valid[i+k+1][j] + topLeft;
                    // Invalid count is totalArea - validCount;
                    invalidPlotCount = ((k +1)*(k+1) - validPlotCount);

                    // If the invalid count is greater than k then this cannot be our answer
                    if(invalidPlotCount > invalid){
                        break;
                    }
                    // All plots within the bounding square (i,j) to (i+k,j+k) now have values >=h except for the <=k plots, 
                    // so check maxArea and update coordinates
                    if(((k+1)*(k+1)) > maxArea){
                        maxArea = (k+1) * (k+1);
                        ans[0] = (i+1); //x1
                        ans[1] = (i+1+k); //x2
                        ans[2] = (j+1); // y1
                        ans[3] = (j+1+k); // y2
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

    public static void calculateMaxAreaForTask7B(int m, int n, int h, int k, int[][] plot){
        System.out.println("TASK 7B");
        System.out.println();
        final Instant startTime = Instant.now();
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
        final Instant endTime = Instant.now();
        System.out.println("Total Plots: "+maxArea);
        System.out.println("Coordinates: x1: " +ans[0]+" y1: "+ans[2]+" x2: "+ans[1]+" y2: "+ans[3]);
        System.out.println("Total Time taken in nano seconds: "+(Duration.between(startTime,endTime).toNanos()));
        System.out.println("--------------------");
        
    }
    
}
