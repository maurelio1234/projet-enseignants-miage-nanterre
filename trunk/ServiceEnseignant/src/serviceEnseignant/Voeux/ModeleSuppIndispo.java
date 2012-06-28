package serviceEnseignant.Voeux;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Enseignant;

/**
 * Servlet implementation class ModeleSuppInd
 */
@WebServlet("/ModeleSuppInd")
public class ModeleSuppIndispo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private EnsIndispo enseignant;
       private int refEns;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModeleSuppIndispo() {
        super();
        enseignant = new EnsIndispo();
       // refEns=2;
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
		/**
		 * On doit récupérer la date de l'indisponibilité
		 * chercher l'indispo associé
		 */
		
		HttpSession session = request.getSession(true);
		Enseignant beanEns = (Enseignant) session.getAttribute("ens");
		
		String date = request.getParameter("date");
		try {
			enseignant.SuppIndispo(date, beanEns.getNumeroEnseignant());
			session.setAttribute("ens",beanEns);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		RequestDispatcher dispatch = this.getServletContext()
				.getRequestDispatcher("/ModeleAffIndispo");
		dispatch.forward(request, response);
		
	}

}
