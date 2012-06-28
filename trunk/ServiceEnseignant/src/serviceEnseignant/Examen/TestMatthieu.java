package serviceEnseignant.Examen;
import serviceEnseignant.DAO.EnseignantDAO;
import beans.Enseignant;
import beans.Formation;
import dao.FormationDAO;


public class TestMatthieu {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FormationDAO formDAO = new FormationDAO();
		Formation form = formDAO.find(2);
		System.out.println(form.getLibelle());
		
		EnseignantDAO ensDAO = new EnseignantDAO();
		Enseignant ens = ensDAO.find(1);
		ensDAO.loadAll(ens);
		System.out.println(ens.getNom());
		System.out.println(ens.getPrenom());
		System.out.println(ens.getMesExamens().get(0).getLibelle());
		
		System.out.println(ens.getMesServices().get(0).getMonEC().getLibelle());
	}

}
