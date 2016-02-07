package org.overturetool.tempoui.example;

import org.overture.interpreter.debug.RemoteInterpreter;
import org.overture.interpreter.values.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldc on 01/02/16.
 */
public class ModelAPI {

    public static final String ROOT_NAME = "plant";
    private static int expertCounter = 0;

    public static void setInterp(RemoteInterpreter interp) {
        ModelAPI.interp = interp;
    }

    private static RemoteInterpreter interp;

    public static List<Expert> getAllExperts() throws Exception {
        StringBuilder cmd = new StringBuilder();
        cmd.append("schedule");
        ValueMap sched = interp.valueExecute(cmd.toString()).deref().mapValue(null);

        List<Expert> r = new ArrayList<>();

        for (Value k : sched.keySet()) {
            for (Value e : sched.get(k).deref().setValue(null)) {

                RecordValue rec = (RecordValue) e.deref();
                r.add(new Expert(extractId(rec.fieldmap.get("expertid")), extractId(k),
                        UiDataModel.string2Enum(rec.fieldmap.get("quali").quoteValue(null))));
            }
        }
        return r;
    }

    private static String extractId(Value s) {
        return s.deref().toString().trim().replace("mk_token(\"", "").replace("\")", "");
    }


    public static void addExpert(UiDataModel.Alarm quali, String period) throws Exception {
        //AssignExpert(mk_Expert(mk_token("e1"),<Chem>),mk_token("p1))
        StringBuilder cmd = new StringBuilder();
        cmd.append("AssignExpert(mk_Expert(mk_token(\"");
        cmd.append(makeExpertId());
        cmd.append("\"),");
        cmd.append(quali);
        cmd.append("),mk_token(\"");
        cmd.append(period);
        cmd.append("\"))");
        interp.execute(cmd.toString());
    }

    private static String makeExpertId() {
        expertCounter++;
        return "E" + Integer.toString(expertCounter);
    }


}
