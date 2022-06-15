public class Matrix {
    public static void main(String[] args) {
        int[] p = {20,6,12,8,5};
        MatrixSolution matrixSolution = new MatrixSolution();
        matrixSolution.matrixChainOrder(p);
    }

}

class MatrixSolution{
    public void matrixChainOrder(int[] p){
        int n = p.length-1;
        int[][] m  = new int[n+1][n+1];
        int[][] s  = new int[n+1][n+1];
        for(int i = 1; i <= n; i++){
            m[i][i] = 0;
        }
        for(int l = 2; l <=n; l++){
            for(int i = 1; i <= n-l+1; i++){
                int j = i+l-1;
                m[i][j]=Integer.MAX_VALUE;
                for(int k = i; k <=j-1; k++){
                    int q = m[i][k]+m[k+1][j]+p[i-1]*p[k]*p[j];
                    if (q<m[i][j]){
                        m[i][j]=q;
                        s[i][j]=k;
                    }
                }
            }
        }
        PrintMatrix(s,1,n);
    }
    public void PrintMatrix(int[][] s, int i, int j){
        if(i==j){
            System.out.print('A');
        }
        else{
            System.out.print('(');
            PrintMatrix(s,i,s[i][j]);
            PrintMatrix(s,s[i][j]+1,j);
            System.out.print(')');
        }
    }
}
