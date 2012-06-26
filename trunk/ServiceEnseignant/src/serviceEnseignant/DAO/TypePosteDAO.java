package serviceEnseignant.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import dao.VoeuxECDAO;

import beans.Enseignant;
import beans.Jours;
import beans.TypePoste;

public class TypePosteDAO extends DAO<TypePoste>{

	public static String TABLE = "TYPE_POSTE";
	
	@Override
	public TypePoste find(int id) {

		TypePoste obj = null;
		
		try {
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM " + TypePosteDAO.TABLE + " WHERE NO_POSTE = " + id);

			if(result.first()){							
				obj = new TypePoste(id,(result.getString("TYPE_POSTE")),result.getString("DESCRIPTION_POSTE"),result.getInt("NB_HEURES"));
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
	public TypePoste create(TypePoste obj) {
		
		try {			
				
			Statement request = this.connect.createStatement();
			request.executeUpdate("INSERT INTO "+ TypePosteDAO.TABLE+" (NO_POSTE,TYPE_POSTE,DESCRIPTION_POSTE,NB_HEURES) " +
					"VALUES (SEQ_TYPE_POSTE.NEXTVAL,'"+obj.getType()+"','"+ obj.getDescription()+"',"+ obj.getNbHeures());
		
			request.close();
			
			// liaison des enseignants qui sont affectes a ce poste
			if(!obj.getMesEnseignants().isEmpty()){
				for(int i=0;i<obj.getMesEnseignants().size();i++){				
					
					EnseignantDAO ensDao = new EnseignantDAO();
										 								
					if(ensDao.find(obj.getMesEnseignants().get(i).getNumeroEnseignant()) == null){
						ensDao.create(obj.getMesEnseignants().get(i));
					}
					else{
						//on met a jour
						Statement update = this.connect.createStatement();
						
						update.executeUpdate("UPDATE "+ EnseignantDAO.TABLE +
								" SET NO_POSTE = "+obj.getNumeroPoste() +
								" WHERE NO_ENSEIGNANT = "+ obj.getMesEnseignants().get(i).getNumeroEnseignant());
						
						update.close();
					}
				}
			}		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	
}

	@Override
	public TypePoste update(TypePoste obj) {
		
		int id = obj.getNumeroPoste();
		
		try {
							
			Statement request = this.connect.createStatement();
			request.executeUpdate("UPDATE "+ TypePosteDAO.TABLE +
					" SET TYPE_POSTE = '"+obj.getType()+"', DESCRIPTION_POSTE = '"+obj.getDescription()+"'," +
					"NB_HEURES ="+obj.getNbHeures()+
					" WHERE NO_POSTE ="+id);
		
			request.close();
				
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.find(obj.getNumeroPoste());
		
	}

	@Override
	public void delete(TypePoste obj) {
		
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("DELETE FROM " + TypePosteDAO.TABLE +" WHERE NO_POSTE = " +obj.getNumeroPoste());
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
