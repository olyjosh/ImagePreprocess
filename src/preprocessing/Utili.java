package preprocessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author Joshua Aroke (olyjosh)
 * olyjoshone@gmail.com
 * f12softwares.com
 */
public class Utili {

    
    final static String PATH=System.getProperty("user.home")+File.separatorChar+"UsmanPreImgProcessing"+File.separatorChar;

    public static void copyFileUsingStream(File source, File dest) throws IOException {
        
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            is.close();
            os.close();
        }
    }
}
