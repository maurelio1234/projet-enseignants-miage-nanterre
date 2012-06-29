package serviceEnseignant.Examen;

import beans.Examen;
import beans.Note;

import dao.*;

public class TestMatthieu {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
/*
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
		*/
		ExamenDAO examdao = new ExamenDAO();
		Examen exam = examdao.find(1);
		exam.setMesNotes(examdao.LoadNote(exam));
		for(Note n : exam.getMesNotes()){
			System.out.println(n.getMonEtudiant().getNumeroEtudiant() + " " + n.getNote());
		}
	}

}
