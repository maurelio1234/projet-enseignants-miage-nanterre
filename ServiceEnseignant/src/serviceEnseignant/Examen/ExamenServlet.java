package serviceEnseignant.Examen;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import serviceEnseignant.DAO.EnseignantDAO;
import serviceEnseignant.DAO.ExamenDAO;
import serviceEnseignant.DAO.NoteDAO;
import beans.*;

/**
 * Servlet implementation class ExamenServlet
 */
@WebServlet("/ExamenServlet")
public class ExamenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Examen exam;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExamenServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//Choix de l'initialisation via la BDD ou via la méthode "en dur".
		boolean SimpleInit = false;
		
		Examen exam;
		ExamenDAO examDAO = new ExamenDAO();

		if (SimpleInit) {
			exam = ExamenServlet.InitTest();
		} else {
			int num_examen = 1;
			// A initialiser via un num_Examen récupéré.
			exam = examDAO.find(num_examen);
			exam.setMesNotes(examDAO.LoadNote(exam));
		}
		request.setAttribute("ExamBeans", exam);
		RequestDispatcher disp = getServletContext().getRequestDispatcher(
				"/jsp/jspExamen/ConsulterExamen.jsp");
		disp.forward(request, response);

	}

	private static Examen InitTest() {
		System.out.println("Création des candidats");
		Candidat c1 = new Candidat(1, "Letellier", "Matthieu", "osef", "Osef",
				null, "maletell", "matthieu", null, true, "osef");
		Candidat c2 = new Candidat(2, "Bonneau", "Julie", "osef", "Osef", null,
				"osef", "osef", null, true, "osef");
		Candidat c3 = new Candidat(3, "Vignau", "Morgane", "osef", "Osef",
				null, "osef", "osef", null, true, "osef");
		Candidat c4 = new Candidat(4, "Levoir", "Adelaide", "osef", "Osef",
				null, "osef", "osef", null, true, "osef");
		Candidat c5 = new Candidat(5, "Wong", "Emilie", "osef", "Osef", null,
				"osef", "osef", null, true, "osef");
		Candidat c6 = new Candidat(6, "Ravelo", "Heni", "osef", "Osef", null,
				"osef", "osef", null, true, "osef");

		System.out.println("\nCréation des Etudiants.");
		Etudiant e1 = new Etudiant(1);
		e1.setMonCandidat(c1);
		Etudiant e2 = new Etudiant(2);
		e2.setMonCandidat(c2);
		Etudiant e3 = new Etudiant(3);
		e3.setMonCandidat(c3);
		Etudiant e4 = new Etudiant(4);
		e4.setMonCandidat(c4);
		Etudiant e5 = new Etudiant(5);
		e5.setMonCandidat(c5);
		Etudiant e6 = new Etudiant(6);
		e6.setMonCandidat(c6);

		System.out.println("\nCréation de l'examen");
		exam = new Examen(1, "10:30", "Test", 1);
		System.out.println("\nCréation des notes");
		Note n1 = new Note(e1, exam, -2);
		Note n2 = new Note(e2, exam, 10);
		Note n3 = new Note(e3, exam, 20);
		Note n4 = new Note(e4, exam, 0);
		Note n5 = new Note(e5, exam, -1);
		Note n6 = new Note(e6, exam, -3);

		for (int i = 0; i < exam.getMesNotes().size(); i++) {
			System.out.println(exam.getMesNotes().get(i).getMonEtudiant()
					.getMonCandidat().getNom()
					+ " "
					+ exam.getMesNotes().get(i).getMonEtudiant()
							.getMonCandidat().getPrenom()
					+ " "
					+ exam.getMesNotes().get(i).getNote());
		}
		System.out.println("\nFin de l'initialisation.");
		return exam;
	}

}
