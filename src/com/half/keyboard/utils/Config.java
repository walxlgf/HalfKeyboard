/**
 *
 */
package com.half.keyboard.utils;

import java.util.ArrayList;
import java.util.List;

import com.half.keyboard.vo.FrogPadData;
import com.half.keyboard.vo.KeyValue;
import com.half.keyboard.vo.NormalKeyData;

/**
 * @author Thinkpad
 */
public class Config {
    private final static String TAG = "Config";
    private volatile static Config instance;
    private FrogPadData mFrogPadData;

    public Config() {
        super();
        initFrogData();
    }

    public static Config getInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                instance = new Config();
            }
        }
        return instance;
    }

    /**
     * 
     *
     * @param position
     * @return
     */
    public NormalKeyData getNormalKeyDataByPosition(int position) {
        NormalKeyData normalKeyData = null;
        if (mFrogPadData != null) {
            List<NormalKeyData> list = mFrogPadData.getNormalKeyDatas();
            if (list != null && position >= 0 && position < list.size()) {
                normalKeyData = list.get(position);
            }
        }
        return normalKeyData;
    }

    private void initFrogData() {
        mFrogPadData = new FrogPadData();
        List<NormalKeyData> normalKeyDatas = new ArrayList<NormalKeyData>();
        NormalKeyData normalKeyData = null;
        KeyValue primarySymbol = null;
        KeyValue primaryLetter = null;
        KeyValue secondarySymbol = null;
        KeyValue secondaryLetter = null;
        KeyValue primaryNumber = null;
        KeyValue secondaryNumber = null;
        KeyValue function = null;
        KeyValue special = null;

        //key1
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("?");
        primarySymbol.setCode(47);
        primarySymbol.setShifted(true);
        primaryLetter = new KeyValue();
        primaryLetter.setName("F");
        primaryLetter.setCode(70);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("Tab");
        secondarySymbol.setCode(9);
        secondarySymbol.setShifted(false);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("Tab");
        secondaryLetter.setCode(9);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("Tab");
        secondaryNumber.setCode(9);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        function = new KeyValue();
        function.setName("Esc");
        function.setCode(27);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("*");
        primaryNumber.setCode(56);
        primaryNumber.setShifted(true);

        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);
        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);

        normalKeyDatas.add(normalKeyData);

        //key2
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("&");
        primarySymbol.setCode(55);
        primarySymbol.setShifted(true);
        primaryLetter = new KeyValue();
        primaryLetter.setName("A");
        primaryLetter.setCode(65);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("+");
        secondarySymbol.setCode(107);
        secondarySymbol.setShifted(false);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("`");
        secondaryLetter.setCode(192);
        secondaryLetter.setShifted(true);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F9");
        secondaryNumber.setCode(120);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        special.setName("Scroll Lk");
        special.setCode(145);
        special.setShifted(false);
        function = new KeyValue();
        function.setName("Home");
        function.setCode(36);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("9");
        primaryNumber.setCode(57);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key3
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("*");
        primarySymbol.setCode(56);
        primarySymbol.setShifted(true);
        primaryLetter = new KeyValue();
        primaryLetter.setName("R");
        primaryLetter.setCode(82);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("=");
        secondarySymbol.setCode(61);
        secondarySymbol.setShifted(false);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("B");
        secondaryLetter.setCode(66);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F8");
        secondaryNumber.setCode(119);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        special.setName("Print Sc");
        special.setCode(154);
        special.setShifted(false);
        function = new KeyValue();
        function.setName("End");
        function.setCode(35);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("8");
        primaryNumber.setCode(56);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key4
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName(":");
        primarySymbol.setCode(59);
        primarySymbol.setShifted(true);
        primaryLetter = new KeyValue();
        primaryLetter.setName("W");
        primaryLetter.setCode(87);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("$");
        secondarySymbol.setCode(52);
        secondarySymbol.setShifted(true);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("M");
        secondaryLetter.setCode(77);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F7");
        secondaryNumber.setCode(118);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        function = new KeyValue();
        function.setName("Pause");
        function.setCode(19);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("7");
        primaryNumber.setCode(55);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key5
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName(";");
        primarySymbol.setCode(59);
        primarySymbol.setShifted(false);
        primaryLetter = new KeyValue();
        primaryLetter.setName("P");
        primaryLetter.setCode(80);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("#");
        secondarySymbol.setCode(51);
        secondarySymbol.setShifted(true);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("J");
        secondaryLetter.setCode(74);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("`");
        secondaryNumber.setCode(192);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        special.setName("Num.Lk");
        special.setCode(144);
        special.setShifted(false);
        function = new KeyValue();
        function.setName("PgUp");
        function.setCode(33);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("*");
        primaryNumber.setCode(56);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);
        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key6
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("/");
        primarySymbol.setCode(47);
        primarySymbol.setShifted(false);
        primaryLetter = new KeyValue();
        primaryLetter.setName("O");
        primaryLetter.setCode(79);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("\\");
        secondarySymbol.setCode(47);
        secondarySymbol.setShifted(false);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("Q");
        secondaryLetter.setCode(81);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F12");
        secondaryNumber.setCode(123);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        special.setName("Right");
        special.setCode(227);
        special.setShifted(false);
        function = new KeyValue();
        function.setName("Ins");
        function.setCode(155);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("/");
        primaryNumber.setCode(111);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key7
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("!");
        primarySymbol.setCode(49);
        primarySymbol.setShifted(true);
        primaryLetter = new KeyValue();
        primaryLetter.setName("E");
        primaryLetter.setCode(69);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("%");
        secondarySymbol.setCode(53);
        secondarySymbol.setShifted(true);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("Z");
        secondaryLetter.setCode(90);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F6");
        secondaryNumber.setCode(117);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        special.setName("Ctrl Hold");
        special.setCode(17);
        special.setShifted(false);
        function = new KeyValue();
        function.setName("Ctrl");
        function.setCode(17);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("6");
        primaryNumber.setCode(54);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key8
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("-");
        primarySymbol.setCode(45);
        primarySymbol.setShifted(false);
        primaryLetter = new KeyValue();
        primaryLetter.setName("H");
        primaryLetter.setCode(72);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("|");
        secondarySymbol.setCode(92);
        secondarySymbol.setShifted(true);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("L");
        secondaryLetter.setCode(76);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F5");
        secondaryNumber.setCode(116);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        special.setName("Alt Hold");
        special.setCode(18);
        special.setShifted(false);
        function = new KeyValue();
        function.setName("Alt");
        function.setCode(18);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("5");
        primaryNumber.setCode(53);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key9
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("@");
        primarySymbol.setCode(50);
        primarySymbol.setShifted(true);
        primaryLetter = new KeyValue();
        primaryLetter.setName("T");
        primaryLetter.setCode(84);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("~");
        secondarySymbol.setCode(192);
        secondarySymbol.setShifted(true);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("C");
        secondaryLetter.setCode(67);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F4");
        secondaryNumber.setCode(115);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        function = new KeyValue();
        function.setName("↑");
        function.setCode(38);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("4");
        primaryNumber.setCode(52);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key10
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("_");
        primarySymbol.setCode(45);
        primarySymbol.setShifted(true);
        primaryLetter = new KeyValue();
        primaryLetter.setName("D");
        primaryLetter.setCode(68);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("^");
        secondarySymbol.setCode(54);
        secondarySymbol.setShifted(true);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("V");
        secondaryLetter.setCode(86);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F11");
        secondaryNumber.setCode(122);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        special.setName("Left");
        special.setCode(226);
        special.setShifted(false);
        function = new KeyValue();
        function.setName("PgDn");
        function.setCode(34);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("+");
        primaryNumber.setCode(521);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);
        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key11
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("\"");
        primarySymbol.setCode(222);
        primarySymbol.setShifted(true);
        primaryLetter = new KeyValue();
        primaryLetter.setName("U");
        primaryLetter.setCode(85);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("'");
        secondarySymbol.setCode(222);
        secondarySymbol.setShifted(false);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("Win");
        secondaryLetter.setCode(524);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("$");
        secondaryNumber.setCode(52);
        secondaryNumber.setShifted(true);
        special = new KeyValue();
        special.setName("Win Hold");
        special.setCode(524);
        special.setShifted(false);
        function = new KeyValue();
        function.setName("Delete");
        function.setCode(127);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName(".");
        primaryNumber.setCode(46);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key12
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("<");
        primarySymbol.setCode(44);
        primarySymbol.setShifted(true);
        primaryLetter = new KeyValue();
        primaryLetter.setName("I");
        primaryLetter.setCode(73);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName(">");
        secondarySymbol.setCode(44);
        secondarySymbol.setShifted(true);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("App");
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F3");
        secondaryNumber.setCode(114);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        special.setName("App Hold");
        function = new KeyValue();
        function.setName("BkSpc");
        function.setCode(8);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("3");
        primaryNumber.setCode(51);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key13
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("(");
        primarySymbol.setCode(57);
        primarySymbol.setShifted(true);
        primaryLetter = new KeyValue();
        primaryLetter.setName("N");
        primaryLetter.setCode(78);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName(")");
        secondarySymbol.setCode(48);
        secondarySymbol.setShifted(true);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("K");
        secondaryLetter.setCode(75);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F2");
        secondaryNumber.setCode(113);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        function = new KeyValue();
        function.setName("←");
        function.setCode(39);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("2");
        primaryNumber.setCode(50);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key14
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("[");
        primarySymbol.setCode(91);
        primarySymbol.setShifted(false);
        primaryLetter = new KeyValue();
        primaryLetter.setName("S");
        primaryLetter.setCode(83);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("]");
        secondarySymbol.setCode(93);
        secondarySymbol.setShifted(false);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("G");
        secondaryLetter.setCode(71);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F1");
        secondaryNumber.setCode(112);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        function = new KeyValue();
        function.setName("↓");
        function.setCode(40);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("1");
        primaryNumber.setCode(49);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        //key15
        normalKeyData = new NormalKeyData();
        primarySymbol = new KeyValue();
        primarySymbol.setName("{");
        primarySymbol.setCode(91);
        primarySymbol.setShifted(true);
        primaryLetter = new KeyValue();
        primaryLetter.setName("Y");
        primaryLetter.setCode(89);
        primaryLetter.setShifted(false);
        secondarySymbol = new KeyValue();
        secondarySymbol.setName("}");
        secondarySymbol.setCode(93);
        secondarySymbol.setShifted(true);
        secondaryLetter = new KeyValue();
        secondaryLetter.setName("X");
        secondaryLetter.setCode(88);
        secondaryLetter.setShifted(false);
        secondaryNumber = new KeyValue();
        secondaryNumber.setName("F10");
        secondaryNumber.setCode(121);
        secondaryNumber.setShifted(false);
        special = new KeyValue();
        function = new KeyValue();
        function.setName("→");
        function.setCode(39);
        function.setShifted(false);
        primaryNumber = new KeyValue();
        primaryNumber.setName("0");
        primaryNumber.setCode(48);
        primaryNumber.setShifted(false);
        normalKeyData.setPrimarySymbol(primarySymbol);
        normalKeyData.setPrimaryLetter(primaryLetter);
        normalKeyData.setSecondarySymbol(secondarySymbol);
        normalKeyData.setSecondaryLetter(secondaryLetter);

        normalKeyData.setFunction(function);
        normalKeyData.setSpecial(special);
        normalKeyData.setPrimaryNubmer(primaryNumber);
        normalKeyData.setSecondaryNubmer(secondaryNumber);
        normalKeyDatas.add(normalKeyData);

        mFrogPadData.setNormalKeyDatas(normalKeyDatas);

        		//ObjectMapper objectMapper = new ObjectMapper();
        //
        //		String str = "";
        //		try {
        //			str = objectMapper.writeValueAsString(mFrogPadData);
        //		} catch (JsonGenerationException e) {
        //			e.printStackTrace();
        //		} catch (JsonMappingException e) {
        //			e.printStackTrace();
        //		} catch (IOException e) {
        //			e.printStackTrace();
        //		}
        //
        ////		Log.d(TAG, str);
        //		FrogPadData frogPadData2 = null;
        //		List<NormalKeyData> normalKeyDatas2 = null;
        //		try {
        //			frogPadData2 = objectMapper.readValue(str, new TypeReference<FrogPadData>() {});
        //		} catch (JsonParseException e) {
        //			Log.e(TAG, "", e);
        //		} catch (JsonMappingException e) {
        //			Log.e(TAG, "", e);
        //		} catch (IOException e) {
        //			Log.e(TAG, "", e);
        //		}
        //		Log.d(TAG, frogPadData2.toString());
    }
}
