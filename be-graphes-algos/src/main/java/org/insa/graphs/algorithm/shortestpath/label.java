package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;
import java.lang.Math;

public class label extends Comparable<E> 
{
	public Node currentvertex;
	public boolean marque = false;
	public double cout;
	public Node pere;
	
	double compareTo(double value)
	{
		return this.cout-value;
	}
	
	public double getCost()
	{
		return this.cout;
	}
	
	public label (Node currentvertex, Node pere)
	{
		this.currentvertex = currentvertex;
		this.cout = Math.pow(2, 63)-1;
		this.marque = false;
		this.pere = pere;
	}
	
	public void setCost(double value)
	{
		this.cout = value;
	}
}