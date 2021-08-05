package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/java/features/login.feature",
		glue = "stepDefinitions")
/*@CucumberOptions(
        features = "src/test/java/features",
        glue="stepDefinitions",
        tags = "@do")*/
public class TestRunner extends AbstractTestNGCucumberTests{}

