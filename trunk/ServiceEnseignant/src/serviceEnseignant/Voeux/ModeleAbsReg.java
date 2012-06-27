package serviceEnseignant.Voeux;

import java.io.IOException;
import java.io.PrintWriter;
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
		ens = new Enseignant();
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

		RequestDispatcher dispatch = this.getServletContext()
				.getRequestDispatcher("/jsp/jspVoeux/index.jsp");
		
		ens.setNumeroEnseignant(1);

		String dD = request.getParameter("dateDbt");
		String j = request.getParameter("jour");
		String tI = request.getParameter("type");
		String prio = request.getParameter("poids");
		String nbO = request.getParameter("nbOccu");
		String dur = request.getParameter("demiJ");

		String valNulle = "";

		System.out.println("avant le premier IF de modeleAbsReg");
		
		// si toutes les valeurs sont renseignées
		if (!dD.equalsIgnoreCase(valNulle) && !j.equalsIgnoreCase(valNulle)
				&& !tI.equalsIgnoreCase(valNulle)
				&& !prio.equalsIgnoreCase(valNulle)
				&& !nbO.equalsIgnoreCase(valNulle)
				&& !dur.equalsIgnoreCase(valNulle)) {

			System.out.println("dans le premier IF de modeleAbsReg");
			
			int typeI = Integer.parseInt(tI);
			int priorite = Integer.parseInt(prio);
			int duree = Integer.parseInt(dur);
			int nbOccu = Integer.parseInt(nbO);
			int jour = Integer.parseInt(j);

			System.out.println("Type : " + typeI + " Priorite : " + priorite
					+ " Duree : " + duree);
			System.out.println("\nDate debut : " + dD + " jour : " + jour
					+ " nb occ : " + nbOccu);

			try {

				// transformer le format string en calendar pour comparer les
				// dates
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Calendar d = format.getCalendar();
				Date date;
				date = format.parse(dD);
				GregorianCalendar dateD = new GregorianCalendar();
				dateD.setTime(date);
				
				Date dt=Calendar.getInstance().getTime();
				// si la date entrée est bien postérieure à la date du jour
				// courant
				System.out.println(date);
				System.out.println(Calendar.getInstance().getTime());
				if (date.after(dt)) {

					enseignant.ajoutIndispoReg(dD, duree, priorite, ens, typeI,
							nbOccu, jour);

					
					dispatch.forward(request, response);
				}
				else{ 
					System.out.println("dans le premier ELSE de modeleAbsReg");
					
					//si date entrée avant date du jour
					
					request.setAttribute("erreur", "La date entrée est déjà passée.");
					
					dispatch.forward(request, response);
					
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{ //si pas toutes renseignées
			
			System.out.println("dans le deuxieme ELSE de modeleAbsReg");
			
			request.setAttribute("erreur", "ERREUR : un (ou plusieurs) paramètre n'a pas été renseigné.");
			
			dispatch.forward(request, response);
			
		}
	}

}
