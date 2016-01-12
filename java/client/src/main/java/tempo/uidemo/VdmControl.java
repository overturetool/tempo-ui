/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tempo.uidemo;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.java.html.boot.BrowserBuilder;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.ACharBasicType;
import org.overture.ast.types.AIntNumericBasicType;
import org.overture.ast.types.ASeqSeqType;
import org.overture.ast.types.PType;
import org.overture.interpreter.debug.RemoteControl;
import org.overture.interpreter.debug.RemoteInterpreter;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;

import com.google.auto.value.AutoValue;

/**
 * Class for controlling model execution via remote interpreter. Also doubles as
 * the main class.
 * 
 * @author ldc
 */
public class VdmControl implements RemoteControl {

	private static final String ROOT_NAME = "root";
	private static RemoteInterpreter interp;

	public static void callOp() throws Exception {
		interp.execute(ROOT_NAME + ".CallOp()");
	}

	/**
	 * Link the interpreter. Start the browser in asynchronous mode.
	 * 
	 */
	@Override
	public void run(RemoteInterpreter ri) throws Exception {
		interp = ri;
		new Thread(new Runnable() {
			public void run() {
				try {
					BrowserBuilder.newBrowser().loadPage("pages/index.html")
							.loadClass(VdmControl.class).invoke("onPageLoad")
							.showAndWait();
					finish();
				} catch (Exception ex) {
					Logger.getLogger(VdmControl.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}).start();
	}

	/**
	 * Called when the page is ready. Attach listeners to bound variables.
	 */
	public static void onPageLoad() throws Exception {
		Data d = DataModel.onPageLoad();
		
		ASeqSeqType stringType = new ASeqSeqType();
		stringType.setSeqof(new ACharBasicType());
		
		List<BoundVarInfo> varsToBind = Arrays.asList(new BoundVarInfo[] {BoundVarInfo.create("opCount", new AIntNumericBasicType()),
				BoundVarInfo.create("opMsg", stringType)});
		
		attachListeners(varsToBind, "new Control()", d);
		
	}

	public static void attachListeners(List<BoundVarInfo> vars,
			String rootConstructor, Data d) throws Exception {
		String root = ROOT_NAME;
		interp.create(root, rootConstructor);
		Value v = interp.valueExecute(root);
		if (v.deref() instanceof ObjectValue) {
			NameValuePairMap members = ((ObjectValue) v.deref()).members;
			for (Entry<ILexNameToken, Value> p : members.entrySet()) {
				for (BoundVarInfo bv : vars) {
					if (bv.name().equals(p.getKey().getName())) {
						if (p.getValue() instanceof UpdatableValue) {
							UpdatableValue u = (UpdatableValue) p.getValue();
							u.addListener(new BoundVarListener(d, bv.name(), bv.type()));
							break;
						}
					}
				}

			}
		}
	}

	public static void finish() {
		interp.finish();
		System.exit(0);
	}

	@AutoValue
	abstract static class BoundVarInfo {
		static BoundVarInfo create(String name, PType type) {
			return new AutoValue_VdmControl_BoundVarInfo(name, type);
		}

		abstract String name();

		abstract PType type();
	}

}
