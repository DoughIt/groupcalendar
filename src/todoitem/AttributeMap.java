package todoitem;

import java.util.HashMap;
import java.util.TreeMap;

public interface AttributeMap {
    HashMap<String, String> getAttrs();

    String getValue(String key);

    void addAttr(String key, String value);

    void removeAttr(String key);


    void addAttrs(HashMap<String, String> attrs);


    TreeMap<String, String> getDetailAttrs();
}
