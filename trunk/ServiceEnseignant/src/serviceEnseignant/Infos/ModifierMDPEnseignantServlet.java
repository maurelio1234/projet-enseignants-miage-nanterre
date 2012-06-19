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
		
		// recupere les infos de l'enseignant
		ensDAO.recupererInfos(numEns);
		Enseignant beanEns = ensDAO.getEns();
		
		String erreur; 
		
		// verifier l'ancien mdp
		if (!ensDAO.verifAncienMDP(ancienMDP)){
			erreur = "L'ancien mot de passe n'est pas valide.";
			request.setAttribute("erreur", erreur);
			request.setAttribute("ens", beanEns); // on passe le bean enseignant à la jsp
			
			disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/modifMDPenseignant.jsp");
		}
		else{
			// verifier si les nouveaux mdp correspondent
			
			// si les nouveaux mdp ne sont pas identiques, on redirige vers la page avec une erreur
			if(!nouveauMDP1.equals(nouveauMDP2)){
				erreur = "La confirmation du nouveau mot de passe est incorrecte.";
				request.setAttribute("erreur", erreur);
				request.setAttribute("ens", beanEns); // on passe le bean enseignant à la jsp
				
				disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/modifMDPenseignant.jsp");
			}
			// sinon, on procede a la mise a jour dans la base de donnees
			else{
				
				String msg;
				
				if (ensDAO.enregistrerMDP(ancienMDP, nouveauMDP1)){
					
					beanEns = ensDAO.getEns();
					
					msg = "Le mot de passe a été modifié avec succès.";
					request.setAttribute("msg", msg);
					
					request.setAttribute("ens", beanEns); // on passe le bean enseignant à la jsp
					
					disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/accueilEnseignant.jsp");	
				}				
				else{
					msg = "Une erreur est survenue lors de la mise à jour du mot de passe.";
					request.setAttribute("msg", msg);
					request.setAttribute("ens", beanEns); // on passe le bean enseignant à la jsp
					
					disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/accueilEnseignant.jsp");
					
				}
								
			}
		}

		disp.forward(request, response);
		
	}

}
