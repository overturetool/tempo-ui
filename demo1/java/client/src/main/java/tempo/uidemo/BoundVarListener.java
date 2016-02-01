package tempo.uidemo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.types.ABooleanBasicType;
import org.overture.ast.types.ACharBasicType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ARealNumericBasicType;
import org.overture.ast.types.PType;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueListener;

/**
 * 
 * @author ldc
 *
 */
public class BoundVarListener implements ValueListener {

	final Object d;
	final PType type;
	final String setterName;

	public BoundVarListener(Object d2, String name, PType t2) {
		d = d2;
		type = t2;
		setterName = makeSetter(name);
	}

	private String makeSetter(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("set");
		sb.append(Character.toUpperCase(name.charAt(0)));
		sb.append(name.substring(1, name.length()));
		return sb.toString();
	}

	@Override
	public void changedValue(ILexLocation loc, Value val, Context ctxt)
			throws AnalysisException {
		try {

			if (ctxt.assistantFactory.createPTypeAssistant().isType(type,
					AIntNumericBasicType.class)) {
				Method method;
				method = d.getClass().getDeclaredMethod(setterName, long.class);
				method.invoke(d, val.intValue(ctxt));
			}

			if (ctxt.assistantFactory.createPTypeAssistant().isType(type,
					ARealNumericBasicType.class)) {
				Method method;
				method = d.getClass().getDeclaredMethod(setterName,
						double.class);
				method.invoke(d, val.stringValue(ctxt));
			}

			if (ctxt.assistantFactory.createPTypeAssistant().isType(type,
					ABooleanBasicType.class)) {
				Method method;
				method = d.getClass().getDeclaredMethod(setterName,
						boolean.class);
				method.invoke(d, val.boolValue(ctxt));
			}

			if (isString(val, ctxt)) {
				Method method;
				method = d.getClass().getDeclaredMethod(setterName,
						String.class);
				method.invoke(d, val.stringValue(ctxt));
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

	private boolean isString(Value val, Context ctxt)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, ValueException {

		return ctxt.assistantFactory.createPTypeAssistant().isSeq(type) // is
																		// Seq
				&& ctxt.assistantFactory.createPTypeAssistant().isType(
						ctxt.assistantFactory.createPTypeAssistant() // of char
								.getSeq(type).getSeqof(), ACharBasicType.class);

	}
}
