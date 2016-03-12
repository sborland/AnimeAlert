package com.finalapp.teamhls.animealert.response;

/**
 * Created by HuiShi on 3/11/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("MALID")
    @Expose
    private String MALID;
    @SerializedName("ANNID")
    @Expose
    private String ANNID;
    @SerializedName("isSequel")
    @Expose
    private Boolean isSequel;
    @SerializedName("airdate")
    @Expose
    private String airdate;
    @SerializedName("simulcast")
    @Expose
    private String simulcast;
    @SerializedName("simulcast_delay")
    @Expose
    private String simulcastDelay;
    @SerializedName("origseasonkey")
    @Expose
    private String origseasonkey;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name_lower")
    @Expose
    private String nameLower;
    @SerializedName("missingAirdate")
    @Expose
    private Boolean missingAirdate;
    @SerializedName("missingAirtime")
    @Expose
    private Boolean missingAirtime;
    @SerializedName("isShort")
    @Expose
    private Boolean isShort;
    @SerializedName("notes")
    @Expose
    private Boolean notes;
    @SerializedName("commentary")
    @Expose
    private Boolean commentary;
    @SerializedName("synopsis_override")
    @Expose
    private Boolean synopsisOverride;
    @SerializedName("quip")
    @Expose
    private Boolean quip;
    @SerializedName("simulcast_delay_orig")
    @Expose
    private Double simulcastDelayOrig;
    @SerializedName("simulcast_link")
    @Expose
    private Boolean simulcastLink;
    @SerializedName("simulcast_invalid")
    @Expose
    private Boolean simulcastInvalid;
    @SerializedName("fansub")
    @Expose
    private Boolean fansub;
    @SerializedName("fansub_link")
    @Expose
    private Boolean fansubLink;
    @SerializedName("other_link")
    @Expose
    private Boolean otherLink;
    @SerializedName("other_link_text")
    @Expose
    private Boolean otherLinkText;
    @SerializedName("hasTranslation")
    @Expose
    private Boolean hasTranslation;
    @SerializedName("simulcastClass")
    @Expose
    private String simulcastClass;
    @SerializedName("fansubClass")
    @Expose
    private String fansubClass;
    @SerializedName("simulcast_delay_rd")
    @Expose
    private String simulcastDelayRd;
    @SerializedName("simulcast_delay_type")
    @Expose
    private String simulcastDelayType;
    @SerializedName("airdate_orig")
    @Expose
    private String airdateOrig;
    @SerializedName("airdate_u")
    @Expose
    private Integer airdateU;
    @SerializedName("airdates")
    @Expose
    private Airdates airdates;
    @SerializedName("isSimulcastAired")
    @Expose
    private Boolean isSimulcastAired;
    @SerializedName("simulcast_timeToAir_u")
    @Expose
    private Integer simulcastTimeToAirU;
    @SerializedName("simulcast_timeToAir_rd")
    @Expose
    private String simulcastTimeToAirRd;

    /**
     *
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The MALID
     */
    public String getMALID() {
        return MALID;
    }

    /**
     *
     * @param MALID
     *     The MALID
     */
    public void setMALID(String MALID) {
        this.MALID = MALID;
    }

    /**
     *
     * @return
     *     The ANNID
     */
    public String getANNID() {
        return ANNID;
    }

    /**
     *
     * @param ANNID
     *     The ANNID
     */
    public void setANNID(String ANNID) {
        this.ANNID = ANNID;
    }

    /**
     *
     * @return
     *     The isSequel
     */
    public Boolean getIsSequel() {
        return isSequel;
    }

    /**
     *
     * @param isSequel
     *     The isSequel
     */
    public void setIsSequel(Boolean isSequel) {
        this.isSequel = isSequel;
    }

    /**
     *
     * @return
     *     The airdate
     */
    public String getAirdate() {
        return airdate;
    }

    /**
     *
     * @param airdate
     *     The airdate
     */
    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    /**
     *
     * @return
     *     The simulcast
     */
    public String getSimulcast() {
        return simulcast;
    }

    /**
     *
     * @param simulcast
     *     The simulcast
     */
    public void setSimulcast(String simulcast) {
        this.simulcast = simulcast;
    }

    /**
     *
     * @return
     *     The simulcastDelay
     */
    public String getSimulcastDelay() {
        return simulcastDelay;
    }

    /**
     *
     * @param simulcastDelay
     *     The simulcast_delay
     */
    public void setSimulcastDelay(String simulcastDelay) {
        this.simulcastDelay = simulcastDelay;

    }

    /**
     *
     * @return
     *     The origseasonkey
     */
    public String getOrigseasonkey() {
        return origseasonkey;
    }

    /**
     *
     * @param origseasonkey
     *     The origseasonkey
     */
    public void setOrigseasonkey(String origseasonkey) {
        this.origseasonkey = origseasonkey;
    }

    /**
     *
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     *     The nameLower
     */
    public String getNameLower() {
        return nameLower;
    }

    /**
     *
     * @param nameLower
     *     The name_lower
     */
    public void setNameLower(String nameLower) {
        this.nameLower = nameLower;
    }

    /**
     *
     * @return
     *     The missingAirdate
     */
    public Boolean getMissingAirdate() {
        return missingAirdate;
    }

    /**
     *
     * @param missingAirdate
     *     The missingAirdate
     */
    public void setMissingAirdate(Boolean missingAirdate) {
        this.missingAirdate = missingAirdate;
    }

    /**
     *
     * @return
     *     The missingAirtime
     */
    public Boolean getMissingAirtime() {
        return missingAirtime;
    }

    /**
     *
     * @param missingAirtime
     *     The missingAirtime
     */
    public void setMissingAirtime(Boolean missingAirtime) {
        this.missingAirtime = missingAirtime;
    }

    /**
     *
     * @return
     *     The isShort
     */
    public Boolean getIsShort() {
        return isShort;
    }

    /**
     *
     * @param isShort
     *     The isShort
     */
    public void setIsShort(Boolean isShort) {
        this.isShort = isShort;
    }

    /**
     *
     * @return
     *     The notes
     */
    public Boolean getNotes() {
        return notes;
    }

    /**
     *
     * @param notes
     *     The notes
     */
    public void setNotes(Boolean notes) {
        this.notes = notes;
    }

    /**
     *
     * @return
     *     The commentary
     */
    public Boolean getCommentary() {
        return commentary;
    }

    /**
     *
     * @param commentary
     *     The commentary
     */
    public void setCommentary(Boolean commentary) {
        this.commentary = commentary;
    }

    /**
     *
     * @return
     *     The synopsisOverride
     */
    public Boolean getSynopsisOverride() {
        return synopsisOverride;
    }

    /**
     *
     * @param synopsisOverride
     *     The synopsis_override
     */
    public void setSynopsisOverride(Boolean synopsisOverride) {
        this.synopsisOverride = synopsisOverride;
    }

    /**
     *
     * @return
     *     The quip
     */
    public Boolean getQuip() {
        return quip;
    }

    /**
     *
     * @param quip
     *     The quip
     */
    public void setQuip(Boolean quip) {
        this.quip = quip;
    }

    /**
     *
     * @return
     *     The simulcastDelayOrig
     */
    public Double getSimulcastDelayOrig() {
        return simulcastDelayOrig;
    }

    /**
     *
     * @param simulcastDelayOrig
     *     The simulcast_delay_orig
     */
    public void setSimulcastDelayOrig(Double simulcastDelayOrig) {
        this.simulcastDelayOrig = simulcastDelayOrig;
    }

    /**
     *
     * @return
     *     The simulcastLink
     */
    public Boolean getSimulcastLink() {
        return simulcastLink;
    }

    /**
     *
     * @param simulcastLink
     *     The simulcast_link
     */
    public void setSimulcastLink(Boolean simulcastLink) {
        this.simulcastLink = simulcastLink;
    }

    /**
     *
     * @return
     *     The simulcastInvalid
     */
    public Boolean getSimulcastInvalid() {
        return simulcastInvalid;
    }

    /**
     *
     * @param simulcastInvalid
     *     The simulcast_invalid
     */
    public void setSimulcastInvalid(Boolean simulcastInvalid) {
        this.simulcastInvalid = simulcastInvalid;
    }

    /**
     *
     * @return
     *     The fansub
     */
    public Boolean getFansub() {
        return fansub;
    }

    /**
     *
     * @param fansub
     *     The fansub
     */
    public void setFansub(Boolean fansub) {
        this.fansub = fansub;
    }

    /**
     *
     * @return
     *     The fansubLink
     */
    public Boolean getFansubLink() {
        return fansubLink;
    }

    /**
     *
     * @param fansubLink
     *     The fansub_link
     */
    public void setFansubLink(Boolean fansubLink) {
        this.fansubLink = fansubLink;
    }

    /**
     *
     * @return
     *     The otherLink
     */
    public Boolean getOtherLink() {
        return otherLink;
    }

    /**
     *
     * @param otherLink
     *     The other_link
     */
    public void setOtherLink(Boolean otherLink) {
        this.otherLink = otherLink;
    }

    /**
     *
     * @return
     *     The otherLinkText
     */
    public Boolean getOtherLinkText() {
        return otherLinkText;
    }

    /**
     *
     * @param otherLinkText
     *     The other_link_text
     */
    public void setOtherLinkText(Boolean otherLinkText) {
        this.otherLinkText = otherLinkText;
    }

    /**
     *
     * @return
     *     The hasTranslation
     */
    public Boolean getHasTranslation() {
        return hasTranslation;
    }

    /**
     *
     * @param hasTranslation
     *     The hasTranslation
     */
    public void setHasTranslation(Boolean hasTranslation) {
        this.hasTranslation = hasTranslation;
    }

    /**
     *
     * @return
     *     The simulcastClass
     */
    public String getSimulcastClass() {
        return simulcastClass;
    }

    /**
     *
     * @param simulcastClass
     *     The simulcastClass
     */
    public void setSimulcastClass(String simulcastClass) {
        this.simulcastClass = simulcastClass;
    }

    /**
     *
     * @return
     *     The fansubClass
     */
    public String getFansubClass() {
        return fansubClass;
    }

    /**
     *
     * @param fansubClass
     *     The fansubClass
     */
    public void setFansubClass(String fansubClass) {
        this.fansubClass = fansubClass;
    }

    /**
     *
     * @return
     *     The simulcastDelayRd
     */
    public String getSimulcastDelayRd() {
        return simulcastDelayRd;
    }

    /**
     *
     * @param simulcastDelayRd
     *     The simulcast_delay_rd
     */
    public void setSimulcastDelayRd(String simulcastDelayRd) {
        this.simulcastDelayRd = simulcastDelayRd;
    }

    /**
     *
     * @return
     *     The simulcastDelayType
     */
    public String getSimulcastDelayType() {
        return simulcastDelayType;
    }

    /**
     *
     * @param simulcastDelayType
     *     The simulcast_delay_type
     */
    public void setSimulcastDelayType(String simulcastDelayType) {
        this.simulcastDelayType = simulcastDelayType;
    }

    /**
     *
     * @return
     *     The airdateOrig
     */
    public String getAirdateOrig() {
        return airdateOrig;
    }

    /**
     *
     * @param airdateOrig
     *     The airdate_orig
     */
    public void setAirdateOrig(String airdateOrig) {
        this.airdateOrig = airdateOrig;
    }

    /**
     *
     * @return
     *     The airdateU
     */
    public Integer getAirdateU() {
        return airdateU;
    }

    /**
     *
     * @param airdateU
     *     The airdate_u
     */
    public void setAirdateU(Integer airdateU) {
        this.airdateU = airdateU;
    }

    /**
     *
     * @return
     *     The airdates
     */
    public Airdates getAirdates() {
        return airdates;
    }

    /**
     *
     * @param airdates
     *     The airdates
     */
    public void setAirdates(Airdates airdates) {
        this.airdates = airdates;
    }

    /**
     *
     * @return
     *     The isSimulcastAired
     */
    public Boolean getIsSimulcastAired() {
        return isSimulcastAired;
    }

    /**
     *
     * @param isSimulcastAired
     *     The isSimulcastAired
     */
    public void setIsSimulcastAired(Boolean isSimulcastAired) {
        this.isSimulcastAired = isSimulcastAired;
    }

    /**
     *
     * @return
     *     The simulcastTimeToAirU
     */
    public Integer getSimulcastTimeToAirU() {
        return simulcastTimeToAirU;
    }

    /**
     *
     * @param simulcastTimeToAirU
     *     The simulcast_timeToAir_u
     */
    public void setSimulcastTimeToAirU(Integer simulcastTimeToAirU) {
        this.simulcastTimeToAirU = simulcastTimeToAirU;
    }

    /**
     *
     * @return
     *     The simulcastTimeToAirRd
     */
    public String getSimulcastTimeToAirRd() {
        return simulcastTimeToAirRd;
    }

    /**
     *
     * @param simulcastTimeToAirRd
     *     The simulcast_timeToAir_rd
     */
    public void setSimulcastTimeToAirRd(String simulcastTimeToAirRd) {
        this.simulcastTimeToAirRd = simulcastTimeToAirRd;
    }

}
