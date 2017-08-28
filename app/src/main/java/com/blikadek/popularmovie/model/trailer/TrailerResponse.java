package com.blikadek.popularmovie.model.trailer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TrailerResponse implements Parcelable {
	private int id;
	private List<TrailerItem> results;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setResults(List<TrailerItem> results){
		this.results = results;
	}

	public List<TrailerItem> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"TrailerResponse{" +
			"id = '" + id + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeList(this.results);
	}

	public TrailerResponse() {
	}

	protected TrailerResponse(Parcel in) {
		this.id = in.readInt();
		this.results = new ArrayList<TrailerItem>();
		in.readList(this.results, TrailerItem.class.getClassLoader());
	}

	public static final Parcelable.Creator<TrailerResponse> CREATOR = new Parcelable.Creator<TrailerResponse>() {
		@Override
		public TrailerResponse createFromParcel(Parcel source) {
			return new TrailerResponse(source);
		}

		@Override
		public TrailerResponse[] newArray(int size) {
			return new TrailerResponse[size];
		}
	};
}