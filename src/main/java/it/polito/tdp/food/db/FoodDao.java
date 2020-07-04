package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Collegamento;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public ArrayList<Food> getFoodByPortion(int n){

		String sql = "SELECT f.food_code AS codice, f.display_name AS nome, COUNT(f.food_code) AS conto \n " + 
				"FROM food f, portion p \n " + 
				"WHERE f.food_code=p.food_code \n " + 
				"GROUP BY f.food_code \n " + 
				"HAVING conto<=? " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, n);
			
			ArrayList<Food> list = new ArrayList<Food>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("codice"), res.getString("nome")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	
	}

	public ArrayList<Collegamento> getCollegamento() {

		String sql = "SELECT f.food_code AS cod1, f.display_name AS nome1, f2.display_name AS nome2 , f2.food_code AS cod2,(c.condiment_calories) AS calorie\n" + 
				"FROM food f, food_condiment fc, food f2, food_condiment fc2, condiment c\n" + 
				"WHERE f.food_code=fc.food_code AND f2.food_code=fc2.food_code \n" + 
				"AND f.food_code<f2.food_code AND fc.condiment_code=fc2.condiment_code\n" + 
				"AND c.condiment_code=fc.condiment_code\n" + 
				"GROUP BY f.food_code, f2.food_code " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			ArrayList<Collegamento> li= new ArrayList<Collegamento>();
			while(res.next()) {
				try {
					li.add(new Collegamento(new Food(res.getInt("cod1"),res.getString("nome1")),new Food(res.getInt("cod2"),res.getString("nome2")),res.getDouble("calorie")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return li ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	
	}
}
