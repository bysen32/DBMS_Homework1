package simpledb;
import java.util.*;

/**
 * SeqScan is an implementation of a sequential scan access method that reads
 * each tuple of a table in no particular order (e.g., as they are laid out on
 * disk).
 */
public class SeqScan implements DbIterator {
	private TransactionId trsid;
	private DbFile dbfile;
	private int tableid;
	private String tableAlias;
	private DbFileIterator FileIt;
    /**
     * Creates a sequential scan over the specified table as a part of the
     * specified transaction.
     *
     * @param tid The transaction this scan is running as a part of.
     * @param tableid the table to scan.
     * @param tableAlias the alias of this table (needed by the parser);
     *         the returned tupleDesc should have fields with name tableAlias.fieldName
     *         (note: this class is not responsible for handling a case where tableAlias
     *         or fieldName are null.  It shouldn't crash if they are, but the resulting
     *         name can be null.fieldName, tableAlias.null, or null.null).
     */
    public SeqScan(TransactionId tid, int tableid, String tableAlias) {
        // some code goes here
    	trsid = tid;
    	this.tableid = tableid;
    	this.tableAlias = tableAlias;
    	dbfile = Database.getCatalog().getDbFile(tableid);
    	FileIt = dbfile.iterator(trsid);
    	
    }

    public void open()
        throws DbException, TransactionAbortedException {
        // some code goes here
    	FileIt.open();
    	
    }

    /**
     * Returns the TupleDesc with field names from the underlying HeapFile,
     * prefixed with the tableAlias string from the constructor.
     * @return the TupleDesc with field names from the underlying HeapFile,
     * prefixed with the tableAlias string from the constructor.
     */
    public TupleDesc getTupleDesc() {
        // some code goes here
    	TupleDesc td = Database.getCatalog().getTupleDesc(dbfile.getId());
    	Type[] types = new Type[td.numFields()];
    	String[] names = new String[td.numFields()];
    	for(int i = 0; i < td.numFields(); i++) {
    		types[i] = td.getType(i);
    		names[i] = tableAlias + '.' + td.getFieldName(i);
    	}
        return new TupleDesc(types,names);
    }

    public boolean hasNext() throws TransactionAbortedException, DbException {
        // some code goes here
        return FileIt.hasNext();
    }

    public Tuple next()
        throws NoSuchElementException, TransactionAbortedException, DbException {
        // some code goes here
        return FileIt.next();
    }

    public void close() {
        // some code goes here
    	FileIt.close();
    }

    public void rewind()
        throws DbException, NoSuchElementException, TransactionAbortedException {
        // some code goes here
    	FileIt.rewind();
    }
}
