package com.elearningbackend.utility;

import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class RandomGeneratorTest {
    @Test
    public void testRandom() throws Exception {
        System.out.println(RandomGenerator.random(20));
    }

}