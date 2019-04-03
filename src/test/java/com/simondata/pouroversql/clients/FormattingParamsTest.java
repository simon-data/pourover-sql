package com.simondata.pouroversql.clients;

import com.simondata.pouroversql.writers.KeyCaseFormat;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FormattingParamsTest {
    @Test
    public void testConstructor() {
        KeyCaseFormat keyCaseFormat = KeyCaseFormat.SNAKE_CASE;
        FormattingParams formattingParams = new FormattingParams(keyCaseFormat);
        assertEquals(keyCaseFormat, formattingParams.getKeyCaseFormat());
    }

    @Test
    public void testDefaultParams() {
        KeyCaseFormat defaultKeyCase = KeyCaseFormat.DEFAULT;
        assertEquals(defaultKeyCase, FormattingParams.getDefaultFormattingParams().getKeyCaseFormat());
    }
}
