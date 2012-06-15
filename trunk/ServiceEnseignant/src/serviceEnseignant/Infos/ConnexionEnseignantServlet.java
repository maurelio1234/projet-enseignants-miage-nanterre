package serviceEnseignant.Infos;


import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import serviceEnseignant.*;
import beans.Enseignant;

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
		// TODO Auto-generated method stub
				
		//recuperation des valeurs saisies
		String login = request.getParameter("login");
		String mdp = request.getParameter("mdp");
		
		RequestDispatcher disp;
		
		// on verifie que le login et le mdp saisis sont corrects
		//if(ensDAO.verifierLoginMdp(login, mdp)){
		if(login.equalsIgnoreCase("emilie")){	
			
			// recuperation des informations de l'enseignant			
			//ensDAO.recupererInfos(ensDAO.getEns().getNumeroEnseignant());
			//Enseignant beanEns = ensDAO.getEns();		
			
			Enseignant beanEns = new Enseignant(1, "wong", "emilie", "2 rue des Lilas Paris", "0645896369", new GregorianCalendar(1970, Calendar.JANUARY, 12), "emilie", "emilie");
			
			request.setAttribute("ens", beanEns); // on passe le bean enseignant à la jsp
			
			disp = this.getServletContext().getRequestDispatcher("/accueilEnseignant.jsp");
		}
		else{
			disp = this.getServletContext().getRequestDispatcher("/erreurConnexion.jsp");
		}
		
		disp.forward(request, response);
		
	}

}
