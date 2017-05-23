package mx.infotec.dads.greenorion.cucumber.stepdefs;

import mx.infotec.dads.greenorion.GreenorionApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = GreenorionApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
