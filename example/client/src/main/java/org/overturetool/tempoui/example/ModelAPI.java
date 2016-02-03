package org.overturetool.tempoui.example;

import org.overture.interpreter.debug.RemoteInterpreter;

import java.util.List;

/**
 * Created by ldc on 01/02/16.
 */
public class ModelAPI {

    public static final String ROOT_NAME = "plant";

    private static RemoteInterpreter interp;

    public static void addAlarm(UiDataModel.Alarm kind, String desc) throws Exception {
        // VDM: AddAlarm(mk_Alarm(desc,<kind>))
        StringBuilder cmd =  new StringBuilder();
        cmd.append("AddAlarm(mk_Alarm(");
        cmd.append(desc);
        cmd.append("),<");
        cmd.append(kind);
        cmd.append(">))");
        interp.execute(cmd.toString());
    }

    public static void addExpert(String id, UiDataModel.Alarm quali, String period) throws Exception {
        //AssignExpert(mk_Expert(mk_token("e1"),<Chem>),mk_token("p1))
        StringBuilder cmd =  new StringBuilder();
        cmd.append("AssignExpert(mk_Expert(mk_token(");
        cmd.append(id);
        cmd.append("),<");
        cmd.append(quali);
        cmd.append(">),mk_token(");
        cmd.append(period);
        cmd.append("))");
        interp.execute(cmd.toString());
    }



}
