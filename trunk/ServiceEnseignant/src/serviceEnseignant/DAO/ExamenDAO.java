package serviceEnseignant.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.*;

import beans.*;

public class ExamenDAO extends DAO<Examen> {
	public static String TABLE = "EXAMEN";

	@Override
	public Examen find(int id) {
		Examen obj = null;
		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ ExamenDAO.TABLE + " WHERE NO_EXAMEN = " + id);

			if (result.first()) {
				obj = new Examen(id);
				ECDAO ecdao = new ECDAO();
				EnseignantDAO ensdao = new EnseignantDAO();
				JoursDAO datedao = new JoursDAO();
				TypeDAO typedao = new TypeDAO();

				obj.setMonEC(ecdao.find(result.getInt("NO_EC"),
						result.getInt("NO_UE"), result.getInt("NO_FORMATION")));
				obj.setDate(datedao.find(DAO.dateFromOracleToJava(result
						.getDate("DATE_EXAMEN"))));
				obj.setLibelle(result.getString("LIBELLE_EXAMEN"));
				obj.setMonType(typedao.find(result.getInt("NO_TYPE")));
				obj.setCoefficient(result.getDouble("COEF_EXAMEN"));
				obj.setHoraire(result.getString("HORAIRE_EXAMEN"));
				obj.setMonEnseignant(ensdao.find(result.getInt("NO_ENSEIGNANT")));
			}
			request.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Examen create(Examen obj) {
		//TO_DO : manque l'initialisation des Notes.
		if (obj.getMonEC() == null || obj.getMonType() == null
				|| obj.getMonEnseignant() == null || obj.getDate() == null) {
			return null;
		}
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("INSERT INTO "
					+ ExamenDAO.TABLE
					+ " (NO_EXAMEN, HORAIRE_EXAMEN, LIBELLE_EXAMEN, COEF_EXAMEN, NO_EC, NO_UE, NO_FORMATION, NO_TYPE, NO_ENSEIGNANT) "
					+ "VALUES (SEQ_PROMOTION.NEXTVAL, "
					+ obj.getHoraire()
					+ ", "
					+ obj.getLibelle()
					+ ", "
					+ obj.getCoefficient()
					+ ", "
					+ obj.getMonEC().getNumeroEC()
					+ ", "
					+ obj.getMonEC().getMonUE().getNumeroUE()
					+ ", "
					+ obj.getMonEC().getMonUE().getMaFormation()
							.getNumeroFormation() + ", "
					+ obj.getMonType().getNumeroType() + ", "
					+ obj.getMonEnseignant().getNumeroEnseignant() + ")");
			request.getConnection().commit();
			request.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Examen update(Examen obj) {
		if (obj.getMonEC() == null || obj.getMonType() == null
				|| obj.getMonEnseignant() == null || obj.getDate() == null) {
			return null;
		}
		if (this.find(obj.getNumeroExamen()) == null) {
			return null;
		} else {
			try {
				Statement request = this.connect.createStatement();
				request.executeUpdate("UPDATE "
						+ ExamenDAO.TABLE
						+ " SET HORAIRE_EXAMEN = '" + obj.getHoraire() 
						+ "', LIBELLE_EXAMEN = '" + obj.getLibelle() 
						+ "', COEF_EXAMEN = " + obj.getCoefficient()
						+ ", NO_EC = "+ obj.getMonEC().getNumeroEC()
						+ ", NO_UE = " + obj.getMonEC().getMonUE().getNumeroUE()
						+ ", NO_FORMATION = " + obj.getMonEC().getMonUE().getMaFormation()
						.getNumeroFormation()
						+ ", NO_TYPE = " + obj.getMonType().getNumeroType()
						+ ", NO_ENSEIGNANT = "+ obj.getMonEnseignant().getNumeroEnseignant() 
						+ " WHERE NO_EXAMEN = " + obj.getNumeroExamen());
				request.getConnection().commit();
				request.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return obj;
	}

	@Override
	public void delete(Examen obj) {
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("DELETE FROM " + ExamenDAO.TABLE
					+ " WHERE NO_EXAMEN = " + obj.getNumeroExamen());
			request.getConnection().commit();
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Examen> LoadAllExamForOneEC(EC obj) {
		List<Examen> listExam = new ArrayList<Examen>();
		try {
			Statement request = this.connect.createStatement();
			ECDAO ecdao = new ECDAO();
			EnseignantDAO ensdao = new EnseignantDAO();
			JoursDAO datedao = new JoursDAO();
			TypeDAO typedao = new TypeDAO();

			ResultSet result = request.executeQuery("SELECT FROM "
					+ ExamenDAO.TABLE + " WHERE NO_EC = " + obj.getNumeroEC()
					+ " AND NO_UE = " + obj.getMonUE().getNumeroUE()
					+ " AND NO_FORMATION = "
					+ obj.getMonUE().getMaFormation().getNumeroFormation()
					+ ")");
			while (result.next()) {
				Examen e = new Examen(result.getInt("NO_EXAMEN"));
				e.setMonEC(ecdao.find(result.getInt("NO_EC"),
						result.getInt("NO_UE"), result.getInt("NO_FORMATION")));
				e.setDate(datedao.find(DAO.dateFromOracleToJava(result
						.getDate("DATE_EXAMEN"))));
				e.setMonType(typedao.find(result.getInt("NO_TYPE")));
				e.setCoefficient(result.getDouble("COEF_EXAMEN"));
				e.setHoraire(result.getString("HORAIRE_EXAMEN"));
				e.setMonEnseignant(ensdao.find(result.getInt("NO_ENSEIGNANT")));
				listExam.add(e);
			}
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listExam;
	}
	
	
	
	public List<Note> LoadNote(Examen obj){
		List<Note> listeNotes = new ArrayList<Note>();
		try {
			Statement request = this.connect.createStatement();
			ExamenDAO examDAO = new ExamenDAO();
			EtudiantDAO etudiantDAO = new EtudiantDAO();

			ResultSet result = request.executeQuery("SELECT * FROM "
					+ NoteDAO.TABLE + " WHERE NO_EXAMEN = " + obj.getNumeroExamen());
			while (result.next()) {
				Note n = new Note(etudiantDAO.find(result.getInt("NO_ETUDIANT")), examDAO.find(result.getInt("NO_EXAMEN")), result.getDouble("NOTE"));
				listeNotes.add(n);
			}
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listeNotes;
	}

	@Override
	public List<Examen> findAll() {
		List<Examen> listExam = new ArrayList<Examen>();
		try {
			Statement request = this.connect.createStatement();
			ECDAO ecdao = new ECDAO();
			EnseignantDAO ensdao = new EnseignantDAO();
			JoursDAO datedao = new JoursDAO();
			TypeDAO typedao = new TypeDAO();

			ResultSet result = request.executeQuery("SELECT FROM "
					+ ExamenDAO.TABLE);
			while (result.next()) {
				Examen e = new Examen(result.getInt("NO_EXAMEN"));
				e.setMonEC(ecdao.find(result.getInt("NO_EC"),
						result.getInt("NO_UE"), result.getInt("NO_FORMATION")));
				e.setDate(datedao.find(DAO.dateFromOracleToJava(result
						.getDate("DATE_EXAMEN"))));
				e.setMonType(typedao.find(result.getInt("NO_TYPE")));
				e.setCoefficient(result.getDouble("COEF_EXAMEN"));
				e.setHoraire(result.getString("HORAIRE_EXAMEN"));
				e.setMonEnseignant(ensdao.find(result.getInt("NO_ENSEIGNANT")));
				listExam.add(e);
			}
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listExam;
	}
}
