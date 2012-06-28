package serviceEnseignant.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import dao.*;
import beans.*;

public class IndisponibiliteDAO extends DAO<Indisponibilite>{
	
	public static String TABLE = "INDISPONIBILITE";

	public Indisponibilite find(Date date, int refEnseig) {
		Indisponibilite obj = null;
		
		try {
			
			GregorianCalendar calendar = new java.util.GregorianCalendar(); 
			calendar.setTime(date);
			
			java.sql.Date sqlDate = new java.sql.Date(calendar.getTimeInMillis());
			
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM " + IndisponibiliteDAO.TABLE + " WHERE NO_ENSEIGNANT = " + refEnseig + " AND DATE_INDISPO = " + sqlDate);

			if(result.first()){
				JoursDAO jdao = new JoursDAO();
				Jours j = jdao.find(calendar);
				EnseignantDAO edao = new EnseignantDAO();
				Enseignant e = edao.find(refEnseig);
				obj = new Indisponibilite(e,j,result.getInt("DEMI_JOURNEE"),result.getInt("POIDS_INDISPO"));
			}
			request.close(); 
			} catch (SQLException e) {
	            e.printStackTrace();
	    }
	    return obj;
	}

	
	@Override
	public Indisponibilite create(Indisponibilite obj)  {
		
		try {
			this.connect.setAutoCommit(false);
			Statement request = this.connect.createStatement();
			GregorianCalendar calendar = obj.getDateIndisponibilite().getDateDuJour();	
			System.out.println(obj.getDateIndisponibilite().getDateDuJour());
			//java.sql.Date sqldate = new java.sql.Date(calendar.getTimeInMillis());
			System.out.println(calendar.get(Calendar.YEAR));
			
			request.executeUpdate("INSERT INTO " + TABLE  +" VALUES (" + DAO.dateFromJavaToOracle(calendar) + ", " 
						+ obj.getMonEnseignant().getNumeroEnseignant() + ", " 
					+ obj.getPoids() + ", " + obj.getDemiJournee() + ")");
			
			this.connect.commit();
			request.close();
			this.connect.close();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}

	/**
	 * NON UTILISE (pas de modif des indispo, que des suppressions)
	 */
	@Override
	public Indisponibilite update(Indisponibilite obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Indisponibilite obj) {
		try {
			this.connect.setAutoCommit(false);
			Statement request = this.connect.createStatement();
			
			GregorianCalendar calendar = obj.getDateIndisponibilite().getDateDuJour();
			java.sql.Date sqldate = new java.sql.Date(calendar.getTimeInMillis());
			
			request.executeUpdate("DELETE FROM " + IndisponibiliteDAO.TABLE 
					+ " WHERE NO_ENSEIGNANT = " + obj.getMonEnseignant().getNumeroEnseignant() 
					+ " AND DATE_INDISPO = " + sqldate);
		
		this.connect.commit();
		request.close();
		this.connect.close();
		
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Indisponibilite find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void loadMesIndisponibilites(Enseignant obj) {
		
		try {
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM Indisponibilite WHERE NO_ENSEIGNANT ="
					+ obj.getNumeroEnseignant() + " ORDER BY DATE_INDISPO");

			while(result.next()){
				GregorianCalendar calendar = new java.util.GregorianCalendar(); 
				calendar.setTime(result.getDate(1));

				Indisponibilite i = new Indisponibilite();
				JoursDAO jdao = new JoursDAO();
				Jours j = jdao.find(calendar);
				
				i.setDemiJournee(result.getInt(4));
				i.setPoids(result.getInt(3));
				
				i.setDateIndisponibilite(j);
				obj.getMesIndisponibilites().add(i);
				
			}
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}


	@Override
	public List<Indisponibilite> findAll() {
		List<Indisponibilite> listIndispo = new ArrayList<Indisponibilite>();
		
		try {
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM " + IndisponibiliteDAO.TABLE);

			while(result.next()){
				listIndispo.add(find(result.getDate("DATE_INDISPO"), result.getInt("NO_ENSEIGNANT")));
			}
			request.close(); 
			} catch (SQLException e) {
	            e.printStackTrace();
	    }
	    return listIndispo;
	}
}
