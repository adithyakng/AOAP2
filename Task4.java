import java.io.*;

public class Task4 {
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
        calculateMaxAreaForTask4(m,n,h,plot);
    }

    public static void calculateMaxAreaForTask4(int m, int n, int h, int[][] plot){

        //Precompute the number of valid plots present from (0,0) to each point (i,j)
        int valid[][] = new int[m][n];

        for(int i=0;i<n;i++){
            if(plot[0][i] >=h){
                valid[0][i] = ((i-1)>=0) ? 1 + valid[0][i-1] : 1; 
            }
            else{
                valid[0][i] = ((i-1)>=0) ? valid[0][i-1] : 0; 
            }
        }

        for(int i=0;i<m;i++){
            if(plot[i][0] >=h){
                valid[i][0] = ((i-1)>=0) ? 1 + valid[i-1][0] : 1; 
            }
            else{
                valid[i][0] = ((i-1)>=0) ? valid[i-1][0] : 0; 
            }
        }

        int left,diagonal,top;
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                left = valid[i][j-1];
                diagonal = valid[i-1][j-1];
                top = valid[i-1][j];
                valid[i][j] = (plot[i][j] >=h ) ? (left+top-diagonal)+1 : (left+top-diagonal);
            }
        }

        // Now using the precomputation fix a point(i,j) at the top left and expand it along the diagnoal and verify all 4 corner plots
        int maxArea = 0;
        int ans[] = new int[4];
        int topLeft,bottomRight;
        int validPlotCount, invalidPlotCount, cornerInvalidCount;
        for(int i =0; i<m; i++){
            for(int j=0; j<n; j++){
                for(int k=0; k<(Math.min(m-i, n-j)); k++){
                    topLeft = (i!=0 && j!=0) ? valid[i-1][j-1] : 0;
                    bottomRight = valid[i+k][j+k];
                    validPlotCount = bottomRight;
                    if(i!=0 && j!=0){
                        validPlotCount = validPlotCount - valid[i-1][j+k] - valid[i+k][j-1] + topLeft;
                    }
                    invalidPlotCount = ((k +1)*(k+1) - validPlotCount);
                    if(invalidPlotCount > 4){
                        break;
                    }
                    else{
                        // Check all 4 corners
                        cornerInvalidCount = 0;
                        if(plot[i][j] < h){
                            cornerInvalidCount++;
                        }
                        if(k!=0 && plot[i][j+k] <h){
                            cornerInvalidCount++;
                        }
                        if(k!=0 && plot[i+k][j] <h){
                            cornerInvalidCount++;
                        }
                        if(k!=0 && plot[i+k][j+k] <h){
                            cornerInvalidCount++;
                        }

                        if(cornerInvalidCount != invalidPlotCount){
                            break;
                        }
                    }

                    // All plots within the bounding square (i,j) to (i+k,j+k) now have values >=h except for the corner plots
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
        System.out.println(ans[0]+" "+ans[2]+" "+ans[1]+" "+ans[3]);
        

    }
}
