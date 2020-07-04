package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeMap;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	SimpleWeightedGraph<Food, DefaultWeightedEdge> grafo;
	ArrayList<Food> cibi;
	
	public void creaGrafo(int i) {
		FoodDao dao=new FoodDao();
		cibi=dao.getFoodByPortion(i);
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, cibi);
		int dim=cibi.size();
		for(Collegamento c:dao.getCollegamento()) {
			if(grafo.containsVertex(c.getF1())&& grafo.containsVertex(c.getF2()))
				Graphs.addEdge(grafo, c.getF1(), c.getF2(), c.getPeso());
				}
	}

	public ArrayList<Food> getCibi() {
		return cibi;
	}

	public SimpleWeightedGraph<Food, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public ArrayList<Collegamento> getMax(Food value) {
		TreeMap<Double,DefaultWeightedEdge> mappa=new TreeMap<Double,DefaultWeightedEdge>();
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			if(grafo.getEdgeSource(e).equals(value) || grafo.getEdgeTarget(e).equals(value)) {
				mappa.put(grafo.getEdgeWeight(e),e);
			}
		}
		ArrayList<Collegamento> max= new ArrayList<Collegamento>();
		ArrayList<DefaultWeightedEdge> temp= new ArrayList<DefaultWeightedEdge>(mappa.values());
		for(int i=temp.size()-1;i>temp.size()-6&&i>0;i--) {
			double p=grafo.getEdgeWeight(temp.get(i));
			if(!grafo.getEdgeSource(temp.get(i)).equals(value)) {
				max.add(new Collegamento(grafo.getEdgeSource(temp.get(i)),null,p));
			} else {
				max.add(new Collegamento(grafo.getEdgeTarget(temp.get(i)),null,p));
			}
		}
		return max;
	}
	
	

}
