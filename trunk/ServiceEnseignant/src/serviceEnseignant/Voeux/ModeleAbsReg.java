package serviceEnseignant.Voeux;

import java.io.IOException;
import java.sql.SQLException;
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
 * Servlet implementation class ModeleIndispo
 */
@WebServlet("/ModeleAbsReg")
public class ModeleAbsReg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EnsIndispo enseignant;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModeleAbsReg() {
		super();
		enseignant = new EnsIndispo();
		//ens = new Enseignant();
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

		String dD = request.getParameter("dateDbt");
		String j = request.getParameter("jour");
		String tI = request.getParameter("type");
		String prio = request.getParameter("poids");
		String nbO = request.getParameter("nbOccu");
		String dur = request.getParameter("demiJ");

		// si toutes les valeurs sont renseign�es
		if (dD != "" && j != null && tI != null && prio != null && nbO != ""
				&& dur != null) {

			try {
				int typeI = Integer.parseInt(tI);
				int priorite = Integer.parseInt(prio);
				int duree = Integer.parseInt(dur);

				int nbOccu = Integer.parseInt(nbO);
				int jour = Integer.parseInt(j);

				// transformer le format string en calendar pour comparer les
				// dates
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Date date;
				date = format.parse(dD);
				GregorianCalendar dateD = new GregorianCalendar();
				dateD.setTime(date);

				if (dateD.get(Calendar.DAY_OF_WEEK) == jour) {

					Date dt = Calendar.getInstance().getTime();
					// si la date entr�e est bien post�rieure � la date du jour
					// courant
					if (date.after(dt)) {

						//ajout de l'indisponivilite
						enseignant.ajoutIndispoReg(dD, duree, priorite,
								beanEns, typeI, nbOccu, jour);
						session.setAttribute("ens", beanEns);
						
						//affichage d'un message de succ�s
						request.setAttribute("erreur",
								"SUCCES : l'indisponibilit� saisie a bien �t� enregistr�e.");

					} else {
						// si date entr�e avant date du jour, affichage d'un message d'erreur
						request.setAttribute("erreur",
								"La date entr�e est d�j� pass�e.");
					}
				} else {
					//affichage d'un message d'erreur
					request.setAttribute("erreur",
							"ERREUR : le jour s�lectionn� ne correspond pas � la date");

				}
			} catch (ParseException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLIntegrityConstraintViolationException sicv) {
				//affichage d'un message d'erreur
				request.setAttribute("erreur",
						"ERREUR : il existe d�j� une indisponibilit� dans la base.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			} catch (NumberFormatException nfe) {
				//affichage d'un message d'erreur
				request.setAttribute("erreur",
						"ERREUR : il faut entrer un chiffre et non un caract�re.");
			}
		} else { // si pas toutes renseign�es

			//affichage d'un message d'erreur
			request.setAttribute("erreur",
					"ERREUR : un (ou plusieurs) param�tre n'a pas �t� renseign�.");
		}
		dispatch.forward(request, response);
	}

}
