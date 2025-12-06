package org.vodafoneWeb.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"org.vodafoneWeb.steps", "org.vodafoneWeb.hooks"},
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-html-report.html",
                "rerun:target/rerun.txt"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
