package org.sensation.snapread.data;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

/**
 * 管理文章
 * Created by Sissel on 2016/10/19.
 */
public class ArticleCenter
{
    private static String dirPath = "";
    private static String cfgPath = "";
    private static int count;
    private static ArticleCenter instance;

    private ArticleCenter()
    {

        cfgPath = "/home/article.txt";
        File inputFile = new File(cfgPath);
        dirPath = "/home/articles";

        try
        {
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            Scanner scanner = new Scanner(fileInputStream);
            count = scanner.nextInt();

            File dir = new File(dirPath);
            dir.mkdir();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static ArticleCenter getInstance()
    {
        if (instance == null)
        {
            instance = new ArticleCenter();
        }
        return instance;
    }

    public String putFile(String fileContent)
    {
        String name = "t" + count;
        ++count;

        FileWriter fileWriter;
        try
        {
            fileWriter = new FileWriter(dirPath + "/" + name);
            fileWriter.write(fileContent);
            fileWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            fileWriter = new FileWriter(cfgPath);
            fileWriter.write("" + count);
            fileWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return name;
    }

    public String getFile(String fileName)
    {
        try
        {
            FileReader reader = new FileReader(dirPath + "/" + fileName);
            Scanner scanner = new Scanner(reader);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext())
            {
                sb.append(scanner.next());
            }
            reader.close();
            return sb.toString();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args)
    {
        ArticleCenter ac = new ArticleCenter();
        String name = ac.putFile("wo hao papa");

        System.out.println(ac.getFile(name));
        String name2 = ac.putFile("wo hao papa2");

        System.out.println(ac.getFile(name2));
        String name3 = ac.putFile("wo hao papa3");

        System.out.println(ac.getFile(name));
    }
}
