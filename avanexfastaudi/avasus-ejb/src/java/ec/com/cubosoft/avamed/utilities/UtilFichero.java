/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author JUAN PABLO 3 <jp3@cubosoft.com.ec>
 * @version 1.0
 */
public class UtilFichero {

    private static int Base64Length = 64;

    /**
     * @param fileName
     * @return byte[] que corresponde a la lectura del archivo de referencia recibido
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    public static byte[] getBytesFromFile(String fileName) throws IOException, FileNotFoundException {

        File file = new File(fileName);

        if (!file.exists()) {
            throw new IOException("Archivo NULO");
        }

        InputStream is = null;
        long length = file.length();
        byte[] bytes = new byte[(int) length];

        is = new FileInputStream(file);

        int offset = 0;
        int numRead = 0;

        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("No se pudo completar la lectura del archivo " + file.getName());
        }

        is.close();

        return bytes;
    }

    /**
     * 
     * @param outFileName - Nombre del archivo de salida
     * @param data - byte[] de datos que se escribirán
     * @throws java.io.IOException
     */
    public static void writeToFile(String outFileName, byte[] data) throws IOException {

        FileOutputStream fos = new FileOutputStream(new File(outFileName));
        fos.write(data);
        fos.flush();
        fos.close();
    }

    /**
     * Codifica en Base 64 una Array de bytes que le lleguen, esto se hace para
     * no perder precision, ya que al copiar archivos de bytes puede sufrir
     * cambios. Este clase se utiliza mucho cuando la entrada no son caracteres
     * ASCII
     * @param outputFileName
     * @param data
     * @throws java.io.IOException
     */
    public static StringBuilder encodeB64MIME(byte[] data) throws IOException {
        BufferedWriter out = null;
        StringBuilder cade = null;
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new ByteArrayInputStream(data));
//            out = new BufferedWriter(new FileWriter(outputFileName));
            Base64Length = 76;
            cade = encodeStreamB64(in);
            out.flush();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            return cade;
        }
    }

    /**
     * Codifica un archivo de origen en Base64, y la salida se dice cual es
     * @param inputFileName - Nombre del Archivo de origen
     * @param outputFileName - Nombre del Archivo de salida codificado
     * @throws java.io.IOException
     */
    public static void encodeB64MIME(String inputFileName, String outputFileName)
            throws IOException {
        encodeB64MIME(getBytesFromFile(inputFileName));
    }
//devuelve los byts

    private static StringBuilder encodeStreamB64(InputStream in) throws IOException {
        StringBuilder cadena = new StringBuilder();
        byte[] buf = new byte[Base64Length / 4 * 3];
        while (true) {
            int len = in.read(buf);
            if (len <= 0) {
                break;
            }
            cadena.append(Base64Coder.encode(buf, len));
//            out.write(Base64Coder.encode(buf, len));
            //Si hay mas por leer creo una nueva línea
            if (in.available() > 0) {
//                out.newLine();
            }
        }
        return cadena;
    }

    /**
     *
     * @param inputFileName
     * @return un Array de bytes con el archivo codificado en BASE64
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static byte[] decode64(String inputFileName) throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(inputFileName));
        byte[] rtn = decodeStreamB64(in);
        if (in != null) {
            in.close();
        }
        return rtn;
    }

    public static byte[] decode64S(String stringdeco) throws FileNotFoundException, IOException {

        byte[] rtn = decodeStreamB64(stringdeco);

        return rtn;
    }

    private static byte[] decodeStreamB64(String in) throws IOException {
        byte[] buf = null;

        String s = in;

        buf = Base64Coder.decode(s);


        return buf;
    }

    private static byte[] decodeStreamB64(BufferedReader in) throws IOException {
        ByteArrayOutputStream rtn = new ByteArrayOutputStream();
        while (true) {
            String s = in.readLine();
            if (s == null) {
                break;
            }
            byte[] buf = Base64Coder.decode(s);
            rtn.write(buf);
        }
        rtn.close();
        return rtn.toByteArray();
    }
}
