import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Percolation {

    private int n;
    private int[] sites;
    private WeightedQuickUnionUF wqUnion;

    public Percolation(int n) throws IllegalAccessException {
        if(n <= 0){
            throw new IllegalAccessException("n <= 0");
        }
        this.n = n;
        wqUnion = new WeightedQuickUnionUF(n * n);
        sites = new int[n * n];
        for(int i = 0; i < n * n; i++){
            sites[i] = 0;
        }
    }

    private void validateRanges(int row, int col){
        if(row <= 0 || row > n || col <= 0 || col > n){
            throw new IllegalArgumentException("Outside of range");
        }
    }

    public int getIndex(int row, int col){
        return n * (row - 1) + col - 1;
    }

    public void open(int row, int col){
        this.validateRanges(row, col);
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
        this.validateRanges(row, col);
        int index = this.getIndex(row, col);
        return sites[index] == 1;
    }

    public boolean isFull(int row, int col){
        this.validateRanges(row, col);
        int index = this.getIndex(row, col);
        return sites[index] == 1;
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

    public void print(){
        for(int i = 0; i < sites.length; i++){
            System.out.print(sites[i]);
            if((i + 1)%5 == 0){
                System.out.println('-');
            }
        }
    }

    public boolean percolates(){
        for(int i = 0; i < n; i++){
            if(sites[i] == 1){
                for(int j = sites.length - n; j < sites.length; j++){
                    if(wqUnion.find(i) == wqUnion.find(j)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args){
        Percolation p = null;
        try {
            p = new Percolation(5);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
