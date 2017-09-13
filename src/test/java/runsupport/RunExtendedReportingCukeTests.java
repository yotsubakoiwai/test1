package runsupport;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;

@CucumberOptions(
		monochrome = true,
		features = "classpath:features",
		plugin = {"pretty", 
				  "html:target/cucumber-html-report", 
				  "json:target/cucumber.json",
				  "usage:target/cucumber-usage.json"
				 },
		glue = { "classpath:steps", "classpath:runsupport" },
		tags = {"@search"}
)
@RunWith(ExtendedCucumberRunner.class)
public class RunExtendedReportingCukeTests {
    @BeforeSuite
    public static void setUp() {
        // TODO: Add your pre-processing
    }
    @AfterSuite
    public static void tearDown() { // See http://mkolisnyk.blogspot.com/2015/05/cucumber-jvm-advanced-reporting.html
    	CucumberResultsOverview results = new CucumberResultsOverview();
    	results.setOutputDirectory("target");
    	results.setOutputName("cucumber-results");
    	results.setSourceFile("target/cucumber.json");
    	try {
			results.executeFeaturesOverviewReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	CucumberUsageReporting report = new CucumberUsageReporting();
    	report.setOutputDirectory("target");
    	report.setJsonUsageFile("target/cucumber-usage.json");
    	try {
			report.executeReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}