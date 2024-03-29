package serviceEnseignant.Examen;

import java.io.IOException;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Etudiant;
import beans.Examen;
import beans.Note;
import dao.*;

@WebServlet("/Servlet")

public class CreateExamenServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;
	private Examen exam;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
	public CreateExamenServlet() throws SQLException {
        super();
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {       
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GregorianCalendar date = null;
		try {
			date = ConvertirDate(request.getParameter("date"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String intitule = request.getParameter("intitule");
		Double coeff = Double.parseDouble(request.getParameter("coeff"));
		String heure = request.getParameter("heure");
		String matiere = request.getParameter("matiere");
		String delims = "[;]";
		String[] tokens = matiere.split(delims);
		
		int choixEnseignant = Integer.parseInt(request.getParameter("numEns"));

		JoursDAO joursDAO = new JoursDAO();
		EnseignantDAO ensdao = new EnseignantDAO();
		ExamenDAO examDAO = new ExamenDAO();
		ECDAO ecDAO = new ECDAO();
		EtudiantDAO etuDAO = new EtudiantDAO();
		NoteDAO noteDAO = new NoteDAO();
		
		exam = new Examen();
		exam.setHoraire(heure);
		exam.setCoefficient(coeff);
		exam.setDate(joursDAO.find(date));
		exam.setLibelle(intitule);
		exam.setMonEnseignant(ensdao.find(choixEnseignant));
		exam.setMonEC(ecDAO.find(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2])));
		
		List<Etudiant> listEtudiant = exam.getMonEC().getMonUE().getMaFormation().getMesPromotions().get(1).getMesEtudiants();
		examDAO.create(exam);
		for(int i = 0; i< listEtudiant.size(); i++ ){
			Note n = new Note(etuDAO.find(listEtudiant.get(i).getNumeroEtudiant()), exam, -1);
			noteDAO.create(n);
		}
		
			request.setAttribute("ExamBeans", exam);
			RequestDispatcher disp=	getServletContext().getRequestDispatcher("/ExamenServlet");
			disp.forward(request, response);
		
	}
	
	
				
		
}

//fin


