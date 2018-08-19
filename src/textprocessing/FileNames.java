package textprocessing;

import java.util.Queue;
import java.util.LinkedList;
public class FileNames {

    private Queue<String> queue = new LinkedList<>();
    private boolean flag = true;       //false, indica que no se devuelven más nombres de ficheros

    //Almacena un nuevo nombre de fichero en el objeto.
    public synchronized void addName(String fileName) {
        //if(flag == false) return;
        notify();
        queue.add(fileName);
    }

    public synchronized String getName() {
        String fileName;
        while((fileName = queue.poll()) == null){
            if(flag == false) return null;
            try{
                wait();      //wait() libera el cerrojo
            }catch(Exception e){}
        }
        notifyAll();
        return fileName;
    }

    //Da lugar a que el objeto no admita más nombres de fichero.
    public synchronized void noMoreNames() {
        flag = false;
        notifyAll();
    }
}
