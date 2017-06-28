/*
 *
 * @author Diego Lopes
 * @version 1.0
 */
package moa.streams.generators;
 
import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Attribute;
import com.yahoo.labs.samoa.instances.DenseInstance;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Instances;
import com.yahoo.labs.samoa.instances.InstancesHeader;
import java.util.Arrays;
import java.util.Random;
import moa.core.Example;
import moa.core.FastVector;
import moa.core.InstanceExample;
import moa.core.ObjectRepository;
import moa.options.AbstractOptionHandler;
import moa.streams.InstanceStream;
import moa.tasks.TaskMonitor;
 
/**
 *
 * @author Diego Lopes
 * @version 1.0
 */
public class TelematicCarGenerators extends AbstractOptionHandler implements InstanceStream {
 
    /*
    * OPTIONS
     */
    public IntOption functionOption = new IntOption("function", 'f', "Classification function used, as defined in the original paper.", 1, 1, 5);
    public FloatOption noisePercentage = new FloatOption("noise", 'n', "% of class noise.", 0.05, 0.0, 1.0f);
    public IntOption instanceRandomSeedOption = new IntOption("instanceRandomSeed", 'i', "Seed for random generation of instances.", 1);
    /*
    * INTERNALS
     */
    protected InstancesHeader streamHeader;
    protected Random instanceRandom;
    protected boolean nextClassShouldBeZero;
    protected ClassFunction classFunction;
    /*
    * FEATURE DEFINITIONS
    12 campos no total - tem 8 e faltam esses 4 date, email, longitude, latitude
     */
    protected static String caridValues[] = {"1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9"};
 
    protected static String ageValues[] = {"18",
        "21",
        "25",
        "27",
        "29",
        "35",
        "39",
        "49"};
 
    protected static String avgVelocityValues[] = {"18",
        "19",
        "25",
        "47",
        "65",
        "80",
        "160",
        "200"};
 
    protected static String genderValues[] = {"F", "M"};
    protected static String crashValues[] = {"S", "N"};
 
    protected static String kmValues[] = {"10",
        "39",
        "50",
        "50",
        "60",
        "100",
        "140",
        "200"};
 
    protected static String marchaValues[] = {"S", "N"};
    protected static String roadValues[] = {"S", "N"};
    protected static String classValues[] = {"Perfect", "Regular", "Poor"};
 
    @Override
    public void getDescription(StringBuilder sb, int i) {
 
    }
 
 
    /*
    * Labeling functions
     */
    protected interface ClassFunction {
 
        public int determineClass(String carid,
                String age,
                String avgVelocity,
                String gender,
                String crash,
                String km,
                String marcha,
                String road);
    }
 
    protected static ClassFunction concepts[] = {
        new ClassFunction() {
            @Override
            public int determineClass(String carid,
                    String age,
                    String avgVelocity,
                    String gender,
                    String crash,
                    String km,
                    String marcha,
                    String road) {
 
                if (Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) > 20 || Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) < 90
                        && crashValues.equals("N") && roadValues.equals("N")) {
                    return indexOfValue("Perfect", classValues);
                }
                else if (Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) > 20 || Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) < 90
                        && crashValues.equals("N") && roadValues.equals("Y")) {
                    return indexOfValue("Perfect", classValues);
                } else if (Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) > 20 || Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) < 90
                        && crashValues.equals("Y") && roadValues.equals("N")) {
                    return indexOfValue("Regular", classValues);
                } else if (Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) > 20 || Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) < 90
                        && crashValues.equals("Y") && roadValues.equals("Y")) {
                    return indexOfValue("Poor", classValues);
                } else if (Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) < 20 || Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) > 90
                        && crashValues.equals("N") && roadValues.equals("N")) {
                    return indexOfValue("Perfect", classValues);
                } else if(Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) < 20 || Integer.parseInt(avgVelocityValues[new Random().nextInt(avgVelocityValues.length)]) > 90
                        && crashValues.equals("N") && roadValues.equals("Y")) {
                    return indexOfValue("Regular", classValues);
                }
                else
                    return indexOfValue("Poor", classValues);
            };
 
    }
    };
        /*
        * Generator core
        */
 
        @Override
    public InstancesHeader getHeader() {
        return streamHeader;
    }
 
    @Override
    public long estimatedRemainingInstances() {
        return Integer.MAX_VALUE;
    }
 
    @Override
    public boolean hasMoreInstances() {
        return true;
    }
 
    @Override
    public boolean isRestartable() {
        return true;
    }
 
    @Override
    public void restart() {
        this.instanceRandom = new Random(this.instanceRandomSeedOption.getValue());
        this.nextClassShouldBeZero = false;
    }
 
    int addNoise(int classObtained) {
        if (this.instanceRandom.nextFloat() <= this.noisePercentage.getValue()) {
            classObtained = classObtained == 0 ? 1 : 0;
        }
        return classObtained;
    }
 
    private static int indexOfValue(String value, Object[] arr) {
        int index = Arrays.asList(arr).indexOf(value);
        return index;
    }
 
    @Override
    public void prepareForUseImpl(TaskMonitor tm, ObjectRepository or) {
           
    classFunction = concepts[this.functionOption.getValue() -1];
 
    FastVector attributes = new FastVector();
    attributes.addElement(new Attribute("carid",
        Arrays.asList(caridValues)));
    attributes.addElement(new Attribute("age",
        Arrays.asList(ageValues)));
    attributes.addElement(new Attribute("avgVelocity",
        Arrays.asList(avgVelocityValues)));
    attributes.addElement(new Attribute("gender",
        Arrays.asList(genderValues)));
    attributes.addElement(new Attribute("crash",
        Arrays.asList(crashValues)));
    attributes.addElement(new Attribute("km",
        Arrays.asList(kmValues)));
    attributes.addElement(new Attribute("marcha",
        Arrays.asList(marchaValues)));
    attributes.addElement(new Attribute("road",
        Arrays.asList(roadValues)));
 
    this.instanceRandom = new Random(System.currentTimeMillis());
 
    FastVector classLabels = new FastVector();
    for (int i = 0; i < classValues.length; i++) {
        classLabels.addElement(classValues[i]);
    }
 
    attributes.addElement(new Attribute("class", classLabels));
    this.streamHeader = new InstancesHeader(new Instances(
        getCLICreationString(InstanceStream.class), attributes, 0));
    this.streamHeader.setClassIndex(this.streamHeader.numAttributes() - 1);
 
    restart();
    }
 
    @Override
    public Example<Instance> nextInstance() {
        Instance instnc = null;
 
        //randomize indexes for new instance
        int indexCarid = this.instanceRandom.nextInt(caridValues.length);
        int indexAge = this.instanceRandom.nextInt(ageValues.length);
        int indexAvgVelocity = this.instanceRandom.nextInt(avgVelocityValues.length);
        int indexGender = this.instanceRandom.nextInt(genderValues.length);
        int indexCrash = this.instanceRandom.nextInt(crashValues.length);
        int indexKm = this.instanceRandom.nextInt(kmValues.length);
        int indexMarcha = this.instanceRandom.nextInt(marchaValues.length);
        int indexRoad = this.instanceRandom.nextInt(roadValues.length);
 
        //retrieve values
        String carid = caridValues[indexCarid];
        String age = ageValues[indexAge];
        String avgvelocity = avgVelocityValues[indexAvgVelocity];
        String gender = genderValues[indexGender];
        String crash = crashValues[indexCrash];
        String km = kmValues[indexKm];
        String marcha = marchaValues[indexMarcha];
        String road = roadValues[indexRoad];
        int classValue = classFunction.determineClass(carid, age, avgvelocity, gender, crash, km, marcha, road);
 
        instnc = new DenseInstance(streamHeader.numAttributes());
        //set values
        instnc.setDataset(this.getHeader());
        instnc.setValue(0, Arrays.asList(caridValues).indexOf(carid));
        instnc.setValue(1, Arrays.asList(ageValues).indexOf(age));
        instnc.setValue(2, Arrays.asList(avgVelocityValues).indexOf(avgvelocity));
        instnc.setValue(3, Arrays.asList(genderValues).indexOf(gender));
        instnc.setValue(4, Arrays.asList(crashValues).indexOf(crash));
        instnc.setValue(5, Arrays.asList(kmValues).indexOf(km));
        instnc.setValue(6, Arrays.asList(marchaValues).indexOf(marcha));
        instnc.setValue(7, Arrays.asList(roadValues).indexOf(road));
        instnc.setClassValue((int) classValue);
 
        return new InstanceExample(instnc);
    }
 
}