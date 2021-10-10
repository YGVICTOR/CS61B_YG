package hw2;
import edu.princeton.cs.algs4.StdRandom;
import  edu.princeton.cs.introcs.StdRandom.*;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.StdStats.*;

import static edu.princeton.cs.algs4.StdRandom.uniform;

public class PercolationStats {
    private double [] thresholds;
    private int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf){
        if(N<=0 || T<=0){
            throw new IllegalArgumentException("N and T should greater than 0");
        }
        Percolation percolation = pf.make(N);
        thresholds = new double[T];
        this.T = T;
        for(int i=0;i<T;i++){
            while(!percolation.percolates()){
                int randomRow =  edu.princeton.cs.introcs.StdRandom.uniform(N);
                int randomCol =   edu.princeton.cs.introcs.StdRandom.uniform(N);
                percolation.open(randomRow,randomCol);
            }
            double threshold = (percolation.numberOfOpenSites()*1.0)/(N*N);
            thresholds[i] = threshold;
        }

    }

    // sample mean of percolation threshold
    public double mean(){
        return edu.princeton.cs.introcs.StdStats.mean(thresholds);

    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return edu.princeton.cs.introcs.StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow(){
        return (this.mean() - 1.96*this.stddev()/Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return (this.mean() + 1.96*this.stddev()/Math.sqrt(T));
    }


    public static void main(String[] args) {
        PercolationStats p =new PercolationStats(20,20,new PercolationFactory());
        System.out.println(p.mean());
        //System.out.println(p.stddev());
        System.out.println(p.confidenceLow());
        //System.out.println(Math.sqrt(p.T));
        System.out.println(p.confidenceHigh());
    }




}
