package serviceEnseignant.Voeux;

import java.io.IOException;
import java.sql.SQLException;
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

import beans.Enseignant;

/**
 * Servlet implementation class ModeleIndispo
 */
@WebServlet("/ModeleAbsReg")
public class ModeleAbsReg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EnsIndispo enseignant;
	
	private Enseignant ens;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModeleAbsReg() {
		super();
		enseignant = new EnsIndispo();
		ens= new Enseignant();
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
		
		
		System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
		ens.setNumeroEnseignant(1);
		String dD = request.getParameter("dateDebut");

		String j = request.getParameter("jour");
		String tI = request.getParameter("type");
		String prio = request.getParameter("poids");
		String nbO = request.getParameter("nbOccu");
		String dur = request.getParameter("demiJ");
		System.out.println("typeeeeeeeeeeeeeeeeeeeeee " + tI);
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
		int nbOccu = Integer.parseInt(nbO);
		int jour = Integer.parseInt(j);
		
		System.out.println("Type : " + typeI + " Priorite : " + priorite + " Duree : " + duree);
		System.out.println("\nDate debut : " + dD + " jour : " + jour + " nb occ : " + nbOccu);
		
		
		try {
			enseignant.ajoutIndispoReg(dD,duree,priorite,ens,typeI,nbOccu,jour);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		
		RequestDispatcher dispatch =
				this.getServletContext().getRequestDispatcher("/jsp/jspVoeux/index.jsp");
				dispatch.forward(request, response);
	}

}
