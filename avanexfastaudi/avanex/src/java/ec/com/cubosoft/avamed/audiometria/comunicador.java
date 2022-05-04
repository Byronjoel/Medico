/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.audiometria;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
//import org.zkoss.web.servlet.http.HttpServlet;


/**
 * @author Patricia Amoroso
 * @author Rubel Quipo
 * @author Juan Pablo Chavez
 */

@WebServlet(name = "ComunicadorAudiometria", urlPatterns = {"/comunicador"})
public class comunicador extends HttpServlet {

    @SuppressWarnings("UseOfObsoleteCollectionType")
//    private Hashtable phones = new Hashtable();
//    private String dataFilePath = "phonelist";
    HttpSession session;

    /**
     * 
     * @param conf
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        log("Comunicador.initialize: Entrando a Servlet " + getServletInfo());
    }

    /**
     * 
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    /**
     * 
     * @param req
     * @param res
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        session = req.getSession(true);
        String derecho, izquierdo = "";
        derecho = req.getParameter("derecho");
        izquierdo = req.getParameter("izquierdo");
        System.out.println(izquierdo);
//        log("metodo post:");
        log("rigth:" + derecho.length());
//        log("metodo post:");
        log("left:" + izquierdo.length());
        session.setAttribute("derecho", derecho);
        session.setAttribute("izquierdo", izquierdo);
    }
    /**
     *  To preserve the phone data when the servlet is unloaded.
     */
    /**
     * Reads in the phone data from the file.
     */
//    public static void CrearImagen(String image) throws IOException {
////        BASE64Decoder deco = new BASE64Decoder();
//        byte[] ima = image.getBytes();
//        InputStream is = new ByteArrayInputStream(ima);
//        BufferedImage imagen = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
//        imagen = ImageIO.read(is);
//        ImageIO.write(imagen, "jpg", new File("C:/Users/Juan Pablo/Desktop/erika.jpg"));
//    }
}
