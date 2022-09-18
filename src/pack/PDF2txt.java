package pack;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/**
 * Servlet implementation class PDF2txt
 */
@WebServlet("/PDF2txt")
public class PDF2txt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static String text;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PDF2txt() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		this.getServletContext().getRequestDispatcher("/WEB-INF/Depot.jsp").forward(request,  response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Part Myfile = (Part) request.getAttribute("myFile");
     	File fichier = new File("/tmp/"+ getFilename(Myfile));
		FileUtils.copyInputStreamToFile(Myfile.getInputStream(), fichier);
		String fichiername = fichier.getName();
	    System.out.println(fichiername +" has now type File !");
	    //Convert(fichier);
	    
	    
	    String txtfile = Convert(fichier);
	    System.out.println(txtfile);
	    request.setAttribute("fichiertexte", txtfile);
	    //String chaine = "Aymen lives in Toulouse. Sofiane Chbani is an engineer";
	    //request.setAttribute("variable_str",  chaine);
	    request.getRequestDispatcher("/NERResume").forward(request, response);;
	}
	
	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
			}
		}
		return null;
	}

	public String Convert(File file) {
		try {
		      //Loading the pdf file into PDDocument
			      //String FilePath = "/tmp/" + file.getName();
			      //File pdfFile = new File(FilePath);
		      PDDocument MyPDF= PDDocument.load(file);
		      //Initializing The PDFTextStripper class
		      PDFTextStripper TextStripper = new PDFTextStripper();
		      String text = TextStripper.getText(MyPDF);
		      // System.out.println(text);
		      //Writing text to the text file
		      MyPDF.close();
		      return text;
		    } catch (IOException e) {
		      e.printStackTrace();
		      String erreur = "fichier non converti";
		      return erreur;
		    }
	}

}
