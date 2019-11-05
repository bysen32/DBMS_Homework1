package simpledb;

import java.io.*;
import java.util.*;

/**
 * HeapFile is an implementation of a DbFile that stores a collection
 * of tuples in no particular order.  Tuples are stored on pages, each of
 * which is a fixed size, and the file is simply a collection of those
 * pages. HeapFile works closely with HeapPage.  The format of HeapPages
 * is described in the HeapPage constructor.
 *
 * @see simpledb.HeapPage#HeapPage
 * @author Sam Madden
 */
public class HeapFile implements DbFile {
	private File file;
	private TupleDesc td;

    /**
     * Constructs a heap file backed by the specified file.
     *
     * @param f the file that stores the on-disk backing store for this heap file.
     */
    public HeapFile(File f, TupleDesc td) {
        // some code goes here
    	this.file = f;
    	this.td = td;
    }

    /**
     * Returns the File backing this HeapFile on disk.
     *
     * @return the File backing this HeapFile on disk.
     */
    public File getFile() {
        // some code goes here
        return this.file;
    }

    /**
    * Returns an ID uniquely identifying this HeapFile. Implementation note:
    * you will need to generate this tableid somewhere ensure that each
    * HeapFile has a "unique id," and that you always return the same value
    * for a particular HeapFile. We suggest hashing the absolute file name of
    * the file underlying the heapfile, i.e. f.getAbsoluteFile().hashCode().
    *
    * @return an ID uniquely identifying this HeapFile.
    */
    public int getId() {
        // some code goes here
    	return this.file.getAbsoluteFile().hashCode();
        //throw new UnsupportedOperationException("implement this");
    }
    
    /**
     * Returns the TupleDesc of the table stored in this DbFile.
     * @return TupleDesc of this DbFile.
     */
    public TupleDesc getTupleDesc() {
    	// some code goes here
    	return this.td;
    	//throw new UnsupportedOperationException("implement this");
    }

    // see DbFile.java for javadocs
    public Page readPage(PageId pid) {
        // some code goes here
    	
    	try
    	{
    		RandomAccessFile f = new RandomAccessFile(this.file,"r");
    		int offset = BufferPool.PAGE_SIZE * pid.pageno();
    		byte[] data = new byte[BufferPool.PAGE_SIZE];
    		if(offset+BufferPool.PAGE_SIZE > f.length()) {
    			System.err.println(" offset too big, error!");
    			System.exit(1);
    		}
    		f.seek(offset);
    		f.readFully(data);
    		f.close();
    		return new HeapPage((HeapPageId)pid,data);
    	}catch(IOException e) {
    		System.err.println("IOException" + e.getMessage());
    		throw new IllegalArgumentException();
    	}
    	
    }

    // see DbFile.java for javadocs
    public void writePage(Page page) throws IOException {
        // some code goes here
        // not necessary for lab1
    }

    /**
     * Returns the number of pages in this HeapFile.
     */
    public int numPages() {
        // some code goes here done
    	
        return (int)Math.ceil(this.file.length()/BufferPool.PAGE_SIZE);
    }

    // see DbFile.java for javadocs
    public ArrayList<Page> addTuple(TransactionId tid, Tuple t)
        throws DbException, IOException, TransactionAbortedException {
        // some code goes here
        return null;
        // not necessary for lab1
    }

    // see DbFile.java for javadocs
    public Page deleteTuple(TransactionId tid, Tuple t)
        throws DbException, TransactionAbortedException {
        // some code goes here
        return null;
        // not necessary for lab1
    }

    // see DbFile.java for javadocs
    public DbFileIterator iterator(final TransactionId tid) {
        // some code goes here
    	return new DbFileIterator() {
    		private final BufferPool bp = Database.getBufferPool();
    		private int tableId = getId();
    		private int pid = -1;
    		private Iterator<Tuple> tuple;
    		
    		
    		public void open() throws DbException,TransactionAbortedException{
    			pid = 0;
    			tuple = null;
    		}
    		
    		public boolean hasNext() throws DbException,TransactionAbortedException{
    		if(tuple != null && tuple.hasNext()) {
    				//System.out.println("1");
    				return true;
    			}
    			else if(pid < 0 || pid >= numPages()) {
    				//System.out.println("2");
    				return false;
    			}
    			else {
    				tuple = ((HeapPage)bp.getPage(tid, new HeapPageId(tableId,pid++),Permissions.READ_ONLY)).iterator();
    				//System.out.println("3");
    				
    				return hasNext();
    				}
    
    			
    		}
    		
    		public Tuple next() throws DbException,TransactionAbortedException,NoSuchElementException{
    			if(hasNext()) {
    				return tuple.next();
    			}
    			else {
    				throw new  NoSuchElementException();
    			}
    		}
    	
    		public void rewind() throws DbException, TransactionAbortedException{
    			pid = 0;
    			tuple = null;
    		}
    		
    		public void close() {
    			pid = -1;
    			tuple =null;
    		}
    		
    	};
    }
    
}

