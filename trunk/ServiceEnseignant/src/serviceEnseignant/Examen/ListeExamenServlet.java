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
	
		Enseignant ens = new Enseignant(10, "Bonneau", "Julie", "6 rue colbert", "0606890718", null, "login", "password");
		
		Formation L3MIAGECLA = new Formation(1,"MIAGE","L3","Classique","parcours MIAGE");
		Promotion p = new Promotion(2009);
		Promotion p1 = new Promotion(2010);
		Promotion p2 = new Promotion(2011);
		Promotion p3 = new Promotion(2012);
		L3MIAGECLA.getMesPromotions().add(p);
		L3MIAGECLA.getMesPromotions().add(p2);
		L3MIAGECLA.getMesPromotions().add(p3);
		
		UE ue = new UE(1, L3MIAGECLA);
		EC ec = new EC(1, ue, L3MIAGECLA);
		
		Type t = new Type(1, "type 1");
		Service service = new Service(ec, ens,t,10);
		System.out.println(service.getMonEnseignant().getNom());
		
		ens.getMesServices().add(service);
			
			request.setAttribute("ensBeans", ens);

			RequestDispatcher disp=	getServletContext().getRequestDispatcher("/jsp/jspExamen/ListeExamen.jsp");
			disp.forward(request, response);

	}

}
