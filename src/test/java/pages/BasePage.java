package pages;

import helpers.InputHelper;
import helpers.PicklistHelper;

public abstract class BasePage {

    protected InputHelper input;

    protected PicklistHelper picklist;

    public abstract boolean isPageOpened();
}
