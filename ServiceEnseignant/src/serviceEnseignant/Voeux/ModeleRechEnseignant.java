package serviceEnseignant.Voeux;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Enseignant;

import serviceEnseignant.DAO.EnseignantDAO;

/**
 * Servlet implementation class ModeleRechEnseignant
 */
@WebServlet("/ModeleRechEnseignant")
public class ModeleRechEnseignant extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EnsIndispo ensIndispo;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModeleRechEnseignant() {
        super();
        ensIndispo = new EnsIndispo();
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
		String ref = request.getParameter("nEns");
		int refEns = Integer.parseInt(ref);
		
		System.out.println(" mon numéro " + refEns);
		EnseignantDAO ensdao= new EnseignantDAO();
		Enseignant ens = ensdao.find(refEns);
		System.out.println("je suis là " + ens.getNumeroEnseignant());
		
		try {
			ensIndispo.afficherIndispo(ens);
			
			request.setAttribute("EnseiBean", ens);
			
			
			RequestDispatcher disp = getServletContext().getRequestDispatcher("/jsp/jspVoeux/AffEnsIndispo.jsp");
			disp.forward(request, response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
