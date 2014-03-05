package jp.co.tafs.commons;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class JapaneseNumeralsUtilTest {

	@Test
	public void testParseInteger_空白() throws ParseException {
		assertThat("empty", JapaneseNumeralsUtil.parseInteger("", false), nullValue());
		assertThat("null", JapaneseNumeralsUtil.parseInteger(null, false), nullValue());
		assertThat("blank", JapaneseNumeralsUtil.parseInteger(" ", false), nullValue());

		assertThat("empty", JapaneseNumeralsUtil.parseInteger("", true), nullValue());
		assertThat("null", JapaneseNumeralsUtil.parseInteger(null, true), nullValue());
		assertThat("blank", JapaneseNumeralsUtil.parseInteger(" ", true), nullValue());

	}

	@Test(expected = ParseException.class)
	public void testParseInteger_アルファベット() throws ParseException {
		assertThat("alpha", JapaneseNumeralsUtil.parseInteger("abcedif", false), nullValue());
		fail();

	}

	@Test(expected = ParseException.class)
	public void testParseInteger_漢字() throws ParseException {
		assertThat("漢字", JapaneseNumeralsUtil.parseInteger("漢字", false), nullValue());
		fail();

	}

	@Test(expected = ParseException.class)
	public void testParseInteger_ひらがな() throws ParseException {
		assertThat("ひらがな", JapaneseNumeralsUtil.parseInteger("ひらがな", false), nullValue());
		fail();

	}

	@Test(expected = ParseException.class)
	public void testParseInteger_カタカナ() throws ParseException {
		assertThat("カタナカ", JapaneseNumeralsUtil.parseInteger("カタナカ", false), nullValue());
		fail();

	}

	@Test(expected = ParseException.class)
	public void testParseInteger_一部カタカナ() throws ParseException {
		assertThat("一部カタナカ", JapaneseNumeralsUtil.parseInteger("一部カタナカ", false), nullValue());
		fail();

	}

	@Test()
	public void testParseInteger_ignoreUnparasble() throws ParseException {
		assertThat("alpha", JapaneseNumeralsUtil.parseInteger("abcedif", true).intValue(), is(0));
		assertThat("漢字", JapaneseNumeralsUtil.parseInteger("漢字", true).intValue(), is(0));
		assertThat("ひらがな", JapaneseNumeralsUtil.parseInteger("ひらがな", true).intValue(), is(0));
		assertThat("カタナカ", JapaneseNumeralsUtil.parseInteger("カタナカ", true).intValue(), is(0));
		assertThat("一部カタナカ", JapaneseNumeralsUtil.parseInteger("千ご百", true).intValue(), is(1100));

	}

	@Test
	public void testParseInteger_整数() throws ParseException {
		assertThat("三", JapaneseNumeralsUtil.parseInteger("三", false).longValue(), is(3L));
		assertThat("一二", JapaneseNumeralsUtil.parseInteger("一二", false).longValue(), is(12L));
		assertThat("十", JapaneseNumeralsUtil.parseInteger("十", false).longValue(), is(10L));
		assertThat("十一", JapaneseNumeralsUtil.parseInteger("十一", false).longValue(), is(11L));
		assertThat("一十一", JapaneseNumeralsUtil.parseInteger("一十一", false).longValue(), is(11L));
		assertThat("二十三", JapaneseNumeralsUtil.parseInteger("二十三", false).longValue(), is(23L));
		assertThat("四五", JapaneseNumeralsUtil.parseInteger("四五", false).longValue(), is(45L));
		assertThat("百", JapaneseNumeralsUtil.parseInteger("百", false).longValue(), is(100L));
		assertThat("一百", JapaneseNumeralsUtil.parseInteger("一百", false).longValue(), is(100L));
		assertThat("千", JapaneseNumeralsUtil.parseInteger("千", false).longValue(), is(1000L));
		assertThat("一千", JapaneseNumeralsUtil.parseInteger("一千", false).longValue(), is(1000L));
		assertThat("万", JapaneseNumeralsUtil.parseInteger("万", false).longValue(), is(10000L));
		assertThat("一万", JapaneseNumeralsUtil.parseInteger("一万", false).longValue(), is(10000L));
		assertThat("二万", JapaneseNumeralsUtil.parseInteger("二万", false).longValue(), is(20000L));
		assertThat("三万", JapaneseNumeralsUtil.parseInteger("三万", false).longValue(), is(30000L));
		assertThat("三万四千五百六十八", JapaneseNumeralsUtil.parseInteger("三万四千五百六十八", false).longValue(), is((long) 34568));
		assertThat("三万", JapaneseNumeralsUtil.parseInteger("三万", false).longValue(), is(30000L));
		assertThat("三千九百七万一", JapaneseNumeralsUtil.parseInteger("三千九百七万一", false).longValue(), is(39070001L));
		assertThat("千万", JapaneseNumeralsUtil.parseInteger("千万", false).longValue(), is(10000000L));
		assertThat("九九九九九九九", JapaneseNumeralsUtil.parseInteger("九九九九九九九", false).longValue(), is(9999999L));
		assertThat("100", JapaneseNumeralsUtil.parseInteger("100", false).longValue(), is(100L));
		assertThat("10億", JapaneseNumeralsUtil.parseInteger("10億", false).longValue(), is(1000000000L));
		assertThat("10億1", JapaneseNumeralsUtil.parseInteger("10億1", false).longValue(), is(1000000001L));
		assertThat("10億百二十1", JapaneseNumeralsUtil.parseInteger("10億百二十1", false).longValue(), is(1000000121L));
		assertThat("1兆", JapaneseNumeralsUtil.parseInteger("1兆", false).longValue(), is(1000000000000L));
		assertThat("壱〇〇", JapaneseNumeralsUtil.parseInteger("壱〇〇", false).longValue(), is((long) 100));
		assertThat("廿参", JapaneseNumeralsUtil.parseInteger("23", false).longValue(), is((long) 23));
		assertThat("９1壱", JapaneseNumeralsUtil.parseInteger("９1壱", false).longValue(), is((long) 911));
		assertThat("九", JapaneseNumeralsUtil.parseInteger("九", false).longValue(), is(9L));
		assertThat("一", JapaneseNumeralsUtil.parseInteger("一", false).longValue(), is(1L));
		assertThat("六百九拾壱", JapaneseNumeralsUtil.parseInteger("六百九拾壱", false).longValue(), is(691L));
		assertThat("三三七八", JapaneseNumeralsUtil.parseInteger("三三七八", false).longValue(), is(3378L));
		assertThat("七万七千百五十七", JapaneseNumeralsUtil.parseInteger("七万七千百五十七", false).longValue(), is(77157L));
		assertThat("七拾七萬伍阡八百伍拾弐", JapaneseNumeralsUtil.parseInteger("七拾七萬伍阡八百伍拾弐", false).longValue(), is(775852L));
		assertThat("七三一〇八六五", JapaneseNumeralsUtil.parseInteger("七三一〇八六五", false).longValue(), is(7310865L));
		assertThat("八千十七万六千四百四十五", JapaneseNumeralsUtil.parseInteger("八千十七万六千四百四十五", false).longValue(), is(80176445L));
		assertThat("伍億壱阡六百壱萬七阡壱百七拾九", JapaneseNumeralsUtil.parseInteger("伍億壱阡六百壱萬七阡壱百七拾九", false).longValue(),
				is(516017179L));
		assertThat("二五六四五七一三八九", JapaneseNumeralsUtil.parseInteger("二五六四五七一三八九", false).longValue(), is(2564571389L));
		assertThat("四百四十六億七千六百九十万四千二百二十七",
				JapaneseNumeralsUtil.parseInteger("四百四十六億七千六百九十万四千二百二十七", false).longValue(), is(44676904227L));
		assertThat("四阡八百四拾伍億壱阡九百壱拾八萬六阡九百六拾", JapaneseNumeralsUtil.parseInteger("四阡八百四拾伍億壱阡九百壱拾八萬六阡九百六拾", false)
				.longValue(), is(484519186960L));
		assertThat("九六〇八七〇三五一三三七三", JapaneseNumeralsUtil.parseInteger("九六〇八七〇三五一三三七三", false).longValue(),
				is(9608703513373L));
		assertThat("六十九兆九千六百七十四億二百九十七万六千六十一", JapaneseNumeralsUtil.parseInteger("六十九兆九千六百七十四億二百九十七万六千六十一", false)
				.longValue(), is(69967402976061L));
		assertThat("七拾兆参阡壱百弐拾伍億参拾弐萬六阡八百七拾六", JapaneseNumeralsUtil.parseInteger("七拾兆参阡壱百弐拾伍億参拾弐萬六阡八百七拾六", false)
				.longValue(), is(70312500326876L));
		assertThat("一六五七五九〇九一一九四七〇七〇", JapaneseNumeralsUtil.parseInteger("一六五七五九〇九一一九四七〇七〇", false).longValue(),
				is(1657590911947070L));
		assertThat("八七二八〇兆六百二十一億九千七百七十三万八千百", JapaneseNumeralsUtil.parseInteger("八七二八〇兆六百二十一億九千七百七十三万八千百", false)
				.longValue(), is(87280062197738100L));
		assertThat("七壱九伍弐四兆四阡四百伍拾四億参阡参百六拾八萬", JapaneseNumeralsUtil.parseInteger("七壱九伍弐四兆四阡四百伍拾四億参阡参百六拾八萬", false)
				.longValue(), is(719524445433680000L));
		assertThat("六八八八三七二〇九四二三九二六〇〇〇〇", JapaneseNumeralsUtil.parseInteger("六八八八三七二〇九四二三九二六〇〇〇〇", false).longValue(),
				is(6888372094239260000L));

	}

	@Test
	public void testParse_整数() throws ParseException {
		// マイナス
		assertThat("一", JapaneseNumeralsUtil.parse("一").longValue(), is(1L));
		assertThat("十", JapaneseNumeralsUtil.parse("十").longValue(), is(10L));
		assertThat("百", JapaneseNumeralsUtil.parse("百").longValue(), is(100L));
		assertThat("万", JapaneseNumeralsUtil.parse("万").longValue(), is(10000L));
		assertThat("三万四千五百六十八", JapaneseNumeralsUtil.parse("三万四千五百六十八").longValue(), is((long) 34568));
		assertThat("千万", JapaneseNumeralsUtil.parse("千万").longValue(), is(10000000L));
		assertThat("六百九拾壱", JapaneseNumeralsUtil.parse("六百九拾壱").longValue(), is(691L));
		assertThat("三三七八", JapaneseNumeralsUtil.parse("三三七八").longValue(), is(3378L));
		assertThat("七万七千百五十七", JapaneseNumeralsUtil.parse("七万七千百五十七").longValue(), is(77157L));
		assertThat("一六五七五九〇九一一九四七〇七〇", JapaneseNumeralsUtil.parse("一六五七五九〇九一一九四七〇七〇").longValue(),
				is(1657590911947070L));
		assertThat("八七二八〇兆六百二十一億九千七百七十三万八千百", JapaneseNumeralsUtil.parse("八七二八〇兆六百二十一億九千七百七十三万八千百").longValue(),
				is(87280062197738100L));
		assertThat("七壱九伍弐四兆四阡四百伍拾四億参阡参百六拾八萬", JapaneseNumeralsUtil.parse("七壱九伍弐四兆四阡四百伍拾四億参阡参百六拾八萬").longValue(),
				is(719524445433680000L));
		assertThat("六八八八三七二〇九四二三九二六〇〇〇〇", JapaneseNumeralsUtil.parse("六八八八三七二〇九四二三九二六〇〇〇〇").longValue(),
				is(6888372094239260000L));
	}

	@Test
	public void testParse_小数() throws ParseException {
		// マイナス
		assertThat("〇・一", JapaneseNumeralsUtil.parse("〇・一").toString(), is("0.1"));
		assertThat("一・二三", JapaneseNumeralsUtil.parse("一・二三").toString(), is("1.23"));
		assertThat("千・一二三", JapaneseNumeralsUtil.parse("千・一二三").toString(), is("1000.123"));
		assertThat("フルマラソン", JapaneseNumeralsUtil.parse("四十二・一九五").toString(), is("42.195"));
		assertThat("円周率下35ケタ", JapaneseNumeralsUtil.parse("三．一四一五九二六五三五八九七九三二三八四六二六四三三八三二七九五〇二八八").toString(),
				is("3.14159265358979323846264338327950288"));

	}

	@Test
	public void testParse_マイナス() throws ParseException {
		// マイナス

		assertThat("-2", JapaneseNumeralsUtil.parse("-2").longValue(), is((long) -2));
		assertThat("マイナス３", JapaneseNumeralsUtil.parse("マイナス３").longValue(), is((long) -3));
		assertThat("－一億二千三百四十五万六千七百八十九", JapaneseNumeralsUtil.parse("－一億二千三百四十五万六千七百八十九").longValue(),
				is((long) -123456789));
		assertThat("マイナス壱〇〇", JapaneseNumeralsUtil.parse("マイナス壱〇〇").longValue(), is((long) -100));
		assertThat("マイナス千", JapaneseNumeralsUtil.parse("マイナス千").longValue(), is((long) -1000));
		assertThat("マイナス一万一千百七十七", JapaneseNumeralsUtil.parse("マイナス一万一千百七十七").longValue(), is((long) -11177));

	}

	@Test
	public void testParse_ゼロ() throws ParseException {
		//ゼロ
		assertThat("０", JapaneseNumeralsUtil.parse("0").longValue(), is((long) 0));
		assertThat("ゼロ", JapaneseNumeralsUtil.parse("ゼロ").longValue(), is((long) 0));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testParse_順序が異常_ignoreUnparsable() throws ParseException {
		JapaneseNumeralsUtil.parse("一万２億");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParse_順序が異常() throws ParseException {
		JapaneseNumeralsUtil.parse("一万２億", false);
	}
}
