/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outils;

import java.io.File;
import java.util.HashMap;

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

    public static void getListExitClient() {
        //On génère liste des id clients grâce aux fichiers
        //Les points sont supprimés pour avoir un id cohérent
        
        File root = new File(repertoire);
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
    }
}
