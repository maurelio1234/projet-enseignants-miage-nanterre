package serviceEnseignant.DAO;

import beans.Candidat;
import beans.ContratApprentissage;
import beans.ContratQuadriennal;
import beans.ConventionStage;
import beans.Creneau;
import beans.EC;
import beans.Enseignant;
import beans.Entreprise;
import beans.EtatDossier;
import beans.Etudiant;
import beans.Examen;
import beans.Formation;
import beans.Indisponibilite;
import beans.Jours;
import beans.Necessite;
import beans.Note;
import beans.OffreDeStage;
import beans.Periode;
import beans.Postule;
import beans.Promotion;
import beans.Promotion_Historisee;
import beans.Reunion;
import beans.Salle;
import beans.Semestre;
import beans.Service;
import beans.Type;
import beans.TypePoste;
import beans.UE;
import beans.Utilisateur;
import beans.VoeuxEC;

public abstract class AbstractDAOFactory {
	public abstract DAO<Candidat> getCandidatDAO();
	public abstract DAO<ContratApprentissage> getContratApprentissageDAO();
	public abstract DAO<ContratQuadriennal> getContratQuadriennalDAO();
	public abstract DAO<ConventionStage> getConventionStageDAO();
	public abstract DAO<Creneau> getCreneauDAO();
	public abstract DAO<EC> getECDAO();
	public abstract DAO<Enseignant> getEnseignantDAO();
	public abstract DAO<Entreprise> getEntrepriseDAO();
	public abstract DAO<EtatDossier> getEtatDossierDAO();
	public abstract DAO<Etudiant> getEtudiantDAO();
	public abstract DAO<Examen> getExamenDAO();
	public abstract DAO<Formation> getFormationDAO();
	public abstract DAO<Indisponibilite> getIndisponibiliteDAO();
	public abstract DAO<Jours> getJoursDAO();
	public abstract DAO<Necessite> getNecessiteDAO();
	public abstract DAO<Note> getNoteDAO();
	public abstract DAO<OffreDeStage> getOffreDeStageDAO();
	public abstract DAO<Periode> getPeriodeDAO();
	public abstract DAO<Postule> getPostuleDAO();
	public abstract DAO<Promotion_Historisee> getPromotion_HistoriseeDAO();
	public abstract DAO<Promotion> getPromotionDAO();
	public abstract DAO<Reunion> getReunionDAO();
	public abstract DAO<Salle> getSalleDAO();
	public abstract DAO<Semestre> getSemestreDAO();
	public abstract DAO<Service> getServiceDAO();
	public abstract DAO<Type> getTypeDAO();
	public abstract DAO<TypePoste> getTypePosteDAO();
	public abstract DAO<UE> getUEDAO();
	public abstract DAO<Utilisateur> getUtilisateurDAO();
	public abstract DAO<VoeuxEC> getVoeuxECDAO();
	

	/**
	 * Méthode nous permettant de récupérer une factory de DAO
	 * @param type
	 * @return AbstractDAOFactory
	 */
	public static AbstractDAOFactory getFactory(FactoryType type){
		
		if(type.equals(FactoryType.DAO_FACTORY)) 
			return new DAOFactory();
		
		return null;
	}
	

}
