package serviceEnseignant.Examen;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.*;
/**
 * Servlet implementation class ExamenServlet
 */
@WebServlet("/ExamenServlet")
public class ExamenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExamenServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		System.out.println("Création des candidats");
		Candidat c1 = new Candidat(1, "Letellier", "Matthieu", "osef", "Osef", null, "maletell", "matthieu", null, true, "osef");
		Candidat c2 = new Candidat(2, "Bonneau", "Julie", "osef", "Osef", null, "osef", "osef", null, true, "osef");
		Candidat c3 = new Candidat(3, "Vignau", "Morgane", "osef", "Osef", null, "osef", "osef", null, true, "osef");
		Candidat c4 = new Candidat(4, "Levoir", "Adelaide", "osef", "Osef", null, "osef", "osef", null, true, "osef");
		Candidat c5 = new Candidat(5, "Wong", "Emilie", "osef", "Osef", null, "osef", "osef", null, true, "osef");
		Candidat c6 = new Candidat(6, "Ravelo", "Heni", "osef", "Osef", null, "osef", "osef", null, true, "osef");
		
		System.out.println("\nCréation des Etudiants.");
		Etudiant e1 = new Etudiant(1);
		e1.setMonCandidat(c1);
		Etudiant e2 = new Etudiant(2);
		e1.setMonCandidat(c2);
		Etudiant e3 = new Etudiant(3);
		e1.setMonCandidat(c3);
		Etudiant e4 = new Etudiant(4);
		e1.setMonCandidat(c4);
		Etudiant e5 = new Etudiant(5);
		e1.setMonCandidat(c5);
		Etudiant e6 = new Etudiant(6);
		e1.setMonCandidat(c6);
		
		System.out.println("\nCréation de l'examen");
		Examen exam = new Examen(1,new GregorianCalendar(2012,GregorianCalendar.JUNE, 12), "Examen Test", 1);
		System.out.println("\nCréation des notes");
		Note n1 = new Note(e1,exam,0);
		exam.getMesNotes().add(n1);
		e1.getMesNotes().add(n1);
		Note n2 = new Note(e2,exam,10);
		exam.getMesNotes().add(n2);
		e2.getMesNotes().add(n2);
		Note n3 = new Note(e3,exam,20);
		exam.getMesNotes().add(n3);
		e3.getMesNotes().add(n3);
		Note n4 = new Note(e4,exam,0);
		exam.getMesNotes().add(n4);
		e4.getMesNotes().add(n4);
		Note n5 = new Note(e5,exam,10);
		exam.getMesNotes().add(n5);
		e5.getMesNotes().add(n5);
		Note n6 = new Note(e6,exam,0);
		exam.getMesNotes().add(n6);
		e6.getMesNotes().add(n6);
		System.out.println("\nFin de l'initialisation.");
			request.setAttribute("ExamBeans", exam);
			RequestDispatcher disp=	getServletContext().getRequestDispatcher("/ConsulterExamen.jsp");
			disp.forward(request, response);

	}

}
