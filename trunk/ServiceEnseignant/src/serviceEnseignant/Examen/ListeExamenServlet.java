package serviceEnseignant.Examen;

import java.io.IOException;
import dao.*;
import java.io.PrintWriter;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.*;
/**
 * Servlet implementation class ExamenServlet
 */
@WebServlet("/ListeExamenServlet")

public class ListeExamenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private dao.EnseignantDAO ensDAO = new dao.EnseignantDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeExamenServlet() {
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
	
		PrintWriter out = response.getWriter(); 
		String formationChoix = request.getParameter("formationChoix");
		String UEChoix = request.getParameter("UEChoix");
		
		// recuperation des informations de l'enseignant			
		//ensDAO.recupererInfos(ensDAO.getEns().getNumeroEnseignant());
		Enseignant beanEns = ensDAO.getEns();		
		
		
		// creation d'une session pour stocker le bean enseignant
		HttpSession session = request.getSession(true);    
		session.setAttribute("ens", beanEns);
				
		
			
			request.setAttribute("ensBeans", beanEns);

			RequestDispatcher disp=	getServletContext().getRequestDispatcher("/jsp/jspExamen/ListeExamen.jsp");
			disp.forward(request, response);

	}

}
