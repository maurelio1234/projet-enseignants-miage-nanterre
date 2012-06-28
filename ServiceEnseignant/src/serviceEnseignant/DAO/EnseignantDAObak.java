package serviceEnseignant.DAO;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import dao.*;

import beans.*;

public class EnseignantDAObak extends DAO<Enseignant> {
	
		public static String TABLE = "ENSEIGNANT";
		
		//private Enseignant ens;		
		
		public EnseignantDAObak(){
			
			//this.ens = new Enseignant();
			//this.chargerDriver();
		}

		
		
		/**
		 * methode qui verifie si le login et le mdp existe dans la table ensignant
		 * @param login
		 * @param mdp
		 * @return un boolean si l'enseignant existe
		 */	
		public Enseignant find(String login, String mdp){
			Enseignant obj = null;
			
			System.out.println("VERIFIE LOGIN MDP");
			
			try {
				
				java.sql.Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
				System.out.println("je suis connecté");		
				//Statement st =  conn.createStatement();
				
				Statement request = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet result =  request.executeQuery("select * from ENSEIGNANT e "+
														"where e.LOGIN_ENSEIGNANT = '"+ login +"' and e.PWD_ENSEIGNANT = '"+ mdp+ "'"); 
				
				System.out.println("j'ai fai ma requete");	
				
				if(result.first()){
					
					String date = result.getString("DATE_NAISSANCE_ENSEIGNANT");
					GregorianCalendar gcDate = ConvertirDate(date);
					
					obj = new Enseignant(result.getInt("NO_ENSEIGNANT"),result.getString("NOM_ENSEIGNANT"),result.getString("PRENOM_ENSEIGNANT"),result.getString("ADRESSE_ENSEIGNANT"),result.getString("TELEPHONE_ENSEIGNANT"),gcDate,result.getString("LOGIN_ENSEIGNANT"),result.getString("PWD_ENSEIGNANT"));
					
					TypePosteDAO typePosteDAO = new TypePosteDAO();
					obj.setMonPoste(typePosteDAO.find(result.getInt("NO_POSTE")));
					
					this.loadMesVoeuxEC(obj);
					
					IndisponibiliteDAO indispoDAO = new IndisponibiliteDAO();
					indispoDAO.loadMesIndisponibilites(obj);
					
					this.loadMesCreneaux(obj);
					
					this.loadMesServices(obj);
					
					this.loadMesExamens(obj);				
					
				}
				System.out.println("fin requete");	
				result.close();
				request.close();
				conn.close();
				
			}
			catch (Exception e){
				System.out.println("Erreur de connexion a la base de donnee ");
				System.exit(1);
			}
			
			return obj;
		}
		
		/**
		 * methode qui enregistre les modifications de l'enseignant
		 * @param numEns
		 * @param nom
		 * @param prenom
		 * @param adresse
		 * @param telephone
		 * @param dateNaissance
		 * @return un boolean si la methode a fait un enregistrement dans la table enseignant
		 */
		
		
		/**
		 * Methode qui verifie que l'ancien mdp est correct
		 * @param ancienMDP
		 * @return
		 */
		/*public boolean verifAncienMDP(int numEns, String ancienMDP){
			boolean ok = false;
			/*
			try {
				
				java.sql.Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
				
				Statement st =  conn.createStatement();
				
				ResultSet resultat =  st.executeQuery("select * from ENSEIGNANT e "+
														"where e.NO_ENSEIGNANT ="+ numEns +" and e.PWD_ENSEIGNANT = '"+ ancienMDP+ "'"); 
				
				while(resultat.next()){				
					ok = true;
				}
				
				resultat.close();
				st.close();
				conn.close();
				
			}
			catch (Exception e){
				//System.out.println("Erreur de connexion a la base de donnee ");
				System.exit(1);
			}
			*/
			
			//System.out.println("je suis dans la méthode verifier ancien mdp");
			/*
			if(this.find(numEns).getPassword().equals(ancienMDP))
				ok = true;
			
			return ok;
		}*/
		
		/**
		 * Methode qui enregistre le novueau mdp 
		 * @param ancienMDP
		 * @param nouveauMDP
		 * @return
		 */
		/*
		public boolean enregistrerMDP(int numEns, String ancienMDP, String nouveauMDP){
			
			//System.out.println("je suis dans la méthode enregistrer nouveau mdp");
			
			boolean modifOk = false;
			
			//si l'ancien mdp est correct pour le numero enseignant, on peut proceder a la modification 
			if(this.verifAncienMDP(numEns, ancienMDP)){
				
				try {
					
					//java.sql.Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
										
					//conn.setAutoCommit(false);
					
					//System.out.println("je suis connectée dans bdd");
					
					PreparedStatement pst =  this.connect.prepareStatement("UPDATE ENSEIGNANT "+
																"SET PWD_ENSEIGNANT = ? " +
																"WHERE NO_ENSEIGNANT = "+numEns);
					
					this.connect.setAutoCommit(false);
					
					pst.setString(1, nouveauMDP);
					//pst.setInt(2, this.getEns().getNumeroEnseignant());
					
					int result = pst.executeUpdate();
					//retourne le nombre de lignes mises a jour
					
					this.connect.commit();
					
					/*System.out.println("j'execute la requete");
					System.out.println("UPDATE ENSEIGNANT "+
							"SET PWD_ENSEIGNANT = '" +nouveauMDP+
							"' WHERE NO_ENSEIGNANT = "+this.getEns().getNumeroEnseignant());
					
					System.out.println("nb lignes mis a jour : "+result);
					*/
					/*
					if(result>0) {
						modifOk = true;
						//System.out.println("succes de la requete update mdp");
					}
					
					pst.close();
					//this.connect.close();
					
				}
				catch (Exception e){
					System.out.println("Erreur de connexion a la base de donnee ");
					System.exit(1);
				}
				
			}
			
			//this.recupererInfos(this.getEns().getNumeroEnseignant());
			this.ens = this.find(numEns);
			return modifOk;	
		}*/
			
				
		// methode qui retourne un enseignant dont on connait le nom
		public Enseignant find(String nom){
			
			Enseignant obj = null;
			
			try {
				Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet result = request.executeQuery("SELECT * FROM " + EnseignantDAO.TABLE + " WHERE NOM_ENSEIGNANT = '" +nom+"'");

				if(result.first()){
				
					String date = result.getString("DATE_NAISSANCE_ENSEIGNANT");
					GregorianCalendar gcDate = ConvertirDate(date);
					
					obj = new Enseignant(result.getInt("NO_ENSEIGNANT"),result.getString("NOM_ENSEIGNANT"),result.getString("PRENOM_ENSEIGNANT"),result.getString("ADRESSE_ENSEIGNANT"),result.getString("TELEPHONE_ENSEIGNANT"),gcDate,result.getString("LOGIN_ENSEIGNANT"),result.getString("PWD_ENSEIGNANT"));
					
					TypePosteDAO typePosteDAO = new TypePosteDAO();
					obj.setMonPoste(typePosteDAO.find(result.getInt("NO_POSTE")));
					
					this.loadMesVoeuxEC(obj);
					
					IndisponibiliteDAO indispoDAO = new IndisponibiliteDAO();
					indispoDAO.loadMesIndisponibilites(obj);
					
					this.loadMesCreneaux(obj);
					
					this.loadMesServices(obj);
					
					this.loadMesExamens(obj);					
					
				}
				
				request.close();
				
		    } catch (SQLException e) {
		            e.printStackTrace();
		    } catch (Exception e) {				
				e.printStackTrace();
			}
		    return obj;
		}
				
		// methode qui recupere les creneaux de l'enseignant
		public void loadMesCreneaux(Enseignant obj){
			int id = obj.getNumeroEnseignant();
			
			try {
				Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet resultat = request.executeQuery("SELECT * FROM " + CreneauDAO.TABLE + " WHERE NO_ENSEIGNANT = " + id);

				while(resultat.next()){
					
					String numSalle = resultat.getString("NO_SALLE");
					int numEC = resultat.getInt("NO_EC");
					int numUE = resultat.getInt("NO_UE");
					int numFormation = resultat.getInt("NO_FORMATION");
					int numType = resultat.getInt("NO_TYPE");
					Date date = resultat.getDate("DATE_CRENEAU");
					int horaire = resultat.getInt("HORAIRE_CRENEAU");
					int duree = resultat.getInt("DUREE_CRENEAU");
									
					ECDAO ecDao = new ECDAO();
					
					SalleDAO salleDao = new SalleDAO();

					TypeDAO typeDao = new TypeDAO();
					
					
					// conversion date en gregorian calendar puis en type "jours"
					GregorianCalendar gcDate = new GregorianCalendar();
					gcDate.setTime(date);				
					Jours monJour = new Jours(gcDate);
					
					// conversion de l'horaire en string
					String h = String.valueOf(horaire);
					
					// creation du creneau
					Creneau monCreneau = new Creneau(obj, salleDao.find(numSalle), ecDao.find(numEC, numUE, numFormation), typeDao.find(numType), monJour, h, duree);
					
					// ajout du creneau dans la liste des creneaux de l'enseignant 					
					obj.getMesCreneaux().add(monCreneau);
				
				}
				
				request.close();
			}
			catch (SQLException e) {
		            e.printStackTrace();
		    }
			
		}
		/*
		// méthode statique qui charge le driver passé en paramètre
		public static void chargerDriver(){
			
			try{
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	        }
	        catch (Exception ex){
	            System.err.println("Erreur lors du chargement du driver");
	            System.exit(1);
	        }       
		}
		*/
		
		@Override
		public Enseignant find(int id) {
			Enseignant obj = null;
			
			try {
				Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet result = request.executeQuery("SELECT * FROM " + EnseignantDAO.TABLE + " WHERE NO_ENSEIGNANT = " + id);

				if(result.first()){
				
					String date = result.getString("DATE_NAISSANCE_ENSEIGNANT");
					GregorianCalendar gcDate = ConvertirDate(date);
					
					obj = new Enseignant(id,(result.getString("NOM_ENSEIGNANT")),result.getString("PRENOM_ENSEIGNANT"),result.getString("ADRESSE_ENSEIGNANT"),result.getString("TELEPHONE_ENSEIGNANT"),gcDate,result.getString("LOGIN_ENSEIGNANT"),result.getString("PWD_ENSEIGNANT"));
					
					TypePosteDAO typePosteDAO = new TypePosteDAO();
					obj.setMonPoste(typePosteDAO.find(result.getInt("NO_POSTE")));
					
					this.loadMesVoeuxEC(obj);
					
					IndisponibiliteDAO indispoDAO = new IndisponibiliteDAO();
					indispoDAO.loadMesIndisponibilites(obj);
					
					this.loadMesCreneaux(obj);
					
					this.loadMesServices(obj);
					
					this.loadMesExamens(obj);					
					
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
		public Enseignant create(Enseignant obj) {
				if(obj.getMonPoste() == null){
					return null;
				}
				else{
					try {
						
						TypePosteDAO typePosteDAO = new TypePosteDAO();
						
						if(typePosteDAO.find(obj.getMonPoste().getNumeroPoste()) == null){
							typePosteDAO.create(obj.getMonPoste());
						}
						
						// cast du gregorianCalendar en String 
						SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
    					String date = dateF.format(obj.getDateNaissance().getTime());   
						
						Statement request = this.connect.createStatement();
						request.executeUpdate("INSERT INTO "+ EnseignantDAO.TABLE+" (NO_ENSEIGNANT,NO_POSTE,NOM_ENSEIGNANT,PRENOM_ENSEIGNANT,ADRESSE_ENSEIGNANT,TELEPHONE_ENSEIGNANT, DATE_NAISSANCE_ENSEIGNANT, LOGIN_ENSEIGNANT, PWD_ENSEIGNANT) " +
								"VALUES (SEQ_ENSEIGNANT.NEXTVAL,"+obj.getMonPoste().getNumeroPoste()+",'"+ obj.getNom()+"','"+ obj.getPrenom()+"','"+ obj.getAdresse()+"','"+obj.getTelephone()+"','"+date+"','"+obj.getLogin()+"','"+obj.getPassword()+"')");
					
						request.close();
						
						// creation des voeux ec de l'enseignant
						if(!obj.getMesVoeuxEC().isEmpty()){
							for(int i=0;i<obj.getMesVoeuxEC().size();i++){				
								
								VoeuxECDAO voeuxEcDao = new VoeuxECDAO();
								
								int numFormation = obj.getMesVoeuxEC().get(i).getMonEC().getMonUE().getMaFormation().getNumeroFormation();
								int numUE = obj.getMesVoeuxEC().get(i).getMonEC().getMonUE().getNumeroUE();
								int numEC = obj.getMesVoeuxEC().get(i).getMonEC().getNumeroEC();
								 								
								if(voeuxEcDao.find(numFormation, numUE, numEC, obj.getNumeroEnseignant()) == null){
									voeuxEcDao.create(obj.getMesVoeuxEC().get(i));
								}
								else{
									//on met a jour
								}
							}
						}
						
						// creation des indisponibilite de l'enseignant
						if(!obj.getMesIndisponibilites().isEmpty()){
							for(int i=0;i<obj.getMesIndisponibilites().size();i++){				
								
								IndisponibiliteDAO indispoDao = new IndisponibiliteDAO();
								
								Jours jIndispo = obj.getMesIndisponibilites().get(i).getDateIndisponibilite();
								Date d = jIndispo.getDateDuJour().getTime(); 
								 								
								if(indispoDao.find(d, obj.getNumeroEnseignant()) == null){
									indispoDao.create(obj.getMesIndisponibilites().get(i));
								}
								else{
									//on met a jour
								}
							}
						}
						
						// creation des creneaux de l'enseignant
						if(!obj.getMesCreneaux().isEmpty()){
							for(int i=0;i<obj.getMesCreneaux().size();i++){				
								
								CreneauDAO creneauDAO = new CreneauDAO();
								
								String numSalle = obj.getMesCreneaux().get(i).getMaSalle().getNumeroSalle();
								int numEC = obj.getMesCreneaux().get(i).getMonEC().getNumeroEC();
								int numUE = obj.getMesCreneaux().get(i).getMonEC().getMonUE().getNumeroUE();
								int numFormation = obj.getMesCreneaux().get(i).getMonEC().getMonUE().getMaFormation().getNumeroFormation();
								int numType = obj.getMesCreneaux().get(i).getMonType().getNumeroType();
								GregorianCalendar gcDate = obj.getMesCreneaux().get(i).getDateCreneau().getDateDuJour();
																 								
								if(creneauDAO.find(obj.getNumeroEnseignant(), numSalle, numEC, numUE, numFormation, numType, gcDate) == null){
									creneauDAO.create(obj.getMesCreneaux().get(i));
								}
								else{
									//on met a jour
								}
							}
						}
						
						// creation des services de l'enseignant
						if(!obj.getMesServices().isEmpty()){
							for(int i=0;i<obj.getMesServices().size();i++){				
								
								ServiceDAO serviceDao = new ServiceDAO();
								
								int numType = obj.getMesServices().get(i).getMonType().getNumeroType();
								int numEC = obj.getMesServices().get(i).getMonEC().getNumeroEC();	
								int numFormation =  obj.getMesServices().get(i).getMonEC().getMonUE().getMaFormation().getNumeroFormation();
								int numUE = obj.getMesServices().get(i).getMonEC().getMonUE().getNumeroUE();
								
																 								
								if(serviceDao.find(obj.getNumeroEnseignant(), numType, numFormation, numUE, numEC) == null){
									serviceDao.create(obj.getMesServices().get(i));
								}
								else{
									//on met a jour
								}
							}
						}
						
						// creation des examens de l'enseignant
						if(!obj.getMesExamens().isEmpty()){
							for(int i=0;i<obj.getMesExamens().size();i++){				
								
								ExamenDAO examDao = new ExamenDAO();
																 								
								if(examDao.find(obj.getMesExamens().get(i).getNumeroExamen()) == null){
									examDao.create(obj.getMesExamens().get(i));
								}
								else{
									//on met a jour
								}
							}
						}
						
					
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
			int nbLignesModifies = 0;
			
			try {
								
				// cast du gregorianCalendar en String 
				SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
				String date = dateF.format(obj.getDateNaissance().getTime());   
				
				Statement request = this.connect.createStatement();
				nbLignesModifies = request.executeUpdate("UPDATE "+ EnseignantDAO.TABLE +
						" SET NO_POSTE ="+ obj.getMonPoste().getNumeroPoste()+", NOM_ENSEIGNANT = '"+obj.getNom()+"',PRENOM_ENSEIGNANT = '"+obj.getPrenom()+"'," +
						"ADRESSE_ENSEIGNANT ='"+obj.getAdresse()+"',TELEPHONE_ENSEIGNANT='"+obj.getTelephone()+"', DATE_NAISSANCE_ENSEIGNANT='"+date+"', LOGIN_ENSEIGNANT='"+obj.getLogin()+"', PWD_ENSEIGNANT = '"+obj.getPassword()+"'" +
						" WHERE NO_ENSEIGNANT ="+id);
				request.close();
				
				// mettre a jour les listes ??
				
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(nbLignesModifies>0)
				return this.find(obj.getNumeroEnseignant());
			else
				return null;
			

		}

		@Override
		public void delete(Enseignant obj) {
				try {
					Statement request = this.connect.createStatement();
					request.executeUpdate("DELETE FROM " + EnseignantDAO.TABLE +" WHERE NO_ENSEIGNANT = " +obj.getNumeroEnseignant());
					request.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		public void loadMesVoeuxEC(Enseignant obj) {
			int id = obj.getNumeroEnseignant();
			
			try {
				Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet result = request.executeQuery("SELECT * FROM " + VoeuxECDAO.TABLE + " WHERE NO_ENSEIGNANT = " + id);

				while(result.next()){
					
					// creation de la formation 
					Formation maFormation = new Formation(result.getInt("NO_FORMATION"));
					
					// creation de l'ue
					UE monUE = new UE(result.getInt("NO_UE"), maFormation);
					
					// creation de l'ec
					EC monEC = new EC(result.getInt("NO_EC"), monUE);
					
					boolean choix = DAO.booleanFromOracleToJava(result.getInt("CHOIX"));
					
					// creation du voeux
					VoeuxEC voeux = new VoeuxEC(monEC, obj, choix);
					
					obj.getMesVoeuxEC().add(voeux);
				}
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		}
		
		public void loadMesServices(Enseignant obj) {
			int id = obj.getNumeroEnseignant();
			
			try {
				Statement request = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet result = request.executeQuery("SELECT * FROM " + ServiceDAO.TABLE + " WHERE NO_ENSEIGNANT = " + id);

				while(result.next()){
					
					// creation de la formation 
					Formation maFormation = new Formation(result.getInt("NO_FORMATION"));
					
					// creation de l'ue
					UE monUE = new UE(result.getInt("NO_UE"), maFormation);
					
					// creation de l'ec
					EC monEC = new EC(result.getInt("NO_EC"), monUE);
					
					// creation du type
					Type monType = new Type(result.getInt("NO_TYPE"));
					
					// conversion des minutes en heure
					int nbHeures = result.getInt("NB_MINUTES_SERVICE") / 60;
					
					// creation du service
					Service monService = new Service(monEC, obj, monType, nbHeures);
										
					obj.getMesServices().add(monService);
				}
				
				request.close();
				
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		}
		
		public List<Examen> loadMesExamens(Enseignant obj){
			List<Examen> listExam = new ArrayList<Examen>();
			try {
				Statement request = this.connect.createStatement();
				ECDAO ecdao = new ECDAO();
				EnseignantDAO ensdao = new EnseignantDAO();
				JoursDAO datedao = new JoursDAO();
				TypeDAO typedao = new TypeDAO();

				ResultSet result = request.executeQuery("SELECT FROM "
						+ ExamenDAO.TABLE + " WHERE NO_ENSEIGNANT = " + obj.getNumeroEnseignant() + ")");
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



		@Override
		public List<Enseignant> findAll() {
			// TODO Auto-generated method stub
			return null;
		}
	}
