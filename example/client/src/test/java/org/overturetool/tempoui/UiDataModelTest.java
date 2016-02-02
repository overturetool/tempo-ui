package org.overturetool.tempoui;

import static org.testng.Assert.*;

import org.overturetool.tempoui.example.VdmData;
import org.testng.annotations.Test;

public class UiDataModelTest {
    @Test
    public void test_Ok_Property() {
        VdmData model = new VdmData();
        model.setOk(false);

        assertFalse(model.isOk());
    }
}
