/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tempo.uidemo;

import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.VoidValue;

/**
 * Class for exposing DataModel to the VDM Model. Will eventually be replaced
 * by dynamic binding of VDM values to DataModel properties.
 * @author ldc
 */
public class VdmCallables {
    
    static Data data;  
    
    public static void init(Data d){
        data = d;
    }
    
    public static Value setOpCount(Value v) throws ValueException{
        System.out.println("Called setOpCount");
        System.out.println(v.toString());
        Long n = v.intValue(null);
        data.setOpCount(n);
        return new VoidValue();
    }
    
}
