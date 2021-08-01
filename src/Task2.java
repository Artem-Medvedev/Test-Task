import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Task2 {

    public static char[][][] matrixMass;
    public static int[][][] endPositions;
    public static  int[] results;
    public static int res = -1;
    public static int size;
    public static int firstX;
    public static int firstY;
    public static int[] last;


    public  static  void main(String[] args) throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader("INPUT.txt"));

        String firstDimension = reader.readLine();
        String[] split = firstDimension.split(" ");
         size = Integer.parseInt(split[0]);
         firstX = Integer.parseInt(split[1]);
         firstY = Integer.parseInt(split[2]);
         matrixMass = new char[size][firstX][firstY];

        int count = 0;
        int countMat = 0;
        while(true){
            firstDimension = reader.readLine();
            if(firstDimension.equals("")) continue;
           // System.out.println(firstDimension);
            for(int i = 0;i<firstY;i++){
                matrixMass[countMat][count][i] = firstDimension.charAt(i);
            }
            count++;
            if(count==firstX) {
                count = 0;
                countMat++;
            }
            if(countMat == size) break;
        }
        matrixMass[size-1][firstX-1][firstY-1]='.';

         endPositions = findEndPosition(matrixMass);
        results = new int[size*firstX*firstY];
                 last = new int[]{firstX - 1, firstY - 1};



        int[] start = {0,0};

        wayFinder(start,0,0);
        System.out.println(res*5);

    }

    public static  void wayFinder(int[] start, int floor,int wayValue){
        int result;
        if(floor==size -1){
            result = shortestPass(matrixMass[floor],start,last);
            if(result==Integer.MAX_VALUE || result<0) return;
            result=result+wayValue;
            if(res==-1 || res>result ){
                res = result;
            }
            return;
        }
        for(int i =0;i< endPositions[floor].length;i++){
            result = shortestPass(matrixMass[floor], start, endPositions[floor][i]);
            if(result==Integer.MAX_VALUE || result<0){
                result = 0;
            } else wayFinder(endPositions[floor][i], floor+1, wayValue+result+1);
        }
    }


    public static int[][][]  findEndPosition(char[][][] matrix){
        int[][][] end = new int[matrix.length-1][matrix[0].length][2];
        int count = 0;

        for(int i=0;i< matrix.length-1;i++){
            int k = i+1;
            for(int j = 0;j<matrix[0].length;j++){
                for(int l = 0;l<matrix[0][0].length;l++){
                    if(matrix[i][j][l]=='.' && matrix[k][j][l]=='.'){
                        end[i][count][0] = j;
                        end[i][count][1] = l;
                        count++;
                    }
                }

            }
            count=0;
        }



        return end;
    }

    public static  int shortestPass(char[][] matrix, int[] start, int[] end){
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];

        for(int i = 0;i<m;i++){
            for(int j = 0;j<n;j++){
                visited[i][j]= matrix[i][j] == 'o';
            }
        }

        return visit(matrix,start, end, visited,Integer.MAX_VALUE,0);
    }

    public static  int visit(char[][] matrix, int[] start, int[] end, boolean[][] visited, int shortest, int dist){
        int sx = start[0];
        int sy = start[1];
        int dx = end[0];
        int dy = end[1];

        if(matrix[sx][sy]=='o' || matrix[dx][dy]=='o' || !isValid(matrix,sx,sy,visited)){
            return Integer.MAX_VALUE;
        }
        if(sx==dx && sy == dy){
            return Math.min(shortest,dist);
        }
        visited[sx][sy] = true;
        if(isValid(matrix,sx-1,sy,visited)) shortest = visit(matrix, new int[]{sx-1,sy},end, visited,shortest,dist+1);
        if(isValid(matrix,sx+1,sy,visited)) shortest = visit(matrix, new int[]{sx+1,sy},end, visited,shortest,dist+1);
        if(isValid(matrix,sx,sy-1,visited)) shortest = visit(matrix, new int[]{sx,sy-1},end, visited,shortest,dist+1);
        if(isValid(matrix,sx,sy+1,visited)) shortest = visit(matrix, new int[]{sx,sy+1},end, visited,shortest,dist+1);

        visited[sx][sy] = false;
        return  shortest;
    }

    public static  boolean isValid(char[][] matrix, int x, int y, boolean[][] visited){
        return x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length && (matrix[x][y] == '.' || matrix[x][y] == '1') && !visited[x][y];
    }

}
