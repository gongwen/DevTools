package com.gw.tools;

import com.gw.tools.lib.util.StringUtil;
import com.gw.tools.lib.util.Trans2RMB;

import org.junit.Test;

import static com.gw.tools.lib.util.StringUtil.GB2312;
import static com.gw.tools.lib.util.StringUtil.GBK;
import static com.gw.tools.lib.util.StringUtil.ISO_8859_1;
import static com.gw.tools.lib.util.StringUtil.UTF_16;
import static com.gw.tools.lib.util.StringUtil.UTF_16BE;
import static com.gw.tools.lib.util.StringUtil.UTF_16LE;
import static com.gw.tools.lib.util.StringUtil.UTF_8;
import static org.junit.Assert.assertEquals;

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
    public void testUrlEncode() throws Exception {
        String str = StringUtil.urlEncode("http://www.baidu.com?a=d&c=我爱你 123abc", StringUtil.ISO_8859_1);
        System.out.println(str);
        System.out.println(StringUtil.urlDecode(str));
    }

    @Test
    public void testRMBTransformer() throws Exception {
        System.out.println("\n--------将数字转换成中文金额的大写形式------------\n");
        String s = Trans2RMB.transformer("01599995555555.333");
        // 如果转换过后是一个空串，则不输出屏幕
        if (!"".equals(s)) {
            System.out.println("转换成中文后为：" + s);
        }
        System.out.println("\n---------------------------------------------");
    }

    @Test
    public void testCharsetTransformer() throws Exception {
        String str = "This is a 中文的 String!";
        System.out.println("原始str：" + str);

        String gbk = StringUtil.toGBK(str);
        System.out.println("转换成GBK码：" + gbk);
        System.out.println();

        String ascii = StringUtil.toASCII(str);
        System.out.println("转换成US-ASCII：" + ascii);
        System.out.println();

        String iso88591 = StringUtil.toISO_8859_1(str);
        System.out.println("转换成ISO-8859-1码：" + iso88591);
        System.out.println();

        gbk = StringUtil.changeCharset(iso88591, ISO_8859_1, GBK);
        System.out.println("再把ISO-8859-1码的字符串转换成GBK码：" + gbk);
        System.out.println();

        gbk = StringUtil.changeCharset(iso88591, ISO_8859_1, UTF_8);
        System.out.println("再把ISO-8859-1码的字符串转换成UTF-8码：" + gbk);
        System.out.println();

        String utf8 = StringUtil.toUTF_8(str);
        System.out.println();
        System.out.println("转换成UTF-8码：" + utf8);
        String utf16be = StringUtil.toUTF_16BE(str);
        System.out.println("转换成UTF-16BE码：" + utf16be);
        gbk = StringUtil.changeCharset(utf16be, UTF_16BE, GBK);
        System.out.println("再把UTF-16BE编码的字符转换成GBK码：" + gbk);
        System.out.println();

        String utf16le = StringUtil.toUTF_16LE(str);
        System.out.println("转换成UTF-16LE码：" + utf16le);
        gbk = StringUtil.changeCharset(utf16le, UTF_16LE, GBK);
        System.out.println("再把UTF-16LE编码的字符串转换成GBK码：" + gbk);
        System.out.println();

        String utf16 = StringUtil.toUTF_16(str);
        System.out.println("转换成UTF-16码：" + utf16);
        String gb2312 = StringUtil.changeCharset(utf16, UTF_16, GB2312);
        System.out.println("再把UTF-16编码的字符串转换成GB2312码：" + gb2312);
    }
}