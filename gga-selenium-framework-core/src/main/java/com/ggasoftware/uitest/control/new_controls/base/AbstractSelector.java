package com.ggasoftware.uitest.control.new_controls.base;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.base.map.MapArray;
import com.ggasoftware.uitest.control.new_controls.complex.TextList;
import com.ggasoftware.uitest.utils.LinqUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.ggasoftware.uitest.control.base.asserter.testNG.Assert.exception;
import static com.ggasoftware.uitest.utils.PrintUtils.print;
import static com.ggasoftware.uitest.utils.Timer.waitCondition;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public abstract class AbstractSelector<TEnum extends Enum, P> extends TemplatesList<SelectElement, TEnum, P> {
    protected TextList<TEnum, P> allLabels;

    public AbstractSelector() {
        super();
    }

    public AbstractSelector(By optionsNamesLocatorTemplate) {
        super(optionsNamesLocatorTemplate, new SelectElement(optionsNamesLocatorTemplate));
    }

    public AbstractSelector(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, new SelectElement(optionsNamesLocatorTemplate));
        allLabels = new TextList<>(allOptionsNamesLocator);
    }

    public AbstractSelector(By optionsNamesLocatorTemplate, TEnum enumMember) {
        super(optionsNamesLocatorTemplate, new SelectElement(optionsNamesLocatorTemplate), enumMember);
    }

    public AbstractSelector(String name, String locator, P parentPanel) {
        super(name, locator, parentPanel);
    }

    protected SelectElement<P> getDefaultElement(By locator) {
        return new SelectElement<>(locator);
    }

    protected void selectAction(String name) {
        if (haveLocator() && getLocator().toString().contains("%s"))
            getElement(name).click();
        else
            new Select(new Element(getLocator()).getWebElement()).selectByVisibleText(name);
    }

    protected void selectByIndexAction(int index) {
        if (index < 0)
            throw exception("Failed to do selectByIndex for '%s'. Index should be greater than 0");
        if (allLabels != null || elementsNames != null)
            getElement(getNames().get(index)).click();
        else
            new Select(getWebElement()).selectByIndex(index);

    }

    protected boolean waitSelectedAction(String value) {
        return waitCondition(() -> getElement(value).isSelected());
    }

    protected MapArray<String, WebElement> getElementsAction() {
        return new MapArray<>(getNames(), name -> name, this::getWebElement);
    }

    protected List<String> getNames() {
        if (allLabels == null && elementsNames == null)
            throw exception(format("Please specify 'allOptionsNamesLocator' locator or Enum to work with getAllElements method for element '%s'", this.toString()));
        List<String> names = (elementsNames != null)
                ? elementsNames
                : allLabels.getLabels();
        if (names == null || names.size() == 0)
            throw exception(format("No labels found for element '%s'", this.toString()));
        return names;
    }

    public final List<String> getOptions() {
        if (allLabels == null && elementsNames == null && !getLocator().toString().contains("%s")) {
            Select select = new Select(getWebElement());
            return (List<String>) LinqUtils.select(select.getOptions(), WebElement::getText);
        } else return getNames();
    }

    public final String getOptionsAsText() {
        return print(getOptions());
    }

    protected String getValueAction() {
        return format("getValueAction not implemented for '%s'", toString());
    }

    protected void setValueAction(String value) {
    }

    public final String getValue() {
        return doJActionResult("Get value", this::getValueAction);
    }

    public final void setValue(String value) {
        doJAction("Set value", () -> setValueRule(value, this::setValueAction));
    }

    @Override
    public int count() {
        if (getOptions().size() > 0)
            return getOptions().size();
        return getElementsList().size();
    }
}
