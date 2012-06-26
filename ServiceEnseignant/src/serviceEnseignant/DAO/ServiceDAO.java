package serviceEnseignant.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.ECDAO;

import beans.Service;
import beans.TypePoste;

public class ServiceDAO extends DAO<Service>{

	public static String TABLE = "SERVICE";
	
	@Override
	public Service find(int id) {
		return null;
		
	}
	
	public Service find(int numEns, int numType, int numFormation, int numUE, int numEC){
		Service obj = null;
		
		try {
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM " + ServiceDAO.TABLE + " WHERE NO_ENSEIGNANT = "+numEns+" AND NO_TYPE="+numType+" AND NO_FORMATION="+numFormation+" AND NO_UE="+numUE+" AND NO_EC="+numEC);

			if(result.first()){	
				
				ECDAO ecDao = new ECDAO();
				EnseignantDAO ensDao = new EnseignantDAO();
				TypeDAO typeDao = new TypeDAO();
								
				obj = new Service(ecDao.find(numEC), ensDao.find(numEns), typeDao.find(numType), result.getInt("NB_MINUTES_SERVICE")/60);
			}
			
			request.close();
			
	    } catch (SQLException e) {
	            e.printStackTrace();
	    } catch (Exception e) {				
			e.printStackTrace();
		}
		
		
		return obj;
	}

	@Override
	public Service create(Service obj) {
		
		try {			
			
			Statement request = this.connect.createStatement();
			request.executeUpdate("INSERT INTO "+ ServiceDAO.TABLE+" (NO_EC, NO_UE, NO_FORMATION, NO_TYPE, NO_ENSEIGNANT, NB_MINUTES_SERVICE) " +
					"VALUES ("+obj.getMonEC().getNumeroEC()+", "+obj.getMonEC().getMonUE().getNumeroUE()+","+obj.getMonEC().getMonUE().getMaFormation().getNumeroFormation()+","+obj.getMonType()+","+obj.getMonEnseignant().getNumeroEnseignant()+","+obj.getNbHeures()+")");
		
			request.close();			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}

	@Override
	public Service update(Service obj) {
		
		try {
							
			int nb_minutes = obj.getNbHeures()*60;
			
			Statement request = this.connect.createStatement();
			request.executeUpdate("UPDATE "+ ServiceDAO.TABLE +
					" SET NB_MINUTES_SERVICE = "+nb_minutes+
					" WHERE NO_EC="+obj.getMonEC().getNumeroEC()+" AND NO_UE="+obj.getMonEC().getMonUE().getNumeroUE()+" AND NO_FORMATION="+obj.getMonEC().getMonUE().getMaFormation().getNumeroFormation()+" AND NO_TYPE="+obj.getMonType().getNumeroType()+" AND NO_ENSEIGNANT="+obj.getMonEnseignant().getNumeroEnseignant());
		
			request.close();
				
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
		
	}

	@Override
	public void delete(Service obj) {
		
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("DELETE FROM " + ServiceDAO.TABLE +" WHERE NO_EC="+obj.getMonEC().getNumeroEC()+" AND NO_UE="+obj.getMonEC().getMonUE().getNumeroUE()+" AND NO_FORMATION="+obj.getMonEC().getMonUE().getMaFormation().getNumeroFormation()+" AND NO_TYPE="+obj.getMonType().getNumeroType()+" AND NO_ENSEIGNANT="+obj.getMonEnseignant().getNumeroEnseignant());
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	

}
