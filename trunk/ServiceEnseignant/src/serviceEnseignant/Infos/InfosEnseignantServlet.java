package serviceEnseignant.Infos;





import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Enseignant;

/**
 * Servlet implementation class InfosEnseignantServlet
 */
@WebServlet("/InfosEnseignantServlet")
public class InfosEnseignantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Enseignant beanEnseignant; 
	//beanEnseignant qui sera envoye a la JSP PageInfoPerso.jsp
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfosEnseignantServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		
		
		
	}

}
