package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;
import java.lang.Math;

public class label implements Comparable<label>
{
	public Node currentvertex;
	public boolean marque = false;
	public double cout;
	public Node pere;
	public int num_node;
	
	@Override
	public int compareTo(label other)
	{
		return (int)(this.cout-other.cout);	
	}
	
	public label (Node currentvertex, Node pere, int num_node)
	{
		this.currentvertex = currentvertex;
		this.cout = Math.pow(2, 63)-1;
		this.marque = false;
		this.pere = pere;
		this.num_node = num_node; //permet de placer la node dans le tableau d'association node-label 
								  //en récupérant l'index de la node dans la liste des nodes d'un graphe.
	}
	
	public double getCost()
	{
		return this.cout;
	}
	
	public void setCost(double value)
	{
		this.cout = value;
	}
}