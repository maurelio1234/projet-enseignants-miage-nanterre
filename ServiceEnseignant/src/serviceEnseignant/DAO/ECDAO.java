package serviceEnseignant.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.CreneauDAO;
import dao.DAO;
import dao.NecessiteDAO;
import dao.UEDAO;
import dao.VoeuxECDAO;

import beans.EC;
import beans.Examen;
import beans.Jours;
public class ECDAO extends DAO<EC> {
	public static String TABLE = "EC";

	@Override
	public EC find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public EC find(int num_ec, int num_ue, int num_formation) {
		EC obj = null;
		try {
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM " + ECDAO.TABLE + " WHERE NO_FORMATION = " + num_formation + " AND NO_UE = " + num_ue + " AND NO_EC = " + num_ec);

			if(result.first()){				
				if((Integer)result.getInt("NO_UE") != null && (Integer)result.getInt("NO_FORMATION") != null){
					UEDAO ueDAO = new UEDAO();
					obj = new EC(num_ec,ueDAO.find(num_ue, num_formation),result.getString("LIBELLE_EC"),result.getInt("COEF_EC"));
				}
			}
			request.close();
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	    return obj;
	}

	@Override
	public List<EC> findAll() {
		List<EC> listeECs = new ArrayList<EC>();
		try {
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT NO_FORMATION, NO_UE, NO_EC FROM " + ECDAO.TABLE );
			while(result.next()){
				listeECs.add(find(result.getInt("NO_EC"),result.getInt("NO_UE"),result.getInt("NO_FORMATION")));
				}
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	    return listeECs;
	}
	
	@Override
	public EC create(EC obj) {
		if(obj.getMonUE() == null){
			return null;
		}
		try {
			UEDAO ueDAO = new UEDAO();
			if(ueDAO.find(obj.getMonUE().getNumeroUE(),obj.getMonUE().getMaFormation().getNumeroFormation()) ==null){
				ueDAO.create(obj.getMonUE());
			}
			else{
				//on met a jour
			}
			
			Statement request = this.connect.createStatement();
			request.executeUpdate("INSERT INTO "+ ECDAO.TABLE +" (NO_EC,NO_UE, NO_FORMATION,LIBELLE_EC, COEFF_EC) " +
					"VALUES (SEQ_EC.NEXTVAL, "+obj.getMonUE().getNumeroUE()+","+obj.getMonUE().getMaFormation().getNumeroFormation()+","+obj.getLibelle()+","+obj.getCoefficient()+" )");
			request.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public EC update(EC obj) {
		// TODO Auto-generated method stub
		if(obj.getMonUE() == null){
			return null;
		}
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("UPDATE "+ ECDAO.TABLE +" SET  LIBELLE_EC   ='" + obj.getLibelle() + "' , COEF_EC =" + obj.getCoefficient()+
					" WHERE NO_EC = " + obj.getNumeroEC()+ " AND  NO_UE = " + obj.getMonUE().getNumeroUE() + " AND NO_FORMATION =" + obj.getMonUE().getMaFormation().getNumeroFormation() + " ");
			request.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public void delete(EC obj) {
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("DELETE FROM " + ECDAO.TABLE +" WHERE NO_EC = " +obj.getNumeroEC() + " AND NO_UE = " + obj.getMonUE().getNumeroUE()+ " AND NO_FORMATION = " + obj.getMonUE().getMaFormation().getNumeroFormation());
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadMesExamens(EC obj) {
		int num_ec = obj.getNumeroEC();
		int num_ue = obj.getMonUE().getNumeroUE();
		int num_form = obj.getMonUE().getMaFormation().getNumeroFormation();
		try {
			DAO<Examen> examDAO = new ExamenDAO();
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT NO_EXAMEN, DATE_EXAMEN FROM " + ExamenDAO.TABLE + " WHERE NO_FORMATION = " + num_form + " AND NO_UE = " + num_ue + " AND NO_EC = " + num_ec);

			while(result.next()){
				Examen exam = examDAO.find(result.getInt("NO_EXAMEN"));
				exam.setDate(new Jours(DAO.dateFromOracleToJava(result.getDate("DATE_EXAMEN"))));
				exam.setMonEC(obj);
				obj.getMesExamens().add(exam);
			}
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}
	
	public void loadMesNecessites(EC obj) {
		int num_ec = obj.getNumeroEC();
		int num_ue = obj.getMonUE().getNumeroUE();
		int num_form = obj.getMonUE().getMaFormation().getNumeroFormation();
		try {
			NecessiteDAO necDAO = new NecessiteDAO();
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);			
			ResultSet result = request.executeQuery("SELECT NO_TYPE FROM " + NecessiteDAO.TABLE + " WHERE NO_FORMATION = " + num_form + " AND NO_UE = " + num_ue + " AND NO_EC = " + num_ec);
			while(result.next()){
				//necDAO.find(num_form,num_ue,num_ec,result.getInt("NO_TYPE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMesVoeuxEC(EC obj) {
		int num_ec = obj.getNumeroEC();
		int num_ue = obj.getMonUE().getNumeroUE();
		int num_form = obj.getMonUE().getMaFormation().getNumeroFormation();
		try {
			VoeuxECDAO voeuxECDAO = new VoeuxECDAO();
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);			
			ResultSet result = request.executeQuery("SELECT NO_ENSEIGNANT FROM " + VoeuxECDAO.TABLE + " WHERE NO_FORMATION = " + num_form + " AND NO_UE = " + num_ue + " AND NO_EC = " + num_ec);
			while(result.next()){
				voeuxECDAO.find(num_form,num_ue,num_ec,result.getInt("NO_ENSEIGNANT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMesCreneaux(EC obj) {
		int num_ec = obj.getNumeroEC();
		int num_ue = obj.getMonUE().getNumeroUE();
		int num_form = obj.getMonUE().getMaFormation().getNumeroFormation();
		try {
			CreneauDAO crDAO = new CreneauDAO();
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);			
			ResultSet result = request.executeQuery("SELECT NO_ENSEIGNANT, NO_SALLE, NO_TYPE, DATE_CRENEAU FROM " + CreneauDAO.TABLE + " WHERE NO_FORMATION = " + num_form + " AND NO_UE = " + num_ue + " AND NO_EC = " + num_ec);
			while(result.next()){
				crDAO.find(result.getInt("NO_ENSEIGNANT"),result.getString("NO_SALLE"),num_form,num_ue,num_ec,result.getInt("NO_TYPE"),DAO.dateFromOracleToJava(result.getDate("DATE_CRENEAU")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMesServices(EC obj){
		int num_ec = obj.getNumeroEC();
		int num_ue = obj.getMonUE().getNumeroUE();
		int num_form = obj.getMonUE().getMaFormation().getNumeroFormation();
		try {
			ServiceDAO servDAO = new ServiceDAO();
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);	
			ResultSet result = request.executeQuery("SELECT NO_TYPE,NO_ENSEIGNANT FROM " + ServiceDAO.TABLE + " WHERE NO_FORMATION = " + num_form + " AND NO_UE = " + num_ue + " AND NO_EC = " + num_ec);
			while(result.next()){
				servDAO.find(result.getInt("NO_TYPE"), num_form,num_ue,num_ec,result.getInt("NO_ENSEIGNANT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}


}

