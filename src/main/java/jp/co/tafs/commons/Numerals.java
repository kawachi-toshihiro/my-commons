package jp.co.tafs.commons;

import org.apache.commons.lang3.StringUtils;

public enum Numerals {
	ZERO(0L, "0０〇零"),
	ONE(1L, "1１一壱"),
	TWO(2L, "2２二弐"),
	THREE(3L, "3３三参"),
	FOUR(4L,"4４四"),
	FIVE(5L, "5５五伍"),
	SIX(6L, "6６六"),
	SEVEN(7L, "7７七"),
	EIGHT(8L,"8８八"),
	NINE(9L, "9９九"),
	TEN(10L, "十拾"),
	TWENTY(20L, "廿"),
	HUNDRED(100L, "百"),
	THOUSAND(1000L, "千阡"),
	TENTHOUSAND(10000L, "万萬"),
	MILLION(100000000L, "億"),
	BILILION(1000000000000L, "兆"),
	IGNORE_CHAR(null,","),
	DECIMAL_POINT(null,"・.．" )
	;

	public static final String[] MINUS_LETTERS = new String[] { "マイナス", "-","－" };
	public static final String MINUS_REGEXP = "("+ StringUtils.join(MINUS_LETTERS, "|") + ")";

	public static final char[] DECIMAL_POINT_LETTERS = DECIMAL_POINT.letters.toCharArray();
	public static final String DECIMAL_POINT_REGEXP = "["+ DECIMAL_POINT.letters.replace(".", "\\.") + "]";

	public static String getAllCharRegExp(){

		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Numerals num: values()){
			if(num.value != null){
				sb.append(StringUtils.join(num.letters,""));
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public static String getOneDigitsCharRegExp(){

		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Numerals num: values()){
			if(num.exponent != null &&  num.exponent == 1 ){
				sb.append(StringUtils.join(num.letters,""));
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public final Long value;
	public final Long exponent;
	public final String letters;

	public long getMultiply() {
		return (long) Math.pow(10, exponent);
	}

	private Numerals(long value, String letters, long exponent) {
		this.value = value;
		this.letters = letters;
		this.exponent = new Long(exponent);
	}

	private Numerals(Long value, String letters) {
		this.value = value;
		this.letters = letters;
		if(value != null){
			this.exponent = new Long(String.valueOf(Math.abs(value)).length() - 1);
		}else{
			this.exponent = null;
		}
	}

	public static Numerals getByValue(long value) {
		for (Numerals num : Numerals.values()) {
			if (num.value == value) {
				return num;
			}
		}
		return null;
	}

	public static Numerals getByChar(char letter) {
		for (Numerals num : Numerals.values()) {
			if (num.letters.contains(String.valueOf(letter))) {
				return num;
			}
		}
		return null;
	}

}
