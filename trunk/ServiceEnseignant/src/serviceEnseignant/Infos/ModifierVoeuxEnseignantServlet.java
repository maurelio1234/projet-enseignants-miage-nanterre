package serviceEnseignant.Infos;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UEDAO;
import dao.VoeuxECDAO;

import java.util.List;
import java.util.ArrayList;

import dao.*;
import beans.*;

/**
 * Servlet implementation class ModifierVoeuxEnseignantServlet
 */
@WebServlet("/ModifierVoeuxEnseignantServlet")
public class ModifierVoeuxEnseignantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierVoeuxEnseignantServlet() {
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
		
		System.out.println("dans le controleur modifier voeux ens");
		
		RequestDispatcher disp;
		Enseignant beanEns; 
		VoeuxEC voeuxEC;
		
		// recuperation de la session pour avoir le bean enseignant
		HttpSession session = request.getSession(true);   
		beanEns = (Enseignant) session.getAttribute("ens");
		
		EnseignantDAO ensDAO =  new EnseignantDAO();		
		VoeuxECDAO voeuxEcDao =  new VoeuxECDAO();	
		
		// listes intermediaires pour les UE et EC de l'enseignant afin de recuperer les voeux de la jsp
		List<UE> mesUE = new ArrayList<UE>(); // les UE dans lesquels l'enseignant enseigne
		List<EC> lesEC = new ArrayList<EC>(); // les EC des UE de l'enseignant
		
		for(int i=0; i<beanEns.getMesServices().size(); i++){
				
			if(mesUE.isEmpty() || !mesUE.contains(beanEns.getMesServices().get(i).getMonEC().getMonUE())){
		
				System.out.println("servlet : ajout dans liste UE n° "+beanEns.getMesServices().get(i).getMonEC().getMonUE().getNumeroUE());
				
				UEDAO ueDao = new UEDAO();
				UE monUE = ueDao.find(beanEns.getMesServices().get(i).getMonEC().getMonUE().getNumeroUE(), beanEns.getMesServices().get(i).getMonEC().getMonUE().getMaFormation().getNumeroFormation());
				ueDao.loadMesEC(monUE);
				
				mesUE.add(monUE);
				
				// on parcourt les EC de l'UE
				for(int j=0; j<mesUE.get(i).getMesEC().size(); j++){
					
					System.out.println("servlet : ajout dans liste EC n°"+mesUE.get(i).getMesEC().get(j).getNumeroEC()+" dans UE n°"+mesUE.get(i).getNumeroUE());
					lesEC.add(mesUE.get(i).getMesEC().get(j));				
				} // fin for EC			
			}
		} // fin for UE
			
		boolean choixEns = false;
		
		// on recupere les voeux des EC des UE de l'enseignant à partir de la jsp
		for(int y=0; y<lesEC.size(); y++){
			
			int numEC = lesEC.get(y).getNumeroEC(); // numero EC pour lequel on doit recuperer le voeux
			
			String nomParam = numEC+"_"+lesEC.get(y).getMonUE().getNumeroUE()+"_"+lesEC.get(y).getMonUE().getMaFormation().getNumeroFormation();
			
			System.out.println("servlet : recupere valeur du param "+nomParam);
			
			String choix = request.getParameter(nomParam);
			
			System.out.println("servlet : valeur choix pour l'ec n°"+numEC + "   choix :"+ choix);
			
			if( !choix.equals("null") )
			{
				if(choix.equals("oui"))
					choixEns = true;
				else
					choixEns = false;				
			}
						
			voeuxEC = new VoeuxEC(lesEC.get(y), beanEns, choixEns); 
			
			if(voeuxEcDao.find(lesEC.get(y).getMonUE().getMaFormation().getNumeroFormation(), lesEC.get(y).getMonUE().getNumeroUE(), lesEC.get(y).getNumeroEC(), beanEns.getNumeroEnseignant()) != null)
				voeuxEcDao.update(voeuxEC);
			else
				voeuxEcDao.create(voeuxEC);
				
		} // fin for
		
		beanEns = ensDAO.find(beanEns.getNumeroEnseignant());
		ensDAO.loadAll(beanEns);
		session.setAttribute("ens", beanEns); 
		
		disp = this.getServletContext().getRequestDispatcher("/jsp/jspInfos/accueilEnseignant.jsp");

		disp.forward(request, response);
	}

}
