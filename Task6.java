
import java.io.*;
import java.util.Arrays;
public class Task6{

    public static void main(String args[]) throws IOException{

        int m,n,h,k;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input[] = br.readLine().trim().split(" ");
        m = Integer.parseInt(input[0]);
        n = Integer.parseInt(input[1]);
        h = Integer.parseInt(input[2]);
        k = Integer.parseInt(input[3]);
        int plot[][] = new int[m][n];
        for(int i=0; i<m;i++){
            input = br.readLine().trim().split(" ");
            for(int j=0;j<n; j++){
                plot[i][j] = Integer.parseInt(input[j]);
            }
        }
        calculateMaxAreaForTask6(m,n,h,k,plot);
    }

    public static void calculateMaxAreaForTask6(int m, int n, int h, int k, int[][] plot){
        
        int maxArea = 0;
        int ans[] = new int[4];
        Arrays.fill(ans, -1);
        int count = 0;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                for(int sl = 1; sl< Math.min(m-i,n-j)+1; sl++){
                    count = 0;
                    // Now verify for each square of size sl if atleast k elements are >=h
                    for(int p=i; p<i+sl; p++){
                        for(int q=j; q<j+sl; q++){
                            if(plot[p][q] < h){
                                count++;
                            }
                        }
                    }
                    // Now only upto k plots can break the minimum of h trees rule
                    if((count <=k) &&  ((sl*sl) > maxArea)){
                        // x1
                        ans[0] = i+1;
                        // x2
                        ans[1] = i+sl;
                        // y1
                        ans[2] = j+1;
                        // y2
                        ans[3] = j+sl;
                        maxArea = sl*sl;
                    }
                }
            }
        }
        
        System.out.println(ans[0]+" "+ans[2]+" "+ans[1]+" "+ans[3]);
    }
}