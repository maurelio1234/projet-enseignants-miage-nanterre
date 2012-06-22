package serviceEnseignant.DAO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.Note;
import beans.Promotion;
import beans.Examen;
import beans.Etudiant;

public class NoteDAO extends DAO<Note> {
	public static String TABLE = "PROMOTION";

	public Note find(int id_etudiant, int id_examen) {
		Note obj = null;
		try {
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM " + NoteDAO.TABLE + " WHERE NO_EXAMEN = " + id_examen + " AND NO_ETUDIANT = " + id_etudiant);

			if(result.first()){
				ExamenDAO examdao = new ExamenDAO();
				EtudiantDAO etudiantdao = new EtudiantDAO();
				obj = new Note(etudiantdao.find(id_etudiant), examdao.find(id_examen), -1,0);
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
	/**
	 * Utiliser la fonction find(int id, int id)
	 */
	public Note find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
