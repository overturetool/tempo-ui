package org.overturetool.tempoui.example;

import org.overture.interpreter.debug.RemoteInterpreter;

/**
 * Created by ldc on 01/02/16.
 */
public class ModelAPI {

    public static final String ROOT_NAME = "plant";

    private static RemoteInterpreter interp;

    public static void spawnExpert() throws Exception {
        String id = Double.toString(Math.random());
        String per = Double.toString(Math.random());

        interp.execute(ROOT_NAME + ".AddExpert(mk_Expert(mk_token_("+id+"), {<Chem>},mk_token("+per+"))");
    }

    public static void foo() throws Exception{

    }



}
