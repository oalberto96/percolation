import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;

public class PercolationStats {

    private double[] results;

    public PercolationStats(int n, int trials) {
        if(n <= 0 || trials <= 0){
            throw new IllegalArgumentException();
        }
        results = new double[trials];
        for(int i = 0; i < trials; i++){
            results[i] = runSample(n);
        }
    }

    /*
    It returns the percolation threshold
     */
    private double runSample(int n) {
        int randomCol, randomRow;
        Percolation percolation = new Percolation(n);
        while(percolation.percolates() != true){
            randomCol = StdRandom.uniform(n) + 1;
            randomRow = StdRandom.uniform(n) + 1;
            percolation.open(randomRow, randomCol);
        }
        double percolationThreshold = percolation.numberOfOpenSites()/(double)(n*n);
        return percolationThreshold;
    }

    public double mean(){
        return StdStats.mean(results);
    }

    public double stddev(){
        return StdStats.stddev(results);
    }

    public double confidenceLo(){
        return mean() - (1.96 * stddev())/ Math.sqrt(results.length);
    }

    public double confidenceHi(){
        return mean() + (1.96 * stddev())/ Math.sqrt(results.length);
    }

    public static void main(String[] args){
        int n, t;
        n = Integer.parseInt(args[0]);
        t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);
        System.out.println("mean \t\t\t\t\t= " + percolationStats.mean());
        System.out.println("stddev \t\t\t\t\t= " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]" );
    }
}
