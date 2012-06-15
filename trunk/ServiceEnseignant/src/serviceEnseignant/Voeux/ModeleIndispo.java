package serviceEnseignant.Voeux;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ModeleIndispo
 */
@WebServlet("/ModeleIndispo")
public class ModeleIndispo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EnsIndispo enseignant;
	
	private int ref=2;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModeleIndispo() {
		super();
		enseignant = new EnsIndispo();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String dD = request.getParameter("dateDbt");
		String dF = request.getParameter("dateFin");

		String tI = request.getParameter("type");
		String prio = request.getParameter("poids");
		String dur = request.getParameter("demiJ");

		/*SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
		Calendar d = format.getCalendar();
*/

		/*Date date = format.parse(dD);
			Date daF = format.parse(dF);

			// pour le jour de début
			GregorianCalendar dateD = new GregorianCalendar();
			dateD.setTime(date);

			// pour le jour de fin
			GregorianCalendar dateF = new GregorianCalendar();
			dateF.setTime(daF);	
			*/	
		
		int typeI = Integer.parseInt(tI);
		int priorite = Integer.parseInt(prio);
		int duree = Integer.parseInt(dur);
		
		System.out.println("Type : " + typeI + " Priorite : " + priorite + " Duree : " + duree);
		System.out.println("\nDate debut : " + dD + " Date fin : " + dF);
		
		
		enseignant.ajoutIndispo(dD,dF,typeI,priorite,ref,duree);	

		
		RequestDispatcher dispatch =
				request.getRequestDispatcher("index.jsp");
				dispatch.forward (request, response);
	}

}
