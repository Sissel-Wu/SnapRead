/**
 * IK 中文分词  版本 5.0.1
 * IK Analyzer release 5.0.1
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 * 
 * 
 */
package org.sensation.snapread.businesslogic.classification;

import org.sensation.snapread.data.ArticleData;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.sensation.snapread.po.ArticlePO;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 使用IKAnalyzer进行分词的演示
 * 2012-10-22
 *
 */
public class ClassificationController {
    Map<String, Integer> dic;
    ArrayList<String> dicArray;
    ArticleData data;

    public ClassificationController() {
        data = new ArticleData();

        //load dictionary
        dic = new HashMap<>();
        dicArray = new ArrayList<>();


        try {
            Resource resource = new ClassPathResource("selected_dic.txt");
            File tempFile = File.createTempFile("selected_dic",".txt");
            FileOutputStream stream = new FileOutputStream(tempFile);
            FileCopyUtils.copy(resource.getInputStream(),stream);

            BufferedReader reader = new BufferedReader(new FileReader(tempFile));
            for(String s = reader.readLine();s!=null;s = reader.readLine()) {
                String[] temp = s.split(",");
                dic.put(temp[0], Integer.parseInt(temp[1]));
                dicArray.add(temp[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String getClassification(String passage) {
        String luceneCutResult = this.getLuceneCutResult(passage);

        ArrayList<Double> wordVector = this.getWordVector(luceneCutResult);

        ArticlePO similarArticle = this.getMostSimilarArticle(wordVector);

        if (similarArticle == null)
        {
            System.out.println("no similar found, returning null");
            return "null";
        }
        else
        {
            System.out.println("find similar with tag : " + similarArticle.getType());
            return similarArticle.getType();
        }
    }

    public String getFeatureString(String passage) {
        // view the analyzed passage
        System.out.println("analyzing:\n " + passage);

        String luceneCutResult = this.getLuceneCutResult(passage);

        ArrayList<Double> wordVector = this.getWordVector(luceneCutResult);

        String featureString = this.toFeatureString(wordVector);

        return featureString;
    }



    private ArticlePO getMostSimilarArticle(ArrayList<Double> wordVector) {
        Iterator<ArticlePO> it = data.getAllArticle();
        double maxSimilarity = Double.MIN_VALUE;
        ArticlePO similarPointer = null;
        while (it.hasNext()) {
            ArticlePO po = it.next();
            ArrayList<Double> a2 = this.fromFeatureString(po.getFeature(),wordVector.size());
            double tempSimilarity = this.calculateCosineDistance(wordVector, a2);
            System.out.println("similarity with post "+po.getTitle()+" is "+tempSimilarity);
            if(tempSimilarity>maxSimilarity) {
                maxSimilarity = tempSimilarity;
                similarPointer = po;
            }
        }

        return similarPointer;
    }

    private double calculateCosineDistance(ArrayList<Double> a1, ArrayList<Double> a2) {
        if(a1.size()!=a2.size())
            return -1;

        double dis = 0.0;
        for(int i=0;i<a1.size();i++) {
            dis += a1.get(i) * a2.get(i);
        }
        dis = dis / this.getVectorLength(a1) / this.getVectorLength(a2);
        return dis;
    }

    private double getVectorLength(ArrayList<Double> a) {
        double sum = 0.0;
        for (Double d : a) {
            sum += d*d;
        }
        return Math.sqrt(sum);
    }

    private boolean checkIntegrity(ArrayList<Double> a1, ArrayList<Double> a2) {
        DecimalFormat df = new DecimalFormat("0.0000");
        for(int i=0;i<a1.size();i++) {
            if (!df.format(a1.get(i)).equals(df.format(a2.get(i)))) {
                System.out.println(i + " " + a1.get(i) + " " + a2.get(i));
            }
        }
        return true;
    }

    private String toFeatureString(ArrayList<Double> src) {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<src.size();i++) {
            if(src.get(i)!=0) {
                DecimalFormat df = new DecimalFormat("#####0.00");
                builder.append(i + "," + df.format(src.get(i)) + ";");
            }
        }
        builder.deleteCharAt(builder.lastIndexOf(";"));

        return builder.toString();
    }

    private ArrayList<Double> fromFeatureString(String src, int arraySize) {
        ArrayList<Double> result = new ArrayList<>();
        for(int i=0;i<arraySize;i++) {
            result.add(0.0);
        }
        String[] pairs = src.split(";");
        for (String pair : pairs) {
            String[] temp = pair.split(",");
            int index = Integer.parseInt(temp[0]);
            result.set(index,Double.parseDouble(temp[1]));
        }

        return result;
    }

    private ArrayList<Double> getWordVector(String cutResult) {
        String[] rawWords = cutResult.split("|");
        Map<String, Double> tempDic = new HashMap<>();
        for (String rawWord : rawWords) {
            if (dic.containsKey(rawWord)) {
                if(!tempDic.containsKey(rawWord))
                    tempDic.put(rawWord, 1.0);
                else
                    tempDic.replace(rawWord, tempDic.get(rawWord) + 1);
            }
        }

        ArrayList<Double> result = new ArrayList<>();
        int c = 0;
        for (String word : dicArray) {
            if(!tempDic.containsKey(word))
                result.add(0.0);
            else {
                result.add(tempDic.get(word) * 100 / dic.get(word));
                c++;
            }
        }
        System.out.println(c+" features are captured");

        return result;


    }

    private String getLuceneCutResult(String passage) {
        //独立Lucene实现
        StringReader re = new StringReader(passage);
        IKSegmenter ik = new IKSegmenter(re, true);
        Lexeme lex = null;
        StringBuilder builder = new StringBuilder();
        try {
            while ((lex = ik.next()) != null) {
                builder.append(lex.getLexemeText() + "|");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        ClassificationController classificationController = new ClassificationController();
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(new File("passage.txt")));
        for(String line = reader.readLine();line!=null;line = reader.readLine()) {
            builder.append(line);
        }
        System.out.println(classificationController.getClassification(builder.toString()));
    }


}
