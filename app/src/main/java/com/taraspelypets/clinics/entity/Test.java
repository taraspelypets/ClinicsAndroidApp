package com.taraspelypets.clinics.entity;

import java.util.List;

/**
 * Created by kilopo on 11.07.2017.
 */
public class Test extends BaseEntity {

    private String name;

    private List<TestsResult> testsResults;

    public Test() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestsResult> getTestsResults() {
        return testsResults;
    }

    public void setTestsResults(List<TestsResult> testsResults) {
        this.testsResults = testsResults;
    }

}
