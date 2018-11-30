/*
  User: Cloudy
  Date: 30-Nov-18
  Time: 21:39
*/

package cz.cloudy.fxengine.utils;

import java.io.*;

public class SerializationUtils {
    public static <T extends Serializable> byte[] convert(T object) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(out);
            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }

        return out.toByteArray();
    }

    public static <T extends Serializable> T deconvert(byte[] bytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            T obj = (T) ois.readObject();
            ois.close();
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
