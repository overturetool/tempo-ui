/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tempo.uidemo;

import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.java.html.boot.BrowserBuilder;

import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.interpreter.debug.RemoteControl;
import org.overture.interpreter.debug.RemoteInterpreter;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;

/**
 * Class for controlling model execution via remote interpreter. Also doubles as
 * the main class.
 * 
 * @author ldc
 */
public class VdmControl implements RemoteControl {

	private static RemoteInterpreter interp;
	
	public static void callOp() throws Exception {
		interp.execute("m.CallOp()");
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
		interp.create("m", "new Control()");
		Value v = interp.valueExecute("m");
		if (v.deref() instanceof ObjectValue) {
			NameValuePairMap members = ((ObjectValue) v.deref()).members;

			for (Entry<ILexNameToken, Value> p : members.entrySet()) {
				if (p.getKey().getName().equals("opCount")) {
					if (p.getValue() instanceof UpdatableValue) {
						UpdatableValue u = (UpdatableValue) p.getValue();
						u.addListener(new PropertyListener(d));
					}
				}
			}
		}
	}

	public static void finish() {
		interp.finish();
		System.exit(0);
	}

}
