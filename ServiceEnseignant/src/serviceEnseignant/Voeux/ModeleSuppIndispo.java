package serviceEnseignant.Voeux;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ModeleSuppInd
 */
@WebServlet("/ModeleSuppInd")
public class ModeleSuppIndispo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private EnsIndispo enseignant;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModeleSuppIndispo() {
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
		// TODO Auto-generated method stub
		/**
		 * On doit r�cup�rer la date de l'indisponibilit�
		 * chercher l'indispo associ�
		 */
		System.out.println("Coucou je suis l� " );
		System.out.println(request.getParameter("date"));
	}

}
