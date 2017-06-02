package pl;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation extends WeightedQuickUnionUF{
	private static int grid_size;       //grid size
	private static int grid_area;     //grid area
	private int[] pl;         //array data structure for percolation
	private int n;        //number of open sites.
	
	/** create N-by-N grid, with all sites initially blocked.
	 * Each site has three states: blocked, open, and full.
	 * Each state is indicated by an integer. 
	 * Blocked (0), Open (1), and Full (2).*/
	public Percolation(int N) {
		super((N * N + 3));
		
		if (N <= 0)
			throw new IllegalArgumentException("N should be larger than 0.");
		else
			this.grid_size = N;
			this.grid_area = N * N;
		    int[] pl = new int[grid_area];
		    for (int i = 0; i < grid_area; i++) {
			    pl[i] = 0;
		    }
			this.pl = pl;
			this.n = 0;
			//union top cells with a pseudo cell grid_area + 1.
			for (int i = 0; i < N; i++) {
				union (pl[i], (grid_area + 1));
			}
			//union bottom cells with a pseudo cell grid_area + 2.
			for (int j = (N * N - 1); j > (N * N - N); j--) {
				union (pl[j], (grid_area + 2));
			}
	}
	
	public Percolation() {
		this(grid_size);
	}
	//helper method to transform row and col info to array index.
	private int index(int row, int col) {
		int index = row  * this.grid_size + col;
		return index;
	}
	
	// open the site (row, col) if it is not open already   
	public void open(int row, int col) {
		int index_to_open = index(row, col);
		if (pl[index_to_open] == 0) {
			pl[index_to_open] = 1;
			n = n + 1;
		}
		//union left cell
		int left = left_cellindex(row, col);
		if (col != 0 && pl[left] !=0 && !connected(index_to_open, left)) {
			union(index_to_open, left);}
		//union right cell
		int right = right_cellindex(row, col);
		if (col < (grid_size - 1) && pl[right] !=0 && !connected(index_to_open, right)) {
			union(index_to_open, right);}
		//union top cell
		int up = up_cellindex(row, col);
		if (row != 0 && pl[up] !=0 && !connected(index_to_open, up)) {
			union(index_to_open, up);}
		//union bottom cell
		int down = down_cellindex(row, col);
		if (row < (grid_size - 1) && pl[down] != 0 && !connected(index_to_open, down)) {
				union(index_to_open, down);}
		
		
		//if there's a full neighbor, full the cell and its union members
		if (has_full_neighbor(row, col)) {
			full(row, col);
			for (int i = 0; i < grid_area; i++) {
			if (connected(index_to_open, i)) {
				full(i);
			}
			}
		}
		
	}
	
	//the action of "full" a cell given coordinates
	private void full(int row, int col) {
		int index_to_full = index(row, col);
		pl[index_to_full] = 2;		
	}
	
	//the action of "full" a cell given array index
	private void full(int index) {
		pl[index] = 2;
	}
	
	private int left_cellindex(int row, int col) {
		return index(row, (col - 1));
	}
	
	private int right_cellindex(int row, int col) {
		return index(row, (col + 1));
	}
	
	private int up_cellindex(int row, int col) {
		return index((row - 1), col);
	}
	
	private int down_cellindex(int row, int col) {
		return index((row + 1), col);
	}
	
		
	//check if there's any full cell in the neighborhood
	private boolean has_full_neighbor(int row, int col) {
		int up = up_cellindex(row , col);
		int down = down_cellindex(row , col);
		int left = left_cellindex(row, col);
		int right = right_cellindex(row, col);
		if (row == 0 || pl[up] == 2)
			return true;
		if (row != (this.grid_size - 1) && pl[down] == 2)
			return true;
		if (col != 0 && pl[left] == 2)
			return true;
		if (col != (this.grid_size - 1) && pl[right] == 2)
			return true;
		else
			return false;
	}
	
	// is the site (row, col) open?   
	public boolean isOpen(int row, int col) {
		int index_to_check = index(row, col);
		if (pl[index_to_check] != 0)
			return true;
		else return false;		
	} 
	
	// is the site (row, col) full?   
	public boolean isFull(int row, int col) {
		int index_to_check = index(row, col);
		if (pl[index_to_check] == 2)
			return true;
		else return false;
	}
	
	// number of open sites   
	public int numberOfOpenSites() {
		return n;
	}  
	
	
	// does the system percolate?   
	public boolean percolates() {
		return (connected((grid_area + 1), (grid_area + 2)));
	}           
	  // public static void main(String[] args)   // unit testing (not required)
}
