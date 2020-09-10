import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
	private final int trials;
	private double meanVal;
	private double stdVal;
	private final double[] trialResults;
	private static final double COEFFICIENT=1.96;
	public PercolationStats(int n, int trials)
	{
		if(n <= 0 || trials <= 0)
			throw new IllegalArgumentException("Invalid arguments");
		this.trials = trials;
		this.meanVal = -1;
		this.stdVal = -1;
		this.trialResults = new double[this.trials];
		for(int i=0;i<this.trials;i++)
		{
			Percolation perc = new Percolation(n);
			while(!perc.percolates()) {
			int row = StdRandom.uniform(n)+1;
			int col = StdRandom.uniform(n)+1;
			if(!perc.isOpen(row, col))
			perc.open(row, col);
			}
			double estimate = (double)perc.numberOfOpenSites()/(n*n);
			this.trialResults[i] = estimate;
		}
	}
    // sample mean of percolation threshold
    public double mean()
    {
    	this.meanVal = StdStats.mean(trialResults);
    	return this.meanVal;
    }
    // sample standard deviation of percolation threshold
    public double stddev()
    {
    	this.stdVal = StdStats.stddev(trialResults);
    	return this.stdVal;
    }
    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
    	if(meanVal == -1)
    		mean();
    	if(stdVal == -1)
    		stddev();
    	return this.meanVal-(COEFFICIENT*this.stdVal/Math.sqrt(this.trials));
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
    	if(meanVal == -1)
    		mean();
    	if(stdVal == -1)
    		stddev();
    	return this.meanVal + (COEFFICIENT * this.stdVal / Math.sqrt(this.trials));
    }
	public static void main(String[] args) {
		int n      = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
	}

}
