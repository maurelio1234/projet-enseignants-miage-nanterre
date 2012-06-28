package serviceEnseignant.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import dao.CreneauDAO;
import dao.DAO;
import dao.FormationDAO;
import dao.ReunionDAO;

import beans.Creneau;
import beans.Examen;
import beans.Indisponibilite;
import beans.Jours;
import beans.Reunion;
import serviceEnseignant.DAO.IndisponibiliteDAO;

public class JoursDAO extends DAO<Jours> {

	public static String TABLE = "JOURS";

	public static boolean debug = true;

	public List<Jours> findAll() {
		List<Jours> listeJours = new ArrayList<Jours>();
		Jours obj = null;
		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ JoursDAO.TABLE);

			while (result.next()) {
				obj = new Jours();
				obj.setDateDuJour(DAO.dateFromOracleToJava(result
						.getDate("DATE_JOUR")));
				listeJours.add(obj);
			}
			request.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeJours;
	}

	@Override
	public Jours create(Jours obj) {
		try {
			Statement request = this.connect.createStatement();
			String requete = "INSERT INTO " + FormationDAO.TABLE
					+ " (DATE_JOUR)" + "VALUES ("
					+ DAO.dateFromJavaToOracle(obj.getDateDuJour()) + ")";
			request.executeUpdate(requete);
			if (debug)
				System.out.println(requete);
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Jours update(Jours obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Jours obj) {
		try {
			Statement request = this.connect.createStatement();
			String requete = "DELETE FROM " + CreneauDAO.TABLE
					+ " WHERE DATE_JOUR = "
					+ DAO.dateFromJavaToOracle(obj.getDateDuJour()) + ";";
			request.executeUpdate(requete);
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Jours find(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Jours find(GregorianCalendar date) {
		Jours j = new Jours();
		try {

			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ JoursDAO.TABLE + " WHERE DATE_JOUR = "
					+ DAO.dateFromJavaToOracle(date));
			if(result.first()){
			j = new Jours(DAO.dateFromOracleToJava(result.getDate("DATE_JOUR")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return j;
	}

	public void loadMesCreneaux(Jours obj) {
		GregorianCalendar date = obj.getDateDuJour();
		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ CreneauDAO.TABLE + " WHERE DATE = '"
					+ DAO.dateFromJavaToOracle(date) + "'");

			while (result.next()) {
				CreneauDAO c = new CreneauDAO();
				Creneau creneau = c.find(result.getInt("NO_ENSEIGNANT"), result
						.getString("NO_SALLE"), result.getInt("NO_EC"), result
						.getInt("NO_UE"), result.getInt("NO_FORMATION"), result
						.getInt("NO_TYPE"), DAO.dateFromOracleToJava(result
						.getDate("DATE_CRENEAU")));

				obj.getMesCreneaux().add(creneau);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMesIndisponibilites(Jours obj) {
		GregorianCalendar date = obj.getDateDuJour();
		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ IndisponibiliteDAO.TABLE + " WHERE JOURS = '"
					+ DAO.dateFromJavaToOracle(date) + "'");

			while (result.next()) {
				IndisponibiliteDAO i = new IndisponibiliteDAO();
				Indisponibilite indispo = i.find(result.getDate("DATE_INDISPO"), result
						.getInt("NO_ENSEIGNANT"));
				obj.getMesIndisponibilites().add(indispo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMesReunions(Jours obj) {
		GregorianCalendar date = obj.getDateDuJour();
		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ ReunionDAO.TABLE + " WHERE DATE = '"
					+ DAO.dateFromJavaToOracle(date) + "'");

			while (result.next()) {
				ReunionDAO r = new ReunionDAO();
				Reunion reunion = r.find(result.getInt("NO_REUNION"));
				obj.getMesReunions().add(reunion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMesExamens(Jours obj) {
		GregorianCalendar date = obj.getDateDuJour();
		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ ExamenDAO.TABLE + " WHERE DATE = '"
					+ DAO.dateFromJavaToOracle(date) + "'");

			while (result.next()) {
				ExamenDAO e = new ExamenDAO();
				Examen examen = e.find(result.getInt("NO_EXAMEN"));
				obj.getMesExamens().add(examen);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
