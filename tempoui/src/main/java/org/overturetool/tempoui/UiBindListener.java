package org.overturetool.tempoui;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.types.*;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueListener;

/**
 * The UiBindListener notifies the Data class of the UI when there have been
 * changes to the underlying model variable.
 * Created by ldc on 01/02/16.
 */
public class UiBindListener implements ValueListener {

    private Object data;
    private ModelBinder.VarBindInfo var;

    /**
     * Main constructor.
     *
     * @param data reference to the UI Data class
     * @param var  the {@link org.overturetool.tempoui.ModelBinder.VarBindInfo} of the VDM variable to listen for
     */
    public UiBindListener(Object data, ModelBinder.VarBindInfo var) {
        this.data = data;
        this.var = var;
    }

    /**
     * React to changes in the VDM variable by using reflection to set the new
     * value in the UI Data class.
     * <p>
     * Mismatches between model and UI Data variables will end badly.
     * </p>
     *
     * @param location location in the model of the changed variable
     * @param value    new value of the changed variable
     * @param ctxt     context in which the variable has changed
     * @throws AnalysisException
     */
    @Override
    public void changedValue(ILexLocation location, Value value, Context ctxt) throws AnalysisException {
        ValueReflectors.reflectIntoData(value, var, ctxt, data);

    }
}
