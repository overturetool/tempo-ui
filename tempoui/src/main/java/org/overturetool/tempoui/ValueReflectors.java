package org.overturetool.tempoui;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.types.*;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.assistant.type.PTypeAssistantInterpreter;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.Interpreter;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * {@link ValueReflectors} is a library for converting
 * VDM {@link org.overture.interpreter.values.Value}s and setting
 * them into fields of Data classes via reflection.
 * Created by ldc on 02/02/16.
 */
public abstract class ValueReflectors {

    /**
     * Main reflection method. Takes the new value (and support information) of a VDM variable
     * and updates the corresponding field in a UI data class.
     *
     * @param value the new value to be set into the data class
     * @param var   name and type information for the variable corresponding to the value
     * @param ctxt  {@link Context} information. Only available when the variable changes value
     * @param data  reference to the Data class.
     * @throws AnalysisException when reflection exceptions occur while trying to set the new value or when Analysis exceptions occur when trying to compute
     *                           the concrete type information of the value.
     */
    public static void reflectIntoData(Value value, ModelBinder.VarBindInfo var, Context ctxt, Object data) throws AnalysisException {
        IInterpreterAssistantFactory assistantFactory = (ctxt == null) ? Interpreter.getInstance().getAssistantFactory() : ctxt.assistantFactory;

        try {

            if (assistantFactory.createPTypeAssistant().isType(var.type(),
                    AIntNumericBasicType.class)) {
                Method method;
                method = data.getClass().getDeclaredMethod(makeSetter(var.name()), long.class);
                method.invoke(data, value.intValue(ctxt));
            }

            if (assistantFactory.createPTypeAssistant().isType(var.type(),
                    ARealNumericBasicType.class)) {
                Method method;
                method = data.getClass().getDeclaredMethod(makeSetter(var.name()),
                        double.class);
                method.invoke(data, value.stringValue(ctxt));
            }

            if (assistantFactory.createPTypeAssistant().isType(var.type(),
                    ABooleanBasicType.class)) {
                Method method;
                method = data.getClass().getDeclaredMethod(makeSetter(var.name()),
                        boolean.class);
                method.invoke(data, value.boolValue(ctxt));
            }

            if (isString(var.type(), assistantFactory.createPTypeAssistant())) {
                Method method;
                method = data.getClass().getDeclaredMethod(makeSetter(var.name()),
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

    /**
     * Alternative reflector method for reflecting values without context.
     * <p>
     * This method is mostly used to initial values of variables when no context information is available.
     *
     * @param value the new value to be set into the data class
     * @param var   name and type information for the variable corresponding to the value
     * @param data  reference to the Data class.
     * @throws AnalysisException when reflection exceptions occur while trying to set the new value or when Analysis exceptions occur when trying to compute
     *                           the concrete type information of the value.
     */
    public static void reflectIntoData(Value value, ModelBinder.VarBindInfo var, Object data) throws AnalysisException {
        reflectIntoData(value, var,null, data);
    }

    private static boolean isString(PType type, PTypeAssistantInterpreter pTypeAssistant)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, ValueException {

        return pTypeAssistant.isSeq(type) // is
                // Seq
                && pTypeAssistant.isType(
                pTypeAssistant // of char
                        .getSeq(type).getSeqof(), ACharBasicType.class);

    }

    private static String makeSetter(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("set");
        sb.append(Character.toUpperCase(name.charAt(0)));
        sb.append(name.substring(1, name.length()));
        return sb.toString();
    }

}
