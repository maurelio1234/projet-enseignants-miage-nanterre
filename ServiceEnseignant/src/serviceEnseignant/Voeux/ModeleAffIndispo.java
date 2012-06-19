package serviceEnseignant.Voeux;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.*;

/**
 * Servlet implementation class ModeleAffIndispo
 */
@WebServlet("/ModeleAffIndispo")
public class ModeleAffIndispo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EnsIndispo ensIndispo;
	private Enseignant ens;
	private int ref = 2;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModeleAffIndispo() {
        super();
        ensIndispo = new EnsIndispo();
        ens = new Enseignant();
        ens.setNumeroEnseignant(2);
        
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
		
	
		try {
			ensIndispo.afficherIndispo(ens);		
			
				request.setAttribute("EnseiBean", ens);
			
			
			RequestDispatcher disp = getServletContext().getRequestDispatcher("/jsp/jspVoeux/AffIndispo.jsp");
			disp.forward(request, response);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
