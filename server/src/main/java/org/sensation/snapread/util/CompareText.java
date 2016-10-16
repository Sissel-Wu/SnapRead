package org.sensation.snapread.util;

import java.util.List;

/**
 * 对比文本相似度
 * Created by Sissel on 2016/10/10.
 */
public class CompareText
{
    public static int compare(String benchmark, String realText)
    {
        int index = 0;
        int max = benchmark.length() / 5 + 1;
        String[] benchmarkSet = new String[max];
        for (; index < max; ++index)
        {
            if (benchmark.length() > index * 5)
            {
                benchmarkSet[index] = benchmark.substring(index * 5, index * 5 + 5);
            }
            else
            {
                benchmarkSet[index] = "";
            }
        }

        int sum = 0;
        for (String mark : benchmarkSet)
        {
            if (mark != null && !mark.equals("") && realText.contains(mark))
            {
                ++sum;
            }
        }

        return sum;
    }
}
