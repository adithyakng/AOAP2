 
import java.io.*;
import java.util.Arrays;
public class Task6New{

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

    public static void calculateMaxAreaForTask6(int m, int n, int h, int minH, int[][] plot){
        
        int maxArea = 0;
        int ans[] = new int[4];
        Arrays.fill(ans, -1);
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                for(int k = i; k<m; k++){
                    for(int l = j; l<n; l++){
                        if(l-j == k-i){
                         boolean value = checkAllValues(i, j, k, l, h, minH, plot);
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

    public static boolean checkAllValues(int i, int j, int k, int l, int h, int minH, int[][] plot){
        int count = 0;
        for(int a=i; a<=k; a++){
            for(int b=j; b<=l; b++){
                if(plot[a][b] < h){
                    count ++;
                }
            }
        }
        return (count <= minH) ? true : false;
    }
}