import java.io.*;
import java.util.Arrays;

public class Task7B {

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
    }
        

    public static int getMinimum(int a, int b, int c){
        return a > b ? ((b > c) ? c : b) :( (a > c) ? c : a);
    }
}
