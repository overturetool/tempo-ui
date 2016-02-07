package org.overturetool.tempoui.example;

import net.java.html.json.ComputedProperty;
import net.java.html.json.Function;
import net.java.html.json.Model;
import net.java.html.json.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Model annotation generates class VdmData with
 * one boolean property named "ok".
 */
@Model(className = "VdmData", targetId = "", properties = {
        @Property(name = "statusOk", type = boolean.class),
        @Property(name = "expertQuali", type = String.class),
        @Property(name = "expertId", type = String.class),
        @Property(name = "expertP", type = String.class),
        @Property(name = "updateFlag", type = boolean.class),
        @Property(name = "periodIds", type = String.class, array = true),
})
final class UiDataModel {


    @Model(className = "Expert", properties = {
            @Property(name = "id", type = String.class),
            @Property(name = "period", type = String.class),
            @Property(name = "quali", type = Alarm.class),
    })
    public static class ExpertModel {
    }

    public enum Alarm {
        Elec, Mech, Bio, Chem;

        @Override
        public String toString() {
            return "<" + super.toString() + ">";
        }
    }

    @ComputedProperty
    static java.util.List<Expert> experts(boolean updateFlag) {
        List<Expert> r = new ArrayList<>();
        try {
            r.addAll(ModelAPI.getAllExperts());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Function
    static void addExpert(VdmData model, String data) throws Exception {
        ModelAPI.addExpert(string2Enum(model.getExpertQuali()), model.getExpertP());
        model.setUpdateFlag(!model.isUpdateFlag());
    }

    public static Alarm string2Enum(String s) {
        String s1 = s.replaceAll("<|>", "");
        return Alarm.valueOf(s1);
    }

}