package serviceEnseignant.Examen;

import java.io.IOException;
import java.util.GregorianCalendar;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import serviceEnseignant.DAO.EtudiantDAO;
import serviceEnseignant.DAO.ExamenDAO;
import serviceEnseignant.DAO.NoteDAO;

import beans.Etudiant;
import beans.Examen;
import beans.Jours;
import beans.Note;


/**
 * Servlet implementation class UpdateExamenServlet
 */
@WebServlet("/UpdateExamenServlet")
public class UpdateExamenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateExamenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public GregorianCalendar ConvertirDate(String date){
		String delims = "[/]";
		String[] tokens = date.split(delims);
		GregorianCalendar gDate = null;
		if (tokens.length == 3) {
			int jour = Integer.parseInt(tokens[0]);
			int mois = Integer.parseInt(tokens[1]) -1;
			int annee = Integer.parseInt(tokens[2]);
			try {
				gDate = new GregorianCalendar(annee, mois, jour);
				gDate.setLenient(false);
			} catch (Exception e) {
				System.out.println("Le Format de la date n'est pas valide");
				return null;
			}
		}
		return gDate;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TO_DO : dans ConsulterExamen. Comment gérer le -1 ? (non visible à la saisie, mais doit pouvoir être récupéré ...)
		//Mise à jour des informations de l'examen : Libelle, Date, Horaire, Coefficent.
		ExamenDAO examDAO = new ExamenDAO();
		EtudiantDAO etudiantDAO;
		etudiantDAO = new EtudiantDAO();
		NoteDAO noteDAO = new NoteDAO();
			
		Integer num_exam = Integer.parseInt(request.getParameter("num_exam"));
		String libelle = request.getParameter("libelle");
		String date = request.getParameter("date");
		String horaire = request.getParameter("heure");
		Double coeff = Double.parseDouble(request.getParameter("coeff"));
		
		Examen exam = examDAO.find(num_exam);
		exam.setCoefficient(coeff);
		exam.setDate(new Jours(ConvertirDate(date)));
		exam.setHoraire(horaire);
		exam.setLibelle(libelle);
		
		examDAO.update(exam);
		
		// Mise à jour des notes
		Etudiant etudiant;
		Note note=null;
		exam.getMesNotes().clear();
		int nb_notes = Integer.parseInt(request.getParameter("nb_notes"));
		for (int i = 0; i < nb_notes; i++){
			etudiant = etudiantDAO.find(Integer.parseInt(request.getParameter("RefEtudiant"+i)));
			System.out.println("n° de l'étudiant recupere : " + etudiant.getNumeroEtudiant());
			switch(Integer.parseInt(request.getParameter("choix"+i))){
			case 1 : note = new Note(etudiant, exam, Double.parseDouble(request.getParameter("Note"+i))); break;
			case 2 : note = new Note(etudiant, exam, -2); break;
			case 3 : note = new Note(etudiant, exam, -3); break;
			}		
			noteDAO.update(note);
		}

		// Renvoi vers la page d'affichage des notes.
		String msg = "L'examen a été mis à jour. Les notes ont été sauvegardées.";
		request.setAttribute("msg", msg);
		RequestDispatcher disp=	getServletContext().getRequestDispatcher("/ExamenServlet");
		disp.forward(request, response);
	}

}
