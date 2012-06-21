package serviceEnseignant.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.Formation;
import beans.Promotion;
import beans.UE;

public class EnseignantDAO extends DAO<Enseignant> {
	
		public static String TABLE = "ENSEIGNANT";

		@Override
		public Enseignant find(int id) {
			Formation obj = null;
			try {
				Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet result = request.executeQuery("SELECT * FROM " + EnseingnatDAO.TABLE + " WHERE NO_FORMATION = " + id);

				if(result.first()){
				
					obj = new Formation(id,(result.getString("LIBELLE_FORMATION")),result.getInt("NIVEAU_FORMATION"),result.getString("TYPE_FORMATION"),result.getString("GRADE_FORMATION"),result.getString("PARCOURS_FORMATION"));
					ContratQuadriennalDAO quadriDAO = new ContratQuadriennalDAO();
					obj.setMonContrat(quadriDAO.find(result.getInt("NO_CONTRAT")));
					
				}
				request.close();
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		    return obj;
		}

			@Override
		public Enseignant create(Enseignant obj) {
				if(obj.getMonContrat() == null){
					return null;
				}
				else{
					try {
						ContratQuadriennalDAO quadriDAO = new ContratQuadriennalDAO();
						if(quadriDAO.find(obj.getMonContrat().getNumeroContrat()) ==null){
							quadriDAO.create(obj.getMonContrat());
						}
						else{
							//on met a jour
						}
						Statement request = this.connect.createStatement();
						request.executeUpdate("INSERT INTO "+ FormationDAO.TABLE+" (NO_FORMATION,LIBELLE_FORMATION,NIVEAU_FORMATION,GRADE_FORMATION,TYPE_FORMATION,PARCOURS_FORMATION, NO_CONTRAT) " +
								"VALUES (SEQ_FORMATION.NEXTVAL,'"+obj.getLibelle() +"',"+ obj.getNiveau()+",'"+ obj.getGrade()+"','"+ obj.getType()+"','"+obj.getParcours()+"',"+obj.getMonContrat().getNumeroContrat()+")");
					
						request.close();
					/*	if(!obj.getMesUE().isEmpty()){
							for(int i=0;i<obj.getMesUE().size();i++){				
								UEDAO ueDAO = new UEDAO();
								if(ueDAO.find(obj.getMesUE().get(i).getNumeroUE()) ==null){
									ueDAO.create(obj.getMesUE().get(i));
								}
								else{
									//on met a jour
								}
							}
						}*/
					/*	if(!obj.getMesPromotions().isEmpty()){
							for(int i=0;i<obj.getMesPromotions().size();i++){				
								PromotionDAO promoDAO = new PromotionDAO();
								if(promoDAO.find(obj.getMesPromotions().get(i).getNumeroPromotion()) ==null){
									promoDAO.create(obj.getMesPromotions().get(i));
								}
								else{
									//on met a jour
								}
							}
						}*/
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return obj;
				}
		}

		@Override
		public Enseignant update(Enseignant obj) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void delete(Enseignant obj) {
				try {
					Statement request = this.connect.createStatement();
					request.executeUpdate("DELETE FROM " + EnseignantDAO.TABLE +" WHERE NO_FORMATION = " +obj.getNumeroFormation());
					request.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		public void loadMesUE(Formation obj) {
			int id = obj.getNumeroFormation();
			try {
				Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet result = request.executeQuery("SELECT * FROM " + UEDAO.TABLE + " WHERE NO_FORMATION = " + id);

				while(result.next()){
					UE ue = new UE(result.getInt("NO_UE"), obj);
					obj.getMesUE().add(ue);
				}
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		}
		
		public void loadMesPromotions(Formation obj) {
			int id = obj.getNumeroFormation();
			try {
				Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet result = request.executeQuery("SELECT * FROM " + PromotionDAO.TABLE + " WHERE NO_FORMATION = " + id);

				while(result.next()){
					Promotion p = new Promotion(result.getInt("NO_PROMOTION"));
					p.setMaFormation(obj);
					obj.getMesPromotions().add(p);
				}
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		}

	}
