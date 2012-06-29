package serviceEnseignant.Voeux;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
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
import javax.servlet.http.HttpSession;

import beans.Enseignant;

/**
 * Servlet implementation class ModeleAbsUniq
 */
@WebServlet("/ModeleAbsUniq")
public class ModeleAbsUniq extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EnsIndispo enseignant;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModeleAbsUniq() {
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

		RequestDispatcher dispatch = this.getServletContext()
				.getRequestDispatcher("/jsp/jspVoeux/index.jsp");

		
		
		HttpSession session = request.getSession(true);
		Enseignant beanEns = (Enseignant) session.getAttribute("ens");
		
		
		String dD = request.getParameter("dateDebut");
		String dF = request.getParameter("dateFin");
		String prio = request.getParameter("poids");
		String dur = request.getParameter("demiJ");

		try {

			// si toutes les valeurs sont renseignées
			if (dD != "" && dF != "" && prio != null && dur != null) {

				int priorite = Integer.parseInt(prio);
				int duree = Integer.parseInt(dur);

				// transformer le format string en calendar pour comparer les
				// dates
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Calendar d = format.getCalendar();

				// date de debut
				Date dateDbt;
				dateDbt = format.parse(dD);
				GregorianCalendar dateD = new GregorianCalendar();
				dateD.setTime(dateDbt);

				// date de fin
				Date dateFin;
				dateFin = format.parse(dF);
				GregorianCalendar dateF = new GregorianCalendar();
				dateF.setTime(dateFin);
				
				
				// si la date entrée est bien postérieure à la date du jour
				// courant
				if (dateDbt.after(Calendar.getInstance().getTime())) {

					if (dateD.before(dateF)) {
						enseignant.ajoutIndispoUniqDAO(dD, dF, priorite, beanEns.getNumeroEnseignant(),
								duree);
						
						request.setAttribute("erreurUniq", "SUCCES : L'indisponibilité a bien été prise en compte");
						session.setAttribute("ens", beanEns);
					} else {
						// si date début apres date de fin

						request.setAttribute("erreurUniq","ERREUR : La date de début est postérieure à la date de fin");
					}
				} else {
					request.setAttribute("erreurUniq","ERREUR : La date de début est antérieure à la date d'aujourd'hui");
				}

			} else { // si pas toutes renseignées

				request.setAttribute("erreurUniq","ERREUR : un (ou plusieurs) paramètre n'a pas été renseigné.");
			}

		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("erreur ex");
		}catch(SQLIntegrityConstraintViolationException sicv){
			request.setAttribute("erreur", "ERREUR : il existe déjà une indisponibilité dans la base.");
		}
		dispatch.forward(request, response);
	}

}
