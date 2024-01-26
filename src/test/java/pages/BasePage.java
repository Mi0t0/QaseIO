package pages;

import helpers.InputHelper;
import helpers.PicklistHelper;

public abstract class BasePage {

    InputHelper input;

    PicklistHelper picklist;

    public abstract boolean isPageOpened();
}
