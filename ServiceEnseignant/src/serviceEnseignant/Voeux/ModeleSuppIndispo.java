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
       private int refEns;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModeleSuppIndispo() {
        super();
        enseignant = new EnsIndispo();
        refEns=2;
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
		String date = request.getParameter("date");
		System.out.println(date);
	}

}
