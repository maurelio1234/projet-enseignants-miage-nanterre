package serviceEnseignant.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import dao.CreneauDAO;
import dao.DAO;
import dao.JoursDAO;
import dao.TypeDAO;
import dao.VoeuxECDAO;
import dao.ECDAO;



import beans.*;

public class EnseignantDAO extends DAO<Enseignant> {

	public static String TABLE = "ENSEIGNANT";

	
	
	@Override
	public Enseignant find(int id) {
		Enseignant obj = null;
		GregorianCalendar date = null;
		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ EnseignantDAO.TABLE + " WHERE NO_ENSEIGNANT = " + id);
			if (result.first()) {
				date = DAO.ConvertirDate(result.getString("DATE_NAISSANCE_ENSEIGNANT"));
				
				obj = new Enseignant(result.getInt("NO_ENSEIGNANT"),
						result.getString("NOM_ENSEIGNANT"),
						result.getString("PRENOM_ENSEIGNANT"),
						result.getString("ADRESSE_ENSEIGNANT"),
						result.getString("TELEPHONE_ENSEIGNANT"), date,
						result.getString("LOGIN_ENSEIGNANT"),
						result.getString("PWD_ENSEIGNANT"));

				TypePosteDAO typePosteDAO = new TypePosteDAO();
				obj.setMonPoste(typePosteDAO.find(result.getInt("NO_POSTE")));	
			}
			request.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	
	
	@Override
	public List<Enseignant> findAll() {
		List<Enseignant> listEns = new ArrayList<Enseignant>();
		Enseignant obj = null;
		GregorianCalendar date;
		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ EnseignantDAO.TABLE);
			while(result.next()) {
				date = DAO.ConvertirDate(result.getString("DATE_NAISSANCE_ENSEIGNANT"));

				obj = new Enseignant(result.getInt("NO_ENSEIGNANT"),
						result.getString("NOM_ENSEIGNANT"),
						result.getString("PRENOM_ENSEIGNANT"),
						result.getString("ADRESSE_ENSEIGNANT"),
						result.getString("TELEPHONE_ENSEIGNANT"), date,
						result.getString("LOGIN_ENSEIGNANT"),
						result.getString("PWD_ENSEIGNANT"));

				TypePosteDAO typePosteDAO = new TypePosteDAO();
				obj.setMonPoste(typePosteDAO.find(result.getInt("NO_POSTE")));

				listEns.add(obj);
			}
			request.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listEns;
	}
	
	@Override
	public Enseignant create(Enseignant obj) {
		if (obj.getMonPoste() == null) {
			return null;
		} else {
			try {
				TypePosteDAO typePosteDAO = new TypePosteDAO();

				if (typePosteDAO.find(obj.getMonPoste().getNumeroPoste()) == null) {
					typePosteDAO.create(obj.getMonPoste());
				}

				Statement request = this.connect.createStatement();
				request.executeUpdate("INSERT INTO "
						+ EnseignantDAO.TABLE
						+ " (NO_ENSEIGNANT,NO_POSTE,NOM_ENSEIGNANT,PRENOM_ENSEIGNANT,ADRESSE_ENSEIGNANT,TELEPHONE_ENSEIGNANT, DATE_NAISSANCE_ENSEIGNANT, LOGIN_ENSEIGNANT, PWD_ENSEIGNANT) "
						+ "VALUES (SEQ_ENSEIGNANT.NEXTVAL,"
						+ obj.getMonPoste().getNumeroPoste() + ",'"
						+ obj.getNom() + "','" + obj.getPrenom() + "','"
						+ obj.getAdresse() + "','" + obj.getTelephone() + "','"
						+ DAO.dateFromJavaToOracle(obj.getDateNaissance()) + "','" + obj.getLogin() + "','"
						+ obj.getPassword() + "')");

				request.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return obj;
		}
	}

	
	
	@Override
	public Enseignant update(Enseignant obj) {

		int id = obj.getNumeroEnseignant();
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("UPDATE "
					+ EnseignantDAO.TABLE + " SET NO_POSTE ="
					+ obj.getMonPoste().getNumeroPoste()
					+ ", NOM_ENSEIGNANT = '" + obj.getNom()
					+ "',PRENOM_ENSEIGNANT = '" + obj.getPrenom() + "',"
					+ "ADRESSE_ENSEIGNANT ='" + obj.getAdresse()
					+ "',TELEPHONE_ENSEIGNANT='" + obj.getTelephone()
					+ "', DATE_NAISSANCE_ENSEIGNANT='" + DAO.dateFromJavaToOracle(obj.getDateNaissance())
					+ "', LOGIN_ENSEIGNANT='" + obj.getLogin()
					+ "', PWD_ENSEIGNANT = '" + obj.getPassword() + "'"
					+ " WHERE NO_ENSEIGNANT =" + id);
			request.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	
	
	@Override
	public void delete(Enseignant obj) {
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("DELETE FROM " + EnseignantDAO.TABLE
					+ " WHERE NO_ENSEIGNANT = " + obj.getNumeroEnseignant());
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	/**
	 * Methode find qui verifie en fonction du login/mdp (connexion).
	 * 
	 * @param login
	 * @param mdp
	 * @return Ref vers l'enseignant, ou null si login/mdp faux.
	 */
	public Enseignant find(String login, String mdp) {
		Enseignant obj = null;
		GregorianCalendar date;

		try {

			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request
					.executeQuery("select * from ENSEIGNANT e "
							+ "where e.LOGIN_ENSEIGNANT = '" + login
							+ "' and e.PWD_ENSEIGNANT = '" + mdp + "'");

			if (result.first()) {
				date = DAO.ConvertirDate(result.getString("DATE_NAISSANCE_ENSEIGNANT"));

				obj = new Enseignant(result.getInt("NO_ENSEIGNANT"),
						result.getString("NOM_ENSEIGNANT"),
						result.getString("PRENOM_ENSEIGNANT"),
						result.getString("ADRESSE_ENSEIGNANT"),
						result.getString("TELEPHONE_ENSEIGNANT"), date,
						result.getString("LOGIN_ENSEIGNANT"),
						result.getString("PWD_ENSEIGNANT"));

				TypePosteDAO typePosteDAO = new TypePosteDAO();
				obj.setMonPoste(typePosteDAO.find(result.getInt("NO_POSTE")));

			}
			
			request.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}


	
	
	/**
	 * Méthode find en fonction du nom de l'enseignant. !! Retourne uniquement
	 * le premier enseignant trouvé !!
	 * 
	 * @param nom
	 * @return Ref de l'enseignant
	 */
	public Enseignant find(String nom) {

		Enseignant obj = null;
		GregorianCalendar date;

		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ EnseignantDAO.TABLE + " WHERE NOM_ENSEIGNANT = '" + nom
					+ "'");
			if (result.first()) {
				date = DAO.ConvertirDate(result.getString("DATE_NAISSANCE_ENSEIGNANT"));

				obj = new Enseignant(result.getInt("NO_ENSEIGNANT"),
						result.getString("NOM_ENSEIGNANT"),
						result.getString("PRENOM_ENSEIGNANT"),
						result.getString("ADRESSE_ENSEIGNANT"),
						result.getString("TELEPHONE_ENSEIGNANT"), date,
						result.getString("LOGIN_ENSEIGNANT"),
						result.getString("PWD_ENSEIGNANT"));

				TypePosteDAO typePosteDAO = new TypePosteDAO();
				obj.setMonPoste(typePosteDAO.find(result.getInt("NO_POSTE")));

			}
			request.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}



	/**
	 * Initialise la liste des creneaux de cours d'un enseignant.
	 * 
	 * @param obj
	 *            un enseignant
	 */
	private void loadMesCreneaux(Enseignant obj) {
		int id = obj.getNumeroEnseignant();

		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet resultat = request.executeQuery("SELECT * FROM "
					+ CreneauDAO.TABLE + " WHERE NO_ENSEIGNANT = " + id);

			while (resultat.next()) {

				String numSalle = resultat.getString("NO_SALLE");
				int numEC = resultat.getInt("NO_EC");
				int numUE = resultat.getInt("NO_UE");
				int numFormation = resultat.getInt("NO_FORMATION");
				int numType = resultat.getInt("NO_TYPE");
				GregorianCalendar date = DAO.dateFromOracleToJava(resultat
						.getDate("DATE_CRENEAU"));
				
				CreneauDAO creneauDAO = new CreneauDAO();

				Creneau monCreneau = creneauDAO.find(obj.getNumeroEnseignant(), numSalle, numEC, numUE, numFormation, numType, date); 
				obj.getMesCreneaux().add(monCreneau);

			}

			request.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	

	public void loadMesVoeuxEC(Enseignant obj) {
		int id = obj.getNumeroEnseignant();

		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ VoeuxECDAO.TABLE + " WHERE NO_ENSEIGNANT = " + id);

			while (result.next()) {
				VoeuxECDAO voeuxDAO = new VoeuxECDAO();
				VoeuxEC voeux = voeuxDAO.find(result.getInt("NO_FORMATION"),result.getInt("NO_UE"), result.getInt("NO_EC"),obj.getNumeroEnseignant());
				obj.getMesVoeuxEC().add(voeux);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	
	public void loadMesServices(Enseignant obj) {
		int id = obj.getNumeroEnseignant();

		try {
			Statement request = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM "
					+ ServiceDAO.TABLE + " WHERE NO_ENSEIGNANT = " + id);

			while (result.next()) {
				ServiceDAO servDAO = new ServiceDAO();
				Service monService = servDAO.find(obj.getNumeroEnseignant(), result.getInt("NO_TYPE"), result.getInt("NO_FORMATION"), result.getInt("NO_UE"), result.getInt("NO_EC"));

				obj.getMesServices().add(monService);
			}

			request.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	
	public void loadMesExamens(Enseignant obj) {
		try {
			Statement request = this.connect.createStatement();

			ResultSet result = request.executeQuery("SELECT * FROM "
					+ ExamenDAO.TABLE + " WHERE NO_ENSEIGNANT = "
					+ obj.getNumeroEnseignant());
			while (result.next()) {
				ExamenDAO examDAO = new ExamenDAO();
				obj.getMesExamens().add(examDAO.find(result.getInt("NO_EXAMEN")));
			}
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	
public void LoadAll(Enseignant obj){
	this.loadMesVoeuxEC(obj);
	IndisponibiliteDAO indispoDAO = new IndisponibiliteDAO();
	indispoDAO.loadMesIndisponibilites(obj);
	this.loadMesCreneaux(obj);
	this.loadMesServices(obj);
	this.loadMesExamens(obj);
}
}
