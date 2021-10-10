package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int width;
    private  int height;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF isFullWeightedQuickUnionUF;
    private int numOfOpenSite;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if(N <= 0){
            throw new IndexOutOfBoundsException("N should always greater than zero");
        }
        grid = new boolean[N][N];
        width = N;
        height = N;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                grid[i][j] = false;
            }
        }
        weightedQuickUnionUF = new WeightedQuickUnionUF(N*N+2);
        isFullWeightedQuickUnionUF = new WeightedQuickUnionUF(N*N+1);
        numOfOpenSite = 0;

    }
    //help to detect the 2D array's element in 1D array's position
    private int xyTo1D(int row,int column){
       return  row * width + column;
    }

    //help to detect if the current site has neighbor;
    /**
     * 0：left neighbour
     * 1：up neighbour；
     * 2：right neighbour
     * 3：down neighbour；
     * */
    private boolean hasNeighbour(int row,int column,int flag){
        switch (flag){
            case 0:
                if(column-1>=0){
                    return true;
                }
                break;
            case 1:
                if(row-1>=0){
                    return true;
                }
                break;
            case 2:
                if(column+1<=width-1){
                    return true;
                }
                break;
            case 3:
                if(row+1<=height-1){
                    return true;
                }
                break;
        }
        return false;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col){
        if(row < 0 || row > height-1 || col <0 || col> width-1){
            throw new IndexOutOfBoundsException("row and column should be in range 0,"+(width-1));
        }

        if(grid[row][col] == false){
            grid[row][col] = true;
            numOfOpenSite += 1;
            if(hasNeighbour(row,col,0)){
                int current = xyTo1D(row,col);
                int left = xyTo1D(row,col-1);
                if(isOpen(row,col-1)){
                    weightedQuickUnionUF.union(current,left);
                    isFullWeightedQuickUnionUF.union(current,left);
                }
            }

            if(hasNeighbour(row,col,1)){
                int current = xyTo1D(row,col);
                int up = xyTo1D(row-1,col);
                if(isOpen(row-1,col)){
                    weightedQuickUnionUF.union(current,up);
                    isFullWeightedQuickUnionUF.union(current,up);
                }
            }

            if(hasNeighbour(row,col,2)){
                int current = xyTo1D(row,col);
                int right = xyTo1D(row,col+1);
                if(isOpen(row,col+1)){
                    weightedQuickUnionUF.union(current,right);
                    isFullWeightedQuickUnionUF.union(current,right);
                }
            }
            if(hasNeighbour(row,col,3)){
                int current = xyTo1D(row,col);
                int down = xyTo1D(row+1,col);
                if(isOpen(row+1,col)){
                    weightedQuickUnionUF.union(current,down);
                    isFullWeightedQuickUnionUF.union(current,down);
                }
            }
        }
        if(row==0){
            int source = width*height;
            int current = xyTo1D(row,col);
            weightedQuickUnionUF.union(current,source);
            isFullWeightedQuickUnionUF.union(current,source);
        }
        if(row == height-1){
            int drain = width*height+1;
            int current = xyTo1D(row,col);
            weightedQuickUnionUF.union(drain,current);
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(row < 0 || row > height-1 || col <0 || col> width-1){
            throw new IndexOutOfBoundsException("row and column should be in range 0,"+(width-1));
        }
        return grid[row][col] == true;
    }



    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(row < 0 || row > height-1 || col <0 || col> width-1){
            throw new IndexOutOfBoundsException("row and column should be in range 0,"+(width-1));
        }
        int current = xyTo1D(row,col);
        int source = width*height;
        return isFullWeightedQuickUnionUF.connected(source,current);
    }

    // number of open sites
    public int numberOfOpenSites(){
        return numOfOpenSite;
    }

    // does the system percolate?
    public boolean percolates(){
        return weightedQuickUnionUF.connected(width*height,width*height+1);
    }
}
