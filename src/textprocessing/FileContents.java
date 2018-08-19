package textprocessing;

import java.util.*;
public class FileContents {

    private Queue<String> queue;
    private int registerCount = 0;
    private boolean closed = false;
    private int maxFiles, maxChars;      //maxFiles == número máximo de ficheros qe se pueden almacenar, maxChars == tamaño total mámimo de las ristras que se pueden almacenar en un moemnto dado.

    public FileContents(int maxFiles, int maxChars) {
        queue = new LinkedList<>();
        this.maxFiles = maxFiles;
        this.maxChars = maxChars;
    }

    //Se llama para indicar que hay un nuevo FileReader está usando el objeto.
    public synchronized void registerWriter() {
        registerCount++;
        notify();
    }

    //Indica que un FileReader ha dejado de producir contenido. Cuando el número de FileReader registrados pase de 1 a 0 el objeto ya no recibirá más contenido.
    public synchronized void unregisterWriter() {
        registerCount--;
        if (registerCount == 0) closed = true;
        notify();
    }

    //Llamado por hilo FileReader para añadir el contenido de un fichero
    public synchronized void addContents(String contents) {
        while (!(queue.size() < maxFiles) && !(contents.length() <= maxChars)) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        notify();
        queue.add(contents);
    }

    //Llamado por hilo FileProcessor para extraer el contenido de un fichero
    public synchronized String getContents() {
        String content;
        while ((content = queue.poll()) == null) {
            if (closed) return null;
            try {
                wait();
            } catch (Exception e) {
            }
        }
        notifyAll();
        return content;
    }

}