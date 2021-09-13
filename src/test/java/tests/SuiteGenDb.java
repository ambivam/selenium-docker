package tests;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuiteGenDb {
    //@Test
    public void runTestNGTest()
    {   //Create an instance on TestNG

        //******************************************************
        //"select usid,tcid, concat(usid,\"_\",tcid) as testcases from predictions limit 10"

        Connection con = null;
        Statement rsstmt = null;
        Statement resultsetstmt = null;
        ResultSet rs = null;
        ResultSet testResultSet = null;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/automationdb","root","root");
            rsstmt = con.createStatement();
            rs = rsstmt.executeQuery("select distinct tagline from predictions ");
            /*while(rs.next()){
                System.out.println(rs.getString("tagline").trim().replaceAll("\\s",""));
            }*/

        /*}catch(Exception e){
            e.printStackTrace();
        }*/
        //******************************************************
        String suitename = null;
        String tagline = null;
        //try{
            while(rs.next()){

                System.out.println("the count is ");
                tagline = rs.getString("tagline");

                String tempReg = rs.getString("tagline").trim().replaceAll("\\s","");
                suitename = tempReg.replaceAll("\\'", "");
                //System.out.println(rs.getString("tagline").trim().replaceAll("\\s",""));
                //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%To Be modularized%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
                System.out.println("suitename is "+ suitename);
                System.out.println("The tag line is "+tagline);
                Map<String,String> testngParams = new HashMap<String,String>();
                //testngParams.put("device1", "Desktop");
                TestNG myTestNG = new TestNG();

                //Create an instance of XML Suite and assign a name for it.
                XmlSuite mySuite = new XmlSuite();
                mySuite.setName(suitename);
                mySuite.setParallel(XmlSuite.ParallelMode.METHODS);

                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$Test Module start$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                XmlTest myTest = new XmlTest(mySuite);
                myTest.setName(suitename);
                //add groups
                    /*myTest.addIncludedGroup("PrometheusHome");
                    myTest.addIncludedGroup("AlertMgrHome");

                    //Add any parameters that you want to set to the Test.
                    myTest.setParameters(testngParams);*/

                //select usid,tcid,concat(usid,"_",tcid) as testcases from predictions where tagline in ('Logos')

                //Create a list which can contain the classes that you want to run.
                List<XmlClass> myClasses = new ArrayList<XmlClass>();
                rsstmt = con.createStatement();
                testResultSet = rsstmt.executeQuery("select usid,tcid,concat(usid,\"_\",tcid) as testcases from predictions where tagline in ('"+tagline+"')");
                String temp = null;
                while(testResultSet.next()) {
                    //Create an instance of XmlTest and assign a name for it.

                    //"\""+blogName+"\""

                    //temp = "tests."+"\""+testResultSet.getString(3)+"\"";
                    //temp = "\""+"tests."+testResultSet.getString(3)+"\"";

                    //temp = "\""+"tests."+testResultSet.getString(3)+"\"";

                    temp = "tests."+testResultSet.getString(3);

                    System.out.println("\""+temp+"\"");
                    System.out.println("#####################################");
                    System.out.println(testResultSet.getString(3));
                    System.out.println("#####################################");

                    //myClasses.add(new XmlClass("\""+temp+"\""));
                    //myClasses.add(new XmlClass("haha.maverick"));
                    try{
                        //myClasses.add(new XmlClass("tests.Test1"));
                        myClasses.add(new XmlClass(temp));
                        System.out.println("Added Successfully ha ha"+ temp + "  suitename is  "+suitename);
                        System.out.println("start debugging here");
                    }catch(Exception e){
                        System.out.println("Unable to find class "+temp);
                    }
                    //myClasses.add(new XmlClass("tests.Test10"));
                }

                    //Assign that to the XmlTest Object created earlier.
                    myTest.setXmlClasses(myClasses);

                    //Create a list of XmlTests and add the Xmltest you created earlier to it.
                    List<XmlTest> myTests = new ArrayList<XmlTest>();
                    myTests.add(myTest);

                    //add the list of tests to your Suite.
                    mySuite.setTests(myTests);

                    //Add the suite to the list of suites.
                    List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
                    mySuites.add(mySuite);

                    //Set the list of Suites to the testNG object you created earlier.
                myTestNG.setXmlSuites(mySuites);
                mySuite.setFileName(mySuite+".xml");
                mySuite.setThreadCount(3);
                myTestNG.run();

                    //Create physical XML file based on the virtual XML content
                    for(XmlSuite suite : mySuites)
                    {
                        createXmlFile(suite,suitename);
                    }
                    System.out.println("File generated successfully.");

                    //Print the parameter values
                    /*Map<String,String> params = myTest.getParameters();
                    for(Map.Entry<String, String> entry : params.entrySet())
                    {
                        System.out.println(entry.getKey() + " => " + entry.getValue());
                    }*/



                //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        //******************************************************

    }
    //This method will create an Xml file based on the XmlSuite data
    public void createXmlFile(XmlSuite mSuite,String suitename)
    {
        FileWriter writer;
        String dir = System.getProperty("user.dir");
        try {
            writer = new FileWriter(new File(dir+"//suites//"+suitename+".xml"));
            writer.write(mSuite.toXml());
            writer.flush();
            writer.close();
            //System.out.println(new File("myTemp.xml").getAbsolutePath());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    //Main Method
    public static void main (String args[])
    {
        SuiteGenDb dt = new SuiteGenDb();
        //This Map can hold your testng Parameters.
        //Map<String,String> testngParams = new HashMap<String,String>();
        //testngParams.put("device1", "Desktop");
        dt.runTestNGTest();
    }
}