package org.overturetool.tempoui;

import net.java.html.boot.BrowserBuilder;
import org.overture.interpreter.debug.RemoteControl;
import org.overture.interpreter.debug.RemoteInterpreter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>The TempoRemoteControl class provides an interface between the standard
 * Overture {@link RemoteInterpreter} and the DukeScript bindings/UI framework.
 * When developing a UI for a VDM model, this is the class to inherit from.
 * </p>
 * <p>
 * Any subclass of TempoRemoteControl must declare and implement a public static
 * method named <code>onPageLoad()</code>. This method is loaded and executed inside the
 * browser UI via reflection. This method must do three things:
 * <ul>
 * <li>initialize the class generated
 * from the DataModel</li>
 * <li>initialise the interpreter by executing the VDM entry point expression</li>
 * <li>attach UI listeners to the model see ({@link ListenerAttacher}</li>
 * </ul>
 * </p>
 * Created by ldc on 01/02/16.
 */
public class TempoRemoteControl implements RemoteControl {

    private static RemoteInterpreter interpreter;
    private static Object data;

    /**
     * The run method is invoked when running a model from a VDM interpreter under remote control
     * of this class. The method is responsible  is responsible for connecting the interpreter, model and
     * UI.
     * <p>
     * In general, you should avoid overriding this method.
     *
     * @param interpreter_ the interpreter that will run the model. Provided by Overture when launching
     * @throws Exception model interpretation or communication errors are wrapped in generic exceptions due to limitations
     *                   in the Overture Java bridge.
     */
    @Override
    public void run(RemoteInterpreter interpreter_) throws Exception {
        interpreter = interpreter_;
        startBrowser();
    }

    private void startBrowser() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    BrowserBuilder.newBrowser().loadPage("pages/index.html")
                            .loadClass(this.getClass()).invoke("onPageLoad")
                            .showAndWait();
                    interpreter.finish();
                    System.exit(0);
                } catch (Exception
                        ex) {
                    Logger.getLogger(this.getClass().getName()).log(
                            Level.SEVERE, null, ex);
                }
            }
        }).start();
    }


}
