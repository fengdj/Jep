/* @author rich
 * Created on 09-Mar-2004
 *
 * This code is covered by a Creative Commons
 * Attribution, Non Commercial, Share Alike license
 * <a href="http://creativecommons.org/licenses/by-nc-sa/1.0">License</a>
 */
package org.lsmp.djep.groupJep.groups;
import org.lsmp.djep.groupJep.interfaces.*;
import org.lsmp.djep.groupJep.values.*;
import org.nfunk.jep.*;
import org.nfunk.jep.type.*;
import java.util.*;
/**
 * A free group generated by a symbol t.
 * 
 * @author Rich Morris
 * Created on 09-Mar-2004
 */
public class FreeGroup extends Group implements RingI {

	protected RingI baseRing;
	protected FreeGroupElement zeroPoly;
	protected FreeGroupElement unitPoly;
	protected FreeGroupElement tPoly;    // t
	protected String symbol;
	protected Complex rootVal=new Complex(Double.NaN);

	/**
	 * Create the ring K(t) where t is a solution of the monic polynomial p.
	 * 
	 * @param baseRing the Ring this is an extension of.
	 * @param poly A monic polynomial whose solution gives an algebraic number which is used to generate this group.
	 * @throws IllegalArgumentException if the base ring of the poly is not the same.
	 * @throws IllegalArgumentException if the polynomial is not monic.
	 */
	public FreeGroup(RingI K,String symbol) {
		super();
		this.symbol = symbol;
		this.baseRing=K;
		
		// construct the zero poly
		zeroPoly = new FreeGroupElement(this,new Number[]{
				baseRing.getZERO()});
		// construct the unit poly
		unitPoly = new FreeGroupElement(this,new Number[]{
					baseRing.getONE()});
		// construct the polynomial t
		tPoly = new FreeGroupElement(this,new Number[]{
				baseRing.getZERO(),
				baseRing.getONE()});
	}
	
	public Number add(Number a,Number b)
	{
		return ((FreeGroupElement) a).add((FreeGroupElement) b);
	}
	public Number sub(Number a,Number b)
	{
		return ((FreeGroupElement) a).sub((FreeGroupElement) b);
	}
	public Number mul(Number a,Number b)
	{
		return ((FreeGroupElement) a).mul((FreeGroupElement) b);
	}
	public boolean equals(Number a,Number b)
	{
		return ((FreeGroupElement) a).equals((FreeGroupElement) b);
	}

	public Number valueOf(String s)	{
		Number coeffs[] = new Number[]{baseRing.valueOf(s)};
		return valueOf(coeffs);
	}

	public Number valueOf(Number coeffs[])	{
		return new FreeGroupElement(this, coeffs);
	}

	public Number getZERO() { return zeroPoly; }
	public Number getONE() { return unitPoly; }
	public Number getInverse(Number a)	{
		return sub(zeroPoly,(FreeGroupElement) a);
	}

	public void addStandardConstants(JEP j)
	{
		baseRing.addStandardConstants(j);
		SymbolTable st = j.getSymbolTable();
		for(Enumeration enum1=st.elements();enum1.hasMoreElements();)
		{
			Variable val = (Variable) enum1.nextElement();
			st.remove(val.getName());
			Number num = (Number) val.getValue();
			Number p = this.valueOf(new Number[]{
					num});
			j.addConstant(val.getName(),p);
		}
		j.addConstant(symbol,tPoly);
	}
	
	public String toString()
	{
		return baseRing.toString() +"["+symbol+"]";
	}
	
	/** Returns the base ring of this extension. */
	public RingI getBaseRing() {
		return baseRing;
	}


	/** Sets the value used to aproximate the root as a complex number. */
	public void setRootVal(Complex complex) {
		rootVal = complex;
	}

	/** Returns an aproximation to the value of the root as a complex number. */
	public Complex getRootVal() {
		return rootVal;
	}

	/** Returns the symbol used to denote the generator. */
	public String getSymbol() {
		return symbol;
	}

	

}
