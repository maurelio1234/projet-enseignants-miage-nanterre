package serviceEnseignant.Infos;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Enseignant;

/**
 * Servlet implementation class ModifierMDPEnseignantServlet
 */
@WebServlet("/ModifierMDPEnseignantServlet")
public class ModifierMDPEnseignantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierMDPEnseignantServlet() {
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
		
		int numEns = Integer.parseInt(request.getParameter("numEns"));
		String ancienMDP = request.getParameter("ancienMDP");
		String nouveauMDP1 = request.getParameter("nouveauMDP1");
		String nouveauMDP2 = request.getParameter("nouveauMDP2");
		
		RequestDispatcher disp;
		
		EnseignantDAO ensDAO =  new EnseignantDAO();
		
		// enregistrement des données dans la base
		
		
		Enseignant beanEns = ensDAO.getEns(); //beanEnseignant qui sera envoye a la JSP PageInfoPerso.jsp
		
		request.setAttribute("ens", beanEns); // on passe le bean enseignant à la jsp
		
		disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/accueilEnseignant.jsp");

		disp.forward(request, response);
		
		//ajouter un message de succès de la modification
		
	}

}
