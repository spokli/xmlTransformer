import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class MyTransformer {

	public static void main(String[] args) {
		try {
			if (args.length != 3) {
				args = new String[3];
				
				System.out.println("Bitte drei Argumente angeben: Pfad der Quelldatei, Pfad der XSL, Pfad der Outputdatei");

				Scanner scanner = new Scanner(System.in);

				System.out.println("Pfad der Quelldatei eingeben:");
				args[0] = scanner.nextLine();

				System.out.println("Pfad der XSL eingeben:");
				args[1] = scanner.nextLine();

				System.out.println("Pfad der zu erstellenden Outputdatei eingeben:");
				args[2] = scanner.nextLine();
				
				scanner.close();
			}
			File xml = new File(args[0]);
			File xsl = new File(args[1]);
			File out = new File(args[2]);

			convert(xml, xsl, out);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void convert(File infile, File xslfile, File outfile) {
		try {
			// Create transformer factory
			TransformerFactory factory = new net.sf.saxon.TransformerFactoryImpl();
//			TransformerFactory factory = TransformerFactory.newInstance();

			// Use the factory to create a template containing the xsl file
			Templates template = factory.newTemplates(new StreamSource(new FileInputStream(xslfile)));

			// Use the template to create a transformer
			Transformer xformer = template.newTransformer();

			// Prepare the input and output files
			Source source = new StreamSource(new FileInputStream(infile));
			Result result = new StreamResult(new FileOutputStream(outfile));

			// Apply the xsl file to the source file and write the result to the
			// output file
			xformer.transform(source, result);
		} catch (TransformerException te) {
			System.out.println(te.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}