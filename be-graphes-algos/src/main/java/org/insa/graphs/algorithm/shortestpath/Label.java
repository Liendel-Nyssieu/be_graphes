package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;
import java.lang.Math;

public class Label implements Comparable<Label>
{
	public Node currentvertex;
	public boolean marque = false;
	public double cout;
	public Node pere;
	public int num_arc;
	
	@Override
	public int compareTo(Label other)
	{
		return (int)(this.cout-other.cout);	
	}
	
	public Label (Node currentvertex, Node pere, int num_arc)
	{
		this.currentvertex = currentvertex;
		this.cout = Math.pow(2, 63)-1;
		this.marque = false;
		this.pere = pere;
		this.num_arc = num_arc;
	}
	
	public double getCost()
	{
		return this.cout;
	}
	
	public void setCost(double value)
	{
		this.cout = value;
	}
	
	public void passage()
	{
		this.marque = true;
	}
	
	public int getnum_arc()
	{
		return this.num_arc;
	}
	
    public Node getdest()
    {
    	return this.currentvertex;
    }
}