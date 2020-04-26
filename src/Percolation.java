import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int n;
    private int[] sites;
    private WeightedQuickUnionUF wqUnion;
    private int virtualBottom;
    private int virtualTop;

    public Percolation(int n) {
        if(n <= 0){
            try {
                throw new IllegalAccessException("n <= 0");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.n = n;
        wqUnion = new WeightedQuickUnionUF(n * n + 2); // Grid size + virtual top + virtual bottom
        sites = new int[n * n];
        virtualTop = n * n;
        virtualBottom = n * n + 1;
        for(int i = 0; i < n * n; i++){
            sites[i] = 0;
        }
    }

    private void validateRanges(int row, int col){
        if(row <= 0 || row > n || col <= 0 || col > n){
            throw new IllegalArgumentException("Outside of range");
        }
    }

    private int getIndex(int row, int col){
        return n * (row - 1) + col - 1;
    }

    public void open(int row, int col){
        this.validateRanges(row, col);
        int index = this.getIndex(row, col);
        int[] neighbors;
        if(sites[index] == 1){
            return;
        }
        if(index < n){
            wqUnion.union(index, virtualTop);
        }
        if(index > n * n - n){
            wqUnion.union(index, virtualBottom);
        }
        int neighbor;
        neighbors = this.getNeighbors(index);
        sites[index] = 1;
        for(int i = 0; i < neighbors.length; i++){
            neighbor = neighbors[i];
            if(neighbor != -1){
                if(sites[neighbor] == 1){
                    wqUnion.union(index, neighbor);
                }
            }
        }
    }

    private int[] getNeighbors(int index){
        int[] neighbors = new int[]{-1, -1, -1, -1};
        int indexAux = index + 1;
        if(indexAux > n){
            neighbors[0] = index - n;
        }
        if( indexAux <= n * n - n){
            neighbors[1] = index + n;
        }
        if(indexAux%n != 0){
            neighbors[2] = index + 1;
        }
        if(indexAux > 1 && (indexAux -1)%n != 0){
            neighbors[3] = index - 1;
        }
        return neighbors;
    }

    public boolean isOpen(int row, int col){
        this.validateRanges(row, col);
        int index = this.getIndex(row, col);
        return sites[index] == 1;
    }

    public boolean isFull(int row, int col){
        this.validateRanges(row, col);
        int index = this.getIndex(row, col);
        if(isOpen(row, col) == false){
            return false;
        }
        if(index < n) return true;
        for(int i = 0; i < n; i++){
            if(sites[i] == 1){
                if(wqUnion.find(i) == wqUnion.find(index)){
                    return true;
                }
            }
        }
        return false;
    }

    public int numberOfOpenSites(){
        int openSites = 0;
        for(int i = 0; i < sites.length; i++) {
            if(sites[i] == 1){
                openSites++;
            }
        }
        return openSites;
    }

    private void print(){
        for(int i = 0; i < sites.length; i++){
            System.out.print(sites[i]);
            if((i + 1)%5 == 0){
                System.out.println('-');
            }
        }
    }

    public boolean percolates(){
        return wqUnion.find(virtualTop) == wqUnion.find(virtualBottom);
    }

    public static void main(String[] args){
        Percolation p = null;
        p = new Percolation(5);
    }
}
