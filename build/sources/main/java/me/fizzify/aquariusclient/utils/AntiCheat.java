package me.fizzify.aquariusclient.utils;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;

public class AntiCheat
{

    /**
    * @author quickdaffy
     */
    public static void deleteSigma()
    {
        File sigmaDataDir = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/sigma");
        File sigmaVerDir = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/versions/Sigma");
        File sigmaNewVerDir = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/versions/Sigma5");
        try
        {
            FileUtils.deleteDirectory(sigmaDataDir);
            FileUtils.deleteDirectory(sigmaVerDir);
            FileUtils.deleteDirectory(sigmaNewVerDir);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Delete Failed!");
        }
    }

    public static void deleteZeroDay()
    {
        File zeroDayData = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/ZeroDay");
        File zeroDayVer = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/versions/ZeroDay");
        try
        {
            FileUtils.deleteDirectory(zeroDayData);
            FileUtils.deleteDirectory(zeroDayVer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteShitSkid()
    {
        File skidData = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/Melon Client");
        File skidVer = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/versions/Melon Client");
        try
        {
            FileUtils.deleteDirectory(skidData);
            FileUtils.deleteDirectory(skidVer);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void deleteShitSkid2()
    {
        File skidData = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/Pluto Client");
        File skidVer = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/versions/Pluto");
        try
        {
            FileUtils.deleteDirectory(skidData);
            FileUtils.deleteDirectory(skidVer);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void deleteShitSkid3()
    {
        File skidData = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/Ivy Client");
        File skidVer = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/versions/Ivy Client");
        try
        {
            FileUtils.deleteDirectory(skidData);
            FileUtils.deleteDirectory(skidVer);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void deleteShitSkid4()
    {
        File skidData = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/River Client");
        File skidVer = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/versions/River Client");
        try
        {
            FileUtils.deleteDirectory(skidData);
            FileUtils.deleteDirectory(skidVer);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void removeCheats()
    {
        deleteSigma();
        deleteZeroDay();
        deleteShitSkid();
        deleteShitSkid2();
        deleteShitSkid3();
        deleteShitSkid4();
        System.out.println("Deleting Cheats!");
    }

}
