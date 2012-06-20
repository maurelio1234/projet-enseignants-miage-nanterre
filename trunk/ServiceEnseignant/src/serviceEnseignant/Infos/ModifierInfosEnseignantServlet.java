package serviceEnseignant.Infos;





import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Enseignant;

/**
 * Servlet implementation class InfosEnseignantServlet
 */
@WebServlet("/ModifierInfosEnseignantServlet")
public class ModifierInfosEnseignantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierInfosEnseignantServlet() {
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
		
		System.out.println("dans le controleur modifier infos ens");
		
		Enseignant beanEns;
		
		// recuperation de la session pour avoir le bean enseignant et avoir son numero enseignant
		HttpSession session = request.getSession(true);   
		beanEns = (Enseignant) session.getAttribute("ens");
		
		EnseignantDAO ensDAO =  new EnseignantDAO();
		ensDAO.setEns(beanEns);	
	
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String dateNaiss = request.getParameter("dateNaiss");
		String adresse = request.getParameter("adresse");
		String telephone = request.getParameter("telephone");
		
		RequestDispatcher disp;	
		
		System.out.println(nom+" "+prenom+" "+adresse+" "+dateNaiss);
		
		// enregistrement des données dans la base
		ensDAO.enregistrerInfos(nom, prenom, adresse, telephone, dateNaiss);
		
		beanEns = ensDAO.getEns(); //beanEnseignant qui sera envoye a la JSP PageInfoPerso.jsp
		
		session.setAttribute("ens", beanEns); // associe le bean mis a jour a la session "ens"
		
		disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/accueilEnseignant.jsp");

		disp.forward(request, response);
		
		//ajouter un message de succès de la modification
	}

}
