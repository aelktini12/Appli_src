package pack;

import java.util.*;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class NERExample {
	
	public static void main(String[] args) {
		StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
		
		String text = " Aymen EL KTINI, i'm from Morocco, Nassim Lakhal but currently i live in France";
		
		CoreDocument coredocument = new CoreDocument(text);
		
		stanfordCoreNLP.annotate(coredocument);
		
		List<CoreLabel> coreLabels = coredocument.tokens();
		
		for (CoreLabel coreLabel : coreLabels) {
			String ner = coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);
			
			System.out.println(coreLabel.originalText() + " = "+ ner);
		}
		
		
	}

}
