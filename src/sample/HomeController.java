package sample;

import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.commons.math3.analysis.function.Cos;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.lists.VmList;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import sample.Helpers.Bus;
import sample.Helpers.Constants;
import sample.Helpers.Messages;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HomeController extends Base{

    @FXML
    public AnchorPane anchorPaneResize;
    @FXML
    public JFXTextField task_txt;
    @FXML
    public JFXTextField vm_txt;


    @FXML
    private JFXListView<VBox> algorithmListView;

    /** The cloudlet list. */
    private static List<Cloudlet> cloudletList;


    Techniques techniques;


    ArrayList<String> algorithms;

    /** The vmlist. */
    private static List<Vm> vmlist;

    public void initialize() {
        checkMaximize();
        navigationTollTips();
        algorithms = new ArrayList<>();
        algorithms.add(Constants.RANDOM);
        algorithms.add(Constants.ROUND_ROBIN);
        algorithms.add(Constants.MIN_MIN);
        algorithms.add(Constants.MIN_MAX);
        algorithms.add(Constants.HONEY_BEE);
        algorithms.add(Constants.ALL_ALGO);
        setTaskValidator();
        setVmValidator();


        for (String s :algorithms) {

            Label label = getSegoiLabel(s);
            VBox vbox = new VBox(label);
            vbox.setSpacing(10);
            //vbox.setPrefHeight(300);
            vbox.setPadding(new Insets(10));
            algorithmListView.getItems().add(vbox);
        }
        algorithmListView.setExpanded(true);
        algorithmListView.setDepth(3);
        algorithmListView.setOrientation(Orientation.HORIZONTAL);
    }
    public void setTaskValidator() {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage(Messages.INVALID_INPUT);
        NumberValidator numberValidator1 = new NumberValidator();
        numberValidator1.setMessage(Messages.INVALID_INPUT);
        task_txt.getValidators().add(numberValidator1);
        task_txt.getValidators().add(requiredFieldValidator);
    }
    public void setVmValidator() {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage(Messages.INVALID_INPUT);
        NumberValidator numberValidator1 = new NumberValidator();
        numberValidator1.setMessage(Messages.INVALID_INPUT);
        vm_txt.getValidators().add(numberValidator1);
        vm_txt.getValidators().add(requiredFieldValidator);
    }






    @FXML
    void startSimulationAction(ActionEvent event) {


        boolean a = vm_txt.validate();
        boolean b = task_txt.validate();
        if (!b || !a) return;
        int numberOfVM  = Integer.parseInt(vm_txt.getText());
        int numberOfCloudLets = Integer.parseInt(task_txt.getText());


        JFXSnackbar toastMessage = new JFXSnackbar(stack_pane);
        if (numberOfCloudLets < 1 || numberOfVM < 1) {

            toastMessage.show(Messages.LESS_THEN_ONE, Constants.TOST_DURATION);
            vm_txt.setText("");
            task_txt.setText("");

            return;
        }

        /*Check Algorithm Selected or not*/
        VBox vbox = algorithmListView.getSelectionModel().getSelectedItem();
        if (vbox == null) {
            toastMessage.show(Messages.SELECT_ONE, Constants.TOST_DURATION);
            return;
        }


        Log.printLine("Starting CloudSimExample2...");

        try {
            // First step: Initialize the CloudSim package. It should be called
            // before creating any entities.            int num_user = 1;   // number of cloud users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;  // mean trace events
            int num_user = 1;

            // Initialize the CloudSim library
            CloudSim.init(num_user, calendar, trace_flag);

            // Second step: Create Datacenters
            //Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
            @SuppressWarnings("unused")
            Datacenter datacenter0 = createDatacenter("Datacenter_0");

            //Third step: Create Broker
            DatacenterBroker broker = createBroker();
            int brokerId = broker.getId();

            //Fourth step: Create one virtual machine
            vmlist = createVM(brokerId, numberOfVM, 0);

            //submit vm list to the broker
            broker.submitVmList(vmlist);


            //Fifth step: Create two Cloudlets
            cloudletList = createCloudlet(brokerId, numberOfCloudLets, 0);


            //submit cloudlet list to the broker
            broker.submitCloudletList(cloudletList);


            //bind the cloudlets to the vms. This way, the broker
            // will submit the bound cloudlets only to the specific VM


            Map<String, Integer> vm_cloudLet_map = new HashMap<>();
            //initlizing list
            for (Vm vm: vmlist){
                String key = "VM "+ vm.getId();
                vm_cloudLet_map.put(key, 0);
            }

            //Random Binding





            Label label =(Label) vbox.getChildren().get(0);

            String selectedAlgo = label.getText();

            if (selectedAlgo.equals(Constants.RANDOM)) {
                for(int i=0;i<cloudletList.size();i++) {
                    Random rand = new Random();
                    int index = rand.nextInt(vmlist.size());
                    Vm vm = vmlist.get(index);

                    String key = "VM " + vm.getId();
                    vm_cloudLet_map.put(key, vm_cloudLet_map.get(key) + 1);
                    broker.bindCloudletToVm(cloudletList.get(i).getCloudletId(),vm.getId());
                }
            }
            if (selectedAlgo.equals(Constants.ROUND_ROBIN)) {
                /*WRITE code to impelement roud robin*/

            }
            if (selectedAlgo.equals(Constants.MIN_MIN)) {
                /*Write code to implement MIN MIN algo*/
            }
            if (selectedAlgo.equals(Constants.MIN_MAX)) {
                /*Write code to implement MIN_MAX*/
            }





            System.out.println(vm_cloudLet_map.toString());

            /*Create technique*/
            techniques = new Techniques("Radom", vm_cloudLet_map);

            // Sixth step: Starts the simulation
            CloudSim.startSimulation();


            // Final step: Print results when simulation is over
            List<Cloudlet> newList = broker.getCloudletReceivedList();

            CloudSim.stopSimulation();

            printCloudletList(newList);

            Log.printLine("CloudSimExample2 finished!");
            //techniquesList.add(techniques);
            techniques.setCloudHeaders(tableColumn);
            Bus.getInstance().addTech(techniques);
            Bus.getInstance().setVmListGlobal(vmlist);
            System.out.println("Tech size " + techniquesList.size());
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.printLine("The simulation has been terminated due to an unexpected error");
        }


    }



    private static Datacenter createDatacenter(String name){

        // Here are the steps needed to create a PowerDatacenter:
        // 1. We need to create a list to store
        // our machine
        List<Host> hostList = new ArrayList<Host>();

        // 2. A Machine contains one or more PEs or CPUs/Cores.
        // In this example, it will have only one core.
        List<Pe> peList = new ArrayList<Pe>();

        int mips = 2800;
        int hostId = 0;
        peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
        peList.add(new Pe(1, new PeProvisionerSimple(mips)));
        peList.add(new Pe(2, new PeProvisionerSimple(mips)));
        peList.add(new Pe(3, new PeProvisionerSimple(mips)));

        for (int i = 0; i < 25; i++) {
            // 3. Create PEs and add these into a list.
            // peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id
            // and MIPS Rating

            // 4. Create Hosts with its id and list of PEs and add them to the list of
            // machines

            int ram = 36000; // host memory (MB)
            long storage = 1000000; // host storage
            int bw = 40000;

            hostList.add(new Host(hostId, new RamProvisionerSimple(ram), new BwProvisionerSimple(bw), storage, peList,
                    new VmSchedulerTimeShared(peList))); // This is our first machine
            hostId++;
        }

        // 5. Create a DatacenterCharacteristics object that stores the
        // properties of a data center: architecture, OS, list of
        // Machines, allocation policy: time- or space-shared, time zone
        // and its price (G$/Pe time unit).
        String arch = "x86"; // system architecture
        String os = "CentOs"; // operating system
        String vmm = "Xen";
        double time_zone = 10.0; // time zone this resource located
        double cost = 3.0; // the cost of using processing in this resource
        double costPerMem = 0.05; // the cost of using memory in this resource
        double costPerStorage = 0.001; // the cost of using storage in this resource
        double costPerBw = 0.0; // the cost of using bw in this resource
        LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN devices by now

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(arch, os, vmm, hostList, time_zone,
                cost, costPerMem, costPerStorage, costPerBw);

        // 6. Finally, we need to create a PowerDatacenter object.
        Datacenter datacenter = null;

        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datacenter;
    }

    //We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
    //to the specific rules of the simulated scenario
    private static DatacenterBroker createBroker(){

        DatacenterBroker broker = null;
        try {
            broker = new DatacenterBroker("Broker");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return broker;
    }

    /**
     * Prints the Cloudlet objects
     * @param list  list of Cloudlets
     */
    private  void printCloudletList(List<Cloudlet> list) {
        int size = list.size();
        Cloudlet cloudlet;
        tableColumn.add("Cloudlet ID");
        tableColumn.add("STATUS");
        tableColumn.add("Data center ID");
        tableColumn.add("VM ID");
        tableColumn.add("Time");
        tableColumn.add("Start Time");
        tableColumn.add("Finish Time");


        List<List<String>>  logList = new ArrayList<>();


        String indent = "    ";
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
                "Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            List<String> logData = new ArrayList<>();
            cloudlet = list.get(i);
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);
            logData.add(String.valueOf(cloudlet.getCloudletId()));

            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
                Log.print("SUCCESS");
                logData.add("SUCCESS");
                logData.add(String.valueOf(cloudlet.getResourceId()));
                logData.add(String.valueOf(cloudlet.getVmId()));
                logData.add(dft.format(cloudlet.getActualCPUTime()));
                logData.add(dft.format(cloudlet.getExecStartTime()));
                logData.add(dft.format(cloudlet.getFinishTime()));



                Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
                        indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
                        indent + indent + dft.format(cloudlet.getFinishTime()));
            }else{
                logData.add("FAIL");
                logData.add("");
                logData.add("");
                logData.add("");
                logData.add("");
                logData.add("");
            }
            logList.add(logData);
        }

        techniques.setCloudLogs(logList);


    }

    private static List<Vm> createVM(int userId, int vms, int idShift) {

        // Creates a container to store VMs. This list is passed to the broker later
        LinkedList<Vm> list = new LinkedList<Vm>();

        // VM Parameters
        int randomNum = ThreadLocalRandom.current().nextInt(200, 1001);

        long size = 10000; // image size (MB)

        int ram = 512; // vm memory (MB)
//			int ram = ThreadLocalRandom.current().nextInt(200, 400);
//			int mips = ThreadLocalRandom.current().nextInt(200, 400);
//			randomNum = ThreadLocalRandom.current().nextInt(1000, 2501);
//			long bw = randomNum;
//			randomNum = ThreadLocalRandom.current().nextInt(1,5);
//			int pesNumber = randomNum; //number of cpus

        String vmm = "Xen"; // VMM name

        // create VMs
        Vm[] vm = new Vm[vms];

        for (int i = 0; i < vms; i++) {

            int mips = ThreadLocalRandom.current().nextInt(200, 400);
            randomNum = ThreadLocalRandom.current().nextInt(1000, 2501);
            long bw = randomNum;
            randomNum = ThreadLocalRandom.current().nextInt(1, 5);
            int pesNumber = randomNum; // number of cpus

            vm[i] = new Vm(idShift + i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
            list.add(vm[i]);
        }

        return list;
    }
    private static List<Cloudlet> createCloudlet(int userId, int cloudlets, int idShift) {
        // Creates a container to store Cloudlets
        LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

        // cloudlet parameters
        long length = 40000;
        long fileSize = 300;
        long outputSize = 300;
        Random randomNumberGenerator = new Random();
//			int random_number = randomNumberGenerator.nextInt(4);
//			if(random_number == 0) {
//				random_number +=1;
//			}
        int pesNumber = 2;
        UtilizationModel utilizationModel = new UtilizationModelFull();

        Cloudlet[] cloudlet = new Cloudlet[cloudlets];

        for (int i = 0; i < cloudlets; i++) {
            cloudlet[i] = new Cloudlet(idShift + i, length, pesNumber, fileSize, outputSize, utilizationModel,
                    utilizationModel, utilizationModel);
            // setting the owner of these Cloudlets
            cloudlet[i].setUserId(userId);
            list.add(cloudlet[i]);
        }

        return list;
    }




}
