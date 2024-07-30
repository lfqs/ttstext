package com.lfq.tts.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 lfq
 * @DATE 2024-07-22
 * current year
 **/
@Slf4j
public class Run {
    /**【语音播报方法】**/
    public static boolean speakingText(String readText){
        boolean isFinish = true;
        ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
        try {
            sap.setProperty("Volume",new Variant(100));              // 音量 0-100
            sap.setProperty("Rate",new Variant(-1));                 // 语音朗读速度 -10 到 +10
            Dispatch sapo = sap.getObject();                         // 获取执行对象
            Dispatch.call(sapo,"Speak",new Variant(readText));    	// 执行朗读
            sapo.safeRelease();                                     // 关闭执行对象
        }catch (Exception e){
            isFinish = false;
            e.printStackTrace();
        }finally {
            sap.safeRelease();                                      // 关闭执行对象
        }
        return isFinish;
    }

    public static void main(String[] args) {
//        Run.speakingText("夏日的天空，总是那么湛蓝深邃，几朵白云悠闲地游走其间，宛如大海中的白帆，给这无边的蓝添上了几分灵动与遐想。午后，阳光变得格外强烈，连空气中都弥漫着一种温暖而慵懒的气息。这时，若能在树荫下小憩片刻，或是手捧一杯冰凉的饮料，坐在窗前静静地看着窗外的世界，那份惬意与宁静，便是夏天给予我们的最好礼物。");
    }

}

