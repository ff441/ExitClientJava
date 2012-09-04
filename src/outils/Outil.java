/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sragneau
 */
public class Outil {

    /**
     * @param String idClient
     */
    public static void suppFichierTemps(String pathOrigine, String pathTemp) {
        // On supprime le fichier original et on renomme le temporaire
        File fichierOriginal = new File(pathOrigine);
        fichierOriginal.delete();
        File fichierModifie = new File(pathTemp);
        fichierModifie.renameTo(fichierOriginal);
        File fichierTemp = new File(pathTemp);
        fichierTemp.delete();
    }

    public static HashMap getListExitClient() throws IOException {
        //On génère liste des id clients grâce aux fichiers
        //Les points sont supprimés pour avoir un id cohérent
        
        Properties prop = new Properties();
        /* Ici le fichier contenant les données de configuration est nommé 'db.myproperties' */
        FileInputStream in;
        try {
            in = new FileInputStream("config.properties");
            prop.load(in);
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Outil.class.getName()).log(Level.SEVERE, null, ex);
        }
        String repertoireExit = prop.getProperty("repertoireExit");
        
        
        File root = new File(repertoireExit);
        File[] list = root.listFiles();
        String nomFichier = null;
        HashMap tableFichierClient = new HashMap();
        
        for (File f : list) {
            if (f.isFile()) {
                String idClientFichier = f.getName();
                idClientFichier = idClientFichier.replaceAll("[.]", "");
                //System.out.println(f.getName());
                tableFichierClient.put(idClientFichier, f.getName());
            }
        }
        return tableFichierClient;
    }
}
