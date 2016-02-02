package org.overturetool.tempoui.example;

import net.java.html.json.Model;
import net.java.html.json.Property;

/**
 * Model annotation generates class VdmData with
 * one boolean property named "ok".
 */
@Model(className = "VdmData",targetId="", properties = {
        @Property(name = "ok", type = boolean.class),
})
final class UiDataModel {

}
