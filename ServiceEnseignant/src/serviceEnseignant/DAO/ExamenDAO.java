package serviceEnseignant.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.Promotion;

public class ExamenDAO extends DAO<Promotion> {
	public static String TABLE = "PROMOTION";

	@Override
	public Examen find(int id) {
		Promotion obj = null;
		try {
			Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet result = request.executeQuery("SELECT * FROM " + PromotionDAO.TABLE + " WHERE NO_PROMOTION = " + id);

			if(result.first()){				
				obj = new Promotion(id);
				if((Integer)result.getInt("NO_FORMATION") != null){
					FormationDAO formDAO = new FormationDAO();
					obj.setMaFormation(formDAO.find(result.getInt("NO_FORMATION")));
				}
			}
			request.close();
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	    return obj;
	}

	@Override
	public Promotion create(Promotion obj) {
		if(obj.getMaFormation() == null){
			return null;
		}
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("INSERT INTO "+ PromotionDAO.TABLE +" (NO_PROMOTION, NO_FORMATION) " +
					"VALUES (SEQ_PROMOTION.NEXTVAL, "+obj.getMaFormation().getNumeroFormation()+" )");
			request.close();
			FormationDAO formDAO = new FormationDAO();
			if(formDAO.find(obj.getMaFormation().getNumeroFormation()) ==null){
				formDAO.create(obj.getMaFormation());
			}
			else{
				//on met a jour
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Promotion update(Promotion obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Promotion obj) {
		try {
			Statement request = this.connect.createStatement();
			request.executeUpdate("DELETE FROM " + PromotionDAO.TABLE +" WHERE NO_PROMOTION = " +obj.getNumeroPromotion());
			request.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
