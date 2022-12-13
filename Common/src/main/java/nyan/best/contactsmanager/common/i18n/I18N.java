package nyan.best.contactsmanager.common.i18n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class I18N {

    private static final Map<String, Lang> langMap = new HashMap<>();

    public static void put(String key, String value) {
        langMap.put(key, new Lang(value));
    }

    public static void put(String key, List<String> value) {
        langMap.put(key, new Lang(value));
    }

    public static String lang(String key) {
        if (!langMap.containsKey(key))
            return "MISSING_LANG::" + key;
        return langMap.get(key).getStrVal();
    }

    public static List<String> langList(String key) {
        if (!langMap.containsKey(key))
            return new ArrayList<>() {{
                add("MISSING_LANG::" + key);
            }};
        return langMap.get(key).getStrListVal();
    }

    private static class Lang {
        private final String strVal;
        private final List<String> strListVal;

        public Lang(String strVal) {
            this.strVal = strVal;
            this.strListVal = null;
        }

        public Lang(List<String> strListVal) {
            this.strVal = null;
            this.strListVal = strListVal;
        }

        public String getStrVal() {
            return strVal;
        }

        public List<String> getStrListVal() {
            return strListVal;
        }
    }

}
