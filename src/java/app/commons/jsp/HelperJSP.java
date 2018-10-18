package app.commons.jsp;

import java.util.ArrayList;
import java.util.List;

public class HelperJSP {

    private List<Integer> selectedIds = new ArrayList<Integer>(); // item ids
    
    private int alreadySelectedIndex = -1; // html select index
    
    public void flagHtmlSelect(int htmlSelectIndex, int id) {
        alreadySelectedIndex = htmlSelectIndex;
        selectedIds.add(new Integer(id));
    }
    
    public boolean htmlSelectAlreadySet(int selectIdx, int id) {
    
        for(int settedId : selectedIds) {
            if(settedId == id || alreadySelectedIndex >= selectIdx) return true;
        }

        return false;

    }
    
}
