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

		// si toutes les valeurs sont renseignées
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
					// si la date entrée est bien postérieure à la date du jour
					// courant
					if (date.after(dt)) {

						//ajout de l'indisponivilite
						enseignant.ajoutIndispoReg(dD, duree, priorite,
								beanEns, typeI, nbOccu, jour);
						session.setAttribute("ens", beanEns);
						
						//affichage d'un message de succès
						request.setAttribute("erreur",
								"SUCCES : l'indisponibilité saisie a bien été enregistrée.");

					} else {
						// si date entrée avant date du jour, affichage d'un message d'erreur
						request.setAttribute("erreur",
								"La date entrée est déjà passée.");
					}
				} else {
					//affichage d'un message d'erreur
					request.setAttribute("erreur",
							"ERREUR : le jour sélectionné ne correspond pas à la date");

				}
			} catch (ParseException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLIntegrityConstraintViolationException sicv) {
				//affichage d'un message d'erreur
				request.setAttribute("erreur",
						"ERREUR : il existe déjà une indisponibilité dans la base.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			} catch (NumberFormatException nfe) {
				//affichage d'un message d'erreur
				request.setAttribute("erreur",
						"ERREUR : il faut entrer un chiffre et non un caractère.");
			}
		} else { // si pas toutes renseignées

			//affichage d'un message d'erreur
			request.setAttribute("erreur",
					"ERREUR : un (ou plusieurs) paramètre n'a pas été renseigné.");
		}
		dispatch.forward(request, response);
	}

}
