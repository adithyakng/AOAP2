import java.io.*;
import java.util.Arrays;

public class Task2 {

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
        calculateMaxAreaForTask2(m,n,h,plot);
    }

    public static void calculateMaxAreaForTask2(int m, int n, int h, int[][] plot){

        int ans[] = new int[4];
        Arrays.fill(ans,-1);
        int maxArea = 0;
        // Iterate through each and every plot
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
        System.out.println(ans[0]+" "+ans[2]+" "+ans[1]+" "+ans[3]);
    }
    
}
