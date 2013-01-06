package jp.co.tafs.commons;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatException;

import org.apache.commons.lang3.StringUtils;

public class JapaneseNumeralsUtil {

	/**
	 * 漢数字を数値に変換します。 変換対象の文字列に数字以外が含まれている場合は無視しします。
	 *
	 *
	 * @param japaneseNumeralsSequence
	 *            漢数字文字列
	 * @return 数字に変換した結果のBigDecimal 変換できない場合は0のBigDecimalを返します。
	 * @throws IllegalArgumentException
	 * 	位の順序が違う場合（９億９９９９兆とか）にthrowします。
	 */
	public static BigDecimal parse(CharSequence japaneseNumeralsSequence) {

		try {
			return parse(japaneseNumeralsSequence, true);
		} catch (ParseException e) {
			// 位の順序が違う場合（９億９９９９兆とか）
			throw new IllegalArgumentException(e);
		}
	}


	/**
	 * 漢数字を数値に変換します。 変換対象の文字列に数字以外が含まれている場合は、ignoreUnparableの設定に依存します。
	 *
	 * @param japaneseNumeralsSequence
	 *            漢数字文字列
	 * @param ignoreUnparsable
	 *            変換できない文字を無視するかどうかのフラグ。
	 *            trueの場合は、変換対象外文字を無視します。falseの場合は、変換対象外文字が存在すれば例外をthrowします。
	 * @return 数字に変換した結果のBigDecimal 変換できない場合は0のBigDecimalを返します。
	 * @throws ParseException
	 *             ignoreUnparsableがtrueの場合にい、変換対象外文字が含まれていればthrowします。
	 */
	public static BigDecimal parse(CharSequence japaneseNumeralsSequence,
			boolean ignoreUnparsable) throws ParseException {

		boolean minus = false;
		String numerals = japaneseNumeralsSequence.toString().trim();
		// マイナス記号で始まる場合は、変換結果を最後にマイナスにする。
		{
			// マイナス記号を認識した後は、変換対象の文字列からは取り除いておく。
			String minus_regex = "^" + Numerals.MINUS_REGEXP;
			if (numerals.matches(minus_regex + ".*$")) {
				minus = true;
				numerals = numerals.replaceFirst(minus_regex, "");
			}
		}

		String[] split = numerals.split(Numerals.DECIMAL_POINT_REGEXP);
		String integerNumerals = split[0];
		String decimalNumerals = split.length > 1 ? split[1] : "";

		BigDecimal total = parseInteger(integerNumerals, ignoreUnparsable);
		// 小数部を追加する。
		if (StringUtils.isNotBlank(decimalNumerals)) {
			BigDecimal decimal = parseInteger(decimalNumerals, ignoreUnparsable);
			if (total == null) {
				total = new BigDecimal(0);
			}
			// 小数部があれば、totalに追加する。
			if (decimal.signum() == 1) {
				decimal = decimal.movePointLeft(decimal.precision());
				total = total.add(decimal);
			}
		}

		if (minus) {
			total = total.negate();
		}
		return total;
	}

	/**
	 * 整数部の変換
	 *
	 * @param numerals
	 *            漢数字文字列
	 * @param ignoreUnparsable
	 *            変換できない文字を無視するかどうかのフラグ。
	 *            trueの場合は、変換対象外文字を無視します。falseの場合は、変換対象外文字が存在すれば例外をthrowします。
	 * @return 漢数字を算用数字に変換した結果です。
	 * @throws ParseException
	 *             ignoreUnparsableがtrueの場合にい、変換対象外文字が含まれていればthrowします。
	 */
	protected static BigDecimal parseInteger(String numerals,
			boolean ignoreUnparsable) throws ParseException {

		BigDecimal total = new BigDecimal(0);
		BigDecimal subtotal = new BigDecimal(0);
		BigDecimal numbers = null; // 0 ～ 9999

		if (StringUtils.isBlank(numerals)) {
			return null;
		}

		for (int i = 0; i < numerals.length(); i++) {
			char letter = numerals.charAt(i);
			Numerals numeral = Numerals.getByChar(letter);
			if (numeral == null) {
				// 変換不可文字の場合、ignoreUnparsable==falseならば例外をthrowする。
				if (!ignoreUnparsable) {
					throw new ParseException("'" + letter + "' in " + numeral
							+ " is not numeral", i);
				}
			} else if (numeral.value == null) {
				// 無視して良い文字の場合は、インスタンスは存在するが、valueがnullのNumeralが返る。
				// この場合、文字を無視する
			} else {
				BigDecimal value = new BigDecimal(numeral.value);
				BigDecimal multiply = new BigDecimal(numeral.getMultiply());
				if (numeral.exponent == 0) {
					// １～９はバッファに追加していく。
					if (numbers == null) {
						numbers = new BigDecimal(0);
					} else {
						numbers = numbers.multiply(BigDecimal.TEN);
					}
					numbers = numbers.add(value);
				} else {
					// 十百千はバッファの桁を上げる
					// 位が上がる場合に、バッファに値があればその値の位を上げる。
					if (numeral.exponent < 4) {
						if (numbers == null) {
							subtotal = subtotal.add(value);
						} else {
							subtotal = subtotal.add(numbers.multiply(multiply));
						}
					} else {
						// 万以上の位を表わす文字は合計済みの値も位を上げる。
						if (numbers != null) {
							subtotal = subtotal.add(numbers);
						}

						if (subtotal.longValue() == 0) {
							subtotal = value;
						} else {
							subtotal = subtotal.multiply(multiply);
						}
						if (0 < total.longValue()
								&& total.compareTo(subtotal) < 0) {
							throw new IllegalArgumentException("位の順序が不正です。" + numerals);
						}

						total = total.add(subtotal);
						subtotal = new BigDecimal(0);
					}
					numbers = null;
				}
			}
		}
		if (numbers != null) {
			total = total.add(numbers);
		}
		if (0 < subtotal.longValue()) {
			total = total.add(subtotal);
		}
		return total;
	}
}