package org.sensation.snapread.util;

/**
 * 对比文本相似度
 * Created by Sissel on 2016/10/10.
 */
public class CompareText
{
    public static int compare(String benchmark, String realText)
    {
        if (realText.contains(benchmark))
        {
            return benchmark.length();
        }
        else if (benchmark.equals(""))
        {
            return 0;
        }
        else
        {
            int left = compare(benchmark.substring(1), realText);
            int right = compare(benchmark.substring(0, benchmark.length() - 1), realText);

            return left > right ? left : right;
        }
    }
}
