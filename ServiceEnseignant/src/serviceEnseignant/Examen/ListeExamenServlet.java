package serviceEnseignant.Examen;

import java.io.IOException;
import java.io.PrintWriter;
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
	
		PrintWriter out = response.getWriter(); 
		String formationChoix = request.getParameter("formationChoix");
		String UEChoix = request.getParameter("UEChoix");

	
		
		
		Enseignant ens = new Enseignant(10, "Bonneau", "Julie", "6 rue colbert", "0606890718", null, "login", "password");
		
		Formation L3MIAGECLA = new Formation(1,"MIAGE","L3","Classique","parcours MIAGE");
		Formation L3MIAGEAPP = new Formation(1,"MIAGE","L3","Apprentissage","parcours MIAGE");
		Formation M1MIAGEAPP = new Formation(1,"MIAGE","M1","Apprentissage","parcours MIAGE");
		Formation M1MIAGECLA = new Formation(1,"MIAGE","M1","Classique","parcours MIAGE");
		
		Promotion p = new Promotion(2009);
		Promotion p1 = new Promotion(2010);
		Promotion p2 = new Promotion(2011);
		Promotion p3 = new Promotion(2012);
		
		L3MIAGECLA.getMesPromotions().add(p);		
		L3MIAGEAPP.getMesPromotions().add(p1);
		M1MIAGEAPP.getMesPromotions().add(p2);
		M1MIAGECLA.getMesPromotions().add(p3);
		
		
		UE ue1 = new UE(1, L3MIAGECLA);
		EC ec1 = new EC(1, ue1, L3MIAGECLA);
		
		UE ue2 = new UE(2, L3MIAGEAPP);
		EC ec2 = new EC(2, ue2, L3MIAGEAPP);

		
		UE ue4 = new UE(4, M1MIAGEAPP);
		EC ec4 = new EC(4, ue4, M1MIAGEAPP);
		
		UE ue5 = new UE(5, M1MIAGECLA);
		EC ec5 = new EC(5, ue5, M1MIAGECLA);
		
		Type t = new Type(1, "type 1");
		
		Service service1 = new Service(ec1, ens,t,10);
		Service service2 = new Service(ec2, ens,t,10);
		Service service3 = new Service(ec4, ens,t,10);
		Service service4 = new Service(ec5, ens,t,10);
		
		System.out.println(service1.getMonEnseignant().getNom());
		
		
			
			request.setAttribute("ensBeans", ens);

			RequestDispatcher disp=	getServletContext().getRequestDispatcher("/jsp/jspExamen/ListeExamen.jsp");
			disp.forward(request, response);

	}

}
