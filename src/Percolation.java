import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Percolation {

    private int n;
    private int[] sites;
    private WeightedQuickUnionUF wqUnion;

    public Percolation(int n){
        this.n = n;
        wqUnion = new WeightedQuickUnionUF(n);
        sites = new int[n * n];
        for(int i = 0; i < n * n; i++){
            sites[i] = 0;
        }
    }

    public int getIndex(int row, int col){
        return n * (row - 1) + col - 1;
    }

    public void open(int row, int col){
        int index = this.getIndex(row, col);
        List<Integer> neighbors;
        if(sites[index] == 1){
            return;
        }
        neighbors = this.getNeighbors(index);
        sites[index] = 1;

        Consumer<Integer> fillNeighbors = neighbor -> {
            if(sites[neighbor] == 1){
                wqUnion.union(index, neighbor);
            }
        };
        neighbors.forEach(fillNeighbors);
    }

    public List<Integer> getNeighbors(int index){
        List<Integer> neighbors = new ArrayList<Integer>();
        int indexAux = index + 1;
        if(indexAux > n){
            neighbors.add(index - n);
        }
        if( indexAux <= n * n - n){
            neighbors.add(index + n);
        }
        if(indexAux%n != 0){
            neighbors.add(index + 1);
        }
        if(indexAux > 1 && (indexAux -1)%n != 0){
            neighbors.add(index - 1);
        }
        return neighbors;
    }

    public boolean isOpen(int row, int col){
        int index = this.getIndex(row, col);
        return sites[index] == 1;
    }

    public boolean isFull(int row, int col){
        return true;
    }

    public int numberOfOpenSites(){
        return 0;
    }

    public boolean percolates(){
        return false;
    }

    public static void main(String[] args){
        Percolation p = new Percolation(5);
        int index = p.getIndex(5,5);
        p.open(5,5);

    }
}
