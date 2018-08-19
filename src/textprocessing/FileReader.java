package textprocessing;

public class FileReader extends Thread{

    private FileNames fn;
    private FileContents fc;

    public FileReader(FileNames fn, FileContents fc){
        this.fn = fn;
        this.fc = fc;
    }

    public void run(){
        String fileNames;
        fc.registerWriter();
        while((fileNames = fn.getName()) != null){
            fc.addContents(Tools.getContents(fileNames));
            //Descomentar para ver los ficheros que se van procesando
            System.out.println("Un FileReader lee el archivo: " + fileNames);
        }
        fc.unregisterWriter();
    }
}
