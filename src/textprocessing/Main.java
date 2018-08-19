package textprocessing;
import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) {
        FileNames fileNames= new FileNames();
        FileContents fileContents= new FileContents(30, 100 * 1024);
        WordFrequencies wordFrequencies= new WordFrequencies();
        /*
        Cree e inicie los hilos AQUÍ
        2 FileReader y 3 FileProcessor
        */
        FileReader [] frs = {new FileReader(fileNames, fileContents), new FileReader(fileNames, fileContents)};
        FileProcessor [] fps = {new FileProcessor(fileContents, wordFrequencies), new FileProcessor(fileContents, wordFrequencies), new FileProcessor(fileContents, wordFrequencies)};

        System.out.println("\n                  SE INICIAN  LOS HILOS:\n        ........................................\n");
        int x = 1;
        for(int i = 0; i < frs.length; i++){
            frs[i].start();
            System.out.println("El hilo fr"+x+ " " + frs[i] + " se inicia...");
            x++;
        }
        x = 1;
        for(int i = 0; i < fps.length; i++){
            fps[i].start();
            System.out.println("El hilo fp"+x+ " " + fps[i] + " se inicia...");
            x++;
        }

        //.......................................................
        //Tools.fileLocator(fileNames, "datospruebas");
        Tools.fileLocator(fileNames, "src\\textprocessing\\datos");  //dir where the books are
        fileNames.noMoreNames();
        /*
        Esperar a que terminen los hilos creados
        */
        //.........................................................
        System.out.println("\n              SE ESPEA A QUE FINALICEN  LOS HILOS:\n        ................................................\n");
        x = 1;
        for(int i = 0; i < frs.length; i++){
            try{
                frs[i].join();
                System.out.println("El hilo fr"+x +" " + frs[i] + " ha finalizado...");
                x++;

            }catch(Exception e){}
        }

        x=1;
        for(int i = 0; i < fps.length; i++){
            try{
                fps[i].join();
                System.out.println("El hilo fp"+x+" " + fps[i] + " ha finalizado...");
                x++;

            }catch(Exception e){}
        }
        //.........................................................
        System.out.println("\n                  R E S U L T A D O:\n        ........................................\n");
        for( String palabra : Tools.wordSelector(wordFrequencies.getFrequencies())) {
            System.out.println("                    "+palabra);
        }
        System.out.println("\nF I N I T O ......");
    }
}

class Tools {
    public static void fileLocator(FileNames fn, String dirname){
        File dir = new File(dirname);
        if ( ! dir.exists() ){
            return;
        }
        if ( dir.isDirectory() ) {
            String[] dirList = dir.list();
            for ( String name: dirList ) {
                if ( name.equals(".") || name.equals("..") ) {
                    continue;
                }
                fileLocator(fn, dir + File.separator + name);
            }
        } else {
            fn.addName(dir.getAbsolutePath());
            System.out.println(dir.getAbsolutePath());
        }
    }
    public static class Order implements Comparator< Map.Entry<String,Integer> > {
        public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2) {
            if ( o1.getValue().compareTo(o2.getValue()) == 0 ) {
                return o1.getKey().compareTo(o2.getKey());
            }
            return o2.getValue().compareTo(o1.getValue());
        }
    }

    public static List<String> wordSelector(Map<String,Integer> wf){
        Set<Map.Entry<String,Integer>> set=wf.entrySet();
        Set<Map.Entry<String,Integer>> orderSet= new TreeSet<Map.Entry<String,Integer>>(
                new Order());
        orderSet.addAll(set);
        List<String> l = new LinkedList<String>();
        int i=0;
        for ( Map.Entry<String,Integer> pair: orderSet){
            l.add(pair.getValue() + " " + pair.getKey() );
            if (++i >= 10 ) break;
        }
        return l;
    }

    public static String getContents(String fileName){
        String text = "";
        try {
            FileInputStream fis = new FileInputStream( fileName );
            BufferedReader br = new BufferedReader( new InputStreamReader(fis, "ISO8859-1") );
            String line;
            while (( line = br.readLine()) != null) {
                text += line + "\n";
            }
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
        return text;
    }
}
