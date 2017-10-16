package com.gw.tools.lib;

import android.util.Base64;

import com.gw.tools.lib.util.StringUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testHash() throws Exception {
        String plainText = "我是真的动了心{}～1";
        System.out.println("plainText-> " + plainText);
        System.out.println("MD5-> " + StringUtil.md5Hash(plainText));
        System.out.println("SHA1-> " + StringUtil.sha1Hash(plainText));
        System.out.println("SHA256-> " + StringUtil.sha256Hash(plainText));
        System.out.println("SHA_512-> " + StringUtil.sha512Hash(plainText));
    }
}