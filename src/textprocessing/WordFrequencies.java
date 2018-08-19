package textprocessing;

import java.util.Map;
import java.util.HashMap;

public class WordFrequencies {

    private Map<String,Integer> wf = new HashMap<>();

    public synchronized void addFrequencies(Map<String,Integer> f){

        for(String e : f.keySet()){
            if(wf.get(e) == null){
                wf.put(e, f.get(e));
            }else{
                wf.put(e, wf.get(e)+f.get(e));
            }
        }
        notifyAll();
    }
    public Map<String,Integer> getFrequencies(){
        return wf;
    }
}
