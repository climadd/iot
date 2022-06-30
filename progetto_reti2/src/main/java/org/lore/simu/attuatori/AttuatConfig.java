package org.lore.simu.attuatori;

public class AttuatConfig {

        //UMI ILL TEM

    private String nameID;

    private int valueUmi;
    private boolean stateUmi;
    private AttuatLevel lvlUmi;
    private AttuatMode modeUmi;

    private int valueIll;
    private boolean stateIll;
    private AttuatLevel lvlIll;
    private AttuatMode modeIll;

    private float Tem;
    private boolean stateTem;
    private AttuatLevel lvlTem;
    private AttuatMode modeTem;

    public AttuatConfig() {
    }

    public AttuatConfig(String nameID, int valueUmi, boolean stateUmi, AttuatLevel lvlUmi, AttuatMode modeUmi, int valueIll, boolean stateIll, AttuatLevel lvlIll, AttuatMode modeIll, float tem, boolean stateTem, AttuatLevel lvlTem, AttuatMode modeTem) {
        this.nameID = nameID;
        this.valueUmi = valueUmi;
        this.stateUmi = stateUmi;
        this.lvlUmi = lvlUmi;
        this.modeUmi = modeUmi;
        this.valueIll = valueIll;
        this.stateIll = stateIll;
        this.lvlIll = lvlIll;
        this.modeIll = modeIll;
        Tem = tem;
        this.stateTem = stateTem;
        this.lvlTem = lvlTem;
        this.modeTem = modeTem;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public int getValueUmi() {
        return valueUmi;
    }

    public void setValueUmi(int valueUmi) {
        this.valueUmi = valueUmi;
    }

    public boolean isStateUmi() {
        return stateUmi;
    }

    public void setStateUmi(boolean stateUmi) {
        this.stateUmi = stateUmi;
    }

    public AttuatLevel getLvlUmi() {
        return lvlUmi;
    }

    public void setLvlUmi(AttuatLevel lvlUmi) {
        this.lvlUmi = lvlUmi;
    }

    public AttuatMode getModeUmi() {
        return modeUmi;
    }

    public void setModeUmi(AttuatMode modeUmi) {
        this.modeUmi = modeUmi;
    }

    public int getValueIll() {
        return valueIll;
    }

    public void setValueIll(int valueIll) {
        this.valueIll = valueIll;
    }

    public boolean isStateIll() {
        return stateIll;
    }

    public void setStateIll(boolean stateIll) {
        this.stateIll = stateIll;
    }

    public AttuatLevel getLvlIll() {
        return lvlIll;
    }

    public void setLvlIll(AttuatLevel lvlIll) {
        this.lvlIll = lvlIll;
    }

    public AttuatMode getModeIll() {
        return modeIll;
    }

    public void setModeIll(AttuatMode modeIll) {
        this.modeIll = modeIll;
    }

    public float getTem() {
        return Tem;
    }

    public void setTem(float tem) {
        Tem = tem;
    }

    public boolean isStateTem() {
        return stateTem;
    }

    public void setStateTem(boolean stateTem) {
        this.stateTem = stateTem;
    }

    public AttuatLevel getLvlTem() {
        return lvlTem;
    }

    public void setLvlTem(AttuatLevel lvlTem) {
        this.lvlTem = lvlTem;
    }

    public AttuatMode getModeTem() {
        return modeTem;
    }

    public void setModeTem(AttuatMode modeTem) {
        this.modeTem = modeTem;
    }
}
