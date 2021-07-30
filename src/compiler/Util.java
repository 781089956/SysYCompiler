package compiler;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Util {
    public static int[] listToIntArray(List<Integer> list,int length)
    {
        int[] ints = new int[length];
        for (int i = 0; i < Math.min(list.size(),length); i++) {
            ints[i] = list.get(i);
        }

        return ints;
    }

    public static int getIntFromStr(String num)
    {
        boolean neg = false;
        if(num.startsWith("-"))
        {
            num = num.substring(1);
            neg = true;
        }else if(num.startsWith("+"))
        {
            num = num.substring(1);
        }
        if(num.startsWith("0x") || num.startsWith("0X"))
        {
            return (int)Long.parseLong(num.toLowerCase(Locale.ROOT).replace("0x", ""), 16);
        }else if(num.startsWith("0") && num.length()>1)
        {
            return (int)Long.parseLong(num.toLowerCase(Locale.ROOT).substring(1),8);
        }
        int i = (int)Long.parseLong(num);
        return neg?-i:i;
    }
}
