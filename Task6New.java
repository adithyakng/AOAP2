 
import java.io.*;
import java.util.Arrays;
public class Task6New{

    public static void main(String args[]) throws IOException{

        int m,n,h,k;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input[] = br.readLine().trim().split(" ");
        // Number of rows
        m = Integer.parseInt(input[0]);
        // Number of columns
        n = Integer.parseInt(input[1]);
        h = Integer.parseInt(input[2]);

        // Number of plots with Exception of >=h for Task 6
        k = Integer.parseInt(input[3]);

        // Store the input plot in a m*n array
        int plot[][] = new int[m][n];
        for(int i=0; i<m;i++){
            input = br.readLine().trim().split(" ");
            for(int j=0;j<n; j++){
                plot[i][j] = Integer.parseInt(input[j]);
            }
        }
        calculateMaxAreaForTask6(m,n,h,k,plot);
    }

    public static void calculateMaxAreaForTask6(int m, int n, int h, int minH, int[][] plot){
        
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

        System.out.println(ans[0]+" "+ans[1]+" "+ans[2]+" "+ans[3]);
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
}