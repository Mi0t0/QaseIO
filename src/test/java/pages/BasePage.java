package pages;

import wrappers.Input;
import wrappers.Picklist;

public abstract class BasePage {

    Input input;

    Picklist picklist;

    public abstract boolean isPageOpened();
}
