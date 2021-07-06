package com.newtours.tests;

import com.newtours.pages.FindFlightPage;
import com.newtours.pages.FlightDetailsPage;
import com.newtours.pages.RegistrationConfirmationPage;
import com.newtours.pages.RegistrationPage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import tests.BaseTest;

public class BookFlightTest extends BaseTest {

    private String noOfPassengers;

    @BeforeTest
    @Parameters({"noOfPassengers"})
    public void setupParameters(String noOfPassengers){
        this.noOfPassengers = noOfPassengers;
    }

    @Test(description = "Registration")
    public void registrationPage(){
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.goTo();;
        registrationPage.enterUserDetails("selenium","docker");
        registrationPage.enterUserCredentials("selenium","docker");
        registrationPage.submit();
    }

    @Test(description = "Registration Confirmation ", dependsOnMethods = "registrationPage")
    public void registrationConfirmationPage(){
        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);
        registrationConfirmationPage.goToFlightDetailsPage();
    }

    @Test(description = "Flight Details Page ",dependsOnMethods = "registrationConfirmationPage")
    public void flightDetailsPage(){
        FlightDetailsPage flightDetailsPage = new FlightDetailsPage(driver);
        flightDetailsPage.selectPassengers(this.noOfPassengers);
        flightDetailsPage.goToFindFlightsPage();
    }

    @Test(description = "Search Flight ",dependsOnMethods = "flightDetailsPage")
    public void findFlightPage(){
        FindFlightPage findFlightPage = new FindFlightPage(driver);
        findFlightPage.submitFindFlightPage();
        findFlightPage.goToFlightConfirmationPage();
    }


}