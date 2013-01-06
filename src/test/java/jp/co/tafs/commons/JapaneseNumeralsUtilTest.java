package jp.co.tafs.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;

import jp.co.tafs.commons.JapaneseNumeralsUtil;

import org.junit.Test;

public class JapaneseNumeralsUtilTest {

	@Test
	public void testParseInteger_空白() throws ParseException{
		assertEquals("empty",null,JapaneseNumeralsUtil.parseInteger("",false));
		assertEquals("null",null,JapaneseNumeralsUtil.parseInteger(null,false));
		assertEquals("blank",null,JapaneseNumeralsUtil.parseInteger(" ",false));

		assertEquals("empty",null,JapaneseNumeralsUtil.parseInteger("",true));
		assertEquals("null",null,JapaneseNumeralsUtil.parseInteger(null,true));
		assertEquals("blank",null,JapaneseNumeralsUtil.parseInteger(" ",true));

	}

	@Test(expected=ParseException.class)
	public void testParseInteger_アルファベット() throws ParseException{
		assertEquals("alpha",null,JapaneseNumeralsUtil.parseInteger("abcedif",false));
		fail();

	}

	@Test(expected=ParseException.class)
	public void testParseInteger_漢字() throws ParseException{
		assertEquals("漢字",null,JapaneseNumeralsUtil.parseInteger("漢字",false));
		fail();

	}
	@Test(expected=ParseException.class)
	public void testParseInteger_ひらがな() throws ParseException{
		assertEquals("ひらがな",null,JapaneseNumeralsUtil.parseInteger("あいうえお",false));
		fail();

	}
	@Test(expected=ParseException.class)
	public void testParseInteger_カタカナ() throws ParseException{
		assertEquals("カタナカ",null,JapaneseNumeralsUtil.parseInteger("カタカナ",false));
		fail();

	}

	@Test(expected=ParseException.class)
	public void testParseInteger_一部カタカナ() throws ParseException{
		assertEquals("一部カタナカ",null,JapaneseNumeralsUtil.parseInteger("千ご百",false));
		fail();

	}

	@Test()
	public void testParseInteger_ignoreUnparasble() throws ParseException{
		assertEquals("alpha",0,JapaneseNumeralsUtil.parseInteger("abcedif",true).intValue());
		assertEquals("漢字",0,JapaneseNumeralsUtil.parseInteger("漢字",true).intValue());
		assertEquals("ひらがな",0,JapaneseNumeralsUtil.parseInteger("あいうえお",true).intValue());
		assertEquals("カタナカ",0,JapaneseNumeralsUtil.parseInteger("カタカナ",true).intValue());
		assertEquals("一部カタナカ",1100,JapaneseNumeralsUtil.parseInteger("千ご百",true).intValue());
	}



	@Test
	public void testParseInteger_整数() throws ParseException{
		assertEquals("三",3L,JapaneseNumeralsUtil.parseInteger("三", false).longValue());
		assertEquals("一二",12L,JapaneseNumeralsUtil.parseInteger("一二", false).longValue());
		assertEquals("十",10L,JapaneseNumeralsUtil.parseInteger("十", false).longValue());
		assertEquals("十一",11L,JapaneseNumeralsUtil.parseInteger("十一", false).longValue());
		assertEquals("一十一",11L,JapaneseNumeralsUtil.parseInteger("一十一", false).longValue());
		assertEquals("二十三",23L,JapaneseNumeralsUtil.parseInteger("二十三", false).longValue());
		assertEquals("四五",45L,JapaneseNumeralsUtil.parseInteger("四五", false).longValue());
		assertEquals("百",100L,JapaneseNumeralsUtil.parseInteger("百", false).longValue());
		assertEquals("一百",100L,JapaneseNumeralsUtil.parseInteger("一百", false).longValue());
		assertEquals("千",1000L,JapaneseNumeralsUtil.parseInteger("千", false).longValue());
		assertEquals("一千",1000L,JapaneseNumeralsUtil.parseInteger("一千", false).longValue());
		assertEquals("万",10000L,JapaneseNumeralsUtil.parseInteger("万", false).longValue());
		assertEquals("一万",10000L,JapaneseNumeralsUtil.parseInteger("一万", false).longValue());
		assertEquals("二万",20000L,JapaneseNumeralsUtil.parseInteger("二万", false).longValue());
		assertEquals("三万",30000L,JapaneseNumeralsUtil.parseInteger("三万", false).longValue());
		assertEquals("三万四千五百六十八",34568,JapaneseNumeralsUtil.parseInteger("三万四千五百六十八", false).longValue());
		assertEquals("三万",30000L,JapaneseNumeralsUtil.parseInteger("三万", false).longValue());
		assertEquals("三千九百七万一",39070001L,JapaneseNumeralsUtil.parseInteger("三千九百七万一", false).longValue());
		assertEquals("千万",10000000L,JapaneseNumeralsUtil.parseInteger("千万", false).longValue());
		assertEquals("九九九九九九九",9999999L,JapaneseNumeralsUtil.parseInteger("九九九九九九九", false).longValue());
		assertEquals("100",100L,JapaneseNumeralsUtil.parseInteger("100", false).longValue());
		assertEquals("10億",1000000000L,JapaneseNumeralsUtil.parseInteger("10億", false).longValue());
		assertEquals("10億1",1000000001L,JapaneseNumeralsUtil.parseInteger("10億1", false).longValue());
		assertEquals("10億百二十1",1000000121L,JapaneseNumeralsUtil.parseInteger("10億百二十1", false).longValue());
		assertEquals("1兆",1000000000000L,JapaneseNumeralsUtil.parseInteger("1兆", false).longValue());
		assertEquals("壱〇〇",100,JapaneseNumeralsUtil.parseInteger("壱〇〇", false).longValue());
		assertEquals("廿参",23,JapaneseNumeralsUtil.parseInteger("23", false).longValue());
		assertEquals("９1壱",911,JapaneseNumeralsUtil.parseInteger("９1壱", false).longValue());
		assertEquals("九",9L,JapaneseNumeralsUtil.parseInteger("九", false).longValue());
		assertEquals("一",1L,JapaneseNumeralsUtil.parseInteger("一", false).longValue());
		assertEquals("六百九拾壱",691L,JapaneseNumeralsUtil.parseInteger("六百九拾壱", false).longValue());
		assertEquals("三三七八",3378L,JapaneseNumeralsUtil.parseInteger("三三七八", false).longValue());
		assertEquals("七万七千百五十七",77157L,JapaneseNumeralsUtil.parseInteger("七万七千百五十七", false).longValue());
		assertEquals("七拾七萬伍阡八百伍拾弐",775852L,JapaneseNumeralsUtil.parseInteger("七拾七萬伍阡八百伍拾弐", false).longValue());
		assertEquals("七三一〇八六五",7310865L,JapaneseNumeralsUtil.parseInteger("七三一〇八六五", false).longValue());
		assertEquals("八千十七万六千四百四十五",80176445L,JapaneseNumeralsUtil.parseInteger("八千十七万六千四百四十五", false).longValue());
		assertEquals("伍億壱阡六百壱萬七阡壱百七拾九",516017179L,JapaneseNumeralsUtil.parseInteger("伍億壱阡六百壱萬七阡壱百七拾九", false).longValue());
		assertEquals("二五六四五七一三八九",2564571389L,JapaneseNumeralsUtil.parseInteger("二五六四五七一三八九", false).longValue());
		assertEquals("四百四十六億七千六百九十万四千二百二十七",44676904227L,JapaneseNumeralsUtil.parseInteger("四百四十六億七千六百九十万四千二百二十七", false).longValue());
		assertEquals("四阡八百四拾伍億壱阡九百壱拾八萬六阡九百六拾",484519186960L,JapaneseNumeralsUtil.parseInteger("四阡八百四拾伍億壱阡九百壱拾八萬六阡九百六拾", false).longValue());
		assertEquals("九六〇八七〇三五一三三七三",9608703513373L,JapaneseNumeralsUtil.parseInteger("九六〇八七〇三五一三三七三", false).longValue());
		assertEquals("六十九兆九千六百七十四億二百九十七万六千六十一",69967402976061L,JapaneseNumeralsUtil.parseInteger("六十九兆九千六百七十四億二百九十七万六千六十一", false).longValue());
		assertEquals("七拾兆参阡壱百弐拾伍億参拾弐萬六阡八百七拾六",70312500326876L,JapaneseNumeralsUtil.parseInteger("七拾兆参阡壱百弐拾伍億参拾弐萬六阡八百七拾六", false).longValue());
		assertEquals("一六五七五九〇九一一九四七〇七〇",1657590911947070L,JapaneseNumeralsUtil.parseInteger("一六五七五九〇九一一九四七〇七〇", false).longValue());
		assertEquals("八七二八〇兆六百二十一億九千七百七十三万八千百",87280062197738100L,JapaneseNumeralsUtil.parseInteger("八七二八〇兆六百二十一億九千七百七十三万八千百", false).longValue());
		assertEquals("七壱九伍弐四兆四阡四百伍拾四億参阡参百六拾八萬",719524445433680000L,JapaneseNumeralsUtil.parseInteger("七壱九伍弐四兆四阡四百伍拾四億参阡参百六拾八萬", false).longValue());
		assertEquals("六八八八三七二〇九四二三九二六〇〇〇〇",6888372094239260000L,JapaneseNumeralsUtil.parseInteger("六八八八三七二〇九四二三九二六〇〇〇〇", false).longValue());

	}

	@Test
	public void testParse_整数() throws ParseException{
		// マイナス
		assertEquals("一",1L,JapaneseNumeralsUtil.parse("一").longValue());
		assertEquals("十",10L,JapaneseNumeralsUtil.parse("十").longValue());
		assertEquals("百",100L,JapaneseNumeralsUtil.parse("百").longValue());
		assertEquals("万",10000L,JapaneseNumeralsUtil.parse("万").longValue());
		assertEquals("三万四千五百六十八",34568,JapaneseNumeralsUtil.parse("三万四千五百六十八").longValue());
		assertEquals("千万",10000000L,JapaneseNumeralsUtil.parse("千万").longValue());
		assertEquals("六百九拾壱",691L,JapaneseNumeralsUtil.parse("六百九拾壱").longValue());
		assertEquals("三三七八",3378L,JapaneseNumeralsUtil.parse("三三七八").longValue());
		assertEquals("七万七千百五十七",77157L,JapaneseNumeralsUtil.parse("七万七千百五十七").longValue());
		assertEquals("一六五七五九〇九一一九四七〇七〇",1657590911947070L,JapaneseNumeralsUtil.parse("一六五七五九〇九一一九四七〇七〇").longValue());
		assertEquals("八七二八〇兆六百二十一億九千七百七十三万八千百",87280062197738100L,JapaneseNumeralsUtil.parse("八七二八〇兆六百二十一億九千七百七十三万八千百").longValue());
		assertEquals("七壱九伍弐四兆四阡四百伍拾四億参阡参百六拾八萬",719524445433680000L,JapaneseNumeralsUtil.parse("七壱九伍弐四兆四阡四百伍拾四億参阡参百六拾八萬").longValue());
		assertEquals("六八八八三七二〇九四二三九二六〇〇〇〇",6888372094239260000L,JapaneseNumeralsUtil.parse("六八八八三七二〇九四二三九二六〇〇〇〇").longValue());
	}

	@Test
	public void testParse_小数() throws ParseException{
		// マイナス
		assertEquals("〇・一","0.1",JapaneseNumeralsUtil.parse("〇・一").toString());
		assertEquals("一・二三","1.23",JapaneseNumeralsUtil.parse("一・二三").toString());
		assertEquals("千・一二三","1000.123",JapaneseNumeralsUtil.parse("千・一二三").toString());
		assertEquals("フルマラソン","42.195",JapaneseNumeralsUtil.parse("四十二・一九五").toString());
		assertEquals("円周率下35ケタ","3.14159265358979323846264338327950288",JapaneseNumeralsUtil.parse("三．一四一五九二六五三五八九七九三二三八四六二六四三三八三二七九五〇二八八").toString());
	}


	@Test
	public void testParse_マイナス() throws ParseException{
		// マイナス
		assertEquals("-2",-2,JapaneseNumeralsUtil.parse("-2").longValue());
		assertEquals("マイナス３",-3,JapaneseNumeralsUtil.parse("マイナス３").longValue());
		assertEquals("－一億二千三百四十五万六千七百八十九",-123456789,JapaneseNumeralsUtil.parse("－一億二千三百四十五万六千七百八十九").longValue());
		assertEquals("マイナス壱〇〇",-100,JapaneseNumeralsUtil.parse("マイナス壱〇〇").longValue());
		assertEquals("マイナス千",-1000,JapaneseNumeralsUtil.parse("マイナス千").longValue());
		assertEquals("マイナス一万一千百七十七",-11177,JapaneseNumeralsUtil.parse("マイナス一万一千百七十七").longValue());
	}

	@Test
	public void testParse_ゼロ() throws ParseException{
		//ゼロ
		assertEquals("０",0,JapaneseNumeralsUtil.parse("0").longValue());
		assertEquals("ゼロ",0,JapaneseNumeralsUtil.parse("0").longValue());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testParse_順序が異常_ignoreUnparsable() throws ParseException{
		JapaneseNumeralsUtil.parse("一万２億");
	}
	@Test(expected=IllegalArgumentException.class)
	public void testParse_順序が異常() throws ParseException{
		JapaneseNumeralsUtil.parse("一万２億",false);
	}
}
