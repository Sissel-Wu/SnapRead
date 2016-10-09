package org.sensation.snapread.businesslogic.classification;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nians on 2016/10/9.
 */
public class FeatureSelector {
    public static void main(String[] args) throws IOException {
        FeatureSelector selector = new FeatureSelector();
        selector.selectFeature();
    }

    public void selectFeature() throws IOException {
        //get the dictionary map
        Map<String, Integer> dic = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("dic.txt")));

        for(String s = reader.readLine();s!=null;s = reader.readLine()) {
            String[] temp = s.split(",");
            dic.put(temp[0], Integer.parseInt(temp[1]));
        }

        FileWriter writer = new FileWriter(new File("selected_dic.txt"));
        for (Map.Entry<String,Integer> entry : dic.entrySet()) {
            if (entry.getValue() >= 10 && entry.getValue() <= 200) {
                writer.write(entry.getKey()+","+entry.getValue()+"\n");
                writer.flush();
            }
        }




    }
}
