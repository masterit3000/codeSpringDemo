package com.elearningbackend.utility;

import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by dohalong on 02/12/2017.
 */
@RunWith(JMockit.class)
public class PaginatorTest {
    @Test
    public void returnHigherNumberWhenTotalElementsCannotDivideNoOfRowPerPage() throws Exception {
        assertThat(Paginator.calculateTotalPage(20,13), is(2));
    }

    @Test
    public void returnValidNumberWhenTotalElementsCanDivideNoOfRowPerPage() throws Exception {
        assertThat(Paginator.calculateTotalPage(20,5), is(4));
    }
}