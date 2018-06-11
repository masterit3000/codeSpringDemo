package com.elearningbackend.utility;

import org.junit.Test;

import static org.junit.Assert.*;

public class SecurityUtilTest {
    @Test
    public void sha256() throws Exception {
        System.out.println(SecurityUtil.sha256("1234qwer"));
    }

}