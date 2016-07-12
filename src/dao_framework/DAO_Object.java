package dao_framework;

public abstract class DAO_Object {

	private Long id;
	
	public DAO_Object(Long p_id){
		set_id(p_id);
	}
	
	public long get_id(){
		return id;
	}
	public void set_id(long p_id){
		this.id = p_id;
	}
}
