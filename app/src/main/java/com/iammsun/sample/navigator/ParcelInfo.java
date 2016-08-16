package com.iammsun.sample.navigator;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelInfo implements Parcelable {

    public String outerId;
    public long skuId;
    public long numIid;
    public long quantity;
    public String propertiesName;

    public ParcelInfo(Parcel in) {
        outerId = in.readString();
        skuId = in.readLong();
        numIid = in.readLong();
        quantity = in.readLong();
        propertiesName = in.readString();
    }

    public ParcelInfo() {
    }

    public static final Creator<ParcelInfo> CREATOR = new Creator<ParcelInfo>() {
        @Override
        public ParcelInfo createFromParcel(Parcel in) {
            return new ParcelInfo(in);
        }

        @Override
        public ParcelInfo[] newArray(int size) {
            return new ParcelInfo[size];
        }
    };


    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public void setNumIid(long numIid) {
        this.numIid = numIid;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(outerId);
        dest.writeLong(skuId);
        dest.writeLong(numIid);
        dest.writeLong(quantity);
        dest.writeString(propertiesName);
    }

    @Override
    public String toString() {
        return String.format("outerId: %s, skuId: %d, numIid: %d, quantity: %d, propertiesName: " +
                "%s", outerId, skuId, numIid, quantity, propertiesName);
    }
}