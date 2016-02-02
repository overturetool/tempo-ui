package org.overturetool.tempoui;

import com.google.auto.value.AutoValue;
import org.overture.ast.intf.lex.ILexNameToken;
import org.overture.ast.types.PType;
import org.overture.interpreter.debug.RemoteInterpreter;
import org.overture.interpreter.values.NameValuePairMap;
import org.overture.interpreter.values.ObjectValue;
import org.overture.interpreter.values.UpdatableValue;
import org.overture.interpreter.values.Value;

import java.util.List;
import java.util.Map;

/**
 * The ListenerAttacher binds variables in a VDM model to
 * UI elements in a DataModel. This is done by means of attaching
 * {@UiBindListener}s to the model.
 * <p>
 * Usage is simple. Instantiate with desired variables. Invoke {@link #attach()}.
 * Created by ldc on 01/02/16.
 */
public class ListenerAttacher {

    List<VarBindInfo> vars2Bind;
    Value root;
    Object data;


    /**
     * Main Constructor. Each variable to bind must be identified by its name ({@link String})
     * and type ({@link PType}) wrapped in a {@link VarBindInfo}.
     * A reference to the class generated from the Data Model is also needed.
     *
     * @param root      the root value of the executed model, as returned by {@link RemoteInterpreter#create(String, String)}.
     * @param vars2Bind the list of variables to bind.
     * @param data      an Object reference to the class generated from the Data Model.
     */
    public ListenerAttacher(Value root, List<VarBindInfo> vars2Bind, Object data) {
        this.vars2Bind = vars2Bind;
        this.data = data;
        this.root=root;
    }

    /**
     * Attach {@link UiBindListener}s to the submitted vars.
     */
    public void attach() {

        if (root.deref() instanceof ObjectValue) {
            NameValuePairMap members = ((ObjectValue) root.deref()).members;
            for (Map.Entry<ILexNameToken, Value> p : members.entrySet()) {
                for (VarBindInfo bv : vars2Bind) {
                    if (bv.name().equals(p.getKey().getName())) {
                        if (p.getValue() instanceof UpdatableValue) {
                            UpdatableValue u = (UpdatableValue) p.getValue();
                            u.addListener(new UiBindListener(data, bv.name(), bv.type()));
                            //FIXME set initial values
                            break;
                        }
                    }
                }
            }
        }
    }


    /**
     * Auto Value class for holding information on variables to bind.
     * Build values with {@link #create(String, PType)}.
     */
    @AutoValue
    public abstract static class VarBindInfo {
        public static VarBindInfo create(String name, PType type) {
            return new AutoValue_ListenerAttacher_VarBindInfo(name, type);
        }

        abstract String name();

        abstract PType type();
    }

}
