package proyectoAAD;

import java.io.IOException;

/**
 * Clase que proporciona métodos para abrir un navegador web predeterminado en diferentes sistemas operativos.
 */
public class OpenBrowser {

    /**
     * Abre el navegador web predeterminado en el sistema operativo Windows.
     *
     * @param url La URL que se abrirá en el navegador.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public void abrirNavegadorPredeterminadorWindows(String url) throws IOException {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
    }
    
    /**
     * Abre el navegador web predeterminado en el sistema operativo Linux.
     *
     * @param url           La URL que se abrirá en el navegador.
     * @throws IOException  Si ocurre un error de entrada/salida.
     */
    public void abrirNavegadorPredeterminadorLinux(String url) throws IOException {
        Runtime.getRuntime().exec("xdg-open " + url);
    }
    
    /**
     * Abre el navegador web predeterminado en el sistema operativo macOS.
     *
     * @param url           La URL que se abrirá en el navegador.
     * @throws IOException  Si ocurre un error de entrada/salida.
     */
    public void abrirNavegadorPredeterminadorMacOsx(String url) throws IOException{
        Runtime.getRuntime().exec("open " + url);
    }

    /**
     * Abre el navegador web predeterminado según el sistema operativo actual.
     *
     * @param url           La URL que se abrirá en el navegador.
     * @throws IOException  Si ocurre un error de entrada/salida.
     */
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
