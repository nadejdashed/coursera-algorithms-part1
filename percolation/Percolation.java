/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final byte OPEN = 0b001;
    private static final byte CONNECT_FROM_TOP = 0b010;
    private static final byte CONNECT_FROM_BOTTOM = 0b100;
    private static final byte CONNECT_BOTH = 0b111;
    private final byte[] connectionGrid;

    private final WeightedQuickUnionUF wqu;
    private final int lengthGrid;
    private final int topPoint;
    private int openSides = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        lengthGrid = n;

        wqu = new WeightedQuickUnionUF(n * n + 1);
        topPoint = n * n;

        connectionGrid = new byte[n * n + 1];
        for (int i = 0; i < connectionGrid.length; i++) {
            connectionGrid[i] = 0;
        }
        connectionGrid[topPoint] = Percolation.CONNECT_FROM_TOP;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkArguments(row, col);
        if (isOpen(row, col)) return;

        int ind = d2ToD1(row, col);
        byte connection = Percolation.OPEN;
        openSides++;

        // Connect top ceil
        if (row == 1) {
            connection = checkConnection(connection, ind, topPoint);
            makeUnion(ind, topPoint);
        }
        else if (isOpen(row - 1, col)) {
            connection = checkConnection(connection, ind, d2ToD1(row - 1, col));
            makeUnion(ind, d2ToD1(row - 1, col));
        }
        // Connect bottom ceil
        if (row == lengthGrid) {
            connection = (byte) (connection | Percolation.CONNECT_FROM_BOTTOM);
        }
        else if (isOpen(row + 1, col)) {
            connection = checkConnection(connection, ind, d2ToD1(row + 1, col));
            makeUnion(ind, d2ToD1(row + 1, col));
        }
        // Connect left ceil
        if (col != 1 && isOpen(row, col - 1)) {
            connection = checkConnection(connection, ind, d2ToD1(row, col - 1));
            makeUnion(ind, d2ToD1(row, col - 1));
        }
        // Connect right ceil
        if (col != lengthGrid && isOpen(row, col + 1)) {
            connection = checkConnection(connection, ind, d2ToD1(row, col + 1));
            makeUnion(ind, d2ToD1(row, col + 1));
        }

        connectionGrid[ind] = connection;
        connectionGrid[wqu.find(ind)] = connection;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkArguments(row, col);
        int ind = d2ToD1(row, col);
        return (connectionGrid[ind] & Percolation.OPEN) != 0;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkArguments(row, col);
        int ind = d2ToD1(row, col);
        int root = wqu.find(ind);
        return (connectionGrid[root] & Percolation.CONNECT_FROM_TOP) != 0;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSides;
    }

    // does the system percolate?
    public boolean percolates() {
        int root = wqu.find(topPoint);
        return connectionGrid[root] == Percolation.CONNECT_BOTH;
    }

    private void checkArguments(int row, int col) {
        if (row < 1 || row > lengthGrid) {
            throw new IllegalArgumentException();
        }
        if (col < 1 || col > lengthGrid) {
            throw new IllegalArgumentException();
        }
    }

    private int d2ToD1(int row, int col) {
        return (row - 1) * lengthGrid + (col - 1);
    }

    private void makeUnion(int ind1, int ind2) {
        wqu.union(ind1, ind2);
    }

    private byte checkConnection(byte currentConnection, int ind1, int ind2) {
        int root1 = wqu.find(ind1);
        int root2 = wqu.find(ind2);
        return (byte) (currentConnection | connectionGrid[root1] | connectionGrid[root2]);
    }
}
