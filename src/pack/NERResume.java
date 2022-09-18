package pack;


//import static org.junit.Assert.*;

//import org.junit.Assert;

import java.io.IOException;

import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;


@WebServlet("/NERResume")
public class NERResume extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String adresse_mail;
	
	//private static String txtfile;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public NERResume() {
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
		System.out.println("Text file is now being parsed");
		//String chaine = (String) request.getAttribute("variable_str");
		String fichiertexte = (String) request.getAttribute("fichiertexte");
		doNER(fichiertexte);
		Get_Linkedin(fichiertexte);
	}
	
	
	public static void doNER(String txt) throws IOException {
		StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
		
		CoreDocument coredocument = new CoreDocument(txt);
		
		stanfordCoreNLP.annotate(coredocument);
		
		ArrayList<CoreLabel> coreLabels = (ArrayList<CoreLabel>) coredocument.tokens();
		for (CoreLabel coreLabel : coreLabels) {
			String ner = coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);
			
			//System.out.print(coreLabels);
			//System.out.println(coreLabel.originalText() + " = "+ ner);
			if ( ner == "EMAIL") {
				String adresse_mail = coreLabel.originalText();
				System.out.println("L'adresse mail est:" + adresse_mail);
			} else if (coreLabel.originalText().contains("@") && (coreLabel.originalText().endsWith(".com") || coreLabel.originalText().endsWith(".fr"))) { 
				String adresse_mail = coreLabel.originalText();
				System.out.println("L'adresse mail est: " + adresse_mail);
				
			}
			
			if ( ner == "NUMBER" && 8 <= coreLabel.originalText().length() && (10 <= coreLabel.originalText().length()) &&  coreLabel.originalText().length()<= 13 ) {
				String numero = coreLabel.originalText();
				System.out.println("Le numéro de téléphone est : "+ numero);
			} else if (coreLabel.originalText().startsWith("+") && coreLabel.originalText().length() == 12) {
				String numero = coreLabel.originalText();
				System.out.println("Le numéro de téléphone est : "+ numero);
			} else if (coreLabel.originalText().startsWith("00") && coreLabel.originalText().length() == 13) {
				String numero = coreLabel.originalText();
				System.out.println("Le numéro de téléphone est : "+ numero);
			} else if (coreLabel.originalText().startsWith("+33") && 8 >= coreLabel.originalText().length()) {
				// var item = coreLabel.originalText();
				int itemPos = coreLabel.index();
				String numero = coreLabel.originalText();
				while (numero.length()<12) {
					numero = numero.concat(coreLabels.get(itemPos).originalText());
					itemPos ++;
				}
			
				
				if (numero.contains("(") && numero.contains(")")) {
						numero = numero.concat(coreLabels.get(itemPos).originalText());
				}

				System.out.println("Le numéro de téléphone est : "+ numero);
			}
			
			if (ner=="PERSON") {
				String nom = coreLabel.originalText();
				System.out.println("Le nom est : " + nom);
			}

			
			//if (coreLabel.originalText().contains("linkedin.com") && isValidUrl(coreLabel.originalText())) {
				//String linkedin = coreLabel.originalText();
				//System.out.println("Le lien linkedin est : " + linkedin);
				
			//} else if ((coreLabel.originalText().contains("www.linkedin") || coreLabel.originalText().contains("linkedin.com")) && !isValidUrl(coreLabel.originalText()) ) {
				//int itemPos = coreLabel.index();
				//int max_iterations = 15,  iterations = 0;
				//String linkedin = coreLabel.originalText();
				//while(!isValidUrl(coreLabel.originalText()) && iterations<= max_iterations) {
					//linkedin = linkedin.concat(coreLabels.get(itemPos).originalText());
				//	itemPos++;
				//	iterations++;
				//}
				//System.out.println("Le lien linkedin est : " + linkedin);
			//}

		}
	}
	
	// Extraire le lien linkedin 
	public static void Get_Linkedin(String s) {
		String[] str_Arr = s.split("[\\s+]");
		List<String> str_listArr = Arrays.asList(str_Arr);
		//System.out.print(str_listArr);
		for (String str : str_listArr) {
			
			if (str.contains("linkedin.com") && isValidUrl(str)) {
				String linkedin = str;
				System.out.println("Le lien linkedin est : " + linkedin);
			
			} else if ((str.contains("www.linkedin") || str.contains("linkedin.com")) && !isValidUrl(str) ) {
				int itemPos = str_listArr.indexOf(str);
				int max_iterations = 15,  iterations = 0;
				String linkedin = str;
				while(!isValidUrl(str) && iterations<= max_iterations) {
					linkedin = linkedin + str_listArr.get(itemPos+1);
					itemPos++;
					iterations++;
				}
				System.out.println("Le lien linkedin est : " + linkedin);
			}
		
		}
	}
	
	public static boolean isNumeric(String s) {
		if (s == null || s.length() == 0) {
			return false;
		} else {
			for (char c: s.toCharArray()) {
				if (!Character.isDigit(c)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean isValidUrl(String url) {
		String pre1 = "www.";
		String pre2 = "https://";
		if (!url.startsWith(pre2) && url.startsWith(pre1)) {
			url = pre2.concat(url);
		} else if (!url.startsWith(pre1)) {
			url = pre2.concat(pre1.concat(url));
		}
		try {
			new URL(url).toURI();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	


}
