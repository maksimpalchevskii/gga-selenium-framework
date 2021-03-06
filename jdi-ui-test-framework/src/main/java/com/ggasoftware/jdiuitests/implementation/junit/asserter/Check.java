package com.ggasoftware.jdiuitests.implementation.junit.asserter;

import com.ggasoftware.jdiuitests.core.asserter.BaseChecker;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JActionT;
import org.junit.Assert;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Check extends BaseChecker {
    public Check() {
        super();
    }

    public Check(String checkMessage) {
        super(checkMessage);
    }

    @Override
    protected JActionT<String> throwFail() {
        return Assert::fail;
    }
}
