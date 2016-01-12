package tempo.uidemo;

import net.java.html.json.Function;
import net.java.html.json.Model;
import net.java.html.json.Property;

/** Model annotation generates class Data with
 * opCount numeric property and callOp function property
 */
@Model(className = "Data", targetId="", properties = {
    @Property(name = "opCount", type = long.class),
    @Property(name = "textLen", type = long.class),
    @Property(name = "textToCheck", type = String.class)
})
final class DataModel {


    @Function static void tickUp(Data model) throws Exception {
        VdmControl.tickUp();
    }
    
    @Function static void checkText(Data model) throws Exception{
    	VdmControl.checkText(model.getTextToCheck());
    }

    private static Data ui;
    
    /**
     * Called when the page is ready. Need to return the Data class for binding to VDM Vars.
     */
    static Data onPageLoad() throws Exception {
        ui = new Data();
        ui.applyBindings();
        return ui;
    }
}
