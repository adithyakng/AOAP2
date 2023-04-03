
import java.io.*;
import java.util.Arrays;
public class Task1{

    public static void main(String args[]) throws IOException{

        int m,n,h;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input[] = br.readLine().trim().split(" ");
        // Number of rows
        m = Integer.parseInt(input[0]);
        // Number of columns
        n = Integer.parseInt(input[1]);
        h = Integer.parseInt(input[2]);

        // Store the input in a 2D array of size m*n
        int plot[][] = new int[m][n];
        for(int i=0; i<m;i++){
            input = br.readLine().trim().split(" ");
            for(int j=0;j<n; j++){
                plot[i][j] = Integer.parseInt(input[j]);
            }
        }
        calculateMaxAreaForTask1(m,n,h,plot);
    }

    public static void calculateMaxAreaForTask1(int m, int n, int h, int[][] plot){
        
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
        
        System.out.println(ans[0]+" "+ans[1]+" "+ans[2]+" "+ans[3]);
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
}