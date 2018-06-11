package com.elearningbackend.utility;

import mockit.integration.junit4.JMockit;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JMockit.class)
public class CodeGeneratorTest {
    @Test
    public void getCodePostFix() throws Exception {
        assertThat(CodeGenerator.getCodePostFix().length(), CoreMatchers.is(6));
    }

}