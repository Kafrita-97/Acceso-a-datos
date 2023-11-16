package proyectoAAD;
import java.io.IOException;

public class OpenBrowser {
    //NAVEGADOR POR DEFECTO POR SISTEMA
    public void abrirNavegadorPredeterminadorWindows(String url) throws IOException{
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
    }
    public void abrirNavegadorPredeterminadorLinux(String url) throws IOException{
        Runtime.getRuntime().exec("xdg-open " + url);
    }
    public void abrirNavegadorPredeterminadorMacOsx(String url) throws IOException{
        Runtime.getRuntime().exec("open " + url);
    }

    //NAVEGADOR POR DEFECTO GENERICO
    public void abrirNavegadorPorDefecto(String url) throws IOException{
        String osName = System.getProperty("os.name");
        if(osName.contains("Windows"))
            abrirNavegadorPredeterminadorWindows(url);
        else if(osName.contains("Linux"))
            abrirNavegadorPredeterminadorLinux(url);
        else if(osName.contains("Mac OS X"))
            abrirNavegadorPredeterminadorMacOsx(url);
    }

}
