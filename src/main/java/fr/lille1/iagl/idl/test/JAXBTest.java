package fr.lille1.iagl.idl.test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import fr.lille1.iagl.idl.generated.ObjectFactory;
import fr.lille1.iagl.idl.generated.Type;

public class JAXBTest {

	public static void main(final String[] args) {
		try {
			final JAXBContext context = JAXBContext.newInstance(contextPath);
			final Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			final ObjectFactory fabrique = new ObjectFactory();
			final Type type = fabrique.createType();

			m.marshal(type, System.out);
		} catch (final JAXBException ex) {
			ex.printStackTrace();
		}
	}
}
