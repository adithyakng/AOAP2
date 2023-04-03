
import java.io.*;
import java.util.Arrays;
public class Task1{

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
        calculateMaxAreaForTask1(m,n,h,plot);
    }

    public static void calculateMaxAreaForTask1(int m, int n, int h, int[][] plot){
        
        int maxArea = 0;
        int ans[] = new int[4];
        Arrays.fill(ans, -1);
        boolean flag = false;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                for(int k = 1; k< Math.min(m-i,n-j)+1; k++){

                    flag = true;
                    // Now verify for each square of size k if all elements are >=h
                    for(int p=i; p<i+k; p++){
                        for(int q=j; q<j+k; q++){
                            if(plot[p][q] < h){
                                flag = false;
                                break;
                            }
                        }
                        if(!flag){
                            break;
                        }
                    }
                    if(flag && (k*k) > maxArea){
                        // x1
                        ans[0] = i+1;
                        // x2
                        ans[1] = i+k;
                        // y1
                        ans[2] = j+1;
                        // y2
                        ans[3] = j+k;
                        maxArea = k*k;
                    }
                }
            }
        }
        
        System.out.println(ans[0]+" "+ans[2]+" "+ans[1]+" "+ans[3]);
    }
}