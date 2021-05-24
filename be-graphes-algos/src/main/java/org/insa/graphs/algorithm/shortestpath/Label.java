package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;
import java.lang.Math;

public class Label implements Comparable<Label>
{
	private Node currentvertex;
	protected boolean marque = false;
	protected double cout;
	private Node pere;
	
	@Override
	public int compareTo(Label other)
	{
		return Double.compare(this.get_tot_cost(), other.get_tot_cost());	
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
    
    public Node getpere()
    {
    	return this.pere;
    }
    
    public boolean getmarque()
    {
    	return this.marque;
    }
    
    public double get_heuristique() {
    	return 0;
    }
    
    public double get_tot_cost() {
    	return this.get_heuristique()+this.getCost();
    }
}