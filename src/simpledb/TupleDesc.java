package simpledb;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc {
	private Type[] typeAr;
	private String[] fieldAr;
    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields
     * fields, with the first td1.numFields coming from td1 and the remaining
     * from td2.
     * @param td1 The TupleDesc with the first fields of the new TupleDesc
     * @param td2 The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc combine(TupleDesc td1, TupleDesc td2) {
        //td1.typeAr is private??
    	Type[] newtype = new Type[td1.numFields()+td2.numFields()];
    	String[] newfield = new String[td1.numFields()+td2.numFields()];
    	System.arraycopy(td1.typeAr, 0, newtype, 0, td1.numFields());
    	System.arraycopy(td2.typeAr, 0, newtype, td1.numFields(), td2.numFields());
    	System.arraycopy(td1.fieldAr, 0, newfield, 0, td1.numFields());
    	System.arraycopy(td2.fieldAr, 0, newfield, td1.numFields(), td2.numFields());
    	TupleDesc newTD = new TupleDesc(newtype,newfield);
        return newTD;
    }

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     *
     * @param typeAr array specifying the number of and types of fields in
     *        this TupleDesc. It must contain at least one entry.
     * @param fieldAr array specifying the names of the fields. Note that names may be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
        //finished
    	if(typeAr.length == 0) {
    		throw new IllegalArgumentException("typeAr contain at least one entry!");
    	}
    	this.typeAr = typeAr;
    	this.fieldAr = fieldAr;
    }

    /**
     * Constructor.
     * Create a new tuple desc with typeAr.length fields with fields of the
     * specified types, with anonymous (unnamed) fields.
     *
     * @param typeAr array specifying the number of and types of fields in
     *        this TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
        // finished
    	if(typeAr.length == 0) {
    		throw new IllegalArgumentException("typeAr contain at least one entry!");
    	}
    	this.typeAr = typeAr;
    	this.fieldAr = new String[typeAr.length];
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        // finished
        return typeAr.length;
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     *
     * @param i index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        //finished
    	if(i >= typeAr.length || i < 0 ) {
    		throw new NoSuchElementException();
    	}
    	return this.fieldAr[i];
    }

    /**
     * Find the index of the field with a given name.
     *
     * @param name name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException if no field with a matching name is found.
     */
    public int nameToId(String name) throws NoSuchElementException {
        //finished
    	if(name == null){
    		throw new NoSuchElementException();
    	}
    	int i;
    	for(i = 0; i < this.numFields(); i++) {
    		//compare
    		if(name.equals(this.fieldAr[i])) {
    			return i;
    		}
    	}
    	throw new NoSuchElementException();
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     *
     * @param i The index of the field to get the type of. It must be a valid index.
     * @return the type of the ith field
     * @throws NoSuchElementException if i is not a valid field reference.
     */
    public Type getType(int i) throws NoSuchElementException {
        //finished
    	if(i >= typeAr.length || i < 0 ) {
    		throw new NoSuchElementException();
    	}
    	return this.typeAr[i];
        
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     * Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        // finished
    	int TotalSize = 0;
    	for(Type typetmp:this.typeAr) {
    		TotalSize += typetmp.getLen();
    	}
        return TotalSize;
    }

    /**
     * Compares the specified object with this TupleDesc for equality.
     * Two TupleDescs are considered equal if they are the same size and if the
     * n-th type in this TupleDesc is equal to the n-th type in td.
     *
     * @param o the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
        //finished
    	if(o == null) {
    		return false;
    	}
    	if(this == o){
    		return true;
    	}
    	if(o instanceof TupleDesc) {
    		TupleDesc oTD = (TupleDesc)o;
    		if(oTD.numFields() == this.numFields()) {
    			for(int i = 0; i < oTD.numFields(); i++) {
    				if((oTD.getType(i) != this.getType(i)) ||(oTD.getFieldName(i) != this.getFieldName(i))) {
    					return false;
    				}
    			}
    			return true;
    		}else return false;
    	}else return false;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * @return String describing this descriptor.
     */
    public String toString() {
        //finished
    	StringBuffer S = new StringBuffer("");
    	for(int i = 0; i < this.numFields(); i++) {
    		S.append(this.getType(i) == Type.INT_TYPE? "int":"string");
    		S.append("(" + this.getFieldName(i)+ ")");
    		if(i < this.numFields()-1) {
    			S.append(',');
    		}
    	}
        return S.toString();
    }
}
