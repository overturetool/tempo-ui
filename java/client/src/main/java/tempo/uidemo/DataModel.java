package tempo.uidemo;

import net.java.html.json.Function;
import net.java.html.json.Model;
import net.java.html.json.Property;

/** Model annotation generates class Data with
 * one message property, boolean property and read only words property
 */
@Model(className = "Data", targetId="", properties = {
    @Property(name = "opCount", type = long.class)
})
final class DataModel {


    @Function static void callOp(Data model) throws Exception {
        VdmControl.callOp();
    }

    private static Data ui;
    /**
     * Called when the page is ready.
     */
    static Data onPageLoad() throws Exception {
        ui = new Data();
        ui.applyBindings();
        return ui;
    }
}
