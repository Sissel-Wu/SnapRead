package businesslogic.classification;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nians on 2016/10/9.
 */
public class DicCreater {
    private final List<String> catas = Arrays.asList("C3-Art", "C4-Literature", "C5-Education", "C6-Philosophy"
            , "C7-History", "C11-Space", "C15-Energy", "C16-Electronics", "C17-Communication", "C19-Computer", "C23-Mine",
            "C29-Transport", "C31-Environment", "C32-Agriculture", "C34-Economy", "C35-Law", "C36-Medical",
            "C37-Military", "C38-Politics", "C39-Sports");

    public static void main(String[] args) throws IOException {
        DicCreater creater = new DicCreater();
        creater.create();
    }

    public void create() throws IOException {
        FileWriter writer = new FileWriter(new File("src.arff"));
        writer.write("@Relation rawText\n" +
                "\n" +
                "@Attribute txt_text string\n" +
                "@Attribute cls_class {C3-Art, C4-Literature, C5-Education, C6-Philosophy" +
                "            , C7-History, C11-Space, C15-Energy, C16-Electronics, C17-Communication, C19-Computer, C23-Mine," +
                "            C29-Transport, C31-Environment, C32-Agriculture, C34-Economy, C35-Law, C36-Medical," +
                "            C37-Military, C38-Politics, C39-Sports}" +
                "\n" +
                "@data\n");
        for (String cata : catas) {
            ArrayList<String> files = this.getFiles(cata);

            for (String f : files) {
                StringBuilder builder = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"GBK"));
                for(String s = br.readLine();s!=null;s = br.readLine()) {
                    builder.append(s);
                }
                String line = this.getCutPassage(builder.toString());
                line = "\""+line+"\","+cata+"\n";
                writer.write(line);
                writer.flush();
            }




        }
        writer.write("\n");

    }

    private ArrayList<String> getFiles(String folderName) {
        ArrayList<String> result = new ArrayList<>();
        String fileName = "answer/" + folderName;
        File f = new File(fileName);
        File[] array = f.listFiles();
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile())
                result.add("answer/"+folderName+"/" + array[i].getName());
            if(result.size()>200)
                break;

        }
        return result;
    }

    private String getCutPassage(String passage) {

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
}
