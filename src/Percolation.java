import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation extends WeightedQuickUnionUF{
	private static int grid_size;       //grid size
	private int[] pl;         //array data structure for percolation
	private int n;        //number of open sites.
	
	/** create N-by-N grid, with all sites initially blocked.
	 * Each site has three states: blocked, open, and full.
	 * Each state is indicated by an integer. 
	 * Blocked (0), Open (1), and Full (2).*/
	public Percolation(int N) {
		super((N * N));
		for (int i = 0; i < N; i++) {
			union (pl[i], (N + 1));
		}
		for (int j = (N * N - 1); j > (N * N - N); j--) {
			union (pl[j], (N + 2));
		}
		if (N <= 0)
			throw new IllegalArgumentException("N should be larger than 0.");
		else
			this.grid_size = N;
			int array_size = N * N;			
		    int[] pl = new int[array_size];
		    for (int i = 0; i < N; i++) {
			    pl[i] = 0;
			this.pl = pl;
			this.n = 0;
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
		//if there's a open neighbor, union this cell with the open neighbor
		if (has_open_neighbor(row, col)) {
			union(index_to_open, open_neighbor_index(row, col));
		}
		//if there's a full neighbor, full the cell
		if (has_full_neighbor(row, col)) {
			full(row, col);
		}
	}
	
	//the action of "full" a cell
	private void full(int row, int col) {
			int index_to_full = index(row, col);
			pl[index_to_full] = 2;		
	}
	
	//check if there's any open cell in the neighborhood
		private boolean has_open_neighbor(int row, int col) {
			int cell_index = index(row, col);
			int up_index = index((row - 1), col);
			int down_index = index((row + 1), col);
			int left_index = index(row, (col - 1));
			int right_index = index(row, (col + 1));
			if (row != 0 && pl[up_index] == 1)
				return true;
			if (row != (this.grid_size - 1) && pl[down_index] == 1)
				return true;
			if (col != 0 && pl[left_index] == 1)
				return true;
			if (col != (this.grid_size - 1) && pl[right_index] == 1)
				return true;
			else
				return false;
		}
	
	//return array index of the open neighbor
		private int open_neighbor_index(int row, int col) {
			int cell_index = index(row, col);
			int up_index = index((row - 1), col);
			int down_index = index((row + 1), col);
			int left_index = index(row, (col - 1));
			int right_index = index(row, (col + 1));
			if (row != 0 && pl[up_index] == 1)
				return up_index;
			if (row != (this.grid_size - 1) && pl[down_index] == 1)
				return down_index;
			if (col != 0 && pl[left_index] == 1)
				return left_index;
			if (col != (this.grid_size - 1) && pl[right_index] == 1)
				return right_index;
			else
				return cell_index;
		}
		
	//check if there's any full cell in the neighborhood
	private boolean has_full_neighbor(int row, int col) {
		int cell_index = index(row, col);
		int up_index = index((row - 1), col);
		int down_index = index((row + 1), col);
		int left_index = index(row, (col - 1));
		int right_index = index(row, (col + 1));
		if (row == 0 || pl[up_index] == 2)
			return true;
		if (row != (this.grid_size - 1) && pl[down_index] == 2)
			return true;
		if (col != 0 && pl[left_index] == 2)
			return true;
		if (col != (this.grid_size - 1) && pl[right_index] == 2)
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
		return true;
	}           
	  // public static void main(String[] args)   // unit testing (not required)
}
