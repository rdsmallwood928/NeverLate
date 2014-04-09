package com.neverlate.NeverLate.activities.components.AndroidScrollSpinner;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by bigwood928 on 4/6/14.
 */
public class AndroidScrollModel {

    private Object[] spinnerModel = new Object[0];
    private int selectedIndex = 0;
    private List<AndroidScrollModelListener> listeners = new CopyOnWriteArrayList<>();

    public AndroidScrollModel(List<Object> data) {
        this.spinnerModel = data.toArray();
        if(spinnerModel.length != 0) {
            setSelectedObject(spinnerModel[0]);
        }
    }

    public boolean setSelectedObject(Object object) {
        for(int i = 0; i<spinnerModel.length; i++) {
            if(spinnerModel[i].equals(object)) {
                selectedIndex = i;
                fireSelectedObjectChanged();
                return true;
            }
        }
        return false;
    }

    private void fireSelectedObjectChanged() {
        for(AndroidScrollModelListener listener : listeners) {
            listener.onSelectedObjectChanged(getSelectedItem());
        }
    }

    public void addListener(AndroidScrollModelListener listener) {
        listeners.add(listener);
    }

    public Object getSelectedItem() {
        return spinnerModel[selectedIndex];
    }

    public Object getObjectXIndexesAway(int numIndexes) {
        int currentIndex = selectedIndex;
        if(numIndexes > 0) {
            for(int i = 0; i<numIndexes; i++) {
                currentIndex++;
                if(currentIndex >= spinnerModel.length) {
                    currentIndex = 0;
                }
            }
        } else if(numIndexes < 0) {
            for(int i = Math.abs(numIndexes); i>0; i--) {
                currentIndex--;
                if(currentIndex < 0) {
                    currentIndex = spinnerModel.length-1;
                }
            }
        }
        return spinnerModel[currentIndex];
    }

    public interface AndroidScrollModelListener {
        public void onSelectedObjectChanged(Object selectedObject);
    }
}
