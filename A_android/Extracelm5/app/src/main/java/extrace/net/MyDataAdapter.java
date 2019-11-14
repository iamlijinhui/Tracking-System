package extrace.net;

public interface MyDataAdapter<T> extends IDataAdapter<T>{

	public void packDataSetChanged();
	public void unPackDataSetChanged();	
}
