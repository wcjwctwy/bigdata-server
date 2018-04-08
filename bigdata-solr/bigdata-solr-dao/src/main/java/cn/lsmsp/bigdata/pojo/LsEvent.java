package cn.lsmsp.bigdata.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@SolrDocument(solrCoreName = "eventlog-collection")
public class LsEvent {
    @Id
    private String id;
    @Field("querytype")
    private Byte queryType;
    @Field("eventtype")
    private Byte eventType;
    @Field("isanalyzerevent")
    private Byte isAnalyzerEvent;
    @Field("devicezone")
    private String deviceZone;
    @Field("deviceproduct")
    private String deviceProduct;
    @Field("eventstarttime")
    private String eventStartTime;
    @Field("eventendtime")
    private String eventEndTime;
    @Field("eventname")
    private String eventName;
    @Field("eventlevel")
    private String eventLevel;
    @Field("devicereceipttime")
    private String deviceReceiptTime;
    @Field("srcaddress")
    private String srcAddress;
    @Field("taraddress")
    private String tarAddress;
    @Field("devicehostname")
    private String deviceHostName;
    @Field("cusid")
    private Long cusId;
    @Field("categorydevice")
    private String categoryDevice;
    @Field("deviceaddress")
    private String deviceAddress;
    @Field("entid")
    private Long entId;
    @Field("sid")
    private String sid;
    @Field()
    private String sids;
    @Field("eventcategorytechnique")
    private String eventCategoryTechnique;
    @Field("eventcategory")
    private String eventCategory;
    @Field("rawevent")
    private String rawevent;
    @Field("tarport")
    private String tarPort;

    public String getTarPort() {
        return tarPort;
    }

    public void setTarPort(String tarPort) {
        this.tarPort = tarPort;
    }

    public String getRawevent() {
        return rawevent;
    }

    public void setRawevent(String rawevent) {
        this.rawevent = rawevent;
    }

    public LsEvent() {
    }

    public String getEventCategoryTechnique() {
        return eventCategoryTechnique;
    }

    public void setEventCategoryTechnique(String eventCategoryTechnique) {
        this.eventCategoryTechnique = eventCategoryTechnique;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLevel() {
        return this.eventLevel;
    }

    public void setEventLevel(String eventLevel) {
        this.eventLevel = eventLevel;
    }

    public Byte getEventType() {
        return this.eventType;
    }

    public Byte getQueryType() {
        return this.queryType;
    }

    public void setQueryType(Byte queryType) {
        this.queryType = queryType;
    }

    public void setEventType(Byte eventType) {
        this.eventType = eventType;
    }

    public Byte getIsAnalyzerEvent() {
        return this.isAnalyzerEvent;
    }

    public void setIsAnalyzerEvent(Byte isAnalyzerEvent) {
        this.isAnalyzerEvent = isAnalyzerEvent;
    }

    public String getDeviceAddress() {
        return this.deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getTarAddress() {
        return this.tarAddress;
    }

    public void setTarAddress(String tarAddress) {
        this.tarAddress = tarAddress;
    }

    public String getSrcAddress() {
        return this.srcAddress;
    }

    public void setSrcAddress(String srcAddress) {
        this.srcAddress = srcAddress;
    }

    public String getDeviceReceiptTime() {
        return this.deviceReceiptTime;
    }

    public void setDeviceReceiptTime(String deviceReceiptTime) {
        this.deviceReceiptTime = deviceReceiptTime;
    }

    public String getDeviceZone() {
        return this.deviceZone;
    }

    public void setDeviceZone(String deviceZone) {
        this.deviceZone = deviceZone;
    }

    public String getDeviceProduct() {
        return this.deviceProduct;
    }

    public void setDeviceProduct(String deviceProduct) {
        this.deviceProduct = deviceProduct;
    }

    public String getEventStartTime() {
        return this.eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventEndTime() {
        return this.eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public Long getEntId() {
        return this.entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSids() {
        return this.sids;
    }

    public void setSids(String sids) {
        this.sids = sids;
    }

    public String getDeviceHostName() {
        return this.deviceHostName;
    }

    public void setDeviceHostName(String deviceHostName) {
        this.deviceHostName = deviceHostName;
    }

    public Long getCusId() {
        return this.cusId;
    }

    public void setCusId(Long cusId) {
        this.cusId = cusId;
    }

    public String getCategoryDevice() {
        return this.categoryDevice;
    }

    public void setCategoryDevice(String categoryDevice) {
        this.categoryDevice = categoryDevice;
    }
}
