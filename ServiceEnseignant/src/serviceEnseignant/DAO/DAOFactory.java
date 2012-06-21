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

public class DAOFactory extends AbstractDAOFactory {


	@Override
	public DAO<Candidat> getCandidatDAO() {		
		return new CandidatureDAO();
	}
	
	@Override
	public DAO<ContratApprentissage> getContratApprentissageDAO() {
		return new ContratApprentissageDAO();
	}
	
	@Override
	public DAO<ContratQuadriennal> getContratQuadriennalDAO() {
		return new ContratQuadriennalDAO();
	}
	
	@Override
	public DAO<ConventionStage> getConventionStageDAO() {
		return new ConventionStageDAO();
	}
	
	@Override
	public DAO<Creneau> getCreneauDAO() {
		return new CreneauDAO();
	}
	
	@Override
	public DAO<EC> getECDAO() {
		return new ECDAO();
	}
	
	@Override
	public DAO<Enseignant> getEnseignantDAO() {
		return new EnseignantDAO();
	}
	
	@Override
	public DAO<Entreprise> getEntrepriseDAO() {
		return new EntrepriseDAO();
	}
	
	@Override
	public DAO<EtatDossier> getEtatDossierDAO() {
		return new EtatDossierDAO();
	}
	
	@Override
	public DAO<Etudiant> getEtudiantDAO() {
		return new EtudiantDAO();
	}
	
	@Override
	public DAO<Examen> getExamenDAO() {
		return new ExamenDAO();
	}
	
	@Override
	public DAO<Formation> getFormationDAO() {
		return new FormationDAO();
	}
	
	@Override
	public DAO<Indisponibilite> getIndisponibiliteDAO() {
		return new IndisponibiliteDAO();
	}
	
	@Override
	public DAO<Jours> getJoursDAO() {
		return new JoursDAO();
	}
	
	@Override
	public DAO<Necessite> getNecessiteDAO() {
		return new NecessiteDAO();
	}
	
	@Override
	public DAO<Note> getNoteDAO() {
		return new NoteDAO();
	}
	
	@Override
	public DAO<OffreDeStage> getOffreDeStageDAO() {
		return new OffreDeStageDAO();
	}
	
	@Override
	public DAO<Periode> getPeriodeDAO() {
		return new PeriodeDAO();
	}
	
	@Override
	public DAO<Postule> getPostuleDAO() {
		return new PostuleDAO();
	}
	
	@Override
	public DAO<Promotion_Historisee> getPromotion_HistoriseeDAO() {
		return new Promotion_HistoriseeDAO();
	}
	
	@Override
	public DAO<Promotion> getPromotionDAO() {
		return new PromotionDAO();
	}
	
	@Override
	public DAO<Reunion> getReunionDAO() {
		return new ReunionDAO();
	}
	
	@Override
	public DAO<Salle> getSalleDAO() {
		return new SalleDAO();
	}
	
	@Override
	public DAO<Semestre> getSemestreDAO() {
		return new SemestreDAO();
	}
	
	@Override
	public DAO<Service> getServiceDAO() {
		return new ServiceDAO();
	}
	
	@Override
	public DAO<Type> getTypeDAO() {
		return new TypeDAO();
	}
	
	@Override
	public DAO<TypePoste> getTypePosteDAO() {
		return new TypePosteDAO();
	}
	
	@Override
	public DAO<UE> getUEDAO() {
		return new UEDAO();
	}
	
	@Override
	public DAO<Utilisateur> getUtilisateurDAO() {
		return new UtilisateurDAO();
	}
	
	@Override
	public DAO<VoeuxEC> getVoeuxECDAO() {
		return new VoeuxECDAO();
	}
}
