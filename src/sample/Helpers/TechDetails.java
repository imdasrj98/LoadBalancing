package sample.Helpers;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.LineInputStream;
import org.cloudbus.cloudsim.Vm;
import sample.Techniques;

import java.util.ArrayList;
import java.util.List;

public class TechDetails  {
    List<Techniques> techniquesList;
    List<Vm> vmListGlobal;
    public TechDetails() {
        techniquesList = new ArrayList<>();
        vmListGlobal = new ArrayList<>();
    }


    public List<Vm> getVmListGlobal() {
        return vmListGlobal;
    }

    public void setVmListGlobal(List<Vm> vmListGlobal) {
        this.vmListGlobal = vmListGlobal;
    }


    public List<Techniques> getTechniquesList() {
        return techniquesList;
    }

    public void addTech(Techniques techniques) {
        techniquesList.add(techniques);
    }

    public void removeTech(int index) {
        techniquesList.remove(index);
    }

}
