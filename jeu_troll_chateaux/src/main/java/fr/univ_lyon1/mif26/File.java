package fr.univ_lyon1.mif26;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class File {

    private static final String filepath = "data/obj";

    public static Object ReadObjectFromFile(final int idJoueur) {
        try {
            FileInputStream fileIn = new FileInputStream(filepath + idJoueur);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void WriteObjectToFile(Object serObj, final int idJoueur) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath + idJoueur);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
