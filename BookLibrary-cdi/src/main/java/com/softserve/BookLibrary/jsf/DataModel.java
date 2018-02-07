package com.softserve.BookLibrary.jsf;

import com.softserve.BookLibrary.entity.SuperEntity;
import com.softserve.BookLibrary.manager.GenericManager;
import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.model.Arrangeable;
import org.richfaces.model.ArrangeableState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataModel<T extends SuperEntity> extends ExtendedDataModel implements Arrangeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataModel.class);

    private Long rowId;
    private Map<Long, T> wrappedData = new HashMap<>();
    private GenericManager<T> genericManager;

    public DataModel(GenericManager<T> genericManager) {
        this.genericManager = genericManager;
    }

    @Override
    public void arrange(FacesContext facesContext, ArrangeableState arrangeableState) {
        genericManager.setArrangeableState(arrangeableState);
    }

    @Override
    public void setRowKey(Object key) {
        rowId = (Long) key;
    }

    @Override
    public Object getRowKey() {
        return rowId;
    }

    @Override
    public void walk(FacesContext facesContext, DataVisitor dataVisitor, Range range, Object argument) {
        List<T> entityList = genericManager.getData(((SequenceRange) range).getFirstRow(), ((SequenceRange) range).getRows());
        if (!entityList.isEmpty()) {
            for (T item : entityList) {
                wrappedData.put(item.getId(), item);
                dataVisitor.process(facesContext, item.getId(), argument);
            }
        }
    }

    @Override
    public boolean isRowAvailable() {
        return rowId != null;
    }

    @Override
    public int getRowCount() {
        return genericManager.getRowCount();
    }

    @Override
    public T getRowData() {
        if (null == rowId) {
            return null;
        }
        return wrappedData.get(rowId);
    }

    @Override
    public int getRowIndex() {
        return 0;
    }

    @Override
    public void setRowIndex(int rowIndex) {
    }

    @Override
    public Object getWrappedData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWrappedData(Object data) {
        throw new UnsupportedOperationException();
    }

}
