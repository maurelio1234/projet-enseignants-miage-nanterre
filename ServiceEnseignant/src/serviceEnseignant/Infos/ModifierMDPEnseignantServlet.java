package serviceEnseignant.Infos;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import serviceEnseignant.DAO.EnseignantDAO;

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
		
		Enseignant beanEns;
		
		// recuperation de la session pour avoir le bean enseignant et avoir son numero enseignant
		HttpSession session = request.getSession(true);    
		beanEns = ((Enseignant) session.getAttribute("ens"));
				
		String ancienMDP = request.getParameter("ancienMDP");
		String nouveauMDP1 = request.getParameter("nouveauMDP1");
		String nouveauMDP2 = request.getParameter("nouveauMDP2");
		
		//System.out.println("ancien mdp : "+ancienMDP);
		//System.out.println("nouveau mdp 1 : "+nouveauMDP1);
		//System.out.println("nouveau mdp 2 : "+nouveauMDP2);
		
		RequestDispatcher disp;
		
		EnseignantDAO ensDAO =  new EnseignantDAO();
		ensDAO.setEns(beanEns); // on initialise l'enseignant associé au DAO 
		
		// recupere les infos de l'enseignant
		//ensDAO.recupererInfos(beanEns.getNumeroEnseignant());
		beanEns = ensDAO.find(beanEns.getNumeroEnseignant());
		
		String erreur; 
		
		// verifier l'ancien mdp
		if (!ensDAO.verifAncienMDP(ancienMDP)){
			
			//System.out.println("l'ancien mdp n'est pas bon");
			
			erreur = "L'ancien mot de passe n'est pas valide.";
			request.setAttribute("erreur", erreur);
			
			disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/modifMDPenseignant.jsp");
		}
		else{
			// verifier si les nouveaux mdp correspondent
			
			// si les nouveaux mdp ne sont pas identiques, on redirige vers la page avec une erreur
			if(!nouveauMDP1.equals(nouveauMDP2)){
				//System.out.println("le nouveau mdp ne correspond pas");
				
				erreur = "La confirmation du nouveau mot de passe est incorrecte.";
				request.setAttribute("erreur", erreur);
				
				disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/modifMDPenseignant.jsp");
			}
			// sinon, on procede a la mise a jour dans la base de donnees
			else{
				
				//System.out.println("on appelle ensDAO pour enregistrer mdp");
				
				String msg;
				
				if (ensDAO.enregistrerMDP(beanEns.getNumeroEnseignant(), ancienMDP, nouveauMDP1)){
					
					beanEns = ensDAO.find(beanEns.getNumeroEnseignant());
					
					msg = "Le mot de passe a été modifié avec succès.";
					request.setAttribute("msg", msg);
					
					session.setAttribute("ens", beanEns); // associe le bean mis a jour a la session "ens"
					
					disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/accueilEnseignant.jsp");	
				}				
				else{
					msg = "Une erreur est survenue lors de la mise à jour du mot de passe.";
					request.setAttribute("msg", msg);
					
					disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/accueilEnseignant.jsp");
					
				}
								
			}
		}

		disp.forward(request, response);
		
	}

}
