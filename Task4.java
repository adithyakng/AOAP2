import java.io.*;

public class Task4 {
    public static void main(String args[]) throws IOException{

        int m,n,h;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input[] = br.readLine().trim().split(" ");

        // Number or rows
        m = Integer.parseInt(input[0]);
        // Number of columns
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

        //Precompute the number of valid plots present from (0,0) to each point (i,j) and store it in valid[i][j]
        int valid[][] = new int[m][n];

        // Precopmute the number of valid plots from (0,0) to (0,i) i.e is first row 
        for(int i=0;i<n;i++){
            if(plot[0][i] >=h){
                valid[0][i] = ((i-1)>=0) ? 1 + valid[0][i-1] : 1; 
            }
            else{
                valid[0][i] = ((i-1)>=0) ? valid[0][i-1] : 0; 
            }
        }

        // Precopmute the number of valid plots from (0,0) to (i,0) i.e is first column
        for(int i=0;i<m;i++){
            if(plot[i][0] >=h){
                valid[i][0] = ((i-1)>=0) ? 1 + valid[i-1][0] : 1; 
            }
            else{
                valid[i][0] = ((i-1)>=0) ? valid[i-1][0] : 0; 
            }
        }

        // Precopmute the number of valid plots from (0,0) to (i,j) for all other points
        int left,diagonal,top;
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                left = valid[i][j-1];
                diagonal = valid[i-1][j-1];
                top = valid[i-1][j];
                /*  Number of valid plots from (0,0) to (i,j) is the sum of number of valid plots from (0,0) to (i,j-1)
                *  (0,0) TO (i-1,j) minus (0,0) to (i-1,j-1) as this sum is counted two times
                */
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
                // Increment along the diagonal
                for(int k=0; k<(Math.min(m-i, n-j)); k++){
                    topLeft = (i!=0 && j!=0) ? valid[i-1][j-1] : 0;
                    bottomRight = valid[i+k][j+k];
                    validPlotCount = bottomRight;
                    if(i!=0 && j!=0){
                        validPlotCount = validPlotCount - valid[i-1][j+k] - valid[i+k][j-1] + topLeft;
                    }
                    invalidPlotCount = ((k +1)*(k+1) - validPlotCount);

                    // If the invalid count is greater than 4 then this cannot be our answer
                    if(invalidPlotCount > 4){
                        break;
                    }
                    else{
                        // Check all 4 corners for invalid plots
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

                        // If the 4 corners are not contributing to the invalid count then this cannot be our answer
                        if(cornerInvalidCount != invalidPlotCount){
                            break;
                        }
                    }

                    // All plots within the bounding square (i,j) to (i+k,j+k) now have values >=h except for the corner plots, 
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
        System.out.println(ans[0]+" "+ans[2]+" "+ans[1]+" "+ans[3]);
        

    }
}
