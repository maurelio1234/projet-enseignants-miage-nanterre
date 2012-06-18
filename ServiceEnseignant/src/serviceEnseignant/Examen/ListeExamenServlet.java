package serviceEnseignant.Examen;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.*;
/**
 * Servlet implementation class ExamenServlet
 */
@WebServlet("/ListeExamenServlet")

public class ListeExamenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeExamenServlet() {
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
	
		Formation L3MIAGECLA = new Formation(1,"MIAGE","L3","Classique","parcours MIAGE");
		Promotion p = new Promotion(2009);
		Promotion p1 = new Promotion(2010);
		Promotion p2 = new Promotion(2011);
		Promotion p3 = new Promotion(2012);
		L3MIAGECLA.getMesPromotions().add(p);
		L3MIAGECLA.getMesPromotions().add(p2);
		L3MIAGECLA.getMesPromotions().add(p3);
		
		
		//Formation M1MIAGECLA = new Formation(1,"MIAGE","M1","Classique","parcours MIAGE");
		
			request.setAttribute("L3MIAGECLA", L3MIAGECLA);
			//request.setAttribute("M1MIAGECLA", M1MIAGECLA);
			RequestDispatcher disp=	getServletContext().getRequestDispatcher("/ListeExamen.jsp");
			disp.forward(request, response);

	}

}
