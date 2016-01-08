/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tempo.uidemo;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.html.boot.BrowserBuilder;
import org.overture.interpreter.debug.RemoteControl;
import org.overture.interpreter.debug.RemoteInterpreter;

/**
 * Class for controlling model execution via remote interpreter. Also doubles
 * as the main class.
 * @author ldc
 */
public class VdmControl implements RemoteControl {

    public static RemoteInterpreter interp;
    
    public static void callOp() throws Exception {
        interp.execute("Control`CallOp()");
    }

    @Override
    public void run(RemoteInterpreter ri) throws Exception {
        interp = ri;
                new Thread(new Runnable() {
           public void run() {
               try { 			
                          BrowserBuilder.newBrowser().
            loadPage("pages/index.html").
            loadClass(VdmControl.class).
            invoke("onPageLoad").
            showAndWait();
                         finish();
               } catch (Exception ex) {
                   Logger.getLogger(VdmControl.class.getName()).log(Level.SEVERE, null, ex);
               }
	}
}).start();
    }
    
        /**
     * Called when the page is ready.
     */
    public static void onPageLoad() throws Exception {
        Data d = DataModel.onPageLoad();
        VdmCallables.init(d);
    }
    
    public static void finish(){
       interp.finish();
       System.exit(0);
    }
    
}
