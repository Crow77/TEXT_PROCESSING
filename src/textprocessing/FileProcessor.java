package textprocessing;

import java.util.*;
import java.util.regex.*;
public class FileProcessor extends Thread{

    private FileContents fc;
    private WordFrequencies wf;
    private Map<String,Integer> f;
    public FileProcessor(FileContents fc, WordFrequencies wf){
        this.fc = fc;
        this.wf = wf;
    }

    public void run(){
        String fileContents;
        while((fileContents = fc.getContents()) != null){
            f = new HashMap<>();
            Pattern p = Pattern.compile("([A-Za-z0-9ñÑáéíóúüÁÉÍÓÚÜ]+)");
            Matcher m = p.matcher(fileContents);
            String word;
            while(m.find()){
                word = m.group(1);
                if(f.get(word) == null){
                    f.put(word, 1);
                }else{
                    f.put(word, f.get(word)+1);
                }
            }
            wf.addFrequencies(f);
        }
    }


}
