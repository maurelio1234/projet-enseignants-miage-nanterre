package serviceEnseignant.Voeux;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import serviceEnseignant.DAO.IndisponibiliteDAO;
import beans.*;

/**
 * Servlet implementation class ModeleAffIndispo
 */
@WebServlet("/ModeleAffIndispo")
public class ModeleAffIndispo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EnsIndispo ensIndispo;
	private IndisponibiliteDAO indao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModeleAffIndispo() {
        super();
        ensIndispo = new EnsIndispo();
        indao = new IndisponibiliteDAO();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(true);
		Enseignant beanEns = (Enseignant) session.getAttribute("ens");

		indao.loadMesIndisponibilites(beanEns);
		request.setAttribute("EnseiBean", beanEns);

		RequestDispatcher disp = getServletContext().getRequestDispatcher(
				"/jsp/jspVoeux/AffIndispo.jsp");
		disp.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		Enseignant beanEns = (Enseignant) session.getAttribute("ens");

		indao.loadMesIndisponibilites(beanEns);
		request.setAttribute("EnseiBean", beanEns);

		RequestDispatcher disp = getServletContext().getRequestDispatcher(
				"/jsp/jspVoeux/AffIndispo.jsp");
		disp.forward(request, response);

	}
}
