package tempo.uidemo;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.intf.lex.ILexLocation;
import org.overture.interpreter.runtime.Context;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueListener;

public class PropertyListener implements ValueListener {

	final Data d;
	
	public PropertyListener(Data d) {
		super();
		this.d = d;
	}



	@Override
	public void changedValue(ILexLocation arg0, Value arg1, Context arg2)
			throws AnalysisException {
		d.setOpCount(arg1.intValue(arg2));
		
	}

}
