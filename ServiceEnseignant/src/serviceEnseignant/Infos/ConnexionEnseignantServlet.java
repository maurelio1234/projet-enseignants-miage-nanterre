package serviceEnseignant.Infos;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import serviceEnseignant.*;
import serviceEnseignant.DAO.EnseignantDAO;
import beans.Enseignant;
import beans.UE;

/**
 * Servlet implementation class ConnexionEnseignantServlet
 */
@WebServlet("/ConnexionEnseignantServlet")
public class ConnexionEnseignantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private EnseignantDAO ensDAO = new EnseignantDAO();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnexionEnseignantServlet() {
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
		
		Enseignant beanEns;
		
		//System.out.println("je suis dans la servlet connexion");
		
		//recuperation des valeurs saisies
		String login = request.getParameter("login");
		String mdp = request.getParameter("mdp");
		
		RequestDispatcher disp;
		
		// on verifie que le login et le mdp saisis sont corrects
		if((beanEns = ensDAO.find(login, mdp))!=null){
		
			//System.out.println("login mdp ok");
						
			//System.out.println("infos beans ens : "+beanEns.getLogin()+ " " + beanEns.getPassword()+" "+beanEns.getDateNaissance());
			
			ensDAO.loadAll(beanEns);
			
			// creation d'une session pour stocker le bean enseignant
			HttpSession session = request.getSession(true);    
			session.setAttribute("ens", beanEns);
			
			//System.out.println("envoi bean a la jsp");
						
			disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/accueilEnseignant.jsp");
		}
		else{
			
			//System.out.println("login mdp non ok");
			
			String msg = "Le login ou le mot de passe est incorrect. Veuillez réessayer à nouveau.";
			request.setAttribute("msg", msg);
			
			disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/ConnexionEnseignant.jsp");
		}
		
		disp.forward(request, response);
		
	}

}
