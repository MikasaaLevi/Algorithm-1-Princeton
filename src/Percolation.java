import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	    // creates n-by-n grid, with all sites initially blocked
		private boolean[] grid;
		private final WeightedQuickUnionUF uf;
		private int numOpenSites;
		private final int n;
	    public Percolation(int n) {
	    	if (n <= 0) {
	            throw new IllegalArgumentException();
	        }
	    	this.n = n;
	    	this.numOpenSites = 0;
	    	this.grid= new boolean[n*n+2];
	    	this.uf = new WeightedQuickUnionUF(n*n+2);
	    	for(int i = 0;i < grid.length; i++)
	    		grid[i]=false;
			/*
			 * for(int i = 1 ; i <= n ; i++) { this.uf.union(vTop,i);
			 * this.uf.union(vBot,vBot-i);
			 */
			 
	    }

	    // opens the site (row, col) if it is not open already
	    public void open(int row, int col) {
	    	if(!valid(row,col))
	    		throw new IllegalArgumentException("Invalid Arguments: indexes out of bounds");
	    	else {
	    		int index = indexFromRowCol(row, col);
	    		if(!isOpen(row, col))
	    		{
	    			this.numOpenSites++;
	    			this.grid[index] = true;
					
					  if (row == 1) { uf.union(index, 0); }
					  
					  if (row == this.n) uf.union(index, this.n * this.n + 1);
					 
	    			if (row > 1 && isOpen(row-1, col)) {
	                    uf.union(index,indexFromRowCol(row-1, col));
	                }
	                
	                // bottom site
	                if (row < n && isOpen(row+1, col)) {
	                    uf.union(index, indexFromRowCol(row+1, col));
	                }
	                
	                // left site
	                if (col > 1 && isOpen(row, col-1)) {
	                    uf.union(index, indexFromRowCol(row, col-1));
	                }
	                
	                // right site
	                if (col < n && isOpen(row, col+1)) {
	                    uf.union(index, indexFromRowCol(row, col+1));
	                }
	    		}
	    	}
	    }

	    // is the site (row, col) open?
	    public boolean isOpen(int row, int col) {
	    	if(!valid(row,col))
	    		throw new IllegalArgumentException("Invalid Arguments: indexes out of bounds");
	    	int index = indexFromRowCol(row, col);
	    	return this.grid[index];
	    }

	    // is the site (row, col) full?
	    @SuppressWarnings("deprecation")
		public boolean isFull(int row, int col) {
	    	if(!valid(row,col))
	    		throw new IllegalArgumentException("Invalid Arguments: indexes out of bounds");
	    	int index = indexFromRowCol(row, col);
	    	return isOpen(row,col) && uf.connected(0, index);
	    }

	    // returns the number of open sites
	    public int numberOfOpenSites() {
	    	return this.numOpenSites;
	    }

	    // does the system percolate?
		@SuppressWarnings("deprecation")
		public boolean percolates() {
	    	return this.uf.connected(0, this.n * this.n + 1);
	    }
	    
	    private int indexFromRowCol(int row,int col) {
	    	return (row-1)*this.n + col;
	    }
	    
	    private boolean valid(int row,int col)
	    {
	    	if(row <= 0 || row > this.n || col <= 0 || col > this.n)
	    		return false;
	    	return true;
	    }
	    // test client (optional)
		/*
		 * public static void main(String[] args) {
		 * 
		 * }
		 */
}
