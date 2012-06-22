package serviceEnseignant.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.ECDAO;

import beans.Examen;
import beans.Note;
import beans.Promotion;

public class ExamenDAO extends DAO<Examen> {
	public static String TABLE = "PROMOTION";

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
				DateDAO datedao = new DateDAO();
				TypeDAO typedao = new TypeDAO();

				obj.setMonEC(ecdao.find(result.getInt("NO_EC"),
						result.getInt("NO_UE"), result.getInt("NO_FORMATION")));
				obj.setDate(datedao.find(DAO.dateFromOracleToJava(result
						.getDate("DATE_EXAMEN"))));
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
			if(this.find(obj.getNumeroExamen()) == null){
				return null;
			}
			else{
				delete(obj);
				create(obj);
			}
			return obj;
		}	


	@Override
	public void delete(Examen obj) {
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("DELETE FROM " + ExamenDAO.TABLE
					+ " WHERE NO_EXAMEN = " + obj.getNumeroExamen());
			request.executeUpdate("DELETE FROM " + NoteDAO.TABLE + " WHERE NO_EXAMEN = " + obj.getNumeroExamen());
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Examen> LoadExamnForOneEC(EC obj){
		List<Examen> listExam = new ArrayList<Examen>;
		
	}
}
