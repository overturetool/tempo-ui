package org.overturetool.tempoui;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.types.*;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The UiBindListener notifies the Data class of the UI when there have been
 * changes to the underlying model variable.
 * Created by ldc on 01/02/16.
 */
public class UiBindListener implements ValueListener {

    private Object data;
    private String setterName;
    private PType type;

    /**
     * Main constructor.
     *
     * @param data reference to the UI Data class
     * @param name name of the VDM variable to listen for. Must match the name on the UI Data class
     * @param type VDM type of the variable to listen for.
     */
    public UiBindListener(Object data, String name, PType type) {
        this.data = data;
        this.setterName = makeSetter(name);
        this.type = type;
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
        try {

            if (ctxt.assistantFactory.createPTypeAssistant().isType(type,
                    AIntNumericBasicType.class)) {
                Method method;
                method = data.getClass().getDeclaredMethod(setterName, long.class);
                method.invoke(data, value.intValue(ctxt));
            }

            if (ctxt.assistantFactory.createPTypeAssistant().isType(type,
                    ARealNumericBasicType.class)) {
                Method method;
                method = data.getClass().getDeclaredMethod(setterName,
                        double.class);
                method.invoke(data, value.stringValue(ctxt));
            }

            if (ctxt.assistantFactory.createPTypeAssistant().isType(type,
                    ABooleanBasicType.class)) {
                Method method;
                method = data.getClass().getDeclaredMethod(setterName,
                        boolean.class);
                method.invoke(data, value.boolValue(ctxt));
            }

            if (isString(ctxt)) {
                Method method;
                method = data.getClass().getDeclaredMethod(setterName,
                        String.class);
                method.invoke(data, value.stringValue(ctxt));
            }

        } catch (NoSuchMethodException e) {
            throw new AnalysisException(e.getMessage(), e);
        } catch (SecurityException e) {
            throw new AnalysisException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new AnalysisException(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new AnalysisException(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new AnalysisException(e.getMessage(), e);
        }
    }

    private boolean isString(Context ctxt)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, ValueException {

        return ctxt.assistantFactory.createPTypeAssistant().isSeq(type) // is
                // Seq
                && ctxt.assistantFactory.createPTypeAssistant().isType(
                ctxt.assistantFactory.createPTypeAssistant() // of char
                        .getSeq(type).getSeqof(), ACharBasicType.class);

    }

    private String makeSetter(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("set");
        sb.append(Character.toUpperCase(name.charAt(0)));
        sb.append(name.substring(1, name.length()));
        return sb.toString();
    }
}
