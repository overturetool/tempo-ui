package org.overturetool.tempoui.example;

import org.overture.ast.types.ABooleanBasicType;
import org.overture.interpreter.runtime.Interpreter;
import org.overture.interpreter.values.Value;
import org.overturetool.tempoui.ModelBinder;
import org.overturetool.tempoui.TempoRemoteControl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldc on 02/02/16.
 */
public class ExampleRC extends TempoRemoteControl {

    public static void onPageLoad() throws Exception {
        // Create and bind DukeScript Data Model
        VdmData uiData = new VdmData();
        uiData.applyBindings();

        // Build and execute entry point
        String root = "plant";
        String rootConstructor = "new Plant()";
        interpreter.create(root, rootConstructor);
        Value v = interpreter.valueExecute(root);


        // Attach listeners to UI properties to VDM variables
        List<ModelBinder.VarBindInfo> vars = new ArrayList<>(1);
        vars.add(ModelBinder.VarBindInfo.create("ok", new ABooleanBasicType()));
        ModelBinder la = new ModelBinder(v, vars, uiData);
        la.bind();

    }

}
