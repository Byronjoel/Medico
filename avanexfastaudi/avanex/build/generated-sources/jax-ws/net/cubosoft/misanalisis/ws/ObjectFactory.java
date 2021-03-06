
package net.cubosoft.misanalisis.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.cubosoft.misanalisis.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PdfXOrden_QNAME = new QName("http://ws.misanalisis.cubosoft.net/", "PdfXOrden");
    private final static QName _PdfXOrdenResponse_QNAME = new QName("http://ws.misanalisis.cubosoft.net/", "PdfXOrdenResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.cubosoft.misanalisis.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PdfXOrden }
     * 
     */
    public PdfXOrden createPdfXOrden() {
        return new PdfXOrden();
    }

    /**
     * Create an instance of {@link PdfXOrdenResponse }
     * 
     */
    public PdfXOrdenResponse createPdfXOrdenResponse() {
        return new PdfXOrdenResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PdfXOrden }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.misanalisis.cubosoft.net/", name = "PdfXOrden")
    public JAXBElement<PdfXOrden> createPdfXOrden(PdfXOrden value) {
        return new JAXBElement<PdfXOrden>(_PdfXOrden_QNAME, PdfXOrden.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PdfXOrdenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.misanalisis.cubosoft.net/", name = "PdfXOrdenResponse")
    public JAXBElement<PdfXOrdenResponse> createPdfXOrdenResponse(PdfXOrdenResponse value) {
        return new JAXBElement<PdfXOrdenResponse>(_PdfXOrdenResponse_QNAME, PdfXOrdenResponse.class, null, value);
    }

}
