package serviceEnseignant.Voeux;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ModeleAbsUniq
 */
@WebServlet("/ModeleAbsUniq")
public class ModeleAbsUniq extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EnsIndispo enseignant;
	private int ref=2;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public ModeleAbsUniq() {
		super();
		enseignant = new EnsIndispo();
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

		String dD = request.getParameter("dateDebut");
		String dF = request.getParameter("dateFin");
		String prio = request.getParameter("poids");
		
		int priorite = Integer.parseInt(prio);
		
		try {
			enseignant.ajoutIndispoUniq(dD,dF,priorite,ref);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
