package serviceEnseignant.Examen;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import serviceEnseignant.DAO.ExamenDAO;
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
		
		Examen exam = null;
		ExamenDAO examDAO = new ExamenDAO();

		if (SimpleInit) {
			
		} else {
			int num_examen = Integer.parseInt(request.getParameter("num_exam"));
			// A initialiser via un num_Examen récupéré.
			exam = examDAO.find(num_examen);
			System.out.println("Examen " + exam.getLibelle() + " chargé.");
			exam.setMesNotes(examDAO.LoadNote(exam));
			
		}
		request.setAttribute("ExamBeans", exam);
		RequestDispatcher disp = getServletContext().getRequestDispatcher("/jsp/jspExamen/ConsulterExamen.jsp");
		disp.forward(request, response);

	}

}
