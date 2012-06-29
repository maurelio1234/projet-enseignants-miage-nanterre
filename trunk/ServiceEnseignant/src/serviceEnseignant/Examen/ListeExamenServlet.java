package serviceEnseignant.Examen;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.*;
import beans.*;
/**
 * Servlet implementation class ExamenServlet
 */
@WebServlet("/ListeExamenServlet")

public class ListeExamenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EnseignantDAO ensDAO = new EnseignantDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeExamenServlet() {
        super();
    }

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// recuperation des informations de l'enseignant			

		Enseignant beanEns = ensDAO.find(2);
		ensDAO.loadAll(beanEns);
		System.out.println(beanEns.getNom());
		
		
		// creation d'une session pour stocker le bean enseignant
		HttpSession session = request.getSession(true);    
		session.setAttribute("ens", beanEns);
				
		
			
			request.setAttribute("ensBeans", beanEns);

			RequestDispatcher disp=	getServletContext().getRequestDispatcher("/jsp/jspExamen/ListeExamen.jsp");
			disp.forward(request, response);

	}

}
