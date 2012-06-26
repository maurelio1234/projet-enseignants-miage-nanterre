package serviceEnseignant.DAO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.*;
import beans.*;

public class NoteDAO extends DAO<Note> {
	public static String TABLE = "PROMOTION";

	@Override
	/**
	 * Utiliser la fonction find(int id, int id)
	 */
	public Note find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Note find(int id_etudiant, int id_examen) {
		Note obj = null;
		try {
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM " + NoteDAO.TABLE + " WHERE NO_EXAMEN = " + id_examen + " AND NO_ETUDIANT = " + id_etudiant);

			if(result.first()){
				ExamenDAO examdao = new ExamenDAO();
				EtudiantDAO etudiantdao = new EtudiantDAO();
				obj = new Note(etudiantdao.find(id_etudiant), examdao.find(id_examen), -1.0);
				if((Double)result.getDouble("NOTE") != null){
					obj.setNote((Double)result.getDouble("NOTE"));
				}
			}
			request.close();
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	    return obj;
	}

	@Override
	public Note create(Note obj) {
		if(obj.getMonEtudiant() == null || obj.getMonExamen()==null){
			return null;
		}
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("INSERT INTO "+ NoteDAO.TABLE +" (NOTE, NO_ETUDIANT, NO_EXAMEN) " +
					"VALUES ("+obj.getNote() +","+ obj.getMonEtudiant().getNumeroEtudiant() + "," + obj.getMonExamen().getNumeroExamen());
			request.getConnection().commit();
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Note update(Note obj) {
		if(obj.getMonEtudiant() == null || obj.getMonExamen()==null){
			return null;
		}
		try {
			Statement request = this.connect.createStatement();
			if(this.find(obj.getMonEtudiant().getNumeroEtudiant(), obj.getMonExamen().getNumeroExamen())==null){
				return null;
			}
			else{
			request.executeUpdate("UPDATE "+ NoteDAO.TABLE +" SET NOTE = " + obj.getNote() + " WHERE NO_ETUDIANT ="+obj.getMonEtudiant().getNumeroEtudiant() +"AND NO_EXAMEN = " + obj.getMonExamen().getNumeroExamen());
			request.getConnection().commit();
			request.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}	


	@Override
	public void delete(Note obj) {
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("DELETE FROM " + NoteDAO.TABLE +" WHERE NO_EXAMEN = " +obj.getMonExamen().getNumeroExamen() + " AND NO_ETUDIANT = " + obj.getMonEtudiant().getNumeroEtudiant());
			request.getConnection().commit();
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	@Override
	public List<Note> findAll() {
		List<Note> listeNote = new ArrayList<Note>();
		
		try{
			Statement request = this.connect.createStatement();
			ExamenDAO examDAO = new ExamenDAO();
			EtudiantDAO etudiantDAO = new EtudiantDAO();

			ResultSet result = request.executeQuery("SELECT FROM "
					+ NoteDAO.TABLE);
			while (result.next()) {
				Note n = new Note(etudiantDAO.find(result.getInt("NO_ETUDIANT")), examDAO.find(result.getInt("NO_EXAMEN")), result.getDouble("NOTE"));
				listeNote.add(n);
			}
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listeNote;
	}

}
