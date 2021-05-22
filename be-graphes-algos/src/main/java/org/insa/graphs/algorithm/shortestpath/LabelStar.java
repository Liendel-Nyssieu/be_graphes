package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;

public class LabelStar extends Label {
	private double heuristique;
	
	public LabelStar (Node currentvertex, Node pere, Node destination, boolean modelength)
	{
		super(currentvertex, pere);
		this.cout = Math.pow(2, 63)-1;
		this.marque = false;
		
		if (modelength == true) {
			this.heuristique = currentvertex.getPoint().distanceTo(destination.getPoint());
		} else {
			this.heuristique = currentvertex.getPoint().distanceTo(destination.getPoint())/(80/3.6); //on considère une vitesse de 80km/h à vol d'oiseau
		}
	}
	
	@Override
	public double get_heuristique() 
	{
		return this.heuristique;
	}
	
	@Override
	public double get_tot_cost()
	{
		return this.get_heuristique()+this.getCost();
	}
	
}