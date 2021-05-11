package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;
import java.lang.Math;

public class Label implements Comparable<Label>
{
	public Node currentvertex;
	public boolean marque = false;
	public double cout;
	public Node pere;
	
	@Override
	public int compareTo(Label other)
	{
		return (int)(this.cout-other.cout);	
	}
	
	public Label (Node currentvertex, Node pere)
	{
		this.currentvertex = currentvertex;
		this.cout = Math.pow(2, 63)-1;
		this.marque = false;
		this.pere = pere;
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
	
    public Node getdest()
    {
    	return this.currentvertex;
    }
    
    public void setpere(Node neo_pere)
    {
    	this.pere = neo_pere;
    }
}