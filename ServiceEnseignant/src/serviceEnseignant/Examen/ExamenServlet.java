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
@WebServlet("/ExamenServlet")
public class ExamenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExamenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public GregorianCalendar ConvertirDate(String date) throws Exception {
		String delims = "[/]";
		String[] tokens = date.split(delims);
		GregorianCalendar gDate = null;
		if (tokens.length == 3) {
			int jour = Integer.parseInt(tokens[0]);
			int mois = Integer.parseInt(tokens[1]) -1;
			int annee = Integer.parseInt(tokens[2]);
			try {
				gDate = new GregorianCalendar(annee, mois, jour);
				gDate.setLenient(false);
			} catch (Exception e) {
				System.out.println("Le Format de la date n'est pas valide");
				return null;
			}
		}
		return gDate;
	}
	
	public double calculeMoyenne(Examen exam) throws Exception {
		int nb_note=0;
		int sum_note=0;
		for(Note n : exam.getMesNotes()){
			if(n.getNote() != -1){
				sum_note += n.getNote();
				nb_note++;
			}
		}
		return sum_note/nb_note;
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
		GregorianCalendar date = null;
		try {
			date = ConvertirDate(request.getParameter("date"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String intitule = request.getParameter("intitule");
		Double coeff = Double.parseDouble(request.getParameter("coeff"));
		String promo = request.getParameter("promo");

	
		Examen exam = new Examen(1,date, intitule, coeff);
		
			request.setAttribute("ExamBeans", exam);
			RequestDispatcher disp=	getServletContext().getRequestDispatcher("/ConsulterExamen.jsp");
			disp.forward(request, response);

	}

}
