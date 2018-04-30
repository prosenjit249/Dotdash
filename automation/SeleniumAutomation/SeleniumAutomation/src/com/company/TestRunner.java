package com.company;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(FeatureTests.class);

        System.out.printf("[+] Total Number of tests Run : %d\n",result.getRunCount());
        System.out.printf("\n[-] Failed Tests : %s\n",result.getFailureCount());

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }


    }
}
