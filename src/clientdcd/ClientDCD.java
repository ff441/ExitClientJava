/*
 * Projet pour mettre une date dans un exit client 
 */
package clientdcd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;
import javax.swing.JOptionPane;
import outils.Outil;

/**
 * Class de démarrage
 *
 * @author sragneau
 */
public class ClientDCD {

    static String repertoire = "C:\\saga_local\\saga2006a\\REP\\";

    /**
     * @param String idClient
     */
    public static boolean deceder(String idClient, boolean deces) throws Exception {

        //Test si le parametre idClient a été indiqué
        //Si non, on affiche une boite de dialogue demandant l'id       
        if (idClient.isEmpty() || idClient == null || (idClient.length() < 5)) {
            throw new Exception("L'id client n'est pas indiqué ou n'est pas conforme");
        } 
        
        
        else {
            String dateDc=null;
            if(deces){
            JOptionPane jop = new JOptionPane();
            String titre = "Date de décès";
            dateDc = (String) jop.showInputDialog(null, "Date de décès !", titre, jop.QUESTION_MESSAGE,
                    null, // icone (image)
                    null,
                    "20120101");   // valeur par defaut);                
            //TODO: AJOUTER LE TEST DU FORMATAGE
            }
            if (dateDc == null && deces) {
                throw new Exception("La date n'est pas conforme");
            } // On démarrre la modification du fichier
            else {
                //Test sur la présence du fichier exit dans le répertoire REP     
                try {

                    // On stock les fichier exit pour avoir l'exit et le nom de fichier avec les "..."                        
                    // résoud le problème de nom de fichier avec des points                        
                    File root = new File(repertoire);
                    File[] list = root.listFiles();
                    String nomFichier = null;
                    HashMap tableFichierClient = new HashMap();

                    
                    
                    if (!tableFichierClient.containsKey(idClient)) {
                        throw new Exception("Fichier exit client inexistant");
                    } //On modifie le fichier
                    else {
                        //Création d'un buffer
                        InputStream ips = null;
                        String PathFichierOriginal = repertoire + tableFichierClient.get(idClient).toString();
                        String PathFichierOriginal2;
                        PathFichierOriginal2 = PathFichierOriginal;
                        ips = new FileInputStream(PathFichierOriginal);
                        InputStreamReader ipsr = new InputStreamReader(ips);
                        BufferedReader br = new BufferedReader(ipsr);
                        //Permet de controler les lignes
                        InputStream ipsVerif = null;
                        ipsVerif = new FileInputStream(PathFichierOriginal2);
                        InputStreamReader ipsrVerif = new InputStreamReader(ipsVerif);
                        BufferedReader brVerif = new BufferedReader(ipsrVerif);
                        String ligne;

                        String PathFichierTemp =  repertoire + "temp";      
                        FileWriter fw = new FileWriter(PathFichierTemp);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter fichierSortie = new PrintWriter(bw);
                        
                        boolean _36=false;
                        boolean _37=false;
                        
                        //On test si le client n'est pas déjà décédé
                        //SI oui on retourne true
                        //si non on continue le programme
                        for (int i = 1; i < 45; i++) {
                            
                            ligne = brVerif.readLine();                   
                                                        
                            if (i == 36 && ligne.equals("1")) {
                                _36=true;
                            }
                            
                            if (i == 37 && ligne.length()==8){
                                _37=true;
                            }
                            
                            
                            //Tests pour savoir s'il n'est pas déjà décédé
                            if (_36 && _37 && deces)
                            {
                                br.close();
                                brVerif.close();
                                fichierSortie.close();
                                JOptionPane.showMessageDialog(null, "L'exit client est déjà décédé \n fichier: " + tableFichierClient.get(idClient).toString(), "Information", JOptionPane.INFORMATION_MESSAGE);
                                return true;
                            }
                            
                            //Tests pour savoir s'il est déjà vivant
                            if (i == 36 && ligne.equals("[ADRESSES]") && !deces)
                            {
                                br.close();
                                brVerif.close();
                                fichierSortie.close();
                                JOptionPane.showMessageDialog(null, "L'exit client est déjà vivant \n fichier: " + tableFichierClient.get(idClient).toString(), "Information", JOptionPane.INFORMATION_MESSAGE);
                                return true;
                            }                                     
                            
                        }
                        

                        //On continue, on modifie le fichier
                        for (int i = 1; i < 45; i++) {
                            
                            if (i != 36 && i != 37) {
                                ligne = br.readLine();
                                if (ligne != null) {
                                    fichierSortie.println(ligne);
                                    //System.out.println(ligne);
                                    ligne += "\n";
                                }
                            }

                            if (i == 36 && deces) {
                                ligne = "1";
                                fichierSortie.println(ligne);
                                ligne += "\n";
                            }

                            if (i == 37 && deces) {
                                ligne = dateDc;
                                fichierSortie.println(ligne);
                                ligne += "\n";
                            }
                            
                            //Permet de supprimer les lignes de décès
                            if (i == 36 && !deces) {
                                ligne = br.readLine();
                                ligne = br.readLine();                                
                            }
                            
                            

                        }
                        //On ferme les deux buffers
                        br.close();
                        brVerif.close();
                        fichierSortie.close();                        
                        
                        Outil.suppFichierTemps(PathFichierOriginal, PathFichierTemp);
                        
                        JOptionPane.showMessageDialog(null, "L'exit client est modifié \n fichier: " + tableFichierClient.get(idClient).toString(), "Information", JOptionPane.INFORMATION_MESSAGE);
                        return true;
                    }



                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Fichier exit client inexistant", "Erreur", JOptionPane.ERROR_MESSAGE);
                    throw new Exception("Fichier exit client inexistant");
                }
            }
        }
    }
}
