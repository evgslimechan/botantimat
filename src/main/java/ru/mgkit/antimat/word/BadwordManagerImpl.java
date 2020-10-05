package ru.mgkit.antimat.word;

import ru.mgkit.antimat.util.ConfigUtil;
import ru.mgkit.antimat.util.tree.Tree;
import ru.mgkit.antimat.util.tree.TreeNode;

import java.util.*;

public class BadwordManagerImpl implements BadwordManager {

    Map<String, List<String>> charDict = new HashMap<>();

    protected BadwordManagerImpl() {
        charDict.put("а", Arrays.asList("а", "a", "@"));
        charDict.put("б", Arrays.asList("б", "6", "b"));
        charDict.put("в", Arrays.asList("в", "b", "v"));
        charDict.put("г", Arrays.asList("г", "r", "g"));
        charDict.put("д", Arrays.asList("д", "d", "g"));
        charDict.put("е", Arrays.asList("е", "e"));
        charDict.put("ё", Arrays.asList("ё", "е", "e"));
        charDict.put("ж", Arrays.asList("ж", "zh", "*"));
        charDict.put("з", Arrays.asList("з", "3", "z"));
        charDict.put("и", Arrays.asList("и", "u", "i"));
        charDict.put("й", Arrays.asList("й", "u", "y", "i"));
        charDict.put("к", Arrays.asList("к", "k", "i", "|"));
        charDict.put("л", Arrays.asList("л", "l", "ji"));
        charDict.put("м", Arrays.asList("м", "m"));
        charDict.put("н", Arrays.asList("н", "h", "n"));
        charDict.put("о", Arrays.asList("о", "o", "0"));
        charDict.put("п", Arrays.asList("п", "n", "p"));
        charDict.put("р", Arrays.asList("р", "r", "p"));
        charDict.put("с", Arrays.asList("с", "c", "s"));
        charDict.put("т", Arrays.asList("т", "m", "t"));
        charDict.put("у", Arrays.asList("у", "y", "u"));
        charDict.put("ф", Arrays.asList("ф", "f"));
        charDict.put("х", Arrays.asList("х", "x", "h", "к", "k", "}{"));
        charDict.put("ц", Arrays.asList("ц", "c", "u,"));
        charDict.put("ч", Arrays.asList("ч", "ch"));
        charDict.put("ш", Arrays.asList("ш", "sh"));
        charDict.put("щ", Arrays.asList("щ", "sch"));
        charDict.put("ь", Arrays.asList("ь", "b"));
        charDict.put("ы", Arrays.asList("ы", "bi"));
        charDict.put("ъ", Arrays.asList("ъ"));
        charDict.put("э", Arrays.asList("э", "е", "e"));
        charDict.put("ю", Arrays.asList("ю", "io"));
        charDict.put("я", Arrays.asList("я", "ya"));
    }


    @Override
    public boolean isBad(String text) {
        return ConfigUtil.getBadWords().parallelStream().anyMatch(text::contains);
    }

}
