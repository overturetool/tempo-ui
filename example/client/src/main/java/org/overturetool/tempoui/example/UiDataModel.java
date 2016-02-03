package org.overturetool.tempoui.example;

import net.java.html.json.Function;
import net.java.html.json.Model;
import net.java.html.json.Property;

import java.util.List;

/**
 * Model annotation generates class VdmData with
 * one boolean property named "ok".
 */
@Model(className = "VdmData", targetId = "", properties = {
        @Property(name = "statusOk", type = boolean.class)
})
final class UiDataModel {

    @Function
    public static void addAlarm(VdmData d, Alarm a, String desc) throws Exception {
        ModelAPI.addAlarm(a, desc);
    }

    public enum Alarm {
        Elec, Mech, Bio, Chem

    }

    @Function
    static void addExpert(String id, Alarm quali, String period) throws Exception {
        ModelAPI.addExpert(id,quali, period);
    }

}