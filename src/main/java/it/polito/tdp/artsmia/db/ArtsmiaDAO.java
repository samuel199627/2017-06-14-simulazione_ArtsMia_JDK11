package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObject() {
		
		String sql = "SELECT * from objects";

		List<ArtObject> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 select distinct begin
from exhibitions
order by begin
	 */
public List<Integer> anni() {
		
		String sql = "select distinct begin " + 
				"from exhibitions " + 
				"order by begin ";

		List<Integer> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(res.getInt("begin"));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

/*
 	select exhibition_id, exhibition_department, exhibition_title, begin, end
	from exhibitions
	where begin>2016
 */
	public Map<Integer,Exhibition> listExhibitions(Integer anno) {
		
		String sql = "select exhibition_id, exhibition_department, exhibition_title, begin, end " + 
				"	from exhibitions " + 
				"	where begin>=? ";
	
		Map<Integer,Exhibition> result = new HashMap<>();
	
		Connection conn = DBConnect.getConnection();
	
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
	
			while (res.next()) {
				
				result.put(res.getInt("exhibition_id"),new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end")));
			}
	
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 	select exhibition_id,count(*)
		from exhibition_objects
		group by exhibition_id
	 */
	public void opereExhibitions(Map<Integer,Exhibition> mostre) {
			
			String sql = "select exhibition_id, object_id " + 
					"		from exhibition_objects ";
		
			Map<Integer,Exhibition> result = new HashMap<>();
		
			Connection conn = DBConnect.getConnection();
		
			try {
				PreparedStatement st = conn.prepareStatement(sql);
				
				ResultSet res = st.executeQuery();
		
				while (res.next()) {
					int id=res.getInt("exhibition_id");
					if(mostre.containsKey(id)) {
						mostre.get(id).addOpera(res.getInt("object_id"));
					}
					
				}
		
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
	
	
}

